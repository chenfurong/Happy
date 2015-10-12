package com.ibm.smartcloud.openstack.keystone.bean;

import java.util.List;

/**
 * @Title:AccessBean     
 * @Description:登陆认证返回信息
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午5:41:50       
 * @version V1.0
 */
@SuppressWarnings("serial")
public class AccessBean implements java.io.Serializable{
	
	private String tokenId;
	private boolean tenantEnabled = true;
	private String tenantId = "";
	private String tenantName = "";
	private String tenantDescription = "";
	private String userId = "";
	private String userName = "";
	private List<String> roleIds;
	private List<String> roleNames;
	private List<OPSTRoleBean> roleList;
	
	
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public boolean isTenantEnabled() {
		return tenantEnabled;
	}
	public void setTenantEnabled(boolean tenantEnabled) {
		this.tenantEnabled = tenantEnabled;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantDescription() {
		return tenantDescription;
	}
	public void setTenantDescription(String tenantDescription) {
		this.tenantDescription = tenantDescription;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	public List<OPSTRoleBean> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<OPSTRoleBean> roleList) {
		this.roleList = roleList;
	}
	public List<String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	
}
