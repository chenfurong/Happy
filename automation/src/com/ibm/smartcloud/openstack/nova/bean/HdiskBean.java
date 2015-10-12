package com.ibm.smartcloud.openstack.nova.bean;

import java.io.Serializable;
/**
 * @Title:FlavorBean     
 * @Description:实例类型
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:09:11       
 * @version V1.0
 */
public class HdiskBean implements Serializable {
	private static final long serialVersionUID = 4017157549706314551L;
	
	private String id = "";//标识符
	private String hdName = "";//名字
	private String hdType = "";//其他属性
	
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
	 * @return the hdName
	 */
	public String getHdName() {
		return hdName;
	}
	/**
	 * @param hdName the hdName to set
	 */
	public void setHdName(String hdName) {
		this.hdName = hdName;
	}
	/**
	 * @return the hdType
	 */
	public String getHdType() {
		return hdType;
	}
	/**
	 * @param hdType the hdType to set
	 */
	public void setHdType(String hdType) {
		this.hdType = hdType;
	}
	
}
