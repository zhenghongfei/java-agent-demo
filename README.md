# java-agent-demo

# 编译工程
+ cd java-agent-demo
+ mvn package

# 动态加载代理方式(require jdk 6+)
1.运行java-dynamic-agent-test.bat，相当于运行应用程序

	HelloBean.hello() -> sleep 5 seconds
	HelloBean.hello() -> sleep 5 seconds
	HelloBean.hello() -> sleep 5 seconds

2.jps查询进程id

	3300 Jps
	7032 java-agent-test-0.1-jar-with-dependencies.jar
	7196

3.运行java-agent-client.bat 7032


4.查看运行程序已经加载代理并生效

	HelloBean.hello() -> sleep 5 seconds
	HelloBean.hello() -> sleep 5 seconds
	The Method Used Time Is:5001ms.
	HelloBean.hello() -> sleep 5 seconds
	The Method Used Time Is:5000ms.

# 启动加载代理方式(require jdk 5+)
运行java-agent-server.bat