package com.ibm.smartcloud.openstack.keystone.bean;

import java.io.Serializable;

/**
 * 类描述:OPST成SessionPersistence实体类
 * 类名称：OPSTSessionPersistenceBean
 * @author zhanglong
 * @version 1.0   创建时间：2014年7月1日 下午1:50
 */

public class OPSTSessionPersistenceBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5372949175954964855L;
	private String cookie_name;
	private String type;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCookie_name() {
		return cookie_name;
	}
	public void setCookie_name(String cookieName) {
		cookie_name = cookieName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
