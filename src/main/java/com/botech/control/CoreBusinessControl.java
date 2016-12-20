package com.botech.control;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.botech.bean.UserBean;
import com.botech.io.netty.bean.BcPackageBuilder.BcPackage;
import com.botech.service.MsgHandleService;
import com.botech.thread.ClientThreadPool;
import com.botech.view.GroupChat;
import com.botech.view.MM;

public class CoreBusinessControl {

	private MM mm;
	private GroupChat groupChat;
	private ExecutorService es = Executors.newCachedThreadPool();// 线程池
	private ClientThreadPool clientThread;
	private UserBean userBean;
	
	private CoreBusinessControl() {
	};

	public CoreBusinessControl(MM mm) {
		this.mm = mm;
	}

	public void doCloseFrame(){
		mm.setVisible(false);
		mm.dispose();
	}
	
	/**
	 * 校验登录
	 */
	public void doCheckConnectLogin(String userName,String userPwd) {
		
		doConnectServer();

		userBean = new UserBean();
		userBean.setUserName(userName);
		userBean.setUserPwd(userPwd);
		
	}
	
	public void doCheckLogin(){
		MsgHandleService.doCheckLogin(userBean);
	}
	
	/**
	 * 
	 * @param infoMsg
	 */
	public void doSendMsg(String msgStr){
		
		MsgHandleService.doSendMsgStr(userBean.getUserName(), msgStr);
	}
	
	/**
	 * 
	 * @param login
	 */
	public void doChangeGroupChatView(String login){
		mm.dispose();
		groupChat = new GroupChat();
		groupChat.setUser(userBean);
	}

	/**
	 * 
	 * @param userListList
	 */
	public void doRefreshFriendList(String[] userListList){
		groupChat.refreshFriendsList(userListList);
	}
	
	/**
	 * 连接服务端
	 * 
	 * @return
	 */
	public void doConnectServer() {
		clientThread = new ClientThreadPool();
		es.execute(clientThread);
	}

	/**
	 * 开启聊天界面
	 */
	public void doOpenChatView() {
		mm.dispose();
		groupChat = new GroupChat();
	}

	/**
	 * 
	 * @param msgInfo
	 */
	public void doReceivedMsgInfo(BcPackage msgInfo) {
		groupChat.refreshReceivedMsg(msgInfo);
	}

}
