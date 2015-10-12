package com.ibm.smartcloud.openstack.nova.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:HypervisorsBean     
 * @Description: 物理主机节点
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:24:54       
 * @version V1.0
 */
public class HypervisorsBean implements Serializable{
	private static final long serialVersionUID = 8795927658899570377L;
	
	private Double proc_units_reserved;
	private Integer ibmi_capable;
	private Integer active_lpar_mobility_capable;
	private Integer memory_mb_reserved;
    private String hypervisor_state = "";
    private String cpu_info =""; 
    private Integer active_migrations_supported;
    private Integer little_endian_guest_capable;
    private Integer free_disk_gb;
    private Integer inactive_migrations_supported; 
    private Integer memory_mb_used; 
    private String disk_available = "";
    private ServiceBean service = null;
    private String usable_local_mb = "";
    private String maintenance_status = "";
    private Integer local_gb_used;
    private Integer remote_restart_enabled;
    private Integer id;
    private Integer current_workload;
    private String state = "";
    private Integer aix_capable;
    private Integer proc_units;
    private String status = "";
    private String cpu_utilization = "";
    private String maintenance_migrate_action = "";
    private String host_ip = "";
    private String hypervisor_hostname = ""; 
    private Integer lmb_size;
    private String disk_total = "";
    private Integer powervm_lpar_simplified_remote_restart_capable; 
    private Integer hypervisor_version;
    private String disk_available_least = "";
    private List<String> compatibility_modes;
    private Integer local_gb;
    private Integer free_ram_mb; 
    private String ha_status = ""; 
    private HealthStatusBean healthStatus = null;
    private Integer vcpus_used;
    private String hypervisor_type;
    private String max_procs_per_aix_linux_lpar;
    private String max_vcpus_per_aix_linux_lpar; 
    private Integer memory_mb;
    private Integer vcpus;
    private Integer running_vms;
    private Integer linux_capable;
    private Integer inactive_migrations_in_progress; 
    private Integer proc_units_used;
    private String disk_used;
    private Integer active_migrations_in_progress;
	/**
	 * @return the proc_units_reserved
	 */
	public Double getProc_units_reserved() {
		return proc_units_reserved;
	}
	/**
	 * @param proc_units_reserved the proc_units_reserved to set
	 */
	public void setProc_units_reserved(Double proc_units_reserved) {
		this.proc_units_reserved = proc_units_reserved;
	}
	/**
	 * @return the ibmi_capable
	 */
	public Integer getIbmi_capable() {
		return ibmi_capable;
	}
	/**
	 * @param ibmi_capable the ibmi_capable to set
	 */
	public void setIbmi_capable(Integer ibmi_capable) {
		this.ibmi_capable = ibmi_capable;
	}
	/**
	 * @return the active_lpar_mobility_capable
	 */
	public Integer getActive_lpar_mobility_capable() {
		return active_lpar_mobility_capable;
	}
	/**
	 * @param active_lpar_mobility_capable the active_lpar_mobility_capable to set
	 */
	public void setActive_lpar_mobility_capable(Integer active_lpar_mobility_capable) {
		this.active_lpar_mobility_capable = active_lpar_mobility_capable;
	}
	/**
	 * @return the memory_mb_reserved
	 */
	public Integer getMemory_mb_reserved() {
		return memory_mb_reserved;
	}
	/**
	 * @param memory_mb_reserved the memory_mb_reserved to set
	 */
	public void setMemory_mb_reserved(Integer memory_mb_reserved) {
		this.memory_mb_reserved = memory_mb_reserved;
	}
	/**
	 * @return the hypervisor_state
	 */
	public String getHypervisor_state() {
		return hypervisor_state;
	}
	/**
	 * @param hypervisor_state the hypervisor_state to set
	 */
	public void setHypervisor_state(String hypervisor_state) {
		this.hypervisor_state = hypervisor_state;
	}
	/**
	 * @return the cpu_info
	 */
	public String getCpu_info() {
		return cpu_info;
	}
	/**
	 * @param cpu_info the cpu_info to set
	 */
	public void setCpu_info(String cpu_info) {
		this.cpu_info = cpu_info;
	}
	/**
	 * @return the active_migrations_supported
	 */
	public Integer getActive_migrations_supported() {
		return active_migrations_supported;
	}
	/**
	 * @param active_migrations_supported the active_migrations_supported to set
	 */
	public void setActive_migrations_supported(Integer active_migrations_supported) {
		this.active_migrations_supported = active_migrations_supported;
	}
	/**
	 * @return the little_endian_guest_capable
	 */
	public Integer getLittle_endian_guest_capable() {
		return little_endian_guest_capable;
	}
	/**
	 * @param little_endian_guest_capable the little_endian_guest_capable to set
	 */
	public void setLittle_endian_guest_capable(Integer little_endian_guest_capable) {
		this.little_endian_guest_capable = little_endian_guest_capable;
	}
	/**
	 * @return the free_disk_gb
	 */
	public Integer getFree_disk_gb() {
		return free_disk_gb;
	}
	/**
	 * @param free_disk_gb the free_disk_gb to set
	 */
	public void setFree_disk_gb(Integer free_disk_gb) {
		this.free_disk_gb = free_disk_gb;
	}
	/**
	 * @return the inactive_migrations_supported
	 */
	public Integer getInactive_migrations_supported() {
		return inactive_migrations_supported;
	}
	/**
	 * @param inactive_migrations_supported the inactive_migrations_supported to set
	 */
	public void setInactive_migrations_supported(
			Integer inactive_migrations_supported) {
		this.inactive_migrations_supported = inactive_migrations_supported;
	}
	/**
	 * @return the memory_mb_used
	 */
	public Integer getMemory_mb_used() {
		return memory_mb_used;
	}
	/**
	 * @param memory_mb_used the memory_mb_used to set
	 */
	public void setMemory_mb_used(Integer memory_mb_used) {
		this.memory_mb_used = memory_mb_used;
	}
	/**
	 * @return the disk_available
	 */
	public String getDisk_available() {
		return disk_available;
	}
	/**
	 * @param disk_available the disk_available to set
	 */
	public void setDisk_available(String disk_available) {
		this.disk_available = disk_available;
	}
	/**
	 * @return the service
	 */
	public ServiceBean getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(ServiceBean service) {
		this.service = service;
	}
	/**
	 * @return the usable_local_mb
	 */
	public String getUsable_local_mb() {
		return usable_local_mb;
	}
	/**
	 * @param usable_local_mb the usable_local_mb to set
	 */
	public void setUsable_local_mb(String usable_local_mb) {
		this.usable_local_mb = usable_local_mb;
	}
	/**
	 * @return the maintenance_status
	 */
	public String getMaintenance_status() {
		return maintenance_status;
	}
	/**
	 * @param maintenance_status the maintenance_status to set
	 */
	public void setMaintenance_status(String maintenance_status) {
		this.maintenance_status = maintenance_status;
	}
	/**
	 * @return the local_gb_used
	 */
	public Integer getLocal_gb_used() {
		return local_gb_used;
	}
	/**
	 * @param local_gb_used the local_gb_used to set
	 */
	public void setLocal_gb_used(Integer local_gb_used) {
		this.local_gb_used = local_gb_used;
	}
	/**
	 * @return the remote_restart_enabled
	 */
	public Integer getRemote_restart_enabled() {
		return remote_restart_enabled;
	}
	/**
	 * @param remote_restart_enabled the remote_restart_enabled to set
	 */
	public void setRemote_restart_enabled(Integer remote_restart_enabled) {
		this.remote_restart_enabled = remote_restart_enabled;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the current_workload
	 */
	public Integer getCurrent_workload() {
		return current_workload;
	}
	/**
	 * @param current_workload the current_workload to set
	 */
	public void setCurrent_workload(Integer current_workload) {
		this.current_workload = current_workload;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the aix_capable
	 */
	public Integer getAix_capable() {
		return aix_capable;
	}
	/**
	 * @param aix_capable the aix_capable to set
	 */
	public void setAix_capable(Integer aix_capable) {
		this.aix_capable = aix_capable;
	}
	/**
	 * @return the proc_units
	 */
	public Integer getProc_units() {
		return proc_units;
	}
	/**
	 * @param proc_units the proc_units to set
	 */
	public void setProc_units(Integer proc_units) {
		this.proc_units = proc_units;
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
	 * @return the maintenance_migrate_action
	 */
	public String getMaintenance_migrate_action() {
		return maintenance_migrate_action;
	}
	/**
	 * @param maintenance_migrate_action the maintenance_migrate_action to set
	 */
	public void setMaintenance_migrate_action(String maintenance_migrate_action) {
		this.maintenance_migrate_action = maintenance_migrate_action;
	}
	/**
	 * @return the host_ip
	 */
	public String getHost_ip() {
		return host_ip;
	}
	/**
	 * @param host_ip the host_ip to set
	 */
	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}
	/**
	 * @return the hypervisor_hostname
	 */
	public String getHypervisor_hostname() {
		return hypervisor_hostname;
	}
	/**
	 * @param hypervisor_hostname the hypervisor_hostname to set
	 */
	public void setHypervisor_hostname(String hypervisor_hostname) {
		this.hypervisor_hostname = hypervisor_hostname;
	}
	/**
	 * @return the lmb_size
	 */
	public Integer getLmb_size() {
		return lmb_size;
	}
	/**
	 * @param lmb_size the lmb_size to set
	 */
	public void setLmb_size(Integer lmb_size) {
		this.lmb_size = lmb_size;
	}
	/**
	 * @return the disk_total
	 */
	public String getDisk_total() {
		return disk_total;
	}
	/**
	 * @param disk_total the disk_total to set
	 */
	public void setDisk_total(String disk_total) {
		this.disk_total = disk_total;
	}
	/**
	 * @return the powervm_lpar_simplified_remote_restart_capable
	 */
	public Integer getPowervm_lpar_simplified_remote_restart_capable() {
		return powervm_lpar_simplified_remote_restart_capable;
	}
	/**
	 * @param powervm_lpar_simplified_remote_restart_capable the powervm_lpar_simplified_remote_restart_capable to set
	 */
	public void setPowervm_lpar_simplified_remote_restart_capable(
			Integer powervm_lpar_simplified_remote_restart_capable) {
		this.powervm_lpar_simplified_remote_restart_capable = powervm_lpar_simplified_remote_restart_capable;
	}
	/**
	 * @return the hypervisor_version
	 */
	public Integer getHypervisor_version() {
		return hypervisor_version;
	}
	/**
	 * @param hypervisor_version the hypervisor_version to set
	 */
	public void setHypervisor_version(Integer hypervisor_version) {
		this.hypervisor_version = hypervisor_version;
	}
	/**
	 * @return the disk_available_least
	 */
	public String getDisk_available_least() {
		return disk_available_least;
	}
	/**
	 * @param disk_available_least the disk_available_least to set
	 */
	public void setDisk_available_least(String disk_available_least) {
		this.disk_available_least = disk_available_least;
	}
	/**
	 * @return the compatibility_modes
	 */
	public List<String> getCompatibility_modes() {
		return compatibility_modes;
	}
	/**
	 * @param compatibility_modes the compatibility_modes to set
	 */
	public void setCompatibility_modes(List<String> compatibility_modes) {
		this.compatibility_modes = compatibility_modes;
	}
	/**
	 * @return the local_gb
	 */
	public Integer getLocal_gb() {
		return local_gb;
	}
	/**
	 * @param local_gb the local_gb to set
	 */
	public void setLocal_gb(Integer local_gb) {
		this.local_gb = local_gb;
	}
	/**
	 * @return the free_ram_mb
	 */
	public Integer getFree_ram_mb() {
		return free_ram_mb;
	}
	/**
	 * @param free_ram_mb the free_ram_mb to set
	 */
	public void setFree_ram_mb(Integer free_ram_mb) {
		this.free_ram_mb = free_ram_mb;
	}
	/**
	 * @return the ha_status
	 */
	public String getHa_status() {
		return ha_status;
	}
	/**
	 * @param ha_status the ha_status to set
	 */
	public void setHa_status(String ha_status) {
		this.ha_status = ha_status;
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
	 * @return the vcpus_used
	 */
	public Integer getVcpus_used() {
		return vcpus_used;
	}
	/**
	 * @param vcpus_used the vcpus_used to set
	 */
	public void setVcpus_used(Integer vcpus_used) {
		this.vcpus_used = vcpus_used;
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
	 * @return the max_procs_per_aix_linux_lpar
	 */
	public String getMax_procs_per_aix_linux_lpar() {
		return max_procs_per_aix_linux_lpar;
	}
	/**
	 * @param max_procs_per_aix_linux_lpar the max_procs_per_aix_linux_lpar to set
	 */
	public void setMax_procs_per_aix_linux_lpar(String max_procs_per_aix_linux_lpar) {
		this.max_procs_per_aix_linux_lpar = max_procs_per_aix_linux_lpar;
	}
	/**
	 * @return the max_vcpus_per_aix_linux_lpar
	 */
	public String getMax_vcpus_per_aix_linux_lpar() {
		return max_vcpus_per_aix_linux_lpar;
	}
	/**
	 * @param max_vcpus_per_aix_linux_lpar the max_vcpus_per_aix_linux_lpar to set
	 */
	public void setMax_vcpus_per_aix_linux_lpar(String max_vcpus_per_aix_linux_lpar) {
		this.max_vcpus_per_aix_linux_lpar = max_vcpus_per_aix_linux_lpar;
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
	 * @return the vcpus
	 */
	public Integer getVcpus() {
		return vcpus;
	}
	/**
	 * @param vcpus the vcpus to set
	 */
	public void setVcpus(Integer vcpus) {
		this.vcpus = vcpus;
	}
	/**
	 * @return the running_vms
	 */
	public Integer getRunning_vms() {
		return running_vms;
	}
	/**
	 * @param running_vms the running_vms to set
	 */
	public void setRunning_vms(Integer running_vms) {
		this.running_vms = running_vms;
	}
	/**
	 * @return the linux_capable
	 */
	public Integer getLinux_capable() {
		return linux_capable;
	}
	/**
	 * @param linux_capable the linux_capable to set
	 */
	public void setLinux_capable(Integer linux_capable) {
		this.linux_capable = linux_capable;
	}
	/**
	 * @return the inactive_migrations_in_progress
	 */
	public Integer getInactive_migrations_in_progress() {
		return inactive_migrations_in_progress;
	}
	/**
	 * @param inactive_migrations_in_progress the inactive_migrations_in_progress to set
	 */
	public void setInactive_migrations_in_progress(
			Integer inactive_migrations_in_progress) {
		this.inactive_migrations_in_progress = inactive_migrations_in_progress;
	}
	/**
	 * @return the proc_units_used
	 */
	public Integer getProc_units_used() {
		return proc_units_used;
	}
	/**
	 * @param proc_units_used the proc_units_used to set
	 */
	public void setProc_units_used(Integer proc_units_used) {
		this.proc_units_used = proc_units_used;
	}
	/**
	 * @return the disk_used
	 */
	public String getDisk_used() {
		return disk_used;
	}
	/**
	 * @param disk_used the disk_used to set
	 */
	public void setDisk_used(String disk_used) {
		this.disk_used = disk_used;
	}
	/**
	 * @return the active_migrations_in_progress
	 */
	public Integer getActive_migrations_in_progress() {
		return active_migrations_in_progress;
	}
	/**
	 * @param active_migrations_in_progress the active_migrations_in_progress to set
	 */
	public void setActive_migrations_in_progress(
			Integer active_migrations_in_progress) {
		this.active_migrations_in_progress = active_migrations_in_progress;
	}
    
}
