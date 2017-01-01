package com.zhenghongfei;

import java.security.CodeSource;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class JavaAgentClient {
	public static void main(String[] args) throws Exception {
		VirtualMachineDescriptor attachVmd = null;
		for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
			if (vmd.id().equals(args[0])) {
				attachVmd = vmd;
				break;
			}
		}

		if (attachVmd != null) {
			VirtualMachine vm = null;
			try {
				vm = VirtualMachine.attach(attachVmd);
				String OS = System.getProperty("os.name");
				CodeSource codeSource = JavaAgentClient.class.getProtectionDomain().getCodeSource();
				String path = codeSource.getLocation().toURI().getPath();
				if (path != null && path.startsWith("/") && OS.startsWith("Windows")) {
					path = path.substring(1);
				}
				vm.loadAgent(path);
				while (true) {
					Thread.sleep(3000);
				}
			} finally {
				if (vm != null) {
					vm.detach();
				}
			}
		}
	}
}
