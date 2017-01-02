package com.zhenghongfei.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class TimeTransformer implements ClassFileTransformer {

	final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
	final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

	String[] methods = new String[] { "sayHello", "sayHello2" };

	public TimeTransformer() {
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		className = className.replace("/", ".");
		if (!className.matches("com.zhenghongfei.HelloBean")) {
			return null;
		}
		try {
			ClassPool classPool = ClassPool.getDefault();
			CtClass ctclass = classPool.get(className);
			for (String methodName : methods) {

				String outputStr = "\nSystem.out.println(\"this method " + methodName
						+ " cost:\" +(endTime - startTime) +\"ms.\");";

				CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);// 得到这方法实例
				String newMethodName = methodName + "$old";// 新定义一个方法叫做比如sayHello$old
				ctmethod.setName(newMethodName);// 将原来的方法名字修改

				// 创建新的方法，复制原来的方法，名字为原来的名字
				CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

				// 构建新的方法体
				StringBuilder bodyStr = new StringBuilder();
				bodyStr.append("{");
				bodyStr.append(prefix);
				bodyStr.append(newMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
				bodyStr.append(postfix);
				bodyStr.append(outputStr);
				bodyStr.append("}");

				newMethod.setBody(bodyStr.toString());// 替换新方法
				ctclass.addMethod(newMethod);// 增加新方法
			}
			return ctclass.toBytecode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}