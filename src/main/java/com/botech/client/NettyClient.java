package com.botech.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class NettyClient {
	private NettyClient(){}
	private static NettyClient instance=new NettyClient();
	public static NettyClient getInstance(){
		return instance;
	}

	public void connect(final String inetHost,final int inetPort) throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new ChildChannelHandler());
			
			//发起异步连接操作
			ChannelFuture f = b.connect(inetHost, inetPort);
			
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
			
		}finally{
			group.shutdownGracefully();
			
		}
	}
	
}
