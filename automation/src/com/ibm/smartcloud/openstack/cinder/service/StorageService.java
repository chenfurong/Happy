/*package com.ibm.smartcloud.openstack.cinder.service;

import java.util.List;

import com.ibm.smartcloud.openstack.cinder.bean.OPSTBlockStorageBean;
import com.ibm.smartcloud.openstack.cinder.bean.OPSTBlockStorageLimitsBean;
import com.ibm.smartcloud.openstack.cinder.bean.OPSTBlockStorageTypeBean;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
*//**
 * Block Storage V2 Service Interface
 * 
 * @author yuanhui
 *
 *//*

public interface StorageService {
	//BlockStorage数据盘
	*//**
	 * 查询所有BlockStorage
	 *//*
	public List<OPSTBlockStorageBean> getBlockStorageListV2(String tenantId,String tokenId)  throws OPSTBaseException;
	*//**
	 * 查询BlockStorage详细
	 *//*
	public List<OPSTBlockStorageBean> getBlockStorageDetialListV2(String tenantId,String tokenId) throws OPSTBaseException;
	*//**
	 * 根据ID查询BlockStorage详细
	 *//*
	public OPSTBlockStorageBean getBlockStorageByIdV2(String argVolumeId,String tenantId,String tokenId) throws OPSTBaseException;
	*//**
	 * 创建数据盘
	 *//*
	public String createBlockStorageV2(OPSTBlockStorageBean argBlockStorageBean,String tenantId,String tokenId) throws OPSTBaseException;
	*//**
	 * 根据ID修改数据盘
	 *//*
	public String updateBlockStorageByIdV2(OPSTBlockStorageBean argBlockStorageBean,String tenantId,String tokenId)throws OPSTBaseException;
	*//**
	 * 根据ID删除数据盘
	 * @throws Exception 
	 *//*
	public Boolean deleteBlockStorageByIdV2(String argVolumeId,String tenantId,String tokenId) throws OPSTBaseException;
	*//**
	 * 查询数据盘的类型
	 *//*
	public List<OPSTBlockStorageTypeBean> getStorageTypeListV2(String tenantId,String tokenId)throws OPSTBaseException;
	
	//Snapshots数据盘快照
	*//**
	 * 查询所有Snapshots
	 *//*
	public List<OPSTSnapshotsBean> getSnapshotListV2(String tenantId,String tokenId)throws OPSTBaseException;
	*//**
	 * 查询Snapshots详细
	 *//*
	public List<OPSTSnapshotsBean> getSnapshotDetialListV2(String tenantId,String tokenId)throws OPSTBaseException;
	*//**
	 * 根据ID查询Snapshots详细
	 *//*
	public OPSTSnapshotsBean getSnapshotByIdV2(String argSnapshotsId,String tenantId,String tokenId)throws OPSTBaseException;
	*//**
	 * 创建数据盘快照
	 * @throws Exception 
	 *//*
	public String createSnapshotV2(OPSTSnapshotsBean argSnapshotsBean,String tenantId,String tokenId) throws OPSTBaseException;
	*//**
	 * 根据ID修改数据盘快照
	 *//*
	public String updateSnapshotV2(OPSTSnapshotsBean argSnapshotsBean,String tenantId,String tokenId)throws OPSTBaseException;
	*//**
	 * 根据ID删除数据盘快照
	 * @throws Exception 
	 *//*
	public Boolean deleteSnapshotByIdV2(String argSnapshotId,String tenantId,String tokenId) throws OPSTBaseException;
	//数据盘 租户配额 
	public OPSTBlockStorageLimitsBean getVolumeLimis(String tenantId,String tokenId) throws OPSTBaseException;
	//数据盘  租户配额
	public  OPSTBlockStorageLimitsBean getVolumeTenantQuotasV2(String tenantId,String tokenId)throws OPSTBaseException;
	
}






















*/