package com.zhenghongfei;

public class JavaAgentServer {
	public static void main(String[] args) {
		System.out.println("===========JavaAgentTestApp=============");
		while (true) {
			HelloBean hello = new HelloBean();
			hello.hello();
		}
	}
}
