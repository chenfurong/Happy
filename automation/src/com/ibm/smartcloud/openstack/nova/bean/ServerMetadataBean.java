package com.ibm.smartcloud.openstack.nova.bean;

/**
 * @Title:ServerMetadataBean     
 * @Description:metadata
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:11:04       
 * @version V1.0
 */
public class ServerMetadataBean {
	private String metadataKey;
	private String metadataValue;
	
	/**
	 * @return the metadataKey
	 */
	public String getMetadataKey() {
		return metadataKey;
	}
	/**
	 * @param metadataKey the metadataKey to set
	 */
	public void setMetadataKey(String metadataKey) {
		this.metadataKey = metadataKey;
	}
	/**
	 * @return the metadataValue
	 */
	public String getMetadataValue() {
		return metadataValue;
	}
	/**
	 * @param metadataValue the metadataValue to set
	 */
	public void setMetadataValue(String metadataValue) {
		this.metadataValue = metadataValue;
	}



	
}
