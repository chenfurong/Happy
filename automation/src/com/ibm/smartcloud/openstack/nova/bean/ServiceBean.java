package com.ibm.smartcloud.openstack.nova.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:ServiceBean     
 * @Description:计算服务。
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 上午11:55:21       
 * @version V1.0
 */
public class ServiceBean implements Serializable {
	private static final long serialVersionUID = -2614988466591624573L;
	
	private String id = "";	 
	private String host_display_name = "";	 
	private String host_type = "";	 
	private List<String> hmc_uuids = null;	 
	private String host = "";	 
	private String host_storage_type = "";	  
	private String disabled_reason = "";
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the host_display_name
	 */
	public String getHost_display_name() {
		return host_display_name;
	}
	/**
	 * @param host_display_name the host_display_name to set
	 */
	public void setHost_display_name(String host_display_name) {
		this.host_display_name = host_display_name;
	}
	/**
	 * @return the host_type
	 */
	public String getHost_type() {
		return host_type;
	}
	/**
	 * @param host_type the host_type to set
	 */
	public void setHost_type(String host_type) {
		this.host_type = host_type;
	}
	/**
	 * @return the hmc_uuids
	 */
	public List<String> getHmc_uuids() {
		return hmc_uuids;
	}
	/**
	 * @param hmc_uuids the hmc_uuids to set
	 */
	public void setHmc_uuids(List<String> hmc_uuids) {
		this.hmc_uuids = hmc_uuids;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the host_storage_type
	 */
	public String getHost_storage_type() {
		return host_storage_type;
	}
	/**
	 * @param host_storage_type the host_storage_type to set
	 */
	public void setHost_storage_type(String host_storage_type) {
		this.host_storage_type = host_storage_type;
	}
	/**
	 * @return the disabled_reason
	 */
	public String getDisabled_reason() {
		return disabled_reason;
	}
	/**
	 * @param disabled_reason the disabled_reason to set
	 */
	public void setDisabled_reason(String disabled_reason) {
		this.disabled_reason = disabled_reason;
	}	 
 
	
}
