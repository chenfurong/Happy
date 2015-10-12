package com.ibm.smartcloud.openstack.core.constants;

public class OPSTTaskstateConst {
	//instance  taskstate
	public final static String taskstate_None = "None";
	//# possible task states during create()
	public final static String taskstate_scheduling = "scheduling";
	public final static String taskstate_block_device_mapping = "block_device_mapping";
	public final static String taskstate_networking = "networking";
	public final static String taskstate_spawning = "spawning";
	//# possible task states during snapshot()
	public final static String taskstate_image_snapshot = "image_snapshot";
	public final static String taskstate_image_snapshot_pending = "image_snapshot_pending";
	public final static String taskstate_image_pending_upload = "image_pending_upload";
	public final static String taskstate_image_uploading = "image_uploading";
	//# possible task states during backup()
	public final static String taskstate_image_backup = "image_backup";
	//# possible task states during set_admin_password()
	public final static String taskstate_updating_password = "updating_password";
	//# possible task states during resize()
	public final static String taskstate_resize_prep = "resize_prep";
	public final static String taskstate_resize_migrating = "resize_migrating";
	public final static String taskstate_resize_migrated = "resize_migrated";
	public final static String taskstate_resize_finish = "resize_finish";
	//# possible task states during revert_resize()
	public final static String taskstate_resize_reverting = "resize_reverting";
	//# possible task states during confirm_resize()
	public final static String taskstate_resize_confirming = "resize_confirming";
	//# possible task states during reboot()
	public final static String taskstate_rebooting = "rebooting";
	public final static String taskstate_reboot_pending = "reboot_pending";
	public final static String taskstate_reboot_started = "reboot_started";
	public final static String taskstate_rebooting_hard = "rebooting_hard";
	public final static String taskstate_reboot_pending_hard = "reboot_pending_hard";
	public final static String taskstate_reboot_started_hard = "reboot_started_hard";
	//# possible task states during pause()
	public final static String taskstate_pausing = "pausing";
	//# possible task states during unpause()
	public final static String taskstate_unpausing = "unpausing";
	//# possible task states during suspend()
	public final static String taskstate_suspending = "suspending";
	//# possible task states during resume()
	public final static String taskstate_resuming = "resuming";
	//# possible task states during power_off()
	public final static String taskstate_powering_off = "powering-off";
	//# possible task states during power_on()
	public final static String taskstate_powering_on = "powering-on";
	//# possible task states during rescue()
	public final static String taskstate_rescuing = "rescuing";
	//# possible task states during unrescue()
	public final static String taskstate_unrescuing = "unrescuing";
	//# possible task states during rebuild()
	public final static String taskstate_rebuilding = "rebuilding";
	public final static String taskstate_rebuild_block_device_mapping = "rebuild_block_device_mapping";
	public final static String taskstate_rebuild_spawning = "rebuild_spawning";
	//# possible task states during live_migrate()
	public final static String taskstate_migrating = "migrating";
	//# possible task states during delete()
	public final static String taskstate_deleting = "deleting";
	//# possible task states during soft_delete()
	public final static String taskstate_soft_deleting = "soft-deleting";
	//# possible task states during restore()
	public final static String taskstate_restoring = "restoring";
	//# possible task states during shelve()
	public final static String taskstate_shelving = "shelving";
	public final static String taskstate_shelving_image_pending_upload = "shelving_image_pending_upload";
	public final static String taskstate_shelving_image_uploading = "shelving_image_uploading";
	//# possible task states during shelve_offload()
	public final static String taskstate_shelving_offloading = "shelving_offloading";
	//# possible task states during unshelve()
	public final static String taskstate_unshelving = "unshelving";
	
	public final static String taskstate_error_deleting="error_deleting";
	
	public final static String STATUS_SHELVED_OFFLOADED="SHELVED_OFFLOADED";
	
	//vloume status
	public final static String volume_state_creating = "creating";
	public final static String volume_state_available="available";
	public final static String volume_state_attaching="attaching";
	public final static String volume_state_detaching="detaching";	
	public final static String volume_state_inuse="in-use";
	public final static String volume_state_deleting="deleting";
	public final static String volume_state_error="error";
	public final static String volume_state_error_deleting="error_deleting";
	public final static String volume_state_backingup="backing-up";
	public final static String volume_state_restoring_backup="restoring-backup";
	public final static String volume_state_error_restoring="error_restoring";

	
	//snapshot status
	public final static String snapshot_state_creating = "creating";
	public final static String snapshot_state_available="available";
	public final static String snapshot_state_deleting="deleting";
	public final static String snapshot_state_error="error";
	public final static String snapshot_state_error_deleting="error_deleting";


	//image status
	public final static String image_state_queued = "queued";
	public final static String image_state_saving = "saving";
	public final static String image_state_active = "active";
	public final static String image_state_killed = "killed";
	public final static String image_state_deleted = "deleted";
	public final static String image_state_pending_delete = "pending_delete";
		
}
