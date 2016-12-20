package com.botech.service;


import io.netty.channel.ChannelHandlerContext;

import com.botech.agreement.MsgAgreement;
import com.botech.bean.UserBean;
import com.botech.control.CoreBusinessControl;
import com.botech.io.netty.bean.BcPackageBuilder;
import com.botech.io.netty.bean.BcPackageBuilder.BcPackage;

public class MsgHandleService {
	
	public static ChannelHandlerContext channel;
	public static CoreBusinessControl coreBusinessControl;
	private static MsgAgreement msgAgree = new MsgAgreement(true);
	
	static public void doMsgForShunt(BcPackageBuilder.BcPackage msg){
		String bctype = msg.getBctype();
		
		//服务器主动打招乎
		if("0".equals(bctype)){//0．	验证请求
			NoticeConnectState(msg);
		}else if("1".equals(bctype)){//1．	验证应答(集群一组地址与模值)
			NoticeLoginState(msg,"");
		}else if("5".equals(bctype)){//5．	发送消息
			NoticeReceivedMsgInfo(msg);
		}else if("6".equals(bctype)){//6．	发送所有客户端列表
			NoticeRefreshFriendsList(msg.getBcmessage().split(","));
		}else if("7".equals(bctype)){//7．	客户端退出
			NoticeLoginOut(msg);
		}else if("2".equals(bctype)){//2．	心跳请求
			//MsgHandleService.coreBusinessControl.doSendMsg("收到服务器心跳消息");//把内容拿过来发出去。
		}else if("4".equals(bctype)){//4．	设备重复登录
			NoticeLoginOut(msg);
		}
		
	}
	
	/**
	 * 
	 * @param msgInfo
	 */
	private static void NoticeReceivedMsgInfo(BcPackage msgInfo) {
		
		coreBusinessControl.doReceivedMsgInfo(msgInfo);
		
	}

	/**
	 * 
	 * @param userListList
	 */
	private static void NoticeRefreshFriendsList(String[] userListList) {
		coreBusinessControl.doRefreshFriendList(userListList);
	}

	/**
	 * 服务器（TimeServerHandler）   通知->   客户端    登 录状态
	 * @param loginState
	 * @param login
	 */
	private static void NoticeLoginState(BcPackage msg, String login) {
		
		if("1".equals(msg.getBctype())){//服务器返回登录成功。
			coreBusinessControl.doChangeGroupChatView(login);
		}else{//服务器不让登录。
			channel.close();
			System.exit(0);
		}
		
	}
	
	public static void  NoticeLoginOut(BcPackage msg){
		channel.writeAndFlush(msg);
		channel.close();
		
		coreBusinessControl.doCloseFrame();
		System.exit(0);
	}
	
	public static void  NoticeLoginReal(BcPackage msg){
		channel.writeAndFlush(msg);
		channel.close();
		System.exit(0);
	}

	/**
	 * 服务器 （ChannelInitializerImpl）  通知-> 客户端	连接状态
	 * @param serverConnectEnum
	 */
	private static void NoticeConnectState(BcPackageBuilder.BcPackage msg) {
		System.out.println("发送登录信息"+msg.getBctype());
		if("0".equals(msg.getBctype())){
			coreBusinessControl.doCheckLogin();
			System.out.println("发送登录信息");
		}else{
			
		}
		
	}

	//登录函数
	static public void doCheckLogin(UserBean user){
		channel.writeAndFlush(msgAgree.doGetLoginInfoPacket(user.getUserName(), user.getUserPwd()));
	}
	
	//发送消息
	static public void doSendMsgStr(String userName,String msgStr){
		channel.writeAndFlush(msgAgree.doGetGroupSendInfoPacket(userName, msgStr));
	}
	
}
