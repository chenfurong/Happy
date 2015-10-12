package com.ibm.smartcloud.openstack.nova.bean;

import java.io.Serializable;
import java.util.List;

import com.ibm.smartcloud.openstack.glance.bean.ImagesBean;
import com.ibm.smartcloud.openstack.neutron.bean.SecurityGroupBean;

/**
 * @Title:OPSTServerBean     
 * @Description:虚拟主机信息
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:23:18       
 * @version V1.0
 */
public class ServerBean implements Serializable,Comparable<ServerBean>{
	private static final long serialVersionUID = 2371101592227482591L;
	
	 private String id = ""; 
	 private String name = ""; 
	 private List<ServerAddressBean> address = null;
	 private ImagesBean image = null;
	 private FlavorBean flavor = null;
	 private ServerMetadataBean metadata = null;
	 private List<SecurityGroupBean> securityGroups = null;
	 private HealthStatusBean healthStatus = null;
	 private Integer ephemeral_gb;
	 private Integer cpus;
     private String OS_EXT_STS_task_state; 
	 private String storage_connectivity_group_id = "";
	 private String vcpu_mode = "";
	 private String desired_compatibility_mode = "";
	 private String endianness = "";
	 private String updated = "";
	 private String memory_mode = "";
	 private String key_name = "";
	 private Integer min_memory_mb;
     private String operating_system = "";
	 private Boolean uncapped = false;
	 private Double min_vcpus;
	 private Double vcpus;
	 private Integer max_memory_mb;
	 private Integer min_cpus;
	 private Double max_vcpus;
     private String OS_EXT_STS_vm_state = "";
     private String OS_EXT_SRV_ATTR_instance_name = "";
     private String OS_EXT_SRV_ATTR_host = "";
     private String os_distro = "";
     private String srr_state = "";
     private String dedicated_sharing_mode = "";
     private String OS_DCF_diskConfig = "";
     private String accessIPv4 = "";
     private String accessIPv6 = "";
     private Integer avail_priority;
     private Integer shared_weight;
     private Integer progress; 
     private String OS_EXT_STS_power_state; 
     private String OS_EXT_AZ_availability_zone = ""; 
     private String launched_at = "";
     private String status = "";
     private String hostId = ""; 
     private String cpu_utilization = "";
     private List<String> compliance_status; 
     private String current_compatibility_mode = "";
     private String user_id = "";
     private Integer root_gb;
     private String OS_EXT_SRV_ATTR_hypervisor_hostname = "";
     private Boolean srr_capability = false;
     private String created = "";
     private String tenant_id = "";
     private Integer memory_mb;
     private Integer max_cpus;
     
     private String health_status;
     
     private Boolean isdisabled = true;//true:checkbox显示可进行下一步操作
	/**
	 * @return the address
	 */
	public List<ServerAddressBean> getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(List<ServerAddressBean> address) {
		this.address = address;
	}
	/**
	 * @return the image
	 */
	public ImagesBean getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(ImagesBean image) {
		this.image = image;
	}
	/**
	 * @return the flavor
	 */
	public FlavorBean getFlavor() {
		return flavor;
	}
	/**
	 * @param flavor the flavor to set
	 */
	public void setFlavor(FlavorBean flavor) {
		this.flavor = flavor;
	}
	/**
	 * @return the metadata
	 */
	public ServerMetadataBean getMetadata() {
		return metadata;
	}
	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(ServerMetadataBean metadata) {
		this.metadata = metadata;
	}
	/**
	 * @return the securityGroups
	 */
	public List<SecurityGroupBean> getSecurityGroups() {
		return securityGroups;
	}
	/**
	 * @param securityGroups the securityGroups to set
	 */
	public void setSecurityGroups(List<SecurityGroupBean> securityGroups) {
		this.securityGroups = securityGroups;
	}
	/**
	 * @return the healthStatus
	 */
	public HealthStatusBean getHealthStatus() {
		return healthStatus;
	}
	/**
	 * @param healthStatus the healthStatus to set
	 */
	public void setHealthStatus(HealthStatusBean healthStatus) {
		this.healthStatus = healthStatus;
	}
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
	 * @return the ephemeral_gb
	 */
	public Integer getEphemeral_gb() {
		return ephemeral_gb;
	}
	/**
	 * @param ephemeral_gb the ephemeral_gb to set
	 */
	public void setEphemeral_gb(Integer ephemeral_gb) {
		this.ephemeral_gb = ephemeral_gb;
	}
	/**
	 * @return the cpus
	 */
	public Integer getCpus() {
		return cpus;
	}
	/**
	 * @param cpus the cpus to set
	 */
	public void setCpus(Integer cpus) {
		this.cpus = cpus;
	}
	/**
	 * @return the oS_EXT_STS_task_state
	 */
	public String getOS_EXT_STS_task_state() {
		return OS_EXT_STS_task_state;
	}
	/**
	 * @param oS_EXT_STS_task_state the oS_EXT_STS_task_state to set
	 */
	public void setOS_EXT_STS_task_state(String oS_EXT_STS_task_state) {
		OS_EXT_STS_task_state = oS_EXT_STS_task_state;
	}
	/**
	 * @return the storage_connectivity_group_id
	 */
	public String getStorage_connectivity_group_id() {
		return storage_connectivity_group_id;
	}
	/**
	 * @param storage_connectivity_group_id the storage_connectivity_group_id to set
	 */
	public void setStorage_connectivity_group_id(
			String storage_connectivity_group_id) {
		this.storage_connectivity_group_id = storage_connectivity_group_id;
	}
	/**
	 * @return the vcpu_mode
	 */
	public String getVcpu_mode() {
		return vcpu_mode;
	}
	/**
	 * @param vcpu_mode the vcpu_mode to set
	 */
	public void setVcpu_mode(String vcpu_mode) {
		this.vcpu_mode = vcpu_mode;
	}
	/**
	 * @return the desired_compatibility_mode
	 */
	public String getDesired_compatibility_mode() {
		return desired_compatibility_mode;
	}
	/**
	 * @param desired_compatibility_mode the desired_compatibility_mode to set
	 */
	public void setDesired_compatibility_mode(String desired_compatibility_mode) {
		this.desired_compatibility_mode = desired_compatibility_mode;
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
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	/**
	 * @return the memory_mode
	 */
	public String getMemory_mode() {
		return memory_mode;
	}
	/**
	 * @param memory_mode the memory_mode to set
	 */
	public void setMemory_mode(String memory_mode) {
		this.memory_mode = memory_mode;
	}
	/**
	 * @return the key_name
	 */
	public String getKey_name() {
		return key_name;
	}
	/**
	 * @param key_name the key_name to set
	 */
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}
	/**
	 * @return the min_memory_mb
	 */
	public Integer getMin_memory_mb() {
		return min_memory_mb;
	}
	/**
	 * @param min_memory_mb the min_memory_mb to set
	 */
	public void setMin_memory_mb(Integer min_memory_mb) {
		this.min_memory_mb = min_memory_mb;
	}
	/**
	 * @return the operating_system
	 */
	public String getOperating_system() {
		return operating_system;
	}
	/**
	 * @param operating_system the operating_system to set
	 */
	public void setOperating_system(String operating_system) {
		this.operating_system = operating_system;
	}
	/**
	 * @return the uncapped
	 */
	public Boolean getUncapped() {
		return uncapped;
	}
	/**
	 * @param uncapped the uncapped to set
	 */
	public void setUncapped(Boolean uncapped) {
		this.uncapped = uncapped;
	}
	/**
	 * @return the min_vcpus
	 */
	public Double getMin_vcpus() {
		return min_vcpus;
	}
	/**
	 * @param min_vcpus the min_vcpus to set
	 */
	public void setMin_vcpus(Double min_vcpus) {
		this.min_vcpus = min_vcpus;
	}
	/**
	 * @return the vcpus
	 */
	public Double getVcpus() {
		return vcpus;
	}
	/**
	 * @param vcpus the vcpus to set
	 */
	public void setVcpus(Double vcpus) {
		this.vcpus = vcpus;
	}
	/**
	 * @return the max_memory_mb
	 */
	public Integer getMax_memory_mb() {
		return max_memory_mb;
	}
	/**
	 * @param max_memory_mb the max_memory_mb to set
	 */
	public void setMax_memory_mb(Integer max_memory_mb) {
		this.max_memory_mb = max_memory_mb;
	}
	/**
	 * @return the min_cpus
	 */
	public Integer getMin_cpus() {
		return min_cpus;
	}
	/**
	 * @param min_cpus the min_cpus to set
	 */
	public void setMin_cpus(Integer min_cpus) {
		this.min_cpus = min_cpus;
	}
	/**
	 * @return the max_vcpus
	 */
	public Double getMax_vcpus() {
		return max_vcpus;
	}
	/**
	 * @param max_vcpus the max_vcpus to set
	 */
	public void setMax_vcpus(Double max_vcpus) {
		this.max_vcpus = max_vcpus;
	}
	/**
	 * @return the oS_EXT_STS_vm_state
	 */
	public String getOS_EXT_STS_vm_state() {
		return OS_EXT_STS_vm_state;
	}
	/**
	 * @param oS_EXT_STS_vm_state the oS_EXT_STS_vm_state to set
	 */
	public void setOS_EXT_STS_vm_state(String oS_EXT_STS_vm_state) {
		OS_EXT_STS_vm_state = oS_EXT_STS_vm_state;
	}
	/**
	 * @return the oS_EXT_SRV_ATTR_instance_name
	 */
	public String getOS_EXT_SRV_ATTR_instance_name() {
		return OS_EXT_SRV_ATTR_instance_name;
	}
	/**
	 * @param oS_EXT_SRV_ATTR_instance_name the oS_EXT_SRV_ATTR_instance_name to set
	 */
	public void setOS_EXT_SRV_ATTR_instance_name(
			String oS_EXT_SRV_ATTR_instance_name) {
		OS_EXT_SRV_ATTR_instance_name = oS_EXT_SRV_ATTR_instance_name;
	}
	/**
	 * @return the oS_EXT_SRV_ATTR_host
	 */
	public String getOS_EXT_SRV_ATTR_host() {
		return OS_EXT_SRV_ATTR_host;
	}
	/**
	 * @param oS_EXT_SRV_ATTR_host the oS_EXT_SRV_ATTR_host to set
	 */
	public void setOS_EXT_SRV_ATTR_host(String oS_EXT_SRV_ATTR_host) {
		OS_EXT_SRV_ATTR_host = oS_EXT_SRV_ATTR_host;
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
	 * @return the srr_state
	 */
	public String getSrr_state() {
		return srr_state;
	}
	/**
	 * @param srr_state the srr_state to set
	 */
	public void setSrr_state(String srr_state) {
		this.srr_state = srr_state;
	}
	/**
	 * @return the dedicated_sharing_mode
	 */
	public String getDedicated_sharing_mode() {
		return dedicated_sharing_mode;
	}
	/**
	 * @param dedicated_sharing_mode the dedicated_sharing_mode to set
	 */
	public void setDedicated_sharing_mode(String dedicated_sharing_mode) {
		this.dedicated_sharing_mode = dedicated_sharing_mode;
	}
	/**
	 * @return the oS_DCF_diskConfig
	 */
	public String getOS_DCF_diskConfig() {
		return OS_DCF_diskConfig;
	}
	/**
	 * @param oS_DCF_diskConfig the oS_DCF_diskConfig to set
	 */
	public void setOS_DCF_diskConfig(String oS_DCF_diskConfig) {
		OS_DCF_diskConfig = oS_DCF_diskConfig;
	}
	/**
	 * @return the accessIPv4
	 */
	public String getAccessIPv4() {
		return accessIPv4;
	}
	/**
	 * @param accessIPv4 the accessIPv4 to set
	 */
	public void setAccessIPv4(String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}
	/**
	 * @return the accessIPv6
	 */
	public String getAccessIPv6() {
		return accessIPv6;
	}
	/**
	 * @param accessIPv6 the accessIPv6 to set
	 */
	public void setAccessIPv6(String accessIPv6) {
		this.accessIPv6 = accessIPv6;
	}
	/**
	 * @return the avail_priority
	 */
	public Integer getAvail_priority() {
		return avail_priority;
	}
	/**
	 * @param avail_priority the avail_priority to set
	 */
	public void setAvail_priority(Integer avail_priority) {
		this.avail_priority = avail_priority;
	}
	/**
	 * @return the shared_weight
	 */
	public Integer getShared_weight() {
		return shared_weight;
	}
	/**
	 * @param shared_weight the shared_weight to set
	 */
	public void setShared_weight(Integer shared_weight) {
		this.shared_weight = shared_weight;
	}
	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return progress;
	}
	/**
	 * @param progress the progress to set
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	/**
	 * @return the oS_EXT_STS_power_state
	 */
	public String getOS_EXT_STS_power_state() {
		return OS_EXT_STS_power_state;
	}
	/**
	 * @param oS_EXT_STS_power_state the oS_EXT_STS_power_state to set
	 */
	public void setOS_EXT_STS_power_state(String oS_EXT_STS_power_state) {
		OS_EXT_STS_power_state = oS_EXT_STS_power_state;
	}
	/**
	 * @return the oS_EXT_AZ_availability_zone
	 */
	public String getOS_EXT_AZ_availability_zone() {
		return OS_EXT_AZ_availability_zone;
	}
	/**
	 * @param oS_EXT_AZ_availability_zone the oS_EXT_AZ_availability_zone to set
	 */
	public void setOS_EXT_AZ_availability_zone(String oS_EXT_AZ_availability_zone) {
		OS_EXT_AZ_availability_zone = oS_EXT_AZ_availability_zone;
	}
	/**
	 * @return the launched_at
	 */
	public String getLaunched_at() {
		return launched_at;
	}
	/**
	 * @param launched_at the launched_at to set
	 */
	public void setLaunched_at(String launched_at) {
		this.launched_at = launched_at;
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
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}
	/**
	 * @param hostId the hostId to set
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	/**
	 * @return the cpu_utilization
	 */
	public String getCpu_utilization() {
		return cpu_utilization;
	}
	/**
	 * @param cpu_utilization the cpu_utilization to set
	 */
	public void setCpu_utilization(String cpu_utilization) {
		this.cpu_utilization = cpu_utilization;
	}
	/**
	 * @return the compliance_status
	 */
	public List<String> getCompliance_status() {
		return compliance_status;
	}
	/**
	 * @param compliance_status the compliance_status to set
	 */
	public void setCompliance_status(List<String> compliance_status) {
		this.compliance_status = compliance_status;
	}
	/**
	 * @return the current_compatibility_mode
	 */
	public String getCurrent_compatibility_mode() {
		return current_compatibility_mode;
	}
	/**
	 * @param current_compatibility_mode the current_compatibility_mode to set
	 */
	public void setCurrent_compatibility_mode(String current_compatibility_mode) {
		this.current_compatibility_mode = current_compatibility_mode;
	}
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the root_gb
	 */
	public Integer getRoot_gb() {
		return root_gb;
	}
	/**
	 * @param root_gb the root_gb to set
	 */
	public void setRoot_gb(Integer root_gb) {
		this.root_gb = root_gb;
	}
	/**
	 * @return the oS_EXT_SRV_ATTR_hypervisor_hostname
	 */
	public String getOS_EXT_SRV_ATTR_hypervisor_hostname() {
		return OS_EXT_SRV_ATTR_hypervisor_hostname;
	}
	/**
	 * @param oS_EXT_SRV_ATTR_hypervisor_hostname the oS_EXT_SRV_ATTR_hypervisor_hostname to set
	 */
	public void setOS_EXT_SRV_ATTR_hypervisor_hostname(
			String oS_EXT_SRV_ATTR_hypervisor_hostname) {
		OS_EXT_SRV_ATTR_hypervisor_hostname = oS_EXT_SRV_ATTR_hypervisor_hostname;
	}
	/**
	 * @return the srr_capability
	 */
	public Boolean getSrr_capability() {
		return srr_capability;
	}
	/**
	 * @param srr_capability the srr_capability to set
	 */
	public void setSrr_capability(Boolean srr_capability) {
		this.srr_capability = srr_capability;
	}
	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
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
	/**
	 * @return the memory_mb
	 */
	public Integer getMemory_mb() {
		return memory_mb;
	}
	/**
	 * @param memory_mb the memory_mb to set
	 */
	public void setMemory_mb(Integer memory_mb) {
		this.memory_mb = memory_mb;
	}
	/**
	 * @return the max_cpus
	 */
	public Integer getMax_cpus() {
		return max_cpus;
	}
	/**
	 * @param max_cpus the max_cpus to set
	 */
	public void setMax_cpus(Integer max_cpus) {
		this.max_cpus = max_cpus;
	}
	public String getHealth_status() {
		return health_status;
	}
	public void setHealth_status(String health_status) {
		this.health_status = health_status;
	}
	public Boolean getIsdisabled() {
		return isdisabled;
	}
	public void setIsdisabled(Boolean isdisabled) {
		this.isdisabled = isdisabled;
	}

	public int compareTo(ServerBean s){
		return s.getIsdisabled().compareTo(this.getIsdisabled())==0?this.getName().compareTo(s.getName()) :s.getIsdisabled().compareTo(this.getIsdisabled()) ;
	}
}
