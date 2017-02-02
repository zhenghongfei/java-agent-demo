package com.zhenghongfei.probe;

public class Probe {
	private long startTime = 0L;

	public void onBefore() {
		startTime = System.currentTimeMillis();
	}

	public void onSuccess() {
		long endTime = System.currentTimeMillis();
		System.out.println("The Method Used Time Is:" + (endTime - startTime) + "ms.");
	}
}