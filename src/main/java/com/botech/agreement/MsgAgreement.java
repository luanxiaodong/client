package com.botech.agreement;


import com.botech.io.netty.bean.BcPackageBuilder;
import com.botech.io.netty.bean.BcPackageBuilder.BcPackage;

public class MsgAgreement {

	@SuppressWarnings("unused")
	private MsgAgreement(){}
	
	public MsgAgreement(boolean bool){
		
		if(bool){
			
		}
		
	}
	
	/**
	 * 【通过用户、密码登录】
	 * get login agreement Group 
	 * @param userName
	 * @param userPwd
	 * @return InformationPacket.Group
	 */
	public BcPackageBuilder.BcPackage doGetLoginInfoPacket(String userName,String userPwd){
		
		BcPackageBuilder.BcPackage msg = BcPackage.newBuilder()
				.setBctype("0")
				.setBcmessage(userName)
				.build();
		return msg;
	}
	
	/**
	 * 【通过人名向所有人发送消息.】
	 * get group send info packet
	 * @param userName
	 * @param msgStr
	 * @return
	 */
	public BcPackageBuilder.BcPackage doGetGroupSendInfoPacket(String userName,String msgStr){
		
		BcPackageBuilder.BcPackage msg = BcPackage.newBuilder()
				.setBctype("5")
				.setClusteredid(userName)
				.setBcmessage(msgStr)
				.build();
		return msg;
		
	}
	
	
}
