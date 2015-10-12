package com.ibm.smartcloud.openstack.nova.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.nova.bean.ServerBean;

/**
 * @Title:InstanceService     
 * @Description: Service   
 * @Auth:LiangRui   
 * @CreateTime:2015年6月18日 上午11:45:12       
 * @version V1.0
 */
public interface InstanceService {
	//Versions
	//TODO:return type must fix
	//public void getOPSTVersionV2();
	
	//Extensions
	//TODO:return type must fix
	//public void getOPSTExtensionListV2();
	//TODO:return type must fix
	//public void getOPSTExtensionDetailV2();
	
	//Limit
	//TODO:return type must fix
	//public void getOPSTLimitV2();
	
	//Servers
	public List<ServerBean> getServerListV2(String tenantId, String tokenId) throws OPSTBaseException ;
	
	//getHdisk info
	public String[] getHdiskList(String names, String addrs) throws OPSTBaseException;
	
	//Create 
	public String createDbInfo(String names, String addrs, String infos) throws OPSTBaseException;
		
	//public String createServerV2(OPSTServerCreateBean opstServerCreateBean,String tenantId, String tokenId) throws OPSTBaseException;
	//public String createServerInfo(String argHostName,String tenantId, String tokenId) throws OPSTBaseException ;
	//public String createServerById(String argServerId,String tenantId, String tokenId) throws OPSTBaseException ;
	//public String createServerInfoByIp(String argHostIp);
	//public String getServerIdByHostName(String argHostName,String tenantId, String tokenId) throws OPSTBaseException ;
	//public String getServerIdByHostIp(String argHostIp,String tenantId, String tokenId) throws OPSTBaseException ;
	//public List<OPSTServerBean> getServerDetailListV2(String tenantId, String tokenId,boolean isAdmin) throws OPSTBaseException ;
	//public OPSTServerBean getServerV2(String argServerId,String tenantId, String tokenId) throws OPSTBaseException ;
	//public String updateServerV2(OPSTServerBean argOPSTServerBean,String tenantId, String tokenId) throws OPSTBaseException ;
	//public void deleteServerV2(String argServerId,String tenantId, String tokenId) throws OPSTBaseException ;
	//Server metadata
	//public OPSTServerBean getServerMetadataV2(OPSTServerBean argOPSTServerBean) throws OPSTBaseException ;
	//public OPSTServerBean createOrRepalceServerMetaDataV2(String argServerId, OPSTServerMetaBean argMetaBean) throws OPSTBaseException ;
	//public OPSTServerBean updateServerMetadataByKeyV2(String argServerId, OPSTServerMetaBean argMetaBean) throws OPSTBaseException ;
	//public OPSTServerBean getServerMetadataByKeyV2(String argServerId, OPSTServerMetaBean argMetaBean) throws OPSTBaseException ;
	//public OPSTServerBean setServerMetadataByKeyV2(String argServerId, OPSTServerMetaBean argMetaBean) throws OPSTBaseException ;
	//public OPSTServerBean deleteServerMetadataV2(String argServerId, String argMetaDataKey) throws OPSTBaseException ;
	//public boolean resetServerStatus(String serverId, String tenantId, String tokenId) throws OPSTBaseException ;
	//Server addresses
	//public OPSTServerAddressBean getServerNetworksV2(String argServerId,String tenantId, String tokenId)throws OPSTBaseException ;
	//public OPSTServerAddressBean getServerIPaddressV2(String argServerId, String argNetworkLabel) throws OPSTBaseException ;
	
	//Server actions 
	//public void doServerActionV2(String argServerId, String argActionType,String tenantId, String tokenId) throws OPSTBaseException ; //主机重启
	
	//Flavors
	//TODO:return type must fix
	//public List<OPSTFlavorBean> getFlavorsListV2(String tenantId,String tokenId) throws OPSTBaseException ;
	//public List<OPSTFlavorBean> getFlavorsDetailListV2(String tenantId, String tokenId) throws OPSTBaseException ;
	//public OPSTFlavorBean getFlavorsDetailV2(String argFlavorId,String tenantId, String tokenId) throws OPSTBaseException ;
	
	//public void createFlavors(OPSTFlavorBean flavorBean,String tenantId,String tokenId) throws OPSTBaseException ;
	//public void deleteFlavors(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException ;
	
	//Images
	//TODO:return type must fix
	//public List<OPSTImageBean> getImagesListV2(String tenantId, String tokenId) throws OPSTBaseException ;
	//public void getImagesDetailListV2(String tenantId, String tokenId) throws OPSTBaseException ;
	//public OPSTImageBean getImageByIdV2(String argImageId,String tenantId, String tokenId) throws OPSTBaseException ;
	//public void deleteImageV2(String argImageId,String tenantId, String tokenId) throws OPSTBaseException ;
	
	//Image metadata
	//TODO:return type must fix
	//public void getImageMetadataV2() throws OPSTBaseException ;
	//public void createOrRepalceImageMetaDataV2() throws OPSTBaseException ;
	//public void updateImageMetadataByKeyV2() throws OPSTBaseException ;
	//public void getImageMetadataByKeyV2() throws OPSTBaseException ;
	//public void setImageMetadataByKeyV2() throws OPSTBaseException ;
	//public void deleteImageMetadataV2() throws OPSTBaseException ;
	
	//Keypairs(os-keypairs)
	//public List<OPSTKeypairsBean> getKeypairsV2Ext(String tenantId, String tokenId) throws OPSTBaseException ;
	//public void deleteKeypairsV2Ext(String argKeypairsName,String tenantId, String tokenId) throws OPSTBaseException ;
	
	//可以用域
	//public List<OPSTAvailabilityZoneInfo> getZonesList(String tenantId, String tokenId) throws OPSTBaseException ;
	//public List<OPSTAvailabilityZoneInfo> getZonesDetailList(String tenantId, String tokenId) throws OPSTBaseException ; 
	/**
	 * 获取openstack虚拟机的vnc地址。
	 * @param openstackVmId
	 * @return
	 */
	//public String getInstanceConsoleV2Ext(String argServerId,String tenantId, String tokenId) throws OPSTBaseException ;
	
	/**
	 * 实例操作。启动，停止，restore，删除。
	 * @param serverId
	 * @param actionType 
	 */
	//public boolean instanceServerActionV2Ext(String argServerId,String serverActionType,String tenantId, String tokenId) throws OPSTBaseException;
	
	/**
	 * 实例操作。创建数据盘快照。
	 * @param serverId
	 * @param snapshotName 
	 */
	//public boolean createSnapshotActionV2(String serverId,String snapshotName,String tenantId, String tokenId) throws OPSTBaseException;
	
	/**
	 * 实例操作。调整主机大小。
	 * @param serverId
	 * @param flavorId 
	 */
	//public boolean instanceResizeActionV2(String serverId,String flavorId,String tenantId, String tokenId) throws OPSTBaseException;
	/**
	 * 实例操作。调整主机大小。确认操作.
	 * @param serverId
	 * @param flavorId 
	 */
	//public void instanceResizeConfirmsV2(String serverId,String tenantId, String tokenId) throws OPSTBaseException;
	//public void instanceRevertResizeV2(String serverId,String tenantId, String tokenId) throws OPSTBaseException;

	/**
	 * 实例操作。重建主机。
	 * @param serverId
	 * @param serName
	 * @param imageId 
	 * @param password 
	 */
	//public boolean instanceRebuildActionV2(String serverId, String serName, String imageId,String tenantId, String tokenId) throws OPSTBaseException;

	/**
	 * 实例操作。绑定和解除浮动IP
	 * @param serId
	 * @param status
	 * @param floatIp
	 */
	//public boolean instanceAddOrRemoveFloatIpActionV2EXT(String serId, String status, String floatIp,String tenantId, String tokenId) throws OPSTBaseException;
	
	/**
	 * 实例操作。获得控制台信息
	 * @param serId
	 */
	//public String instanceServerGetConsoleActionV2EXT(String serId,String tenantId, String tokenId) throws OPSTBaseException;
	
	/**
	 * 实例操作。获得控制台信息(V3)
	 * @param serId
	 */
	//public String instanceServerGetConsoleActionV3(String serId,String tenantId, String tokenId) throws OPSTBaseException;

	//创建实例备份
	//public String createServerBackupActionV2EXT(String serId,String backupName,String backupType,String backupRotation,String tenantId, String tokenId)throws OPSTBaseException;
		
	//public OPSTTenantQuotasBean getTenantQuotasV2EXT(String tenantId, String tokenId, String tenant_id)throws OPSTBaseException;
	//public String updateTenantQuotasV2EXT(OPSTTenantQuotasBean opstBean,String tenantId, String tokenId)throws OPSTBaseException;
	//public String deleteTenantQuotasV2EXT(String tenantId, String tokenId)throws OPSTBaseException;
	
	//limits
	//public OPSTServersLimitsBean getServersLimisV2EXT(String tenantId, String tokenId)throws OPSTBaseException;
	//UsageReports
	//public List<OPSTUsageReportsBean> getComputeUsageReportsListV2EXT(String tenantId, String tokenId)throws OPSTBaseException;
	//hypervisor
	//public List<OPSTHypervisorsBean> getHypervisorDetailListV2EXT(String tenantId, String tokenId)throws OPSTBaseException;
	//public List<OPSTHypervisorsBean> getHypervisorListV2EXT(String tenantId,String tokenId)throws OPSTBaseException;
	//挂载数据盘	
	//public String createVolumeMountV2EXT(OPSTServerVolumeEXTBean opBean,String tenantId, String tokenId)throws OPSTBaseException;
	//分离数据盘
	//public String deleteVolumesSeparateByIdV2EXT(String serverId,String attachmentId,String tenantId, String tokenId)throws OPSTBaseException ;
	//查看挂载数据盘信息
	//public OPSTServerVolumeEXTBean getServerVolumeV2EXT(String serverId,String attachmentId,String tenantId, String tokenId)throws OPSTBaseException;
	//获得系统计算服务信息
	//public List<OPSTServiceBean> getServicesInfo(String tenantId, String tokenId) throws OPSTBaseException ;
	//获得系统网络代理信息
	//public List<OPSTAgentBean> getAgentsInfo(String tenantId, String tokenId) throws OPSTBaseException ;
	/**
	  * @Title: instanceMigrateV2
	  * @Description: 实例迁移
	  * @param @param tokenId
	  * @param @param serverId
	  * @param @param tenantId
	  * @param @return
	  * @param @throws OPSTBaseException
	  * @return boolean
	  * @author LiangRui
	  * @throws
	  * @Time 2015年5月7日 下午2:18:35
	 */
	//public boolean instanceMigrateV2(String tokenId,String serverId,String tenantId) throws OPSTBaseException; 
	/**
	  * @Title: instanceMigrateV2
	  * @Description: 实时实例迁移
	  * @param @param tokenId
	  * @param @param serverId
	  * @param @param tenantId
	  * @param @return
	  * @param @throws OPSTBaseException
	  * @return boolean
	  * @author LiangRui
	  * @throws
	  * @Time 2015年5月7日 下午4:16:51
	 */
	//public boolean instanceLiveMigrateV2(String tokenId,String serverId,String tenantId,String hostId,String overCommits,String blockMigrates) throws OPSTBaseException; 
}












