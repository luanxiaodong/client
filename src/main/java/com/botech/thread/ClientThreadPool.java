package com.botech.thread;


import com.botech.client.NettyClient;
import com.botech.conf.ClientConfig;
import com.botech.conf.ClientConfigFactory;

public class ClientThreadPool implements Runnable{
	
	public void run() {
		try {
			ClientConfig config = ClientConfigFactory.getInstance().getConfig();
			NettyClient nc = NettyClient.getInstance();
			nc.connect(config.getIp(), config.getPort());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
