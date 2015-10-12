package com.ibm.smartcloud.openstack.neutron.bean;

import java.io.Serializable;

/**
 * @Title:SecurityGroupBean     
 * @Description:安全组 
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:13:05       
 * @version V1.0
 */
public class SecurityGroupBean  implements Serializable{
	private static final long serialVersionUID = -7962001740900643342L;
	
	private String  id; 		
	private String  name; 
	private String  description;
	private String  tenant_id;
	private String  errorMessage;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the tenant_id
	 */
	public String getTenant_id() {
		return tenant_id;
	}
	/**
	 * @param tenant_id the tenant_id to set
	 */
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
