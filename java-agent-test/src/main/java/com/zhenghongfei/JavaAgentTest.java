package com.zhenghongfei;

public class JavaAgentTest {
	public static void main(String[] args) {
		System.out.println("===========main方法被执行=============");
		HelloBean hello = new HelloBean();
		hello.sayHello();
		hello.sayHello2("hello world222222222");
	}
}
