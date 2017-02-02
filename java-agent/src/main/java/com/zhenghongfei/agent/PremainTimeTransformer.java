package com.zhenghongfei.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;

/**
 * 通过启动加载agent方式可以为类添加方法等操作
 */
public class PremainTimeTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		className = className.replace("/", ".");
		if (!className.matches(JavaAgent.CLASS_NAME)) {
			return null;
		}

		CtClass cc = null;
		byte[] datas = null;

		try {

			final ClassPool cp = new ClassPool(null);
			cp.insertClassPath(new LoaderClassPath(loader));

			cc = cp.get(className);
			cc.defrost();

			CtMethod cm = cc.getDeclaredMethod(JavaAgent.METHOD_NAME);
			cm.setName(JavaAgent.METHOD_NAME_PROXY);

			CtMethod newMethod = CtNewMethod.copy(cm, JavaAgent.METHOD_NAME, cc, null);
			newMethod.setBody(buildMethodBody());

			cc.addMethod(newMethod);

			datas = cc.toBytecode();

		} catch (Exception e) {
			e.printStackTrace();
			datas = null;
		} finally {
			if (null != cc) {
				cc.freeze();
			}
		}
		return datas;
	}

	private String buildMethodBody() {
		StringBuilder methodBody = new StringBuilder();
		methodBody.append("{");
		methodBody.append("long startTime = System.currentTimeMillis();");
		methodBody.append(JavaAgent.METHOD_NAME_PROXY + "($$);");
		methodBody.append("long endTime = System.currentTimeMillis();");
		methodBody.append("System.out.println(\"The Method Used Time Is:\"+(endTime-startTime)+\"ms\");");
		methodBody.append("}");
		return methodBody.toString();
	}
}