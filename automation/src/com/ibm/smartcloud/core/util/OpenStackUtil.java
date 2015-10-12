package com.ibm.smartcloud.core.util;


import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;


public class OpenStackUtil {
	/**
	 * 获取openstack token id
	 * @param req
	 * @return
	 */
	private static String token = "";
	private static String tenantId = "";
	public static String getTokenId(){
		if(SpringHelper.getRequest().getSession()!=null&&SpringHelper.getRequest().getSession().getAttribute("tokenId")!=null){
			token = SpringHelper.getRequest().getSession().getAttribute("tokenId").toString();
			return token;
		}else{
			throw new LoginTimeOutException("登陆超时");
		}
		
		
	}	

	public static String getTenantId(){

		if(SpringHelper.getRequest().getSession()!=null&&SpringHelper.getRequest().getSession().getAttribute("tenantId")!=null){
			tenantId = SpringHelper.getRequest().getSession().getAttribute("tenantId").toString();
			return tenantId;
		}else{
			throw new LoginTimeOutException("登陆超时");
		}		
	}	
	


	
}
