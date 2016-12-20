package com.botech.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

import com.botech.io.netty.bean.BcPackageBuilder;
import com.botech.service.MsgHandleService;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{

	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		MsgHandleService.channel = ctx;
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		BcPackageBuilder.BcPackage pack = (BcPackageBuilder.BcPackage) msg;
		
		MsgHandleService.doMsgForShunt(pack);
		ctx.fireChannelRead(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		/*
		if(null!=MsgHandleService.channel && MsgHandleService.channel.channel().isActive()){
			ClientConfig config = ClientConfigFactory.getInstance().getConfig();
			NettyClient.getInstance().connect(config.getIp(), config.getPort());
		}
		*/
	}
	
	
}
