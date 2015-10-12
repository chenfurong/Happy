package com.ibm.smartcloud.openstack.glance.bean;

import java.io.File;
import java.io.Serializable;

/**
 * @Title:ImagesBean     
 * @Description:
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 上午11:35:52       
 * @version V1.0
 */
public class ImagesBean implements Serializable{
	private static final long serialVersionUID = -5070681343713592296L;
	
	private String id = "";
	private String status = "";
	private String name = "";
	private String container_format = "";
	private String created_at = "";
	private String disk_format = "";
	private int min_disk = 0;
	private Boolean imgProtected = false;
	private int min_ram = 0;
	private String checksum = "";
	private String owner="";
	private String update_at = "";
	private File file;
	private long size = 0;
	private String self = "";
	private String tags = "";
	private String visibility = "";
	private String schema = "";
	private String hypervisor_type = "";
	private String virtual_size = "";
	private String os_distro = "";
	private String endianness = "";
	private String architecture = "";
	private String bdm_v2 = "";
	private String root_device_name = "";
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
	 * @return the container_format
	 */
	public String getContainer_format() {
		return container_format;
	}
	/**
	 * @param container_format the container_format to set
	 */
	public void setContainer_format(String container_format) {
		this.container_format = container_format;
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
	 * @return the disk_format
	 */
	public String getDisk_format() {
		return disk_format;
	}
	/**
	 * @param disk_format the disk_format to set
	 */
	public void setDisk_format(String disk_format) {
		this.disk_format = disk_format;
	}
	/**
	 * @return the min_disk
	 */
	public int getMin_disk() {
		return min_disk;
	}
	/**
	 * @param min_disk the min_disk to set
	 */
	public void setMin_disk(int min_disk) {
		this.min_disk = min_disk;
	}
	/**
	 * @return the imgProtected
	 */
	public Boolean getImgProtected() {
		return imgProtected;
	}
	/**
	 * @param imgProtected the imgProtected to set
	 */
	public void setImgProtected(Boolean imgProtected) {
		this.imgProtected = imgProtected;
	}
	/**
	 * @return the min_ram
	 */
	public int getMin_ram() {
		return min_ram;
	}
	/**
	 * @param min_ram the min_ram to set
	 */
	public void setMin_ram(int min_ram) {
		this.min_ram = min_ram;
	}
	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the update_at
	 */
	public String getUpdate_at() {
		return update_at;
	}
	/**
	 * @param update_at the update_at to set
	 */
	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @return the self
	 */
	public String getSelf() {
		return self;
	}
	/**
	 * @param self the self to set
	 */
	public void setSelf(String self) {
		this.self = self;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}
	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}
	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}
	/**
	 * @return the hypervisor_type
	 */
	public String getHypervisor_type() {
		return hypervisor_type;
	}
	/**
	 * @param hypervisor_type the hypervisor_type to set
	 */
	public void setHypervisor_type(String hypervisor_type) {
		this.hypervisor_type = hypervisor_type;
	}
	/**
	 * @return the virtual_size
	 */
	public String getVirtual_size() {
		return virtual_size;
	}
	/**
	 * @param virtual_size the virtual_size to set
	 */
	public void setVirtual_size(String virtual_size) {
		this.virtual_size = virtual_size;
	}
	/**
	 * @return the os_distro
	 */
	public String getOs_distro() {
		return os_distro;
	}
	/**
	 * @param os_distro the os_distro to set
	 */
	public void setOs_distro(String os_distro) {
		this.os_distro = os_distro;
	}
	/**
	 * @return the endianness
	 */
	public String getEndianness() {
		return endianness;
	}
	/**
	 * @param endianness the endianness to set
	 */
	public void setEndianness(String endianness) {
		this.endianness = endianness;
	}
	/**
	 * @return the architecture
	 */
	public String getArchitecture() {
		return architecture;
	}
	/**
	 * @param architecture the architecture to set
	 */
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	/**
	 * @return the bdm_v2
	 */
	public String getBdm_v2() {
		return bdm_v2;
	}
	/**
	 * @param bdm_v2 the bdm_v2 to set
	 */
	public void setBdm_v2(String bdm_v2) {
		this.bdm_v2 = bdm_v2;
	}
	/**
	 * @return the root_device_name
	 */
	public String getRoot_device_name() {
		return root_device_name;
	}
	/**
	 * @param root_device_name the root_device_name to set
	 */
	public void setRoot_device_name(String root_device_name) {
		this.root_device_name = root_device_name;
	}
	
	
}