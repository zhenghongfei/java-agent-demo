# java-agent-demo

# 编译工程
+ cd java-agent-demo
+ mvn package

# 动态加载代理方式(require jdk 6+)
## 启动应用程序
1. cd java-agent-test
2. 运行  java-dynamic-agent-test.bat 

***

	JavaAgentTestApp
	HelloBean.hello() -> sleep 5 seconds
	HelloBean.hello() -> sleep 5 seconds
	HelloBean.hello() -> sleep 5 seconds		

##jps查询进程id

***

	3300 Jps
	7032 java-agent-test-0.1-jar-with-dependencies.jar
	7196

##运行代理客户端
1. cd java-agent
2. 运行  java-agent-client.bat 7032


##查看应用程序日志已经加载代理并生效

***

	Java Agent with Agentmain
	HelloBean.hello() -> sleep 5 seconds
	HelloBean.hello() -> sleep 5 seconds
	The Method Used Time Is:5001ms.
	HelloBean.hello() -> sleep 5 seconds
	The Method Used Time Is:5000ms.

# 启动加载代理方式(require jdk 5+)
## 启动应用程序
1. cd java-agent-test 
2. 运行java-agent-server.bat

***

	Java Agent with Premain
	JavaAgentTestApp
	HelloBean.hello() -> sleep 5 seconds
	The Method Used Time Is:5002ms
	HelloBean.hello() -> sleep 5 seconds
	The Method Used Time Is:6164ms