package com.ibm.smartcloud.openstack.cinder.bean;

public class OPSTBlockStorageLimitsBean {
	private String totalSnapshotsUsed = "";
	private String maxTotalVolumeGigabytes = "";
	private String totalGigabytesUsed = "";
	private String maxTotalSnapshots = "";
	private String totalVolumesUsed = "";
	private String maxTotalVolumes = "";
	public String getTotalSnapshotsUsed() {
		return totalSnapshotsUsed;
	}
	public void setTotalSnapshotsUsed(String totalSnapshotsUsed) {
		this.totalSnapshotsUsed = totalSnapshotsUsed;
	}
	public String getMaxTotalVolumeGigabytes() {
		return maxTotalVolumeGigabytes;
	}
	public void setMaxTotalVolumeGigabytes(String maxTotalVolumeGigabytes) {
		this.maxTotalVolumeGigabytes = maxTotalVolumeGigabytes;
	}
	public String getTotalGigabytesUsed() {
		return totalGigabytesUsed;
	}
	public void setTotalGigabytesUsed(String totalGigabytesUsed) {
		this.totalGigabytesUsed = totalGigabytesUsed;
	}
	public String getMaxTotalSnapshots() {
		return maxTotalSnapshots;
	}
	public void setMaxTotalSnapshots(String maxTotalSnapshots) {
		this.maxTotalSnapshots = maxTotalSnapshots;
	}
	public String getTotalVolumesUsed() {
		return totalVolumesUsed;
	}
	public void setTotalVolumesUsed(String totalVolumesUsed) {
		this.totalVolumesUsed = totalVolumesUsed;
	}
	public String getMaxTotalVolumes() {
		return maxTotalVolumes;
	}
	public void setMaxTotalVolumes(String maxTotalVolumes) {
		this.maxTotalVolumes = maxTotalVolumes;
	}
	
	

}
