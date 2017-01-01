package com.zhenghongfei.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.List;

public class JavaAgent {

	public static void premain(String args, Instrumentation inst) {
		main(args, inst);
	}

	public static void agentmain(String args, Instrumentation inst) {
		main(args, inst);
	}

	private static void main(String agentOps, Instrumentation inst) {
		System.out.println("=========Java Agent 方法被执行========");
		System.out.println(agentOps);

		List<Class<?>> modifiedClasses = new ArrayList<Class<?>>();
		for (Class<?> clazz : inst.getAllLoadedClasses()) {
			if (clazz.getName().matches("com.zhenghongfei.HelloBean")) {
				modifiedClasses.add(clazz);
			}
		}
		// 添加Transformer
		TimeTransformer transformer = new TimeTransformer();
		inst.addTransformer(transformer, true);
		try {
			inst.retransformClasses(modifiedClasses.toArray(new Class[modifiedClasses.size()]));
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
	}
}
