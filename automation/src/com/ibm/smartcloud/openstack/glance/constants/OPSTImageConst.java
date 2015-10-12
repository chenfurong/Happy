package com.ibm.smartcloud.openstack.glance.constants;

public class OPSTImageConst {
	
	public final static String PARAM_TENANTID = "{tenant_id}";
	public final static String PARAM_IMAGEID = "{image_id}";//Image
	public final static String PARAM_OWNERID = "{owner_id}";
	public final static String PARAM_IMAGEMETADATAKEY = "{key}​";//Image metadata
	
	//JSON key
	public final static String IMAGES = "images";
	public final static String IMAGE = "image";
	public final static String IMAGE_ID = "id";
	public final static String IMAGE_NAME = "name";
	public final static String IMAGE_STATUS = "status";
	public final static String IMAGE_CONTAINER_FORMAT = "container_format";
	public final static String IMAGE_CREATED_AT = "created_at";
	public final static String IMAGE_UPDATE_AT = "updated_at";
	public final static String IMAGE_DISK_FORMAT = "disk_format";
	public final static String IMAGE_MIN_DISK = "min_disk";
	public final static String IMAGE_ISPUBLIC = "is_public";
	public final static String IMAGE_PROTECTED = "protected";
	public final static String IMAGE_MIN_RAM = "min_ram";
	public final static String IMAGE_CHECKSUM = "checksum";
	public final static String IMAGE_SIZE = "size";
	public final static String IMAGE_VISIBILITY = "visibility";
	public final static String IMAGE_FILE = "file";
	public final static String IMAGE_DESCRIPTION= "description";
	public final static String IMAGE_OWNER = "owner";
	
	public final static String IMAGE_OS_TYPE = "os_type";
	public final static String IMAGE_OS_NAME = "os_name";
	
	public final static String IMAGE_TYPE = "image_type";
	
	public final static String IMAGE_HYPERVISOR_TYPE = "hypervisor_type";
	
	public final static String VOLUME_TYPES="volume_types";
	public final static String VOLUME_TYPES_ID = "id";
	public final static String VOLUME_TYPES_NAME = "name";
	public final static String VOLUME_TYPES_EXTRA_SPECS = "extra_specs";
	
	public final static String SNAPSHOTS = "snapshots";
	public final static String SNAPSHOT = "snapshot";
	public final static String SNAPSHOT_ID = "id";
	public final static String SNAPSHOT_NAME = "name";
	public final static String SNAPSHOT_STATUS = "status";
	public final static String SNAPSHOT_VOLUME_ID = "volume_id";
	public final static String SNAPSHOT_DESCRIPTION = "description";
	public final static String SNAPSHOT_CREATED_AT = "created_at";
	public final static String SNAPSHOT_SIZE = "size";
	public final static String SNAPSHOT_METADATA = "metadata";
	public final static String SNAPSHOT_DISPLAY_NAME = "display_name";
	public final static String SNAPSHOT_DISPLAY_DESCRIPTION = "display_description";
	public final static String SNAPSHOT_FORCE = "force";
	
	
	//json key for detail
	
	
	
	
	public final static String INSTANCE_TYPE_ID="instance_type_id";
	
	public final static String IMAGE_LOCATION="image_location";

	public final static String IMAGE_STATE="image_state";

	public final static String INSTANCE_TYPE_EPHEMERAL_GB="instance_type_ephemeral_gb";

	public final static String INSTANCE_TYPE_FLAVORID="instance_type_flavorid";

	public final static String INSTANCE_TYPE_MEMORY_MB="instance_type_memory_mb";

	public final static String INSTANCE_TYPE_NAME="instance_type_name";

	public final static String INSTANCE_TYPE_ROOT_GB="instance_type_root_gb";

	public final static String INSTANCE_TYPE_RXTX_FACTOR="instance_type_rxtx_factor";

	public final static String INSTANCE_TYPE_SWAP="instance_type_swap";

	public final static String INSTANCE_TYPE_VCPUS="instance_type_vcpus";

	public final static String INSTANCE_UUID="instance_uuid";

	public final static String NETWORK_ALLOCATED="network_allocated";

	public final static String OWNER_ID="owner_id";
	
	public final static String OWNER="owner";

	public final static String USER_ID="user_id";
	
	public final static String BASE_IMAGE_REF="base_image_ref";

}
