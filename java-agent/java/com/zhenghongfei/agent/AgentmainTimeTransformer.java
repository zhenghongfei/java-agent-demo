package com.zhenghongfei.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import com.zhenghongfei.probe.Probes;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

/**
 * 通过attach方式动态加载agent方式不允许修改方法体
 */
public class AgentmainTimeTransformer implements ClassFileTransformer {
	private int id;

	public AgentmainTimeTransformer() {
		id = Probes.createId();
	}

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
			cm.insertBefore(String.format("com.zhenghongfei.probe.Probes.onBefore(%s);", id));
			cm.insertAfter(String.format("com.zhenghongfei.probe.Probes.onSuccess(%s);", id));
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
}