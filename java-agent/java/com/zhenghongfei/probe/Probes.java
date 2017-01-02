package com.zhenghongfei.probe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Probes {
	private static final Map<Integer, Probe> probeMap = new HashMap<>();

	private static AtomicInteger seq = new AtomicInteger(1);

	public static void onBefore(int id) {
		Probe probe = probeMap.get(id);
		if (probe != null) {
			probe.onBefore();
		}
	}

	public static void onSuccess(int id) {
		Probe probe = probeMap.get(id);
		if (probe != null) {
			probe.onSuccess();
		}
	}

	public static int createId() {
		int id = seq.getAndIncrement();
		probeMap.put(id, new Probe());
		return id;
	}
}