package com.ibm.smartcloud.openstack.nova.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Service;

import com.ibm.smartcloud.openstack.core.constants.OPSTExtensionsConst;
import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.nova.bean.FlavorBean;
import com.ibm.smartcloud.openstack.nova.constants.OPSTComputeConst;
import com.ibm.smartcloud.openstack.nova.service.FlavorsService;

@Service("flavorsService")
public class FlavorsServiceImpl implements FlavorsService {
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	
	/**
	 * 查询所有实例类型
	 */
	@Override
	public List<FlavorBean> getAllFlavorsList(String tenantId,String tokenId){
		List<FlavorBean> flavorList = new ArrayList<FlavorBean>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_FlavorsList);
		// 拼接URL
		String strOrgUrl = novaHost+strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).trim();
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(response);
		JSONArray flavors = jsonObj.getJSONArray(OPSTExtensionsConst.FLAVORS);
		for (int i = 0; i < flavors.size(); i++) {
			flavorList.add(jsonObjToFlavorV2Bean(flavors.getJSONObject(i)));
		}
		return flavorList;
	} 
	
	 
	/*@Override
	public List<OPSTFlavorBean> getFlavorsListV2Ext(String tenantId,String tokenId) throws OPSTBaseException {
		
		List<OPSTFlavorBean> flavorList = new ArrayList<OPSTFlavorBean>();
		//èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_FlavorsExtList);
		//æ‹¼æŽ¥URL
		String strOrgUrl = novaHost+strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).trim();
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		// èŽ·å�–Flavors list
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			JSONArray flavors = jsonObj.getJSONArray(OPSTExtensionsConst.FLAVORS);
			for (int i = 0; i < flavors.size(); i++) {
				flavorList.add(jsonObjToFlavorV2Bean(flavors.getJSONObject(i)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new OPSTOperationException("èŽ·å�–flavorAccessListçš„æ—¶å€™å‡ºçŽ°å¤„ç�†jsonæ•°æ�®å¼‚å¸¸ï¼Œç±»åž‹ä¸ºï¼š"+e.getMessage());
		}
		return flavorList;
	} 
	
	*//**
	 * æŸ¥è¯¢Flavos Detialä¿¡æ�¯
	 * @throws Exception 
	 * @throws IOException 
	 * @throws HttpException 
	 *//*
	@Override
	public List<OPSTFlavorBean> getFlavorsDetailListV2Ext(String tenantId,String tokenId) throws OPSTBaseException {
		List<OPSTFlavorBean> flavorV2List = new ArrayList<OPSTFlavorBean>();
		//èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_FlavorsExtDetails);
		//æ‹¼æŽ¥URL
		String strOrgUrl = novaHost+strServerApi;
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strOrgUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
	
		// èŽ·å�–flavors list
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			JSONArray flavors = jsonObj.getJSONArray(OPSTExtensionsConst.FLAVORS);
			for (int i = 0; i < flavors.size(); i++) {
				flavorV2List.add(this.jsonObjToFlavorV2Bean(flavors.getJSONObject(i)));
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("èŽ·å�–flavorAccessListçš„æ—¶å€™å‡ºçŽ°å¤„ç�†jsonæ•°æ�®å¼‚å¸¸ï¼Œç±»åž‹ä¸ºï¼š"+e.getMessage());
		}
		return flavorV2List;
	}
		
	*//**
	 * æ ¹æ�®IDæŸ¥è¯¢Flavosä¿¡æ�¯
	 * @throws Exception 
	 * @throws IOException 
	 * @throws HttpException 
	 *//*
	@Override
	public OPSTFlavorBean getFlavorsDetailV2Ext(String argFlavorId,String tenantId,String tokenId) throws OPSTBaseException {
		//èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_FlavorsExtDetails);
		//æ‹¼æŽ¥URL
		String strOrgUrl = novaHost+strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).trim();
		String strDestUrl2 = strDestUrl.replace(OPSTExtensionsConst.PARAM_FLAVORID, argFlavorId).trim();
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		
		} catch (OPSTBaseException e) {
			throw e;
		}
		OPSTFlavorBean bean;
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTExtensionsConst.FLAVOR);
			bean = this.jsonObjToFlavorV2Bean(jsonObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new OPSTOperationException("èŽ·å�–flavorAccessListçš„æ—¶å€™å‡ºçŽ°å¤„ç�†jsonæ•°æ�®å¼‚å¸¸ï¼Œç±»åž‹ä¸ºï¼š"+e.getMessage());
		}
		return bean;
	}
	
	*//**
	 * æ ¹æ�®IDæŸ¥è¯¢Flavosä¿¡æ�¯
	 * @throws Exception 
	 * @throws IOException 
	 * @throws HttpException 
	 *//*
	@Override
	public List<OPSTFlavorExtraBean> getFlavorExtraSpecs(String argFlavorId, String tenantId, String tokenId) throws OPSTBaseException {
		
		//å®šä¹‰è¿”å›žç»“æžœ
		List<OPSTFlavorExtraBean> beans = null;
		
		//èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_FlavorExtraSpecs);
		//æ‹¼æŽ¥URL
		String strOrgUrl = novaHost+strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).trim();
		String strDestUrl2 = strDestUrl.replace(OPSTExtensionsConst.PARAM_FLAVORID, argFlavorId).trim();
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTExtensionsConst.EXTRA_SPECS);
			if(!jsonObj.isEmpty()) {
				beans = this.jsonObjToOPSTFlavorExtraBean(jsonObj);
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("èŽ·å�–flavorAccessListçš„æ—¶å€™å‡ºçŽ°å¤„ç�†jsonæ•°æ�®å¼‚å¸¸ï¼Œç±»åž‹ä¸ºï¼š"+e.getMessage());
		}
		return beans;
	}
	
	@Override
	public Boolean createFlavorsV2Ext(OPSTFlavorBean flavorBean,String tenantId,String tokenId) throws OPSTBaseException {

		// èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_Flavors);
		// æ‹¼æŽ¥URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID,tenantId);

		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();

		if (flavorBean.getId() != null) {
			// æž„å»ºæ ·ä¾‹"id":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_ID, flavorBean.getId());
		}
		if (flavorBean.getName() != null) {
			// æž„å»ºæ ·ä¾‹"name":"xx"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_NAME, flavorBean.getName());
		}
		if (flavorBean.getRam() != null) {
			// æž„å»ºæ ·ä¾‹"ram":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_RAM, flavorBean.getRam());
		}
		if (flavorBean.getVcpus() != null){
			// æž„å»ºæ ·ä¾‹"vcpus":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_VCPUS, flavorBean.getVcpus());
		}
		if (flavorBean.getDisk() != null) {
			// æž„å»ºæ ·ä¾‹"disk":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_DISK, flavorBean.getDisk());
		}  
		if (flavorBean.getEphemeral() != null) {
			// æž„å»ºæ ·ä¾‹"ram":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_EPHEMERAL, flavorBean.getEphemeral());
		}
		if (flavorBean.getSwap() != null){
			// æž„å»ºæ ·ä¾‹"vcpus":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_SWAP, flavorBean.getSwap());
		}
		if (flavorBean.getIsPublic() != null) {
			// æž„å»ºæ ·ä¾‹"disk":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_ISPUBLIC, flavorBean.getIsPublic());
		}

		serverRequestBody.element(OPSTFlavorsConst.FLAVOR, serverJSONObj);
		
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl, serverRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTFlavorsConst.FLAVOR)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return true; 
	}

	@Override
	public Boolean deleteFlavorsV2Ext(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException {
		// èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_Flavors);
		// æ‹¼æŽ¥URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID,tenantId).replace(OPSTFlavorsConst.PARAM_FLAVORSID, flavor_id);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return true; 
	}
	
	@Override
	public List<OPSTFlavorAccessBean> getFlavorsAccessListV2Ext(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException {
		// èŽ·å�–å�‚æ•°ä¿¡æ�¯
		List<OPSTFlavorAccessBean> List = new ArrayList<OPSTFlavorAccessBean>();
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.FLAVOR_ACCESS_LIST);
		// æ‹¼æŽ¥URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID,tenantId).replace(OPSTFlavorsConst.PARAM_FLAVORSID,flavor_id);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		// èŽ·å�–flavors list
		JSONArray flavorsAccess;
		try {
			flavorsAccess = JSONObject.fromObject(response).getJSONArray(OPSTExtensionsConst.FLAVOR_ACCESS);
			for (int i = 0; i < flavorsAccess.size(); i++) {
				List.add(this.jsonObjToFlavorAccessV2Bean(flavorsAccess.getJSONObject(i)));
			}
		}catch (JSONException e) {
			throw new OPSTOperationException("èŽ·å�–flavorAccessListçš„æ—¶å€™å‡ºçŽ°å¤„ç�†jsonæ•°æ�®å¼‚å¸¸ï¼Œç±»åž‹ä¸ºï¼š"+e.getMessage());
		}catch (Exception e) {
			throw new OPSSystemException("èŽ·å�–flavorAccessListçš„æ—¶å€™å‡ºçŽ°å¼‚å¸¸ï¼Œç±»åž‹ä¸ºï¼š"+e.getMessage());
		}
		
		return List;
	}
	
	@Override
	public Boolean createFlavorsAccessV2Ext(String flavor_id,String tenantName,String tenantId,String tokenId) throws OPSTBaseException {
		// èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.FLAVOR_ACCESS_OPERATE);
		// æ‹¼æŽ¥URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID,tenantId);
		strDestUrl = strDestUrl.replace(OPSTFlavorsConst.PARAM_FLAVORSID,flavor_id);
		
		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();
		if (tenantName != null) {
			// æž„å»ºæ ·ä¾‹"id":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.TENANT,tenantName);
		}
		serverRequestBody.element(OPSTFlavorsConst.ADD_TENANT_ACCESS, serverJSONObj);
		
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl,serverRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTFlavorsConst.FLAVOR_ACCESS)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		} 
		return true; 
	}
	
	@Override
	public void deleteFlavorsAccessV2Ext(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException {
		// èŽ·å�–å�‚æ•°ä¿¡æ�¯
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.FLAVOR_ACCESS_OPERATE);
		// æ‹¼æŽ¥URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID,tenantId).replace(OPSTFlavorsConst.PARAM_FLAVORSID, flavor_id);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
	}

	/**
	 * jsonObjToFlavorV2Bean
	 * @param argFlavor
	 * @return
	 */
	private FlavorBean jsonObjToFlavorV2Bean(JSONObject argFlavor){
		FlavorBean flavor = new FlavorBean();
		if(null!=argFlavor.getString("id")){
			flavor.setId(argFlavor.getString("id"));
		}
		if(null!=argFlavor.getString("name")){
			flavor.setName(argFlavor.getString("name"));
		}
		if(null!=argFlavor.getString("ram")){
			flavor.setRam(argFlavor.getString("ram"));
		}
		if(null!=argFlavor.getString("vcpus")){
			flavor.setVcpus(argFlavor.getString("vcpus"));
		}
		if(null!=argFlavor.getString("disk")){
			flavor.setDisk(argFlavor.getString("disk"));
		}
		if(null!=argFlavor.getString("OS-FLV-EXT-DATA:ephemeral")){
			flavor.setEphemeral(argFlavor.getString("OS-FLV-EXT-DATA:ephemeral"));
		}
		if(null!=argFlavor.getString("swap")){
			flavor.setSwap(argFlavor.getString("swap"));
		}
		if(null!=argFlavor.getString("os-flavor-access:is_public")){
			if(argFlavor.getString("os-flavor-access:is_public").equals("true")){
				flavor.setIsPublic("true");
			}else {
				flavor.setIsPublic("false");
			}
		}
		return flavor;
	}
	
	/*
	//JsonObjOPSTFlavorExtraBean
	@SuppressWarnings("unchecked")
	private List<OPSTFlavorExtraBean> jsonObjToOPSTFlavorExtraBean(JSONObject argFlavor) {
		List<OPSTFlavorExtraBean> flavorExtras = new ArrayList<OPSTFlavorExtraBean>();
		Set<String> keys = argFlavor.keySet();
		for(String key : keys) {
			OPSTFlavorExtraBean flavorExtra = new OPSTFlavorExtraBean();
			flavorExtra.setFlavorKey(key);
			flavorExtra.setFlavorValue(argFlavor.getString(key));
			flavorExtras.add(flavorExtra);
		}
		
		return flavorExtras;
	}
	
	*//**
	 * jsonObjToFlavorAccessV2Bean
	 * @param argFlavor
	 * @return
	 *//*
	private OPSTFlavorAccessBean jsonObjToFlavorAccessV2Bean(JSONObject argFlavor){
		OPSTFlavorAccessBean flavorAccess = new OPSTFlavorAccessBean();
		flavorAccess.setFlavor_id(argFlavor.getString("flavor_id"));
		flavorAccess.setTenant_id(argFlavor.getString("tenant_id"));	
		return flavorAccess;
	}*/
}