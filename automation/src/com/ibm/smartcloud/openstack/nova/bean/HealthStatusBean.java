package com.ibm.smartcloud.openstack.nova.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:HealthStatusBean     
 * @Description: 物理机健康状态
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 上午11:59:56       
 * @version V1.0
 */
public class HealthStatusBean implements Serializable{
	private static final long serialVersionUID = 5927143707121031159L;
	
	private String id = ""; 
	private String health_value = ""; 
	private List<String> value_reason = null; 
	private String updated_at = "";
	
	
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
	 * @return the health_value
	 */
	public String getHealth_value() {
		return health_value;
	}
	/**
	 * @param health_value the health_value to set
	 */
	public void setHealth_value(String health_value) {
		this.health_value = health_value;
	}
	/**
	 * @return the value_reason
	 */
	public List<String> getValue_reason() {
		return value_reason;
	}
	/**
	 * @param value_reason the value_reason to set
	 */
	public void setValue_reason(List<String> value_reason) {
		this.value_reason = value_reason;
	}
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	} 
    
	
}
