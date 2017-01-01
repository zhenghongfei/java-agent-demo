package com.zhenghongfei;

public class HelloBean {
	public void sayHello() {
		try {
			Thread.sleep(2000);
			System.out.println("hello world!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sayHello2(String hello) {
		try {
			Thread.sleep(1000);
			System.out.println(hello);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}