package com.zhenghongfei;

public class JavaDynamicAgentTest {
	public static void main(String[] args) {
		System.out.println("===========main方法被执行=============");
		while (true) {
			HelloBean hello = new HelloBean();
			hello.sayHello();
			hello.sayHello2("hello world222222222");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
