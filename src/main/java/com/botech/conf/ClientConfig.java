package com.botech.conf;

/**
 * 【服务器的配置】
* @ClassName: ServerConfig 
* @Description: TODO
* @author luanxd
* @date 2015-7-29 上午9:46:26 
*
 */
public class ClientConfig {
	private String ip;//客户端IP
	private int port;//客户端端口
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
