package com.ibm.smartcloud.openstack.nova.constants;

public class OPSTComputeConst {
	
	//OpenStack Server Status
	public final static String SERVER_STATUS_ACTIVE = "ACTIVE";
	public final static String SERVER_STATUS_BUILD = "BUILD";
	public final static String SERVER_STATUS_DELETED = "DELETED";
	public final static String SERVER_STATUS_ERROR = "ERROR";
	public final static String SERVER_STATUS_HARD_REBOOT = "HARD_REBOOT";
	public final static String SERVER_STATUS_PASSWORD = "PASSWORD";
	public final static String SERVER_STATUS_REBOOT = "REBOOT";
	public final static String SERVER_STATUS_REBUILD = "REBUILD";
	public final static String SERVER_STATUS_RESCUE = "RESCUE";
	public final static String SERVER_STATUS_RESIZE = "RESIZE";
	public final static String SERVER_STATUS_REVERT_RESIZE = "REVERT_RESIZE";
	public final static String SERVER_STATUS_SHUTOFF = "SHUTOFF";
	public final static String SERVER_STATUS_SUSPENDED = "SUSPENDED";
	public final static String SERVER_STATUS_UNKNOWN = "UNKNOWN";
	public final static String SERVER_STATUS_VERIFY_RESIZE = "VERIFY_RESIZE";
	
	public final static String PARAM_TENANTID = "{tenant_id}";
	public final static String PARAM_SERVERID = "{server_id}";
	public final static String KEYPAIR_NAMES = "{keypair_name}";
	public final static String PARAM_USERID = "{user_id}";
	public final static String PARAM_RESOURCEID = "{resource_id}";
	public final static String PARAM_METERNAME = "{meter_name}";
	//数据盘 ext
	public final static String PARAM_EXT_ATTACHMENT_ID = "{attachment_id}";
		
	//JSON key
	public final static String SERVERBODY = "servers";
	public final static String AVAILABILITYZONEINFO = "availabilityZoneInfo";
	public final static String SERVER = "server";
	public final static String CONSOLE = "console";
	public final static String SERVICEBODY = "services";
	public final static String AGENTBODY = "agents";
	 
	public final static String KEYPAIR = "keypair";
	public final static String KEYPAIR_NAME = "name";
	public final static String KEYPAIR_KEY = "public_key";
	public final static String SERVER_NAME = "name";
	public final static String SERVER_KEYPAIR = "key_name";
	public final static String SERVER_IMAGEID = "imageRef";
	public final static String SERVER_FLAVORID = "flavorRef";
	public final static String SERVER_MAXCOUNT = "max_count";
	public final static String AVAILABILITY_ZONE = "availability_zone";
	public final static String SERVER_MINCOUNT = "min_count";
	public final static String SERVER_UUID = "uuid";
	public final static String SECURITY_NAME = "name";
	public final static String SECURITY_GROUP = "security_groups";
	public final static String NETWORKBODY = "networks";
	public final static String NETWORK = "network";
	
	
	//limits
	public final static String SERVER_LIMITS = "limits";	
	public final static String SERVER_LIMITS_RATE = "rate";
	public final static String SERVER_LIMITS_ABSOLUTE = "absolute";
	public final static String SERVER_LIMITS_MAXSERVERMETA = "maxServerMeta";
	public final static String SERVER_LIMITS_MAXPERSONALITY = "maxPersonality";
	public final static String SERVER_LIMITS_MAXIMAGEMETA = "maxImageMeta";
	public final static String SERVER_LIMITS_MAXPERSONALITYSIZE = "maxPersonalitySize";
	public final static String SERVER_LIMITS_MAXSECURITYGROUPRULES = "maxSecurityGroupRules";

	public final static String SERVER_LIMITS_MAXTOTALKEYPAIRS = "maxTotalKeypairs";
	public final static String SERVER_LIMITS_TOTALRAMUSED = "totalRAMUsed";
	public final static String SERVER_LIMITS_TOTALINSTANCESUSED = "totalInstancesUsed";
	public final static String SERVER_LIMITS_MAXSECURITYGROUPS = "maxSecurityGroups";
	public final static String SERVER_LIMITS_TOTALFLOATINGIPSUSED = "totalFloatingIpsUsed";

	public final static String SERVER_LIMITS_MAXTOTALCORES = "maxTotalCores";
	public final static String SERVER_LIMITS_TOTALSECURITYGROUPSUSED = "totalSecurityGroupsUsed";
	public final static String SERVER_LIMITS_MAXTOTALFLOATINGIPS = "maxTotalFloatingIps";
	public final static String SERVER_LIMITS_MAXTOTALINSTANCES = "maxTotalInstances";
	public final static String SERVER_LIMITS_TOTALCORESUSED = "totalCoresUsed";
	public final static String SERVER_LIMITS_MAXTOTALRAMSIZE = "maxTotalRAMSize";


	//项目 概况 Usage reports tenant_usage
	public final static String EXT_USAGEREPORTS_TENANT_USAGE = "tenant_usage";
	public final static String EXT_USAGEREPORTS_TU_START ="start";
	public final static String EXT_USAGEREPORTS_TU_STOP ="stop";
	public final static String EXT_USAGEREPORTS_TU_TENANT_ID ="tenant_id";
	public final static String EXT_USAGEREPORTS_TU_TOTAL_HOURS = "total_hours";
	public final static String EXT_USAGEREPORTS_TU_TOTAL_LOCAL_GB_USAGE ="total_local_gb_usage";
	public final static String EXT_USAGEREPORTS_TU_TOTAL_MEMORY_MB_USAGE ="total_memory_mb_usage";
	public final static String EXT_USAGEREPORTS_TU_TOTAL_VCPUS_USAGE ="total_vcpus_usage";	
	
	public final static String EXT_USAGEREPORTS_TU_SERVER_USAGES = "server_usages";
	public final static String EXT_USAGEREPORTS_SU_INSTANCE_ID = "instance_id";
	public final static String EXT_USAGEREPORTS_SU_UPTIME = "uptime";
	public final static String EXT_USAGEREPORTS_SU_STARTED_AT = "started_at";
	public final static String EXT_USAGEREPORTS_SU_ENDED_AT = "ended_at";
	public final static String EXT_USAGEREPORTS_SU_MEMORY_MB = "memory_mb";
	public final static String EXT_USAGEREPORTS_SU_TENANT_ID = "tenant_id";
	public final static String EXT_USAGEREPORTS_SU_STATE = "state";
	public final static String EXT_USAGEREPORTS_SU_HOURS = "hours";
	public final static String EXT_USAGEREPORTS_SU_VCPUS = "vcpus";
	public final static String EXT_USAGEREPORTS_SU_FLAVOR = "flavor";
	public final static String EXT_USAGEREPORTS_SU_LOCAL_GB = "local_gb";
	public final static String EXT_USAGEREPORTS_SU_NAME = "name";

	//admin 概况  hypervisor
	public final static String EXT_HYPERVISORS = "hypervisors";
	public final static String EXT_HYPERVISORS_VCPUS_USED = "vcpus_used";
	public final static String EXT_HYPERVISORS_HYPERVISOR_TYPE = "hypervisor_type";
	public final static String EXT_HYPERVISORS_LOCAL_GB_USED = "local_gb_used";
	public final static String EXT_HYPERVISORS_HYPERVISOR_HOSTNAME = "hypervisor_hostname";
	public final static String EXT_HYPERVISORS_MEMORY_MB_USED = "memory_mb_used";
	public final static String EXT_HYPERVISORS_MEMORY_MB = "memory_mb";
	public final static String EXT_HYPERVISORS_CURRENT_WORKLOAD = "current_workload";
	public final static String EXT_HYPERVISORS_VCPUS = "vcpus";
	public final static String EXT_HYPERVISORS_CPU_INFO = "cpu_info"; 
	public final static String EXT_HYPERVISORS_RUNNING_VMS = "running_vms"; 
	public final static String EXT_HYPERVISORS_FREE_DISK_GB = "free_disk_gb";
	public final static String EXT_HYPERVISORS_VERSION = "hypervisor_version";
	public final static String EXT_HYPERVISORS_DISK_AVAILABLE_LEAST = "disk_available_least";
	public final static String EXT_HYPERVISORS_LOCAL_GB = "local_gb";
	public final static String EXT_HYPERVISORS_FREE_RAM_MB = "free_ram_mb"; 
	public final static String EXT_HYPERVISORS_ID = "id";
	
	//扩展  Volume attachments (os-volume_attachments)
	public final static String EXT_VOLUME_VOLUMEATTACHMENT = "volumeAttachment";
	public final static String EXT_VOLUME_VOLUMEID = "volumeId";
	public final static String EXT_VOLUME_DEVICE = "device";
	public final static String EXT_VOLUME_ATTACHMENTS = "attachments";
	public final static String EXT_ATTACHMENTS_DEVICE = "device";
	public final static String EXT_ATTACHMENTS_SERVER_ID = "serverId";
	public final static String EXT_ATTACHMENTS_ID = "id";
	public final static String EXT_ATTACHMENTS_HOST_NAME = "host_name";
	


	
}
