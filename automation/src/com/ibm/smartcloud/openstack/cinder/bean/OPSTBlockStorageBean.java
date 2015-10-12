package com.ibm.smartcloud.openstack.cinder.bean;

import java.util.List;

import com.ibm.smartcloud.openstack.core.bean.OPSTLinksBean;
/**
 * 类描述: 
 * 类名称：OPSTBlockStorageBean     
 * 创建人：梁瑞   
 * 创建时间：2014年4月30日 上午11:32:01     
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OPSTBlockStorageBean implements java.io.Serializable {
	private String id = "";
	private String name = "";
	private String status = "";
//	private String attachments = "";
	private String availability_zone = "";
	private String os_vol_host_attr_host = "";
	private List<OPSTLinksBean> links;
	private String  source_volid= "";
	private String  snapshot_id= "";
	private String  description= "";
	private String  created_at= "";
	private String  volume_type= "";
	private String  os_vol_tenant_attr_tenant_id= "";
	private String  size= "";
	private String  metadata= "";
	
	private String imageRef="";
	
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
//	/**
//	 * @return the attachments
//	 */
//	public String getAttachments() {
//		return attachments;
//	}
//	/**
//	 * @param attachments the attachments to set
//	 */
//	public void setAttachments(String attachments) {
//		this.attachments = attachments;
//	}
	/**
	 * @return the availability_zone
	 */
	public String getAvailability_zone() {
		return availability_zone;
	}
	/**
	 * @param availability_zone the availability_zone to set
	 */
	public void setAvailability_zone(String availability_zone) {
		this.availability_zone = availability_zone;
	}
	/**
	 * @return the links
	 */
	public List<OPSTLinksBean> getLinks() {
		return links;
	}
	/**
	 * @param links the links to set
	 */
	public void setLinks(List<OPSTLinksBean> links) {
		this.links = links;
	}
	/**
	 * @return the source_volid
	 */
	public String getSource_volid() {
		return source_volid;
	}
	/**
	 * @param source_volid the source_volid to set
	 */
	public void setSource_volid(String source_volid) {
		this.source_volid = source_volid;
	}
	/**
	 * @return the snapshot_id
	 */
	public String getSnapshot_id() {
		return snapshot_id;
	}
	/**
	 * @param snapshot_id the snapshot_id to set
	 */
	public void setSnapshot_id(String snapshot_id) {
		this.snapshot_id = snapshot_id;
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
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the volume_type
	 */
	public String getVolume_type() {
		return volume_type;
	}
	/**
	 * @param volume_type the volume_type to set
	 */
	public void setVolume_type(String volume_type) {
		this.volume_type = volume_type;
	}
	/**
	 * @return the os_vol_tenant_attr_tenant_id
	 */
	public String getOs_vol_tenant_attr_tenant_id() {
		return os_vol_tenant_attr_tenant_id;
	}
	/**
	 * @param os_vol_tenant_attr_tenant_id the os_vol_tenant_attr_tenant_id to set
	 */
	public void setOs_vol_tenant_attr_tenant_id(String os_vol_tenant_attr_tenant_id) {
		this.os_vol_tenant_attr_tenant_id = os_vol_tenant_attr_tenant_id;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}
	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	/**
	 * @return the os_vol_host_attr_host
	 */
	public String getOs_vol_host_attr_host() {
		return os_vol_host_attr_host;
	}
	/**
	 * @param os_vol_host_attr_host the os_vol_host_attr_host to set
	 */
	public void setOs_vol_host_attr_host(String os_vol_host_attr_host) {
		this.os_vol_host_attr_host = os_vol_host_attr_host;
	}
	public String getImageRef() {
		return imageRef;
	}
	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}
	
	
}
