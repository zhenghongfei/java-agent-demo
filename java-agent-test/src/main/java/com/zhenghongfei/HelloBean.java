package com.zhenghongfei;

import java.util.concurrent.TimeUnit;

public class HelloBean {
	public void hello() {
		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("HelloBean.hello() -> sleep 5 seconds");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}