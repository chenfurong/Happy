package com.ibm.smartcloud.openstack.neutron.constants;

public class OPSTNetworkConst {
	
	public final static String PARAM_NETWORKID = "{network_id}";
	public final static String PARAM_SUBNETID = "{subnet_id}";
	public final static String PARAM_PORTID = "{port_id}";
	public final static String PARAM_SECURITYGROUPID = "{security_group_id}";
	public final static String PARAM_SECURITYGROUPSRULESID = "{rules-security-groups-id}";
	
	public final static String PARAM_VIPID = "{vip_id}";
	public final static String PARAM_MEMBERID = "{member_id}";
	public final static String PARAM_TENANT_ID = "{tenant_id}";
	//JSON key
	public final static String NETWORKBODY = "networks";
	public final static String INTERFACEBODY = "interfaceAttachments";
	public final static String NETWORK = "network";
	public final static String NETWORK_ID = "id";
	public final static String NETWORK_NAME = "name";
	public final static String NETWORK_ADMIN_STATE_UP = "admin_state_up";
	public final static String NETWORK_SHARED = "shared";
	public final static String NETWORK_TENANT_ID = "tenant_id";
	public final static String NETWORK_STATUS = "status";
	public final static String NETWORK_SUBNETS = "subnets";
	public final static String NETWORK_ROUTER_EXTERNAL = "router:external";
	public final static String NETWORK_PROVIDER_NETWORK_TYPE = "provider:network_type";
	public final static String NETWORK_PROVIDER_PHYSICAL_NETWORK = "provider:physical_network";
	public final static String NETWORK_PROVIDER_SEGMENTATION_ID = "provider:segmentation_id";
	
	public final static String SUBNETBODY = "subnets";
	public final static String SUBNET = "subnet";
	public final static String SUBNET_ID = "id";
	public final static String SUBNET_NAME = "name";
	public final static String SUBNET_ENABLE_DHCP = "enable_dhcp";
	public final static String SUBNET_NETWORK_ID = "network_id";
	public final static String SUBNET_TENANT_ID = "tenant_id";
	public final static String SUBNET_DNS_NAMESERVERS = "dns_nameservers";
	public final static String SUBNET_ALLOCATION_POOLS = "allocation_pools";
	public final static String SUBNET_HOST_ROUTES = "host_routes";
	public final static String SUBNET_IP_VERSION = "ip_version";
	public final static String SUBNET_GATEWAY_IP = "gateway_ip";
	public final static String SUBNET_CIDR = "cidr";
	public final static String SUBNET_ALLOCATION_POOLS_START = "start";
	public final static String SUBNET_ALLOCATION_POOLS_END = "end";
	
	public final static String PORTBODY = "ports";
	public final static String PORT = "port";
	public final static String PORT_ID = "id";
	public final static String PORT_DEVICE_ID = "device_id";
	public final static String PORT_NAME = "name";
	public final static String PORT_STATUS = "status";
	public final static String PORT_BINDING_HOST_ID = "binding:host_id";
	public final static String PORT_ALLOWED_ADRESS_PAIRS = "allowed_address_pairs";
	public final static String PORT_ADMIN_STATE_UP = "admin_state_up";
	public final static String PORT_NETWORK_ID = "network_id";
	public final static String PORT_TENANT_ID = "tenant_id";
	public final static String PORT_EXTRA_DHCP_OPTS = "extra_dhcp_opts";
	public final static String PORT_BINDING_VIF_DETAILS = "binding:vif_details";
	public final static String PORT_BINDING_VIF_TYPE = "binding:vif_type";
	public final static String PORT_DEVICE_OWNER = "device_owner";
	public final static String PORT_MAC_ADRESS = "mac_address";
	public final static String PORT_BINDING_PROFILE = "binding:profile";
	public final static String PORT_BINDING_VNIC_TYPE = "binding:vnic_type";
	public final static String PORT_FIXED_IPS = "fixed_ips";
	public final static String PORT_SECURITY_GROUPS = "security_groups";
	public final static String BINDING_VIF_DETAILS_PORT_FILTER = "port_filter";
	public final static String BINDING_VIF_DETAILS_OVS_HYBRID_PLUG = "ovs_hybrid_plug";
	public final static String FIXED_IPS_SUBNET_ID = "subnet_id";
	public final static String FIXED_IPS_IP_ADRESS = "ip_address";
	
	//安全组
	public final static String SECURITYGROUPBODY = "security_groups";
	public final static String SECURITYGROUP = "security_group";
	public final static String SECURITY_DESCRIPTION = "description";
	public final static String SECURITY_ID = "id";
	public final static String SECURITY_NAME = "name";
	public final static String SECURITY_SECURITY_GROUP_RULES = "security_group_rules";
	public final static String SECURITY_TENANT_ID = "tenant_id";
	//安全组 规则
	public final static String SECURITY_SECURITY_GROUP_RULE = "security_group_rule";
	public final static String SGRULE_REMOTEGROUPID = "remote_group_id";
	public final static String SGRULE_DIRECTION = "direction";
	public final static String SGRULE_REMOTEIPPREFIX = "remote_ip_prefix";
	public final static String SGRULE_PROTOCOL = "protocol";
	public final static String SGRULE_ETHERTYPE = "ethertype";
	public final static String SGRULE_TENANT_ID = "tenant_id";
	public final static String SGRULE_PORTRANGEMAX = "port_range_max";
	public final static String SGRULE_PORTRANGEMIN = "port_range_min";
	public final static String SGRULE_ID = "id";
	public final static String SGRULE_SECURITY_GROUP_ID = "security_group_id";
	
	//quotas
	public final static String QUOTAS_FLOATINTIP = "floatingip";
	public final static String QUOTAS_NETWORK = "network";
	public final static String QUOTAS_PORT = "port";
	public final static String QUOTAS_ROUTER = "router";
	public final static String QUOTAS_SUBNET = "subnet";
	public final static String QUOTAS_SECURITY_GROUP = "security_group";
	public final static String QUOTAS_SECURITY_GROUP_RULE = "security_group_rule";
	public final static String QUOTAS_TENANT_ID = "{tenant_id}";
	public final static String QUOTAS_QUOTAS = "quotas";
	public final static String QUOTAS_QUOTA = "quota";
	
	//LBaas
	//VIP
	public final static String VIPBODY = "vips";
	public final static String VIP = "vip";
	public final static String VIP_ID = "id";
	
	public final static String VIP_ADDRESS = "address";
	public final static String VIP_ADMIN_STATE_UP = "admin_state_up";
	public final static String VIP_CONNECTION_LIMIT = "connection_limit";
	public final static String VIP_DESCRIPTION = "description";
	public final static String VIP_STATUS_DESCRIPTION = "status_description";
	
	public final static String VIP_NAME = "name";
	public final static String VIP_POOL_ID = "pool_id";
	public final static String VIP_PORT_ID = "port_id";
	public final static String VIP_PROTOCOL = "protocol";
	public final static String VIP_PROTOCOL_PORT = "protocol_port";
	
	public final static String VIP_SESSION_PERSISTENCE = "session_persistence";
	public final static String COOKIE_NAME = "cookie_name";
	public final static String TYPE = "type";
	public final static String VIP_STATUS = "status";
	public final static String VIP_SUBNET_ID = "subnet_id";
	public final static String VIP_TENANT_ID = "tenant_id";
	
	//Health Monitor
	public final static String HEALTHMONITORBODY = "health_monitors";
	public final static String HEALTHMONITOR = "health_monitor";
	public final static String HEALTHMONITOR_HEALTH_MONITOR_ID = "{health_monitor_id}";
	
	public final static String HEALTHMONITOR_ADMIN_STATE_UP = "admin_state_up";
	public final static String HEALTHMONITOR_DELAY = "delay";
	public final static String HEALTHMONITOR_EXPECTED_CODES = "expected_codes";
	public final static String HEALTHMONITOR_HTTP_METHOD = "http_method";
	public final static String HEALTHMONITOR_ID = "id";
	
	public final static String HEALTHMONITOR_MAX_RETRIES = "max_retries";
	public final static String HEALTHMONITOR_POOLS = "pools";
	public final static String HEALTHMONITOR_TENANT_ID = "tenant_id";
	public final static String HEALTHMONITOR_TIMEOUT = "timeout";
	public final static String HEALTHMONITOR_TYPE = "type";
	public final static String HEALTHMONITOR_URL_PATH = "url_path";
	
	//Pool
	public final static String POOLBODY = "pools";
	public final static String POOL = "pool";
	public final static String POOL_POOL_ID = "{pool_id}";
	public final static String POOL_HEALTH_MONITOR_ID = "{health_monitor_id}";
	
	public final static String POOL_ADMIN_STATE_UP = "admin_state_up";
	public final static String POOL_DESCRIPTION = "description";
	public final static String POOL_HEALTH_MONITORS = "health_monitors";
	public final static String POOL_HEALTH_MONITORS_STATUS = "health_monitors_status";
	public final static String POOL_ID = "id";
	
	public final static String POOL_LB_METHOD = "lb_method";
	public final static String POOL_MEMBERS = "members";
	public final static String POOL_NAME = "name";
	public final static String POOL_PROTOCOL = "protocol";
	public final static String POOL_PROVIDER = "provider";
	
	public final static String POOL_STATUS = "status";
	public final static String POOL_STATUS_DESCRIPTION = "status_description";
	public final static String POOL_SUBNET_ID = "subnet_id";
	public final static String POOL_TENANT_ID = "tenant_id";
	public final static String POOL_VIP_ID = "vip_id";
	
	//Member
	public final static String MEMBERBODY = "members";
	public final static String MEMBER = "member";
	
	public final static String MEMBER_ADMIN_STATE_UP = "admin_state_up";
	public final static String MEMBER_ADDRESS = "address";
	public final static String MEMBER_ID = "id";
	public final static String MEMBER_POOL_ID = "pool_id";
	public final static String MEMBER_PROTOCOL = "protocol";
	
	public final static String MEMBER_STATUS_DESCRIPTION = "status_description";
	public final static String MEMBER_PROTOCOL_PORT = "protocol_port";
	public final static String MEMBER_STATUS = "status";
	public final static String MEMBER_TENANT_ID = "tenant_id";	
	public final static String MEMBER_WEIGHT = "weight";
	
	//OPSTHealthMonitorsStatusBean
	public final static String HealthMonitorsStatus_MONITOR_ID= "monitor_id";
	public final static String HealthMonitorsStatus_STATUS= "status";
	public final static String HealthMonitorsStatus_STATUS_DESCRIPTION= "status_description";
	
	//OPSTPoolOfHealthMonitorBean
	public final static String PoolOfHealthMonitor_POOL_ID= "pool_id";
	public final static String PoolOfHealthMonitor_STATUS= "status";
	public final static String PoolOfHealthMonitor_STATUS_DESCRIPTION= "status_description";
	
}
