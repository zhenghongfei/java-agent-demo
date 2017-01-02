package com.zhenghongfei.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.List;

public class JavaAgent {

	public static final String CLASS_NAME = "com.zhenghongfei.HelloBean";

	public static final String METHOD_NAME = "hello";

	public static final String METHOD_NAME_PROXY = METHOD_NAME + "$proxy";

	public static void premain(String agentOps, Instrumentation inst) {
		System.out.println("	Java Agent with Premain		");

		PremainTimeTransformer transformer = new PremainTimeTransformer();
		inst.addTransformer(transformer, true);
	}

	public static void agentmain(String args, Instrumentation inst) {
		System.out.println("	Java Agent with Agentmain	");

		List<Class<?>> modifiedClasses = new ArrayList<Class<?>>();
		for (Class<?> clazz : inst.getAllLoadedClasses()) {
			if (clazz.getName().matches(CLASS_NAME)) {
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