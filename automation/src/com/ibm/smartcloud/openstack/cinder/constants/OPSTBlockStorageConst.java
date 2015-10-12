package com.ibm.smartcloud.openstack.cinder.constants;

public class OPSTBlockStorageConst {
	
	public final static String PARAM_TENANTID = "{tenant_id}";
	public final static String PARAM_VOLUMEID = "{volume_id}";//Volumes​
	public final static String PARAM_VOLUMETYPEID = "{volume_type_id}​";//Volume types
	public final static String PARAM_SNAPSHOTID = "{snapshot_id}";//Snapshots
	
	//JSON key
	public final static String VOLUMES = "volumes";
	public final static String VOLUME = "volume";
	public final static String VOLUME_ID = "id";
	public final static String VOLUME_NAME = "name";
	public final static String VOLUME_STATUS = "status";
	public final static String VOLUME_ATTACHMENTS = "attachments";
	public final static String VOLUME_AVAILABILITY_ZONE = "availability_zone";
	public final static String VOLUME_OS_VOL_HOST_ATTR_HOST = "os-vol-host-attr:host";
	public final static String VOLUME_SOURCE_VOLID = "source_volid";
	public final static String VOLUME_SNAPSHOT_ID = "snapshot_id";
	public final static String VOLUME_DESCRIPTION = "description";
	public final static String VOLUME_CREATED_AT = "created_at";
	public final static String VOLUME_VOLUME_TYPE = "volume_type";
	public final static String VOLUME_OS_VOL_TENANT_ATTR_TENANT_ID = "os-vol-tenant-attr:tenant_id";
	public final static String VOLUME_SIZE = "size";
	public final static String VOLUME_METADATA = "metadata";
	public final static String VOLUME_DISPLAY_NAME = "display_name";
	public final static String VOLUME_DISPLAY_DESCRIPTION = "display_description";
	public final static String VOLUME_IMAGEREF="imageRef";
	
	
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
	
	//limits
	public final static String BLOCKSTORAGE_LIMITS = "limits";
	public final static String BLOCKSTORAGE_LIMITS_RATE = "rate";
	public final static String BLOCKSTORAGE_LIMITS_ABSOLUTE = "absolute";
	public final static String BLOCKSTORAGE_LIMITS_TOTALSNAPSHOTSUSED = "totalSnapshotsUsed";
	public final static String BLOCKSTORAGE_LIMITS_MAXTOTALVOLUMEGIGABYTES = "maxTotalVolumeGigabytes";
	public final static String BLOCKSTORAGE_LIMITS_TOTALGIGABYTESUSED = "totalGigabytesUsed";
	public final static String BLOCKSTORAGE_LIMITS_MAXTOTALSNAPSHOTS = "maxTotalSnapshots";
	public final static String BLOCKSTORAGE_LIMITS_TOTALVOLUMESUSED = "totalVolumesUsed";
	public final static String BLOCKSTORAGE_LIMITS_MAXTOTALVOLUMES = "maxTotalVolumes";
	
	//
	public final static String VOLUME_TENANT_QUOTA_SET = "quota_set";
	public final static String VOLUME_TENANT_GIGABYTES ="gigabytes";
	public final static String VOLUME_TENANT_GIGABYTES_LIMIT = "limit";
	public final static String VOLUME_TENANT_GIGABYTES_IN_USE = "in_use";
	public final static String VOLUME_TENANT_VOLUMES = "volumes";
	public final static String VOLUME_TENANT_VOLUMES_LIMIT ="limit";
	public final static String VOLUME_TENANT_VOLUMES_IN_USE = "in_use";
	
	//
	public final static String EXT_VOLUME_ATTACHMENTS = "attachments";
	public final static String EXT_ATTACHMENTS_VOLUME_ID = "volume_id";
	public final static String EXT_ATTACHMENTS_DEVICE = "device";
	public final static String EXT_ATTACHMENTS_SERVER_ID = "server_id";
	public final static String EXT_ATTACHMENTS_ID = "id";
	public final static String EXT_ATTACHMENTS_HOST_NAME = "host_name";
	
	
	

	
}
