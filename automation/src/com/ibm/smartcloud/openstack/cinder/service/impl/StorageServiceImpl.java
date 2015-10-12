/*package com.ibm.smartcloud.openstack.cinder.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ibm.smartcloud.openstack.cinder.bean.OPSTBlockStorageBean;
import com.ibm.smartcloud.openstack.cinder.bean.OPSTBlockStorageLimitsBean;
import com.ibm.smartcloud.openstack.cinder.bean.OPSTBlockStorageTypeBean;
import com.ibm.smartcloud.openstack.cinder.constants.OPSTBlockStorageConst;
import com.ibm.smartcloud.openstack.cinder.service.StorageService;
import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSSystemException;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTErrorMessageException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.service.impl.OPSTBaseServiceImpl;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.StringUtil;
import com.ibm.smartcloud.openstack.glance.bean.OPSTSnapshotsBean;
import com.ibm.smartcloud.openstack.nova.bean.OPSTServerVolumeEXTBean;

@Service("storageService")
public class StorageServiceImpl extends OPSTBaseServiceImpl<Object> implements StorageService  {
	
	
	*//**
	 * 查询所有BlockStorage  volumes 
	 *//*
	@Override
	public List<OPSTBlockStorageBean> getBlockStorageListV2(String tenantId,String tokenId) throws OPSTBaseException {
		List<OPSTBlockStorageBean> blockStroList = new ArrayList<OPSTBlockStorageBean>();
		//获取参数信息
		String volumeHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		String strVolumeAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VOLUME_LIST);
		//  /v2/​{tenant_id}​/volumes		
		String strUrl = volumeHost + strVolumeAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取list
			JSONArray volumes = jsonObj.getJSONArray(OPSTBlockStorageConst.VOLUMES);
			for (int i = 0; i < volumes.size(); i++) {
				blockStroList.add(this.jsonObjToVolumeBeanV2(volumes.getJSONObject(i)));
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取volumesList的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return blockStroList;
	}
	
	*//**
	 * 查询BlockStorage详细List  volumes
	 * @throws OPSTBaseException 
	 *//*
	@Override
	public List<OPSTBlockStorageBean> getBlockStorageDetialListV2(String tenantId,String tokenId) throws OPSTBaseException {
		List<OPSTBlockStorageBean> blockStroList = new ArrayList<OPSTBlockStorageBean>();
		//获取参数信息
		String volumeHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//  /v2/​{tenant_id}​/volumes/detail
		String strVolumeDetailAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VOLUME_DETAIL_LIST);
		String strUrl = volumeHost + strVolumeDetailAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);			
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取list
			JSONArray volumes = jsonObj.getJSONArray(OPSTBlockStorageConst.VOLUMES);
			for (int i = 0; i < volumes.size(); i++) {			
				OPSTBlockStorageBean opstBlockStorageBean = this.jsonObjToVolumeBeanV2(volumes.getJSONObject(i));
				JSONArray attachmentsObj = volumes.getJSONObject(i).getJSONArray(OPSTBlockStorageConst.EXT_VOLUME_ATTACHMENTS);
				if(attachmentsObj!=null&&attachmentsObj.size()>0){			
					List<OPSTServerVolumeEXTBean> attachments = new ArrayList<OPSTServerVolumeEXTBean>();
					for(int j=0;j<attachmentsObj.size();j++){
						OPSTServerVolumeEXTBean opstServerVolumeEXTBean = this.jsonObjToVolumeBeanDetailV2(attachmentsObj.getJSONObject(j));
						attachments.add(opstServerVolumeEXTBean);					
					}
					opstBlockStorageBean.setAttachments(attachments);		
				}
				blockStroList.add(opstBlockStorageBean);
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取volumesDetailList的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return blockStroList;
	}
	
	*//**
	 * 根据ID查询BlockStorage详细 volumes
	 * @throws OPSTBaseException 
	 *//*
	@Override
	public OPSTBlockStorageBean getBlockStorageByIdV2(String argVolumeId,String tenantId,String tokenId) throws OPSTBaseException{
		OPSTBlockStorageBean opstBlockStorageBean = null;
		//获取参数信息
		String volumeHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//  /v2/​{tenant_id}​/volumes/​{volume_id}
		String strVolumeDetailAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VOLUMEBYID);
		String strUrl = volumeHost + strVolumeDetailAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID,tenantId)
				.replace(OPSTBlockStorageConst.PARAM_VOLUMEID,argVolumeId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			if(response.equals("")){
				return null;
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTBlockStorageConst.VOLUME);
			opstBlockStorageBean = this.jsonObjToVolumeBeanV2(jsonObj);
			JSONArray attachmentsObj = jsonObj.getJSONArray(OPSTBlockStorageConst.EXT_VOLUME_ATTACHMENTS);
			if(attachmentsObj!=null&&attachmentsObj.size()>0){			
				List<OPSTServerVolumeEXTBean> attachments = new ArrayList<OPSTServerVolumeEXTBean>();
				for(int i=0;i<attachmentsObj.size();i++){
					OPSTServerVolumeEXTBean opstServerVolumeEXTBean = this.jsonObjToVolumeBeanDetailV2(attachmentsObj.getJSONObject(i));
					attachments.add(opstServerVolumeEXTBean);
					
				}
				opstBlockStorageBean.setAttachments(attachments);		
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取volumesDetail的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return opstBlockStorageBean;
	}
	
	*//**
	 * 创建数据盘
	 *//*
	@Override
	public String createBlockStorageV2(OPSTBlockStorageBean argBlockStorageBean,String tenantId,String tokenId) throws OPSTBaseException{
		//获取参数信息		
		String novHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//  /v2/​{tenant_id}​/volumes		
		String strVolumeCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATEVOLUME);
		String strUrl = novHost + strVolumeCreateAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		JSONObject volumeObj = new JSONObject();
		JSONObject volumeRequest =  new JSONObject();

		{
		    "volume": {
		        "availability_zone": null,
		        "source_volid": null,
		        "display_description": null,
		        "snapshot_id": null,
		        "size": 10,
		        "display_name": "my_volume",
		        "imageRef": null,
		        "volume_type": null,
		        "metadata": {}
		    }
		}
		if(!"".equals(argBlockStorageBean.getAvailability_zone())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_AVAILABILITY_ZONE, argBlockStorageBean.getAvailability_zone());
		}
		if(!"".equals(argBlockStorageBean.getSource_volid())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_SOURCE_VOLID, argBlockStorageBean.getSource_volid());
		}
		if(!"".equals(argBlockStorageBean.getName())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_DISPLAY_NAME, argBlockStorageBean.getName());
		}
		if(!"".equals(argBlockStorageBean.getSize())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_SIZE, argBlockStorageBean.getSize());
		}
		if(!"".equals(argBlockStorageBean.getVolume_type())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_VOLUME_TYPE, argBlockStorageBean.getVolume_type());
		}
		if(!"".equals(argBlockStorageBean.getSnapshot_id())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_SNAPSHOT_ID, argBlockStorageBean.getSnapshot_id());
		}
		if(!"".equals(argBlockStorageBean.getDescription())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_DISPLAY_DESCRIPTION, argBlockStorageBean.getDescription());
		}
		if(!"".equals(argBlockStorageBean.getImageRef())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_IMAGEREF, argBlockStorageBean.getImageRef());
		}
		volumeObj.element(OPSTBlockStorageConst.VOLUME, volumeRequest);
		String strResponse ="";
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl, volumeObj.toString());
			if(!strResponse.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(strResponse);
				if(!jsonObj.containsKey(OPSTBlockStorageConst.VOLUME)){
					String error = StringUtil.jsonErrorToErrorMessage(strResponse);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		String volumeId;
		try {
			JSONObject jsonObj=JSONObject.fromObject(strResponse).getJSONObject(OPSTBlockStorageConst.VOLUME);
			volumeId = jsonObj.get("id").toString();
		} catch (Exception e) {
			throw new OPSTOperationException("创建Instance的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return volumeId;
	}
	
	*//**
	 * 根据ID修改数据盘
	 *//*
	@Override
	public String updateBlockStorageByIdV2(OPSTBlockStorageBean argBlockStorageBean,String tenantId,String tokenId) throws OPSTBaseException{
		//获取参数信息
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//  /v2/{tenant_id}/volumes/{volume_id}
		String strVolumeUpdateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATEVOLUMESBYID);
		String strUrl = strHost + strVolumeUpdateAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID,tenantId)
				.replace(OPSTBlockStorageConst.PARAM_VOLUMEID,argBlockStorageBean.getId());
		JSONObject volumeObj = new JSONObject();
		JSONObject volumeRequest =  new JSONObject();
//		//API 修改数据盘的json,但是不会保存,无法修改成功   key改成name\description则可更新成功
//		{
//		    "volume": {
//		        "display_name": "vol-003",
//		        "display_description": "This is yet, another volume."
//		    }
//		}
		if(!"".equals(argBlockStorageBean.getName())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_NAME, argBlockStorageBean.getName());
		}
		if(!"".equals(argBlockStorageBean.getDescription())){
		volumeRequest.element(OPSTBlockStorageConst.VOLUME_DESCRIPTION, argBlockStorageBean.getDescription());
		}
		volumeObj.element(OPSTBlockStorageConst.VOLUME, volumeRequest);
		String strResponse ="";
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl, volumeObj.toString());
			if(!strResponse.equals("") && strResponse!=null){
				JSONObject jsonObj = JSONObject.fromObject(strResponse);
				if(!jsonObj.containsKey(OPSTBlockStorageConst.VOLUME)){
					String error = StringUtil.jsonErrorToErrorMessage(strResponse);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return strResponse;
		
	}
	
	*//**
	 * 根据ID删除数据盘 volumes
	 * @throws Exception 
	 *//*
	@Override
	public Boolean deleteBlockStorageByIdV2(String argVolumeId,String tenantId,String tokenId) throws OPSTBaseException {
		//获取参数信息
		String volumeHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		if(argVolumeId == null){
			throw new OPSSystemException("数据盘删除时发生空指针异常");
		}
		///v2/​{tenant_id}​/volumes/​{volume_id}
		String strVolumeDeleteAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_VOLUMESBYID);
		String strUrl = volumeHost + strVolumeDeleteAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID,tenantId).replace(OPSTBlockStorageConst.PARAM_VOLUMEID,argVolumeId);
		
		String strResponse ="";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);		
			if (!strResponse.equals("") && strResponse!=null) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return true;
	}
	
	*//**
	 * 查询数据盘的类型
	 *//*
	@Override
	public List<OPSTBlockStorageTypeBean> getStorageTypeListV2(String tenantId,String tokenId) throws OPSTBaseException {
		
		List<OPSTBlockStorageTypeBean> volumesTypeList = new ArrayList<OPSTBlockStorageTypeBean>();
		//获取参数信息
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//  /v2/​{tenant_id}​/types
		String strVolumeTypeAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VOLUMETYPE_LIST);
		String strUrl = strHost + strVolumeTypeAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取volumes type
			JSONArray volumeTypes = jsonObj.getJSONArray(OPSTBlockStorageConst.VOLUME_TYPES);
			for (int i = 0; i < volumeTypes.size(); i++) {
				volumesTypeList.add(this.jsonObjToVolumeTypeBeanV2(volumeTypes.getJSONObject(i)));
			}	
		} catch (JSONException e) {
			throw new OPSTOperationException("获取volumeType的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return volumesTypeList;
	}
	
	public OPSTBlockStorageLimitsBean getVolumeLimis(String tenantId, String tokenId) throws OPSTBaseException {
		
	
			OPSTBlockStorageLimitsBean bean = null;
			//获取参数信息
			String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
			//v2/{tenant_id}/limits
			String strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_LimitsList);
			String strUrl = strHost + strAPI;
			String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
			String response = "";
			try {
				response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			} catch (OPSTBaseException e) {
				throw e;
			}
			try {
				if(!response.equals("")){
					JSONObject jsonObjLimits = JSONObject.fromObject(response).getJSONObject(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS);
					JSONObject jsonObj = JSONObject.fromObject(jsonObjLimits).getJSONObject(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_ABSOLUTE);
					bean = this.jsonObjToLimitsBeanV2(jsonObj);
				}
			} catch (JSONException e) {
				throw new OPSTOperationException("获取volumeLimit时出现处理json数据异常，类型为："+e.getMessage());
			}
			return bean;
		
	}
	
	*//**
	 * 查询所有Snapshots
	 *//*
	@Override
	public List<OPSTSnapshotsBean> getSnapshotListV2(String tenantId,String tokenId) throws OPSTBaseException{
		
		List<OPSTSnapshotsBean> snapshotsList = new ArrayList<OPSTSnapshotsBean>();
		//获取参数信息
		String strhost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		// /v2/​{tenant_id}​/snapshots
		String strSnapshotsListAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SNAPSHOT_LIST);
		String strUrl = strhost + strSnapshotsListAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取snapshots list
			JSONArray snapshots = jsonObj.getJSONArray(OPSTBlockStorageConst.SNAPSHOTS);
			for (int i = 0; i < snapshots.size(); i++) {
				snapshotsList.add(this.jsonObjToSnapshotBeanV2(snapshots.getJSONObject(i)));
			}
		}catch (JSONException e) {
			throw new OPSTOperationException("获取snapshotList的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return snapshotsList;
	}
	
	*//**
	 * 查询Snapshots详细List
	 *//*
	@Override
	public List<OPSTSnapshotsBean> getSnapshotDetialListV2(String tenantId,String tokenId) throws OPSTBaseException {
		List<OPSTSnapshotsBean> snapshotsList = new ArrayList<OPSTSnapshotsBean>();
		//获取参数信息
		String strhost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//  /v2/​{tenant_id}​/snapshots/detail
		String strSnapshotsDetialAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SNAPSHOT_LIST);
		String strUrl = strhost + strSnapshotsDetialAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取list
			JSONArray snapshots = jsonObj.getJSONArray(OPSTBlockStorageConst.SNAPSHOTS);
			for (int i = 0; i < snapshots.size(); i++) {
				snapshotsList.add(this.jsonObjToSnapshotBeanV2(snapshots.getJSONObject(i)));
			}
		}catch (JSONException e) {
			throw new OPSTOperationException("获取snapshotDetailList的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return snapshotsList;
	}
	
	*//**
	 * 根据ID查询Snapshots详细
	 *//*
	@Override
	public OPSTSnapshotsBean getSnapshotByIdV2(String argSnapshotsId,String tenantId,String tokenId) throws OPSTBaseException{
		OPSTSnapshotsBean bean = null;
		//获取参数信息
		String strhost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		// /v2/​{tenant_id}​/snapshots/​{snapshot_id}
		String strSnapshotAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SNAPSHOTBYID);
		String strUrl = strhost + strSnapshotAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID,tenantId)
				.replace(OPSTBlockStorageConst.PARAM_SNAPSHOTID,argSnapshotsId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTBlockStorageConst.SNAPSHOT);
				bean = this.jsonObjToSnapshotBeanV2(jsonObj);
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取snapshotDetailList的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return bean;
	}
	
	*//**
	 * 创建数据盘快照
	 * @throws Exception 
	 *//*
	@Override
	public String createSnapshotV2(OPSTSnapshotsBean argSnapshotsBean,String tenantId,String tokenId) throws OPSTBaseException {
		//创建用参数检查
		if (argSnapshotsBean == null) {
			//参数错误
			throw new OPSSystemException("创建数据盘快照时发生空指针异常");
		} 
		//volume_id是必须提供字段
		if ("".equals(argSnapshotsBean.getVolume_id())) {
			//参数错误
			throw new OPSSystemException("创建数据盘快照时发生空指针异常");
		}
		// /v2/​{tenant_id}​/snapshots
		//获取参数信息
		String novHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		//json格式
		{
		    "snapshot": {
		        "display_name": "snap-001",
		        "display_description": "Daily backup",
		        "volume_id": "521752a6-acf6-4b2d-bc7a-119f9148cd8c",
		        "force": true
		    }
		}
		JSONObject snapshotObj = new JSONObject();
		JSONObject snapshotRequest =  new JSONObject();
		snapshotRequest.element(OPSTBlockStorageConst.SNAPSHOT_NAME, argSnapshotsBean.getName());
		snapshotRequest.element(OPSTBlockStorageConst.SNAPSHOT_DESCRIPTION, argSnapshotsBean.getDescription());
		snapshotRequest.element(OPSTBlockStorageConst.SNAPSHOT_VOLUME_ID, argSnapshotsBean.getVolume_id());
		snapshotRequest.element(OPSTBlockStorageConst.SNAPSHOT_FORCE, Boolean.TRUE);
		snapshotObj.element(OPSTBlockStorageConst.SNAPSHOT, snapshotRequest);
		
		String strSnapshotCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATESNAPSHOT);
		String strUrl = novHost + strSnapshotCreateAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);

		String strResponse = "";
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl, snapshotObj.toString());
			if(!strResponse.equals("") && strResponse != null){
				JSONObject jsonObj = JSONObject.fromObject(strResponse);
				if(!jsonObj.containsKey(OPSTBlockStorageConst.SNAPSHOT)){
					String error = StringUtil.jsonErrorToErrorMessage(strResponse);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	
	*//**
	 * 根据ID修改数据盘快照
	 *//*
	@Override
	public String updateSnapshotV2(OPSTSnapshotsBean argSnapshotsBean,String tenantId,String tokenId) throws OPSTBaseException{
		
		if(argSnapshotsBean==null){
			throw new NullPointerException();
		}
		// /v2/​{tenant_id}​/snapshots
		//获取参数信息
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		
//		{
//		    "snapshot": {
//		        "display_name": "snap-002",
//		        "display_description": "This is yet, another snapshot."
//		    }
//		}
		JSONObject snapshotObj = new JSONObject();
		JSONObject snapshotRequest =  new JSONObject();
		if(!"".equals(argSnapshotsBean.getName())){
		snapshotRequest.element(OPSTBlockStorageConst.SNAPSHOT_DISPLAY_NAME, argSnapshotsBean.getName());
		}
		if(!"".equals(argSnapshotsBean.getDescription())){
		snapshotRequest.element(OPSTBlockStorageConst.SNAPSHOT_DISPLAY_DESCRIPTION, argSnapshotsBean.getDescription());
		}
		snapshotObj.element(OPSTBlockStorageConst.SNAPSHOT, snapshotRequest);
		
		String strSnapshotUpdateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATESNAPSHOTBYID);
		String strUrl = strHost + strSnapshotUpdateAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId).replace(OPSTBlockStorageConst.PARAM_SNAPSHOTID, argSnapshotsBean.getId());
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl, snapshotObj.toString());
			if(!strResponse.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(strResponse);
				if(!jsonObj.containsKey(OPSTBlockStorageConst.SNAPSHOT)){
					String error = StringUtil.jsonErrorToErrorMessage(strResponse);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	
	*//**
	 * 根据ID删除数据盘快照
	 * @throws Exception 
	 *//*
	@Override
	public Boolean deleteSnapshotByIdV2(String argSnapshotId,String tenantId,String tokenId) throws OPSTBaseException {
		//获取参数信息
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		if(argSnapshotId == null){
			throw new OPSSystemException("数据盘快照删除时发生空指针异常");
		}
		// /v2/​{tenant_id}​/snapshots/​{snapshot_id}
		String strSnapshotDeleteAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_SNAPSHOTBYID);
		String strUrl = strHost + strSnapshotDeleteAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId).replace(OPSTBlockStorageConst.PARAM_SNAPSHOTID, argSnapshotId);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (!strResponse.equals("") && strResponse!=null) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (Exception e) {
			throw new OPSSystemException("数据盘快照删除时发生"+e.getMessage()+"异常");
		}
		
		return true;
	}
	
	*//**
	 * OPSTBlockStorageBean
	 * @param volumes
	 * @return
	 *//*
	private OPSTBlockStorageBean jsonObjToVolumeBeanV2(JSONObject volumes) {
		
		OPSTBlockStorageBean bs = new OPSTBlockStorageBean();
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_ID)){
			bs.setId(volumes.getString(OPSTBlockStorageConst.VOLUME_ID));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_NAME)){
			bs.setName(volumes.getString(OPSTBlockStorageConst.VOLUME_NAME));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_STATUS)){
			bs.setStatus(volumes.getString(OPSTBlockStorageConst.VOLUME_STATUS));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_AVAILABILITY_ZONE)){
			bs.setAvailability_zone(volumes.getString(OPSTBlockStorageConst.VOLUME_AVAILABILITY_ZONE));
		}		
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_OS_VOL_HOST_ATTR_HOST)){
			bs.setOs_vol_host_attr_host(volumes.getString(OPSTBlockStorageConst.VOLUME_OS_VOL_HOST_ATTR_HOST));		
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_SOURCE_VOLID)){
			bs.setSource_volid(volumes.getString(OPSTBlockStorageConst.VOLUME_SOURCE_VOLID));	
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_SNAPSHOT_ID)){
			bs.setSnapshot_id(volumes.getString(OPSTBlockStorageConst.VOLUME_SNAPSHOT_ID));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_DESCRIPTION)){
			bs.setDescription(volumes.getString(OPSTBlockStorageConst.VOLUME_DESCRIPTION));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_CREATED_AT)){
			bs.setCreated_at(volumes.getString(OPSTBlockStorageConst.VOLUME_CREATED_AT));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_VOLUME_TYPE)){
			bs.setVolume_type(volumes.getString(OPSTBlockStorageConst.VOLUME_VOLUME_TYPE));	
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_OS_VOL_TENANT_ATTR_TENANT_ID)){
			bs.setOs_vol_tenant_attr_tenant_id(volumes.getString(OPSTBlockStorageConst.VOLUME_OS_VOL_TENANT_ATTR_TENANT_ID));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_SIZE)){
			bs.setSize(volumes.getString(OPSTBlockStorageConst.VOLUME_SIZE));
		}
		if(volumes.containsKey(OPSTBlockStorageConst.VOLUME_METADATA)){
			bs.setMetadata(volumes.getString(OPSTBlockStorageConst.VOLUME_METADATA));
		}
		return bs;
	}
	
	private OPSTServerVolumeEXTBean jsonObjToVolumeBeanDetailV2(JSONObject attachment) {
		
		OPSTServerVolumeEXTBean bean = new OPSTServerVolumeEXTBean();
		if(attachment.containsKey(OPSTBlockStorageConst.EXT_ATTACHMENTS_ID)){
			bean.setId(attachment.getString(OPSTBlockStorageConst.EXT_ATTACHMENTS_ID));
		}
		if(attachment.containsKey(OPSTBlockStorageConst.EXT_ATTACHMENTS_VOLUME_ID)){
			bean.setVolumeId(attachment.getString(OPSTBlockStorageConst.EXT_ATTACHMENTS_VOLUME_ID));
		}
		if(attachment.containsKey(OPSTBlockStorageConst.EXT_ATTACHMENTS_SERVER_ID)){
			bean.setServerId(attachment.getString(OPSTBlockStorageConst.EXT_ATTACHMENTS_SERVER_ID));
		}
		if(attachment.containsKey(OPSTBlockStorageConst.EXT_ATTACHMENTS_DEVICE)){
			bean.setDevice(attachment.getString(OPSTBlockStorageConst.EXT_ATTACHMENTS_DEVICE));
		}
		if(attachment.containsKey(OPSTBlockStorageConst.EXT_ATTACHMENTS_HOST_NAME)){
			bean.setHostName(attachment.getString(OPSTBlockStorageConst.EXT_ATTACHMENTS_HOST_NAME));
		}		
		return bean;
	}	
	
	*//**
	 * OPSTSnapshotsBean
	 * @param snapshots
	 * @return
	 *//*
	private OPSTSnapshotsBean jsonObjToSnapshotBeanV2(JSONObject snapshots) {
		
		OPSTSnapshotsBean snp = new OPSTSnapshotsBean();
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_ID)){
			snp.setId(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_ID));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_NAME)){
			snp.setName(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_NAME));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_STATUS)){
			snp.setStatus(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_STATUS));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_VOLUME_ID)){
			snp.setVolume_id(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_VOLUME_ID));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_DESCRIPTION)){
			snp.setDescription(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_DESCRIPTION));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_CREATED_AT)){
			snp.setCreated_at(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_CREATED_AT));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_SIZE)){
			snp.setSize(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_SIZE));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.SNAPSHOT_METADATA)){
			snp.setMetadata(snapshots.getString(OPSTBlockStorageConst.SNAPSHOT_METADATA));
		}
		return snp;
	}
	
	*//**
	 * OPSTSnapshotsBean
	 * @param snapshots
	 * @return
	 *//*
	private OPSTBlockStorageTypeBean jsonObjToVolumeTypeBeanV2(JSONObject snapshots) {
		
		OPSTBlockStorageTypeBean bst = new OPSTBlockStorageTypeBean();
		if(snapshots.containsKey(OPSTBlockStorageConst.VOLUME_TYPES_ID)){
			bst.setId(snapshots.getString(OPSTBlockStorageConst.VOLUME_TYPES_ID));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.VOLUME_TYPES_NAME)){
			bst.setName(snapshots.getString(OPSTBlockStorageConst.VOLUME_TYPES_NAME));
		}
		if(snapshots.containsKey(OPSTBlockStorageConst.VOLUME_TYPES_EXTRA_SPECS)){
			bst.setExtra_specs(snapshots.getString(OPSTBlockStorageConst.VOLUME_TYPES_EXTRA_SPECS));
		}		
		return bst;
	}
	
	private OPSTBlockStorageLimitsBean jsonObjToLimitsBeanV2(JSONObject jsonObj) throws OPSTBaseException{	
		OPSTBlockStorageLimitsBean limits = new OPSTBlockStorageLimitsBean();
		if(jsonObj.containsKey(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_MAXTOTALSNAPSHOTS)){
			limits.setMaxTotalSnapshots(jsonObj.getString(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_MAXTOTALSNAPSHOTS));
		}
		if(jsonObj.containsKey(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_MAXTOTALVOLUMEGIGABYTES)){
			limits.setMaxTotalVolumeGigabytes(jsonObj.getString(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_MAXTOTALVOLUMEGIGABYTES));
		}		
		if(jsonObj.containsKey(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_MAXTOTALVOLUMES)){
			limits.setMaxTotalVolumes(jsonObj.getString(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_MAXTOTALVOLUMES));
		}
		if(jsonObj.containsKey(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_TOTALGIGABYTESUSED)){
			limits.setTotalGigabytesUsed(jsonObj.getString(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_TOTALGIGABYTESUSED));
		}
		if(jsonObj.containsKey(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_TOTALSNAPSHOTSUSED)){
			limits.setTotalSnapshotsUsed(jsonObj.getString(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_TOTALSNAPSHOTSUSED));
		}
		if(jsonObj.containsKey(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_TOTALVOLUMESUSED)){
			limits.setTotalVolumesUsed(jsonObj.getString(OPSTBlockStorageConst.BLOCKSTORAGE_LIMITS_TOTALVOLUMESUSED));
		}		
		return limits;
	}
	
	//数据盘  租户配额
	public  OPSTBlockStorageLimitsBean getVolumeTenantQuotasV2(String tenantId,String tokenId) throws OPSTBaseException{
		//获取参数信息
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.VOLUME_TWO);
		// v2/{tenant_id}/os-quota-sets/{tenant_id}
		String strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VOLUMETENANTQUOTAS);
		String strUrl = strHost + strAPI+"?usage=True";
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		OPSTBlockStorageLimitsBean  bean = null;
		try {
			if(!response.equals("")){
				bean = new OPSTBlockStorageLimitsBean();			
				JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTBlockStorageConst.VOLUME_TENANT_QUOTA_SET);
				JSONObject jsonGigabytes = JSONObject.fromObject(jsonObj).getJSONObject(OPSTBlockStorageConst.VOLUME_TENANT_GIGABYTES);
				bean.setMaxTotalVolumeGigabytes(jsonGigabytes.getString(OPSTBlockStorageConst.VOLUME_TENANT_GIGABYTES_LIMIT));
				bean.setTotalGigabytesUsed(jsonGigabytes.getString(OPSTBlockStorageConst.VOLUME_TENANT_GIGABYTES_IN_USE));
				JSONObject jsonVolumes = JSONObject.fromObject(jsonObj).getJSONObject(OPSTBlockStorageConst.VOLUME_TENANT_VOLUMES);
				bean.setMaxTotalVolumes(jsonVolumes.getString(OPSTBlockStorageConst.VOLUME_TENANT_VOLUMES_LIMIT));
				bean.setTotalVolumesUsed(jsonVolumes.getString(OPSTBlockStorageConst.VOLUME_TENANT_VOLUMES_IN_USE));
				return bean;			
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取volumeTenantQuotas时出现处理json数据异常，类型为："+e.getMessage());
		}	
		return bean;
	}
}*/