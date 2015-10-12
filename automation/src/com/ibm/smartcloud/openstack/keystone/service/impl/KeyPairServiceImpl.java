/*package com.ibm.smartcloud.openstack.keystone.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTErrorMessageException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.service.impl.OPSTBaseServiceImpl;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.StringUtil;
import com.ibm.smartcloud.openstack.keystone.service.KeyPairService;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTKeypairsBean;
import com.ibm.smartcloud.openstack.nova.constants.OPSTComputeConst;

*//**
 * 类描述:     
 * 类名称：IOPSTKeyPairServiceImpl     
 * 创建人：梁瑞   
 * 创建时间：2014年8月13日 下午2:24:17     
 * @version 1.0
 *//*
@Service("keyPairService")
public class KeyPairServiceImpl extends OPSTBaseServiceImpl<Object> implements KeyPairService{
	
	
	*//**
	 * 密钥对查询
	 *//*
	@Override
	public List<OPSTKeypairsBean> getKeypairsV2Ext(String tenantId, String tokenId) throws OPSTBaseException {
		
		List<OPSTKeypairsBean> keypairV2ExtList = new ArrayList<OPSTKeypairsBean>();
		//获取参数信息
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_KEYPAIRS);
		//拼接URL
		String strOrgUrl = novaHost+strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		//String url = novaHost+"/v2/{tenant_id}/os-keypairs";
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取keyPairs list
			JSONArray keypair = jsonObj.getJSONArray("keypairs");
			for (int i = 0; i < keypair.size(); i++) {
				JSONObject jsonObj2 = JSONObject.fromObject(keypair.get(i)).getJSONObject("keypair");
				keypairV2ExtList.add(this.jsonObjToKeypairsV2ExtBean(jsonObj2));
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取KeyPairList的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return keypairV2ExtList;
	}
	
	*//**
	 * 创建密钥对。
	 * @param KeypairsBean
	 *//*
	public OPSTKeypairsBean createKeypairsV2Ext(String tenantId, String tokenId, OPSTKeypairsBean keypairsBean) throws OPSTBaseException {
		
		//获取参数信息
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_KEYPAIRS);
		
		//拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		//String url = novaHost+"/v2/" +tenantId+"/os-keypairs";
		"keypair": {
	        "name": "",
	        "public_key": "AAAABSw9JD9UZHYcpSxsIbECHw== Generated by Nova"
	    }
		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();
		if (keypairsBean.getKeyName() != null) {
			//构建样例"name":"xx"
			serverJSONObj.element(OPSTComputeConst.KEYPAIR_NAME, keypairsBean.getKeyName());
		}
		if (keypairsBean.getPublicKey() != null) {
			//构建样例"public_key":"xx"
			serverJSONObj.element(OPSTComputeConst.KEYPAIR_KEY, keypairsBean.getPublicKey());
		}
		serverRequestBody.element(OPSTComputeConst.KEYPAIR, serverJSONObj);
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl, serverRequestBody.toString());
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTComputeConst.KEYPAIR);
			if (!jsonObj.containsKey("public_key")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}else{
				return this.jsonObjToKeypairsV2ExtBean(jsonObj);
			}
		}catch(JSONException e){
			throw new OPSTOperationException("创建密钥对的时候出现处理json数据异常，类型为："+e.getMessage());
		}
	}
	
	*//**
	 * 删除密钥对。
	 * @param id
	 *//*
	public void deleteKeypairsV2Ext(String tenantId, String tokenId, String argKeypairsName) {
		
		//获取参数信息
		String novaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_KEYPAIRS);
		//拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		strDestUrl = strDestUrl.replace(OPSTComputeConst.KEYPAIR_NAMES,argKeypairsName);
		try {
			HttpClientUtil.deleteMethod(tokenId, strDestUrl);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	*//**
	 * jsonObjToImageV2Bean
	 * @param image
	 * @return
	 *//*
	private OPSTKeypairsBean jsonObjToKeypairsV2ExtBean(JSONObject keyPair){
		OPSTKeypairsBean key = new OPSTKeypairsBean();
		key.setFingerprint(keyPair.getString("fingerprint"));
		key.setKeyName(keyPair.getString("name"));
		key.setPublicKey(keyPair.getString("public_key"));
		if(keyPair.containsKey("private_key")){
			key.setPrivateKey(keyPair.getString("private_key"));
		}
		return key;
	}

}*/