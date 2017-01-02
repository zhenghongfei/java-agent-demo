package com.zhenghongfei.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.List;

public class JavaAgent {

	public static void premain(String agentOps, Instrumentation inst) {
		System.out.println("=========Java Agent premain 方法被执行========");

		PremainTimeTransformer transformer = new PremainTimeTransformer();
		inst.addTransformer(transformer, true);
	}

	public static void agentmain(String args, Instrumentation inst) {
		System.out.println("=========Java Dynamic Agent agentmain 方法被执行========");

		List<Class<?>> modifiedClasses = new ArrayList<Class<?>>();
		for (Class<?> clazz : inst.getAllLoadedClasses()) {
			if (clazz.getName().matches("com.zhenghongfei.HelloBean")) {
				modifiedClasses.add(clazz);
			}
		}

		AgentmainTimeTransformer transformer = new AgentmainTimeTransformer();
		inst.addTransformer(transformer, true);

		try {
			inst.retransformClasses(modifiedClasses.toArray(new Class[modifiedClasses.size()]));
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		} finally {
			inst.removeTransformer(transformer);
		}
	}
}