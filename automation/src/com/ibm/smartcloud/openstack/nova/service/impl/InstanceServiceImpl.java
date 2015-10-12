package com.ibm.smartcloud.openstack.nova.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.glance.bean.ImagesBean;
import com.ibm.smartcloud.openstack.neutron.bean.SecurityGroupBean;
import com.ibm.smartcloud.openstack.nova.bean.FlavorBean;
import com.ibm.smartcloud.openstack.nova.bean.ServerAddressBean;
import com.ibm.smartcloud.openstack.nova.bean.ServerBean;
import com.ibm.smartcloud.openstack.nova.constants.OPSTComputeConst;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;


/**
 * @Title:InstanceServiceImpl     
 * @Description:ServiceImpl
 * @Auth:LiangRui   
 * @CreateTime:2015年6月18日 上午11:44:48       
 * @version V1.0
 */
@Service("instanceService")
public class InstanceServiceImpl implements InstanceService {
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	ObjectMapper om = new ObjectMapper();
	
	/**
	 * 查询虚机 Lists IDs, names, and links for all servers.
	 * @throws OPSTOperationException 
	 */
	@Override
	public List<ServerBean> getServerListV2(String tenantId, String tokenId) throws OPSTOperationException {
		List<ServerBean> serverList = new ArrayList<ServerBean>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_ServersList);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			System.out.println(response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			JSONArray servers = jsonObj.getJSONArray(OPSTComputeConst.SERVERBODY);
			for (int i = 0; i < servers.size(); i++) {
				serverList.add(this.jsonObjToOPSTServerBean(servers.getJSONObject(i)));
			}
			serverList = this.getServerListByStatus(serverList);
		} catch (Exception e) {
			throw new OPSTOperationException("获取主机列表的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return serverList;
	}

	public List<ServerBean> getServerListByStatus(List<ServerBean> list){		
		for(ServerBean ser : list){
			Boolean isdisabled = true;
			if(!ser.getStatus().toUpperCase().equals("ACTIVE")){
				isdisabled = false;
			}else if(!ser.getHealth_status().toUpperCase().equals("OK")){
				isdisabled = false;
			}else if(ser.getOperating_system()==null||ser.getOperating_system().toUpperCase().indexOf("AIX 7")==-1){
				isdisabled = false;
			}else if(ser.getAddress()==null||ser.getAddress().size()<1||ser.getAddress().get(0)==null||ser.getAddress().get(0).getAddr()==null){
				isdisabled = false;
			}
			ser.setIsdisabled(isdisabled);
		}
		return list;
	}
	
	/**
	 * 查询HDisk信息
	 */
	@Override
	public String[] getHdiskList(String names, String addrs)throws OPSTBaseException  {
		// 获取参数信息
		String hdiskHost = p.getProperty(OPSTPropertyKeyConst.AMS2_HOST);
		String hdiskApi = p.getProperty(OPSTPropertyKeyConst.POST_ams2_service_cmd);
		// 拼接URL
		String strOrgUrl = hdiskHost + hdiskApi;
		// 构建json
		String actionJson = "{\"name\": \""+names+"\", \"type\": \"shell\", \"exec\": \"cfgmgr && lspv\", "
				+ "\"async\": false,\"node\": {\"host\": \""+addrs+"\", \"addr\": \""+addrs+"\"},"
						+ " \"cred\": {\"user\": \"root\", \"pass\": \"passw0rd\"}}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(strOrgUrl, actionJson);
			System.out.println("response::"+response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			Object resp = jsonObj.get("msg");
			String msg = resp.toString();
			//解析
			String[] lines = msg.split("\\\n");
			System.out.println(lines);
			return lines; 
		} catch (Exception e) {
			throw new OPSTOperationException("获取HDisks的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
	}
	
	/**
	 * 执行
	 */
	@Override
	public String createDbInfo(String names, String addrs, String infos)throws OPSTBaseException {
		// 获取参数信息
		String hdiskHost = p.getProperty(OPSTPropertyKeyConst.AMS2_HOST);
		String hdiskApi = p.getProperty(OPSTPropertyKeyConst.POST_ams2_service_cmd);
		// 拼接URL
		String strOrgUrl = hdiskHost + hdiskApi;
		// 构建json
		String actionJson = "{\"name\": \""+names+"\", \"type\": \"touch\", \"exec\": \"/tmp/aaa.txt\", \"async\": false,\"text\": \""+infos+"\",\"node\": {\"host\": \""+addrs+"\", \"addr\": \""+addrs+"\"}, \"cred\": {\"user\": \"root\", \"pass\": \"passw0rd\"}}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(strOrgUrl, actionJson);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			//JSONObject jsonObj = JSONObject.fromObject(response);
			//Object resp = jsonObj.get("msg");
			//String msg = resp.toString();
			//解析
			//String[] lines = msg.split("\\\n");
			return response; 
		} catch (Exception e) {
			throw new OPSTOperationException("获取HDisks的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
	}
	
	
	
	/**
	 * 创建虚拟机Creates a server.
	 *//*
	@Override
	public String createServerV2(OPSTServerCreateBean argServerBean, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.POST_Servers);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// String url = novaHost+"/v2/" +tenantId+"/servers";
		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();
		JSONObject networksJSONObj = null;
		JSONObject sercurityJSONObj = null;
		JSONArray networksJSONArray = new JSONArray();
		JSONArray sercurityJSONArray = new JSONArray();
		if (argServerBean.getName() != null) {
			// 构建样例"name":"xx"
			serverJSONObj.element(OPSTComputeConst.SERVER_NAME, argServerBean.getName());
		}
		if (argServerBean.getKeyPair() != null) {
			// 构建样例"os-keypairs":"xx"
			serverJSONObj.element(OPSTComputeConst.SERVER_KEYPAIR, argServerBean.getKeyPair());
		}
		if (argServerBean.getImageId() != null) {
			// 构建样例"imageRef":"sample_network"
			serverJSONObj.element(OPSTComputeConst.SERVER_IMAGEID, argServerBean.getImageId());
		}
		if (argServerBean.getFlavorsId() != null) {
			// 构建样例"flavorRef":"sample_network"
			serverJSONObj.element(OPSTComputeConst.SERVER_FLAVORID, argServerBean.getFlavorsId());
		}
		if (argServerBean.getAvailabilityZone() != null) {
			// 构建样例"availability_zone":"nova"
			serverJSONObj.element(OPSTComputeConst.AVAILABILITY_ZONE, argServerBean.getAvailabilityZone());
		}
		if (argServerBean.getNetworkId() != null) {
			for (int i = 0; i < argServerBean.getNetworkId().size(); i++) {
				networksJSONObj = new JSONObject();
				// 构建样例"uuid":"sample_network"
				networksJSONObj.element(OPSTComputeConst.SERVER_UUID, argServerBean.getNetworkId().get(i));
				// 构建样例{"networks":{}}
				networksJSONArray.element(networksJSONObj);
			}
			serverJSONObj.element(OPSTComputeConst.NETWORKBODY, networksJSONArray);
		}
		if (argServerBean.getSecurityGroups() != null) {
			for (int i = 0; i < argServerBean.getSecurityGroups().size(); i++) {
				sercurityJSONObj = new JSONObject();
				// 构建样例"name":"default"
				sercurityJSONObj.element(OPSTComputeConst.SECURITY_NAME, argServerBean.getSecurityGroups().get(i));
				// 构建样例{"networks":{}}
				sercurityJSONArray.element(sercurityJSONObj);
			}
			serverJSONObj.element(OPSTComputeConst.SECURITY_GROUP, sercurityJSONArray);
		}
		serverRequestBody.element(OPSTComputeConst.SERVER, serverJSONObj);
		logger.info("create server json=" + serverRequestBody.toString());
		String response = "";
		JSONObject j = new JSONObject();
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, serverRequestBody.toString());
			j = JSONObject.fromObject(response);
			if (!j.containsKey(OPSTComputeConst.SERVER)) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		String serverId;
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject("server");
			serverId = jsonObj.get("id").toString();
		} catch (Exception e) {
			throw new OPSTOperationException("创建Instance的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return serverId;
	}

	*//**
	 * 查询虚拟机详细信息Lists details for all servers.
	 *//*
	@Override
	public List<OPSTServerBean> getServerDetailListV2(String tenantId, String tokenId,boolean isAdmin) throws OPSTBaseException {
		List<OPSTServerBean> serverList = new ArrayList<OPSTServerBean>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_ServersListDetails);
		// 拼接URL
		String response = "";
			if (isAdmin) {
				String strOrgUrl = novaHost + strServerApi + "?all_tenants=True";
				String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
				try {
					response = HttpClientUtil.getMethod(tokenId, strDestUrl);// 查询Server信息
				} catch (OPSTBaseException e1) {
					throw e1;
				}
			} else {
				String strOrgUrl = novaHost + strServerApi;
				String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
				try {
					response = HttpClientUtil.getMethod(tokenId, strDestUrl);// 查询Server信息
				} catch (OPSTBaseException e1) {
					throw e1;
				}
			}
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取images list
			JSONArray servers = jsonObj.getJSONArray(OPSTComputeConst.SERVERBODY);
			for (int i = 0; i < servers.size(); i++) {
				serverList.add(this.jsonObjToOPSTServerBean(servers.getJSONObject(i)));
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取InstanceList的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return serverList;
	}

	*//**
	 * 根据ID查询虚拟机Gets details for a specified server.
	 *//*
	@Override
	public OPSTServerBean getServerV2(String argServerId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_ServersDetails);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		String strDestUrls = strDestUrl.replace(OPSTComputeConst.PARAM_SERVERID, argServerId);
		// String url = novaHost+"/v2/"+tenantId+"/servers/"+argServerId;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(strDestUrls);
		getMethod.addRequestHeader("Content-Type", "application/json");
		try {
			getMethod.setRequestHeader("X-Auth-Token", tokenId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String response = "";
		try {
			int status = httpClient.executeMethod(getMethod);
			if (status != HttpStatus.SC_OK) {
				System.out.println("not ok,code is =" + status);
			}
			response = getMethod.getResponseBodyAsString();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			getMethod.releaseConnection();
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTComputeConst.SERVER);
			return this.jsonObjToOPSTServerBean(jsonObj);
		} catch (Exception e) {
			throw new OPSTOperationException("根据ID查询虚机信息的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
	}

	*//**
	 * 主机重启
	 *//*
	@Override
	public void doServerActionV2(String argServerId, String serverActionType, String tenantId, String tokenId) {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String actionJson = "";
		if (serverActionType.equals(OPSTServerActionTypeConst.REBOOT.getAction())) {
			actionJson = "{\"reboot\" : {\"type\" : \"SOFT\" }}";
		}
		if (actionJson != null && actionJson.trim().length() > 0) {
			String url = novaHost + "/v2/" + tenantId + "/servers/" + argServerId + "/action";
			try {
				HttpClientUtil.getMethod(tokenId, url);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}


	*//**
	 * 查询Flavos Detial信息
	 *//*
	@Override
	public List<OPSTFlavorBean> getFlavorsDetailListV2(String tenantId, String tokenId) throws OPSTBaseException {
		List<OPSTFlavorBean> flavorV2List = new ArrayList<OPSTFlavorBean>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_FlavorsExtDetails);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		// String url = novaHost+"/v2/flavors/detail";
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strOrgUrl);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取flavors list
			JSONArray flavors = jsonObj.getJSONArray(OPSTExtensionsConst.FLAVORS);
			for (int i = 0; i < flavors.size(); i++) {
				flavorV2List.add(this.jsonObjToFlavorV2Bean(flavors.getJSONObject(i)));
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取FlavorsDetial的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return flavorV2List;
	}

	*//**
	 * 根据ID查询Flavos信息
	 *//*
	@Override
	public OPSTFlavorBean getFlavorsDetailV2(String argFlavorId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_FlavorsExtDetails);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).trim();
		String strDestUrl2 = strDestUrl.replace(OPSTExtensionsConst.PARAM_FLAVORID, argFlavorId).trim();
		// String url = novaHost+"/v2/"+tenantId+"/flavors/"+ argFlavorId;
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTExtensionsConst.FLAVOR);
			return this.jsonObjToFlavorV2Bean(jsonObj);
		} catch (Exception e) {
			throw new OPSTOperationException("获取FlavorsDetial的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
	}

	@Override
	public void createFlavors(OPSTFlavorBean flavorBean, String tenantId, String tokenId) {

		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.POST_Flavors);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID, tenantId);
		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();
		JSONObject networksJSONObj = new JSONObject();
		if (flavorBean.getId() != null) {
			// 构建样例"id":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_ID, flavorBean.getId());
		}
		if (flavorBean.getName() != null) {
			// 构建样例"name":"xx"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_NAME, flavorBean.getName());
		}
		if (flavorBean.getRam() != null) {
			// 构建样例"ram":"sample_network"
			networksJSONObj.element(OPSTFlavorsConst.FLAVORS_RAM, flavorBean.getRam());
		}
		if (flavorBean.getVcpus() != null) {
			// 构建样例"vcpus":"sample_network"
			networksJSONObj.element(OPSTFlavorsConst.FLAVORS_VCPUS, flavorBean.getVcpus());
		}
		if (flavorBean.getDisk() != null) {
			// 构建样例"disk":"sample_network"
			serverJSONObj.element(OPSTFlavorsConst.FLAVORS_DISK, flavorBean.getDisk());
		}
		serverRequestBody.element(OPSTFlavorsConst.FLAVOR, serverJSONObj);
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, serverRequestBody.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(response);
	}

	@Override
	public void deleteFlavors(String flavor_id, String tenantId, String tokenId) {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.DELETE_Flavors);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_TENANTID, tenantId);
		strDestUrl = strOrgUrl.replace(OPSTFlavorsConst.PARAM_FLAVORSID, flavor_id);
		try {
			HttpClientUtil.deleteMethod(tokenId, strDestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 获取镜像List
	 *//*
	@Override
	public List<OPSTImageBean> getImagesListV2(String tenantId, String tokenId) throws OPSTBaseException {
		List<OPSTImageBean> imgList = new ArrayList<OPSTImageBean>();
		// 获取参数信息
		String strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		String strImageApi = p.getProperty(OPSTPropertyKeyConst.GET_ImagesList);
		// String url = imageHost+ "/v2/images";
		String strOrgUrl = strImageHost + strImageApi;
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strOrgUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取images list
			JSONArray images = jsonObj.getJSONArray(OPSTImageConst.IMAGES);
			for (int i = 0; i < images.size(); i++) {
				imgList.add(this.jsonObjToImageV2Bean(images.getJSONObject(i)));
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取ImageList的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return imgList;
	}

	@Override
	public void getImagesDetailListV2(String tenantId, String tokenId) {
		// TODO Auto-generated method stub

	}

	*//**
	 * 根据ID查询镜像信息
	 *//*
	@Override
	public OPSTImageBean getImageByIdV2(String argImageId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		String strImageApi = p.getProperty(OPSTPropertyKeyConst.GET_ImagesDetails);
		// String url=imageHost+ "/v2/"+"images/"+argImageId;
		String strOrgUrl = strImageHost + strImageApi;
		String strDestUrl = strOrgUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			return this.jsonObjToImageV2Bean(jsonObj);
		} catch (Exception e) {
			throw new OPSTOperationException("获取ImageInfo的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
	}

	*//**
	 * 获取openstack虚拟机的vnc地址。
	 * 
	 * @param openstackVmId
	 * @return
	 *//*
	@Override
	public String getInstanceConsoleV2Ext(String argServerId, String tenantId, String tokenId) throws OPSTBaseException {
		String address = null;
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		// String url =
		// novaHost+"/v2/"+tenantId+"/servers/"+argServerId+"/action";
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.POST_ServerActionsRebuild);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).trim();
		String strDestUrl2 = strDestUrl.replace(OPSTComputeConst.PARAM_SERVERID, argServerId).trim();
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject(OPSTComputeConst.CONSOLE);
			address = jsonObj.getString("url");
		} catch (Exception e) {
			throw new OPSTOperationException("获取VNC地址的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return address;
	}

	*//**
	 * 实例操作。启动，停止，restore，删除。
	 * 
	 * @param serverId
	 * @param actionType
	 *//*
	@Override
	public boolean instanceServerActionV2Ext(String argServerId, String serverActionType, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String actionJson = "";
		if (serverActionType.equals(OPSTServerActionTypeConst.OSSTART.getAction())) {// API有疑问
			// 开始
			actionJson = "{\"os-start\": null}";// os-start
		} else if (serverActionType.equals(OPSTServerActionTypeConst.OSSTOP.getAction())) {// API有疑问
			// 停止
			actionJson = "{\"os-stop\": null}";// os-stop
		} else if (serverActionType.equals(OPSTServerActionTypeConst.TERMINATES.getAction())) {// 暂时用不到
			// terminates
			actionJson = "";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.RESTORE.getAction())) {// 暂时用不到
			actionJson = "{\"restore\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.PAUSED.getAction())) {
			// 暂停
			actionJson = "{\"pause\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.UNPAUSED.getAction())) {
			// 恢复暂停的实例
			actionJson = "{\"unpause\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.SUSPEND.getAction())) {
			// 挂起
			actionJson = "{\"suspend\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.RESUME.getAction())) {
			// 激活挂起的实例
			actionJson = "{\"resume\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.REBOOT.getAction())) {
			// 软重启
			actionJson = "{\"reboot\" : {\"type\" : \"SOFT\" }}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.HARDREBOOT.getAction())) {
			// 硬重启
			actionJson = "{\"reboot\" : {\"type\" : \"HARD\" }}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.CONFIRMRESIZE.getAction())) {
			// 确认调整主机大小
			actionJson = "{\"confirmResize\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.REVERTRESIZE.getAction())) {
			// 取消调整主机大小
			actionJson = "{\"revertResize\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.SERVERSHELVE.getAction())) {
			// 休眠
			actionJson = "{\"shelve\": null}";
		} else if (serverActionType.equals(OPSTServerActionTypeConst.SERVERUNSHELVE.getAction())) {
			// 取消休眠
			actionJson = "{\"unshelve\": null}";
		}
		boolean returnFlg = false;
		if (actionJson != null && actionJson.trim().length() > 0) {
			String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
			// 拼接URL
			String strOrgUrl = novaHost + action;
			String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, argServerId);
			String response = "";
			try {
				response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
				if ("".equals(response) || response == null) {
					returnFlg = true;
				} else {
					String error = StringUtil.jsonErrorToErrorMessage(response);
					throw new OPSTErrorMessageException(error);
				}
			} catch (OPSTBaseException e) {
				throw e;
			}
		}
		return returnFlg;
	}

	@Override
	public void deleteImageV2(String argImageId, String tenantId, String tokenId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getImageMetadataV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createOrRepalceImageMetaDataV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateImageMetadataByKeyV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getImageMetadataByKeyV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImageMetadataByKeyV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteImageMetadataV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOPSTVersionV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOPSTExtensionListV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOPSTExtensionDetailV2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOPSTLimitV2() {
		// TODO Auto-generated method stub

	}

	*//**
	 * 修改主机信息
	 *//*
	@Override
	public String updateServerV2(OPSTServerBean argOPSTServerBean, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.PUT_Servers);// PUT_Servers=/v2/{tenant_id}/servers/{server_id}
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		String strDestUrls = strDestUrl.replace(OPSTComputeConst.PARAM_SERVERID, argOPSTServerBean.getId());
		// 构建结构
		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();
		if (argOPSTServerBean.getName() != null) {
			// 构建样例"name":"xx"
			serverJSONObj.element(OPSTComputeConst.SERVER_NAME, argOPSTServerBean.getName());
		}
		serverRequestBody.element(OPSTComputeConst.SERVER, serverJSONObj);
		logger.info("create server json=" + serverRequestBody.toString());
		String response = "";
		try {
			response = HttpClientUtil.updateMethod(tokenId, strDestUrls, serverRequestBody.toString());
			if (!response.contains(OPSTComputeConst.SERVER)) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (Exception e) {
			throw new OPSTOperationException("修改实例信息的时候出现异常，类型为：" + e.getMessage());
		}
		return response;
	}

	*//**
	 * 根据ID删除实例
	 *//*
	@Override
	public void deleteServerV2(String argServerId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.DELETE_Servers);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		strDestUrl = strDestUrl.replace(OPSTComputeConst.PARAM_SERVERID, argServerId);
		try {
			HttpClientUtil.deleteMethod(tokenId, strDestUrl);
		} catch (Exception e) {
			throw new OPSTOperationException("删除实例的时候出现异常，类型为：" + e.getMessage());
		}
	}

	@Override
	public OPSTServerBean getServerMetadataV2(OPSTServerBean argOPSTServerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerBean createOrRepalceServerMetaDataV2(String argServerId, OPSTServerMetaBean argMetaBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerBean updateServerMetadataByKeyV2(String argServerId, OPSTServerMetaBean argMetaBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerBean getServerMetadataByKeyV2(String argServerId, OPSTServerMetaBean argMetaBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerBean setServerMetadataByKeyV2(String argServerId, OPSTServerMetaBean argMetaBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerBean deleteServerMetadataV2(String argServerId, String argMetaDataKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerAddressBean getServerNetworksV2(String argServerId, String tenantId, String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTServerAddressBean getServerIPaddressV2(String argServerId, String argNetworkLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	*//**
	 * 密钥对查询
	 *//*
	@Override
	public List<OPSTKeypairsBean> getKeypairsV2Ext(String tenantId, String tokenId) throws OPSTBaseException {
		List<OPSTKeypairsBean> keypairV2ExtList = new ArrayList<OPSTKeypairsBean>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_KEYPAIRS);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// String url = novaHost+"/v2/{tenant_id}/os-keypairs";
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
			throw new OPSTOperationException("获取KeyPairList的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return keypairV2ExtList;
	}

	*//**
	 * 可用域
	 *//*
	public List<OPSTAvailabilityZoneInfo> getZonesDetailList(String tenantId, String tokenId) throws OPSTBaseException {
		List<OPSTAvailabilityZoneInfo> zoneList = new ArrayList<OPSTAvailabilityZoneInfo>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_OsAvailabilityZoneDetail);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取images list
			JSONArray servers = jsonObj.getJSONArray(OPSTComputeConst.AVAILABILITYZONEINFO);
			for (int i = 0; i < servers.size(); i++) {
				zoneList.add(this.jsonObjToOPSTAvailabilityZone(servers.getJSONObject(i)));
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取AvailabilityZoneDetail的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return zoneList;
	}

	public List<OPSTAvailabilityZoneInfo> getZonesList(String tenantId, String tokenId) throws OPSTBaseException {
		List<OPSTAvailabilityZoneInfo> zoneList = new ArrayList<OPSTAvailabilityZoneInfo>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_OsAvailabilityZone);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取images list
			JSONArray servers = jsonObj.getJSONArray(OPSTComputeConst.AVAILABILITYZONEINFO);
			for (int i = 0; i < servers.size(); i++) {
				zoneList.add(this.jsonObjToOPSTAvailabilityZone(servers.getJSONObject(i)));
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取AvailabilityZone的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return zoneList;
	}

	@Override
	public void deleteKeypairsV2Ext(String argKeypairsName, String tenantId, String tokenId) {
		// TODO Auto-generated method stub

	}

	*//**
	 * jsonObjToImageV2Bean
	 * 
	 * @param image
	 * @return
	 *//*
	private OPSTKeypairsBean jsonObjToKeypairsV2ExtBean(JSONObject keyPair) {
		OPSTKeypairsBean key = new OPSTKeypairsBean();
		key.setFingerprint(keyPair.getString("fingerprint"));
		key.setKeyName(keyPair.getString("name"));
		key.setPublicKey(keyPair.getString("public_key"));
		return key;
	}

	*//**
	 * jsonObjToImageV2Bean
	 * 
	 * @param image
	 * @return
	 *//*
	private OPSTImageBean jsonObjToImageV2Bean(JSONObject image) {
		OPSTImageBean ib = new OPSTImageBean();
		if (null != image.getString("id")) {
			ib.setId(image.getString("id"));
		}
		if (image.containsKey("name")) {
			if (null != image.getString("name")) {
				ib.setName(image.getString("name"));
			}
		}
		if (null != image.getString("status")) {
			ib.setStatus(image.getString("status"));
		}
		if (image.containsKey("container_format")) {
			if (null != image.getString("container_format")) {
				ib.setContainer_format(image.getString("container_format"));
			}
		}
		if (null != image.getString("created_at")) {
			ib.setCreated_at(image.getString("created_at"));
		}
		if (image.containsKey("disk_format")) {
			if (null != image.getString("disk_format")) {
				ib.setDisk_format(image.getString("disk_format"));
			}
		}
		if (null != image.getString("min_disk")) {
			ib.setMin_disk(Integer.parseInt(image.getString("min_disk")));
		}
		if (null != image.getString("protected")) {
			ib.setImgProtected(Boolean.parseBoolean(image.getString("protected")));
		}
		if (null != image.getString("min_ram")) {
			ib.setMin_ram(Integer.parseInt(image.getString("min_ram")));
		}
		if (image.containsKey("checksum")) {
			if (null != image.getString("checksum")) {
				ib.setChecksum(image.getString("checksum"));
			}
		}
		if (null != image.getString("size")) {
			ib.setSize(Long.parseLong(image.getString("size")));
		}
		return ib;
	}

	/**
	 * jsonObjToOPSTServerBean
	 * 
	 * @param argServer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ServerBean jsonObjToOPSTServerBean(JSONObject argServer) {
		ServerBean server = new ServerBean();
		if (argServer.containsKey("id")) {
			server.setId(argServer.getString("id"));
		} else {
			server.setId("-");
		}
		if (argServer.containsKey("name")) {
			server.setName(argServer.getString("name"));
		} else {
			server.setName("-");
		}
		if (argServer.containsKey("status")) {
			server.setStatus(argServer.getString("status"));
		} else {
			server.setStatus("-");
		}
		if (argServer.containsKey("tenant_id")) {
			server.setTenant_id(argServer.getString("tenant_id"));
		} else {
			server.setTenant_id("-");
		}
		if (argServer.containsKey("updated")) {
			server.setUpdated(argServer.getString("updated"));
		} else {
			server.setUpdated("-");
		}
		if (argServer.containsKey("user_id")) {
			server.setUser_id(argServer.getString("user_id"));
		} else {
			server.setUser_id("-");
		}
		if (argServer.containsKey("hostId")) {
			server.setHostId(argServer.getString("hostId"));
		} else {
			server.setHostId("-");
		}
		if (argServer.containsKey("created")) {
			server.setCreated(argServer.getString("created"));
		} else {
			server.setCreated("-");
		}
		if (argServer.containsKey("accessIPv4")) {
			server.setAccessIPv4(argServer.getString("accessIPv4"));
		} else {
			server.setAccessIPv4("-");
		}
		if (argServer.containsKey("accessIPv6")) {
			server.setAccessIPv6(argServer.getString("accessIPv6"));
		} else {
			server.setAccessIPv6("-");
		}
		if (argServer.containsKey("OS-DCF:diskConfig")) {
			server.setOS_DCF_diskConfig(argServer.getString("OS-DCF:diskConfig"));
		} else {
			server.setOS_DCF_diskConfig("-");
		}
		if (argServer.containsKey("key_name")) {
			server.setKey_name(argServer.getString("key_name"));
		} else {
			server.setKey_name("-");
		}
		if (argServer.containsKey("OS-EXT-STS:power_state")) {// 电源状态
			if (argServer.getString("OS-EXT-STS:power_state").equals("0")) {// No
				server.setOS_EXT_STS_power_state("No State");
			} else if (argServer.getString("OS-EXT-STS:power_state").equals("1")) {// Running
				server.setOS_EXT_STS_power_state("Running");
			} else if (argServer.getString("OS-EXT-STS:power_state").equals("4")) {// Shutdown
				server.setOS_EXT_STS_power_state("Shutdown");
			} else {
				server.setOS_EXT_STS_power_state("Shutdown");
			}
		} else {
			server.setOS_EXT_STS_power_state("-");
		}
		if (argServer.containsKey("OS-EXT-STS:task_state")) {// 任务状态
			if (argServer.getString("OS-EXT-STS:task_state").equals("null")) {// None
				server.setOS_EXT_STS_task_state("None");
			} else {
				server.setOS_EXT_STS_task_state(argServer.getString("OS-EXT-STS:task_state"));
			}
		} else {
			server.setOS_EXT_STS_task_state("-");
		}
		if (argServer.containsKey("OS-EXT-AZ:availability_zone")) {
			server.setOS_EXT_AZ_availability_zone(argServer.getString("OS-EXT-AZ:availability_zone"));
		} else {
			server.setOS_EXT_AZ_availability_zone("-");
		}
		if (argServer.containsKey("OS-EXT-SRV-ATTR:hypervisor_hostname")) {
			server.setOS_EXT_SRV_ATTR_hypervisor_hostname(argServer.getString("OS-EXT-SRV-ATTR:hypervisor_hostname"));
		} else {
			server.setOS_EXT_SRV_ATTR_hypervisor_hostname("-");
		}
		if (argServer.containsKey("OS-EXT-SRV-ATTR:instance_name")) {
			server.setOS_EXT_SRV_ATTR_instance_name(argServer.getString("OS-EXT-SRV-ATTR:instance_name"));
		} else {
			server.setOS_EXT_SRV_ATTR_instance_name("-");
		}
		if (argServer.containsKey("addresses")) {
			JSONObject obj1 = argServer.getJSONObject("addresses");
			JSONArray obj2 = obj1.names();
			List<ServerAddressBean> serAdd = new ArrayList<ServerAddressBean>();
			for (int i = 0; i < obj2.size(); i++) {
				ServerAddressBean address = new ServerAddressBean();
				String netName = (String) obj2.get(i);
				JSONArray netInfo = obj1.getJSONArray(netName);
				if(netInfo.size()>0){
					JSONObject obj3 = netInfo.getJSONObject(0);
					address.setNetName(netName);
					address.setAddr(obj3.getString("addr"));
					address.setOS_EXT_IPS_type(obj3.getString("OS-EXT-IPS:type"));
					address.setVersion(obj3.getString("version"));
					serAdd.add(address);
				}
				
			}
			server.setAddress(serAdd);
		}
		ImagesBean asa = new ImagesBean();
		if (!argServer.get("image").equals("")) {
			if (null != argServer.getJSONObject("image")) {
				JSONObject img = (JSONObject) argServer.get("image");
				String id = img.getString("id");
				if (id != null) {
					asa.setId(id);
					server.setImage(asa);
				}
			} else {
				String img = (String) argServer.get("image");
				if (img == null) {
					img = "-";
				}
				JSONObject imgObj = JSONObject.fromObject(img);
				String id = imgObj.getString("id");
				if (id != null) {
					asa.setId(id);
					server.setImage(asa);
				}
			}
		} else {
			String img = (String) argServer.get("image");
			if (img.equals("") || img == null) {
				img = "-";
				server.setImage(asa);
			} else {
				JSONObject imgObj = JSONObject.fromObject(img);
				String id = imgObj.getString("id");
				if (id != null) {
					asa.setId(id);
					server.setImage(asa);
				}
			}
		}
		FlavorBean ofb = new FlavorBean();
		if (argServer.containsKey("flavor")) {
			JSONObject fla = (JSONObject) argServer.get("flavor");
			ofb.setId(fla.getString("id"));
			server.setFlavor(ofb);
		}
		if (argServer.containsKey("security_groups")) {
			if (null != argServer.getJSONArray("security_groups")) {
				JSONArray group = argServer.getJSONArray("security_groups");
				List<SecurityGroupBean> grp = JSONArray.toList(group, SecurityGroupBean.class);
				server.setSecurityGroups(grp);
			}
		}
		
		if(argServer.containsKey("health_status")){
			JSONObject fla = (JSONObject) argServer.get("health_status");
			if(fla.containsKey("health_value")){
				server.setHealth_status(fla.getString("health_value"));
			}
		}
		
		if(argServer.containsKey("operating_system")){
			server.setOperating_system(argServer.getString("operating_system"));
			
		}
		
		return server;
	}


	

	/**
	 * jsonObjToOPSTServerBean
	 * 
	 * @param argServer
	 * @return
	 *//*
	private OPSTAvailabilityZoneInfo jsonObjToOPSTAvailabilityZone(JSONObject argServer) {
		OPSTAvailabilityZoneInfo zone = new OPSTAvailabilityZoneInfo();
		zone.setZoneState(argServer.getString("zoneState"));
		zone.setZoneName(argServer.getString("zoneName"));
		return zone;
	}

	*//**
	 * jsonObjToFlavorV2Bean
	 * 
	 * @param argFlavor
	 * @return
	 *//*
	private OPSTFlavorBean jsonObjToFlavorV2Bean(JSONObject argFlavor) {
		OPSTFlavorBean flavor = new OPSTFlavorBean();
		flavor.setId(argFlavor.getString("id"));
		// flavor.setLinks(argFlavor.getString("links"));
		flavor.setName(argFlavor.getString("name"));
		flavor.setRam(argFlavor.getString("ram"));
		flavor.setVcpus(argFlavor.getString("vcpus"));
		flavor.setDisk(argFlavor.getString("disk"));
		flavor.setSwap(argFlavor.getString("swap"));
		flavor.setEphemeral(argFlavor.getString("OS-FLV-EXT-DATA:ephemeral"));
		// flavor.setIsPublic(argFlavor.getString("os-flavor-access:is_public"));
		flavor.setTotalDisk(String.valueOf(Double.parseDouble(flavor.getDisk()) + Double.parseDouble(flavor.getEphemeral())));

		return flavor;
	}

	// 调用LimitsAPI
	public OPSTServersLimitsBean getServersLimisV2EXT(String tenantId, String tokenId) throws OPSTBaseException {
		
			// 获取参数信息
			String strHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			// v2/{tenant_id}/limits
			String strAPI = p.getProperty(OPSTPropertyKeyConst.GET_LimitsList);
			String strUrl = strHost + strAPI;
			String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
			String response = "";
			try {
				response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!response.equals("")) {
				JSONObject jsonObject = JSONObject.fromObject(response);
				if (jsonObject.containsKey(OPSTComputeConst.SERVER_LIMITS)) {
					JSONObject jsonObjLimits = jsonObject.getJSONObject(OPSTComputeConst.SERVER_LIMITS);
					if (jsonObjLimits.containsKey(OPSTComputeConst.SERVER_LIMITS_ABSOLUTE)) {
						JSONObject jsonObj = JSONObject.fromObject(jsonObjLimits).getJSONObject(OPSTComputeConst.SERVER_LIMITS_ABSOLUTE);
						return this.jsonObjToLimitsBeanV2EXT(jsonObj);
					}
				}
			}
		
		return null;
	}

	private OPSTServersLimitsBean jsonObjToLimitsBeanV2EXT(JSONObject jsonObj) {
		OPSTServersLimitsBean limits = new OPSTServersLimitsBean();
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXSERVERMETA)) {
			limits.setMaxServerMeta(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXSERVERMETA));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXPERSONALITY)) {
			limits.setMaxPersonality(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXPERSONALITY));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXIMAGEMETA)) {
			limits.setMaxImageMeta(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXSERVERMETA));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXPERSONALITYSIZE)) {
			limits.setMaxPersonalitySize(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXPERSONALITYSIZE));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXSECURITYGROUPRULES)) {
			limits.setMaxSecurityGroupRules(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXSECURITYGROUPRULES));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXTOTALKEYPAIRS)) {
			limits.setMaxTotalKeypairs(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXTOTALKEYPAIRS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_TOTALRAMUSED)) {
			limits.setTotalRAMUsed(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_TOTALRAMUSED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_TOTALINSTANCESUSED)) {
			limits.setTotalInstancesUsed(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_TOTALINSTANCESUSED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXSECURITYGROUPS)) {
			limits.setMaxSecurityGroups(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXSECURITYGROUPS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_TOTALFLOATINGIPSUSED)) {
			limits.setTotalFloatingIpsUsed(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_TOTALFLOATINGIPSUSED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXTOTALCORES)) {
			limits.setMaxTotalCores(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXTOTALCORES));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_TOTALSECURITYGROUPSUSED)) {
			limits.setTotalSecurityGroupsUsed(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_TOTALSECURITYGROUPSUSED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXTOTALFLOATINGIPS)) {
			limits.setMaxTotalFloatingIps(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXTOTALFLOATINGIPS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXTOTALINSTANCES)) {
			limits.setMaxTotalInstances(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXTOTALINSTANCES));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_TOTALCORESUSED)) {
			limits.setTotalCoresUsed(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_TOTALCORESUSED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.SERVER_LIMITS_MAXTOTALRAMSIZE)) {
			limits.setMaxTotalRAMSize(jsonObj.getString(OPSTComputeConst.SERVER_LIMITS_MAXTOTALRAMSIZE));
		}
		return limits;
	}

	*//**
	 * 实例操作。创建数据盘快照。
	 * 
	 * @param serverId
	 * @param snapshotName
	 *//*
	@Override
	public boolean createSnapshotActionV2(String serverId, String snapshotName, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 创建数据盘快照
		String actionJson = "{\"createImage\" : {\"name\" : \"" + snapshotName + "\" }}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			if (!response.equals("") && response != null) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			} else {
				return true;
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
	}

	*//**
	 * Usage reports (os-simple-tenant-usage) API 项目概况信息
	 *//*
	public List<OPSTUsageReportsBean> getComputeUsageReportsListV2EXT(String tenantId, String tokenId) throws OPSTBaseException {
		
			List<OPSTUsageReportsBean> beanList = null;
			// 获取参数信息
			String strHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			// 参数 开始时间\结束时间
			SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, 1);
			String endDate = matter.format(date);
			String stratDate = matter.format(cal.getTime());
			// /v2/{tenant_id}/os-simple-tenant-usage/{tenant_id}
			String strAPI = p.getProperty(OPSTPropertyKeyConst.GET_TENANTUSAGEDETAIL);
			String strUrl = strHost + strAPI + "?start=" + stratDate + "T00:00:00&end=" + endDate + "T23:59:59";
			String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
			String response = "";
			try {
				response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			} catch (OPSTBaseException e) {
				throw e;
			}
			try {
				if (!response.equals("")) {
					JSONObject jsonObj = JSONObject.fromObject(response);
					if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_TENANT_USAGE)) {
						JSONObject jsonObjLimits = jsonObj.getJSONObject(OPSTComputeConst.EXT_USAGEREPORTS_TENANT_USAGE);
						if (jsonObjLimits.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_TU_SERVER_USAGES)) {
							beanList = new ArrayList<OPSTUsageReportsBean>();
							JSONArray list = jsonObjLimits.getJSONArray(OPSTComputeConst.EXT_USAGEREPORTS_TU_SERVER_USAGES);
							for (int i = 0; i < list.size(); i++) {
								beanList.add(this.jsonObjToUsageReportBeanV2EXT(list.getJSONObject(i)));
							}
							return beanList;
						}
					}
				}
			} catch (Exception e) {
				throw new OPSTOperationException("获取ComputeUsageReportsList时出现处理json数据异常，类型为：" + e.getMessage());
			}
		
		return null;
	}

	private OPSTUsageReportsBean jsonObjToUsageReportBeanV2EXT(JSONObject jsonObj) {
		OPSTUsageReportsBean opBean = new OPSTUsageReportsBean();
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_INSTANCE_ID)) {
			opBean.setInstance_id(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_INSTANCE_ID));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_UPTIME)) {
			opBean.setUptime(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_UPTIME));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_STARTED_AT)) {
			opBean.setStarted_at(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_STARTED_AT));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_ENDED_AT)) {
			opBean.setEnded_at(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_STARTED_AT));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_MEMORY_MB)) {
			opBean.setMemory_mb(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_MEMORY_MB));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_TENANT_ID)) {
			opBean.setTenant_id(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_TENANT_ID));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_STATE)) {
			opBean.setState(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_STATE));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_HOURS)) {
			opBean.setHours(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_HOURS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_VCPUS)) {
			opBean.setVcpus(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_VCPUS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_FLAVOR)) {
			opBean.setFlavor(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_FLAVOR));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_LOCAL_GB)) {
			opBean.setLocal_gb(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_LOCAL_GB));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_USAGEREPORTS_SU_NAME)) {
			opBean.setName(jsonObj.getString(OPSTComputeConst.EXT_USAGEREPORTS_SU_NAME));
		}
		return opBean;
	}

	// Hypervisor 虚拟机管理
	public List<OPSTHypervisorsBean> getHypervisorDetailListV2EXT(String tenantId, String tokenId) throws OPSTBaseException {
		
			List<OPSTHypervisorsBean> hBeanList = new ArrayList<OPSTHypervisorsBean>();
			// 获取参数信息
			String strHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			// String tenantId;
			// /v2/{tenant_id}/os-hypervisors/detail
			String strAPI = p.getProperty(OPSTPropertyKeyConst.GET_HYPERVISORDETAIL);
			String strUrl = strHost + strAPI;
			String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
			String response = "";
			try {
				response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			} catch (OPSTBaseException e) {
				throw e;
			}
			try {
				if (!response.equals("")) {
					JSONObject jsonObj = JSONObject.fromObject(response);
					if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS)) {
						JSONArray list = jsonObj.getJSONArray(OPSTComputeConst.EXT_HYPERVISORS);
						for (int i = 0; i < list.size(); i++) {
							hBeanList.add(this.jsonObjToHypervisorBeanV2EXT(list.getJSONObject(i)));
						}
						return hBeanList;
					}
				}
			} catch (JSONException e) {
				throw new OPSTOperationException("获取HypervisorDetailList时出现处理json数据异常，类型为：" + e.getMessage());
			}
		
		return null;
	}

	private OPSTHypervisorsBean jsonObjToHypervisorBeanV2EXT(JSONObject jsonObj) {
		OPSTHypervisorsBean hbean = new OPSTHypervisorsBean();
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_CPU_INFO)) {
			hbean.setCpu_info(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_CPU_INFO));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_CURRENT_WORKLOAD)) {
			hbean.setCurrent_workload(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_CURRENT_WORKLOAD));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_DISK_AVAILABLE_LEAST)) {
			hbean.setDisk_available_least(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_DISK_AVAILABLE_LEAST));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_FREE_DISK_GB)) {
			hbean.setFree_disk_gb(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_FREE_DISK_GB));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_FREE_RAM_MB)) {
			hbean.setFree_ram_mb(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_FREE_RAM_MB));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_HYPERVISOR_HOSTNAME)) {
			hbean.setHypervisor_hostname(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_HYPERVISOR_HOSTNAME));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_HYPERVISOR_TYPE)) {
			hbean.setHypervisor_type(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_HYPERVISOR_TYPE));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_VERSION)) {
			hbean.setHypervisor_version(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_VERSION));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_ID)) {
			hbean.setId(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_ID));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_LOCAL_GB)) {
			hbean.setLocal_gb(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_LOCAL_GB));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_LOCAL_GB_USED)) {
			hbean.setLocal_gb_used(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_LOCAL_GB_USED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_CPU_INFO)) {
			hbean.setMemory_mb(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_MEMORY_MB));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_MEMORY_MB_USED)) {
			hbean.setMemory_mb_used(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_MEMORY_MB_USED));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_RUNNING_VMS)) {
			hbean.setRunning_vms(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_RUNNING_VMS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_VCPUS)) {
			hbean.setVcpus(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_VCPUS));
		}
		if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS_VCPUS_USED)) {
			hbean.setVcpus_used(jsonObj.getString(OPSTComputeConst.EXT_HYPERVISORS_VCPUS_USED));
		}
		return hbean;
	}

	// Hypervisor 虚拟机管理
	public List<OPSTHypervisorsBean> getHypervisorListV2EXT(String tenantId,String tokenId) throws OPSTBaseException {
		
		List<OPSTHypervisorsBean> hBeanList = new ArrayList<OPSTHypervisorsBean>();
		// 获取参数信息
		String strHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		// String tenantId;
		// /v2/{tenant_id}/os-hypervisors
		String strAPI = p.getProperty(OPSTPropertyKeyConst.GET_HYPERVISORLIST);
		String strUrl = strHost + strAPI;
		String strDestUrl = strUrl.replace(OPSTBlockStorageConst.PARAM_TENANTID, tenantId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			if (!response.equals("")) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				if (jsonObj.containsKey(OPSTComputeConst.EXT_HYPERVISORS)) {
					JSONArray list = jsonObj.getJSONArray(OPSTComputeConst.EXT_HYPERVISORS);
					for (int i = 0; i < list.size(); i++) {
						hBeanList.add(this.jsonObjToHypervisorBeanV2EXT(list.getJSONObject(i)));
					}
					return hBeanList;
				}
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取HypervisorList时出现处理json数据异常，类型为：" + e.getMessage());
		}
		return hBeanList;
	}

	// 挂载数据盘
	public String createVolumeMountV2EXT(OPSTServerVolumeEXTBean opBean, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.POST_CREATEVOLUMEMOUNT);
		// 拼接URL
		// /v2/{tenant_id}/servers/{server_id}/os-volume_attachments
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID,
				opBean.getServerId());
		JSONObject serverRequestBody = new JSONObject();
		JSONObject serverJSONObj = new JSONObject();
		// {
		// "volumeAttachment": {
		// "volumeId": "a26887c6-c47b-4654-abb5-dfadf7d3f803",
		// "device": "/dev/vdd"
		// }
		// }
		if (opBean.getVolumeId() != null) {
			serverJSONObj.element(OPSTComputeConst.EXT_VOLUME_VOLUMEID, opBean.getVolumeId());
		}
		// serverJSONObj.element(OPSTComputeConst.EXT_VOLUME_DEVICE,"/dev/vdd");
		serverRequestBody.element(OPSTComputeConst.EXT_VOLUME_VOLUMEATTACHMENT, serverJSONObj);
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, serverRequestBody.toString());
			if (!response.equals("")) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				if (!jsonObj.containsKey(OPSTComputeConst.EXT_VOLUME_VOLUMEATTACHMENT)) {
					String str = StringUtil.jsonErrorToErrorMessage(response);
					return str;
				}
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取挂载数据盘信息的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return null;

	}

	// 分离数据盘
	public String deleteVolumesSeparateByIdV2EXT(String serverId, String attachmentId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.DELETE_VOLUMESSEPARATEBYID);
		// 拼接URL
		// /v2/{tenant_id}/servers/{server_id}/os-volume_attachments/{attachment_id}
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId)
				.replace(OPSTComputeConst.PARAM_EXT_ATTACHMENT_ID, attachmentId);
		try {
			String response = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (!response.equals("")) {
				String str = StringUtil.jsonErrorToErrorMessage(response);
				return str;
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return null;
	}

	// 查看挂载数据盘信息
	public OPSTServerVolumeEXTBean getServerVolumeV2EXT(String serverId, String attachmentId, String tenantId, String tokenId)
			throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_SERVERVOLUMESBYID);
		// /v2/{tenant_id}/servers/{server_id}/os-volume_attachments/{attachment_id}
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId)
				.replace(OPSTComputeConst.PARAM_EXT_ATTACHMENT_ID, attachmentId);
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			if (jsonObj.containsKey("volumeAttachment")) {
				JSONObject jsonObjBean = jsonObj.getJSONObject("volumeAttachment");
				return this.jsonObjToServerVolumeBeanDetailV2(jsonObjBean);
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取挂载数据盘信息的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return null;
	}

	private OPSTServerVolumeEXTBean jsonObjToServerVolumeBeanDetailV2(JSONObject attachment) {
		OPSTServerVolumeEXTBean bean = new OPSTServerVolumeEXTBean();
		if (attachment.containsKey(OPSTComputeConst.EXT_ATTACHMENTS_ID)) {
			bean.setId(attachment.getString(OPSTComputeConst.EXT_ATTACHMENTS_ID));
		}
		if (attachment.containsKey(OPSTComputeConst.EXT_VOLUME_VOLUMEID)) {
			bean.setVolumeId(attachment.getString(OPSTComputeConst.EXT_VOLUME_VOLUMEID));
		}
		if (attachment.containsKey(OPSTComputeConst.EXT_ATTACHMENTS_SERVER_ID)) {
			bean.setServerId(attachment.getString(OPSTComputeConst.EXT_ATTACHMENTS_SERVER_ID));
		}
		if (attachment.containsKey(OPSTComputeConst.EXT_ATTACHMENTS_DEVICE)) {
			bean.setDevice(attachment.getString(OPSTComputeConst.EXT_ATTACHMENTS_DEVICE));
		}

		return bean;
	}

	*//**
	 * 实例操作。重建主机。
	 * 
	 * @param serverId
	 * @param serName
	 * @param imageId
	 * @param password
	 *//*
	@Override
	public boolean instanceRebuildActionV2(String serverId, String serName, String imageId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		String imageRef = novaHost + "/v2/images/" + imageId;
		// 重建主机
		String actionJson = "";
		actionJson = "{\"rebuild\" : {\"imageRef\" : \"" + imageRef + "\",\"name\" : \"" + serName + "\" }}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			if (!response.contains(OPSTComputeConst.SERVER)) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		if (response.indexOf("\"server\":") != -1) {
			return true;
		} else {
			return false;
		}
	}

	*//**
	 * 实例操作。调整主机大小。
	 * 
	 * @param serverId
	 * @param flavorId
	 *//*
	@Override
	public boolean instanceResizeActionV2(String serverId, String flavorId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"resize\" : {\"flavorRef\" : \"" + flavorId + "\" }}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		if ("".equals(response) || response == null) {
			return true;
		} else {
			return false;
		}
	}

	*//**
	 * 调整主机大小。确认操作
	 * 
	 * @param serverId
	 * @param flavorId
	 *//*
	public void instanceResizeConfirmsV2(String serverId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"confirmResize\" : null}";
		try {
			HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void instanceRevertResizeV2(String serverId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"revertResize\" : null}";
		try {
			HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
	}

	*//**
	 * 实例操作。绑定和解除浮动IP
	 * 
	 * @param serverId
	 * @param status
	 * @param floatIp
	 *//*
	@Override
	public boolean instanceAddOrRemoveFloatIpActionV2EXT(String serverId, String status, String floatIp, String tenantId, String tokenId)
			throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 绑定浮动IP
		String actionJson = "";
		if (status.equals(OPSTServerActionTypeConst.ADDFLOATINGIP.getAction())) {
			// 绑定浮动IP
			actionJson = "{\"addFloatingIp\" : {\"address\" : \"" + floatIp + "\" }}";
		} else if (status.equals(OPSTServerActionTypeConst.REMOVEFLOATINGIP.getAction())) {
			// 解除绑定浮动IP
			actionJson = "{\"removeFloatingIp\" : {\"address\" : \"" + floatIp + "\" }}";
		}
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			System.out.println(response);
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		if ("".equals(response) || response == null) {
			return true;
		} else {
			return false;
		}
	}

	*//**
	 * 实例操作。获得控制台信息
	 * 
	 * @param serverId
	 *//*
	@Override
	public String instanceServerGetConsoleActionV2EXT(String serverId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"os-getVNCConsole\" : {\"type\" : \"novnc\" }}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			System.out.println(response);
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		String consoleUrl = "";
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject("console");
			consoleUrl = (jsonObj.get("url")).toString();
		} catch (JSONException e) {
			// TODO: handle exception
			throw new OPSTOperationException("操作实例的控制台时发生异常，类型为：" + e.getMessage());
		}
		return consoleUrl;
	}

	*//**
	 * 实例操作。获得控制台信息(V3)
	 * 
	 * @param serverId
	 *//*
	@Override
	public String instanceServerGetConsoleActionV3(String serverId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String actionV3 = p.getProperty(OPSTPropertyKeyConst.POST_ActionV3);
		// 拼接URL
		String strOrgUrl = novaHost + actionV3;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"get_vnc_console\" : {\"type\" : \"novnc\" }}"; // API
																				// 1
		// String actionJson =
		// "{\"get_spice_console\" : {\"type\" : \"spice-html5\" }}"; // API 2
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			System.out.println(response);
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		String consoleUrl = "";
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject("console");
			consoleUrl = (jsonObj.get("url")).toString();
		} catch (JSONException e) {
			throw new OPSTOperationException("操作实例的控制台时发生异常，类型为：" + e.getMessage());
		}
		return consoleUrl;
	}

	@Override
	public String createServerInfo(String argHostName, String tenantId, String tokenId) throws OPSTBaseException {
		OPSTServerCreateBean argServerBean = new OPSTServerCreateBean();
		NetworkService oPSTNetworkService = new NetworkServiceImpl();
		// String argHostNames = "instance-000000b3";
		String serverId = getServerIdByHostName(argHostName, tenantId, tokenId);
		OPSTServerBean serverBean = getServerV2(serverId, tenantId, tokenId);
		argServerBean.setAvailabilityZone(serverBean.getOS_EXT_AZ_availability_zone());
		argServerBean.setName(serverBean.getName());
		argServerBean.setFlavorsId(serverBean.getFlavor().getId());
		argServerBean.setImageId(serverBean.getImage().getId());
		List<OPSTNetworkBean> networkList = oPSTNetworkService.getNetworkListV2(tenantId, tokenId);
		List netList = new ArrayList();
		if (networkList != null) {
			for (int i = 0; i < serverBean.getAddresses().size(); i++) {
				for (int j = 0; j < networkList.size(); j++) {
					if (networkList.get(i).getName() == serverBean.getAddresses().get(i).getNetName()) {
						netList.add(networkList.get(i).getId());
					}
				}
			}
		}
		argServerBean.setNetworkId(netList);
		List<OPSTSecurityGroupBean> securityGroups = serverBean.getSecurityGroup();
		List securList = new ArrayList();
		if (securityGroups != null) {
			for (int i = 0; i < securityGroups.size(); i++) {
				securList.add(securityGroups.get(i).getName());
			}
		}
		argServerBean.setSecurityGroups(securList);
		String creatInfo = null;
		try {
			if (serverBean.getName() != "" && serverBean.getFlavor().getId() != "" && serverBean.getImage().getId() != "" && netList.size() != 0) {
				creatInfo = createServerV2(argServerBean, tenantId, tokenId);
			}
		} catch (OPSTErrorMessageException e) {
			throw new OPSTErrorMessageException(e.getMessage(), e);
		} catch (OPSTBaseException e) {
			throw new OPSTBaseException("获取createRouter的时候出现异常，类型为：" + e.getMessage(), e);
		}
		return creatInfo;
	}

	@Override
	public String getServerIdByHostName(String argHostName, String tenantId, String tokenId) {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_ServersListDetails);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(response);
		// 获取server list
		JSONArray servers = jsonObj.getJSONArray(OPSTComputeConst.SERVERBODY);
		String serverId = null;
		for (int i = 0; i < servers.size(); i++) {
			String host = servers.getJSONObject(i).get("OS-EXT-SRV-ATTR:instance_name").toString();
			if (host.equals(argHostName)) {
				serverId = servers.getJSONObject(i).get("id").toString();
			}
		}
		return serverId;
	}

	*//**
	 * 负载均衡 弹性伸缩 虚机创建
	 *//*
	@Override
	public String createServerById(String argServerId, String tenantId, String tokenId) throws OPSTBaseException {
		System.out.println("Begin createServerById");
		System.out.println("Begin OPSTComputeServiceImpl createServerById 1 ");
		OPSTServerCreateBean argServerBean = new OPSTServerCreateBean();
		NetworkService oPSTNetworkService = new NetworkServiceImpl();
		OPSTServerBean serverBean = getServerV2(argServerId, tenantId, tokenId);
		argServerBean.setAvailabilityZone(serverBean.getOS_EXT_AZ_availability_zone());
		argServerBean.setName(serverBean.getName());
		argServerBean.setFlavorsId(serverBean.getFlavor().getId());
		argServerBean.setImageId(serverBean.getImage().getId());
		// 作为后续改善
		
		 * if(serverBean.getKey_name() != "") {
		 * argServerBean.setKeyPair(serverBean.getKey_name()); }
		 
		List<OPSTNetworkBean> networkList = oPSTNetworkService.getNetworkListV2(tenantId, tokenId);
		List netList = new ArrayList();
		if (networkList != null) {
			for (int i = 0; i < serverBean.getAddresses().size(); i++) {
				for (int j = 0; j < networkList.size(); j++) {
					if (networkList.get(j).getName().equals(serverBean.getAddresses().get(i).getNetName())) {
						netList.add(networkList.get(i).getId());
					}
				}
			}
		}
		argServerBean.setNetworkId(netList);
		List<OPSTSecurityGroupBean> securityGroups = serverBean.getSecurityGroup();
		List securList = new ArrayList();
		if (securityGroups != null) {
			for (int i = 0; i < securityGroups.size(); i++) {
				securList.add(securityGroups.get(i).getName());
			}
		}
		System.out.println("Run argServerBean.setSecurityGroups(securList); before");
		argServerBean.setSecurityGroups(securList);
		System.out.println("Run argServerBean.setSecurityGroups(securList); after");
		String creatInfo = "";
		if (serverBean.getName() != "" && serverBean.getFlavor().getId() != "" && serverBean.getImage().getId() != "" && netList.size() != 0) {
			creatInfo = createServerV2(argServerBean, tenantId, tokenId);
		}
		if (creatInfo != "") {
			String serId2 = creatInfo + ";" + tenantId + ";" + tokenId;
			ExecutorService exs = Executors.newCachedThreadPool();
			ArrayList<Future<String>> al = new ArrayList<Future<String>>();
			al.add(exs.submit(new CallableThread(serId2)));
			try {
				return al.get(0).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return creatInfo;
	}

	*//**
	 * 根据实例IP 创建实例
	 * 
	 * @Override public String createServerInfoByIp(String argHostIp) {
	 *           ServerBean argServerBean = new ServerBean(); OpenStackUtil
	 *           openStackUtil = new OpenStackUtil(); OPSTNetworkService
	 *           oPSTNetworkService = new OPSTNetworkServiceImpl(); //String
	 *           argHostIps = "10.10.6.124"; String serverId =
	 *           getServerIdByHostIp(argHostIp); String tokenIds = null; try {
	 *           tokenIds = this.getTokenId(request); } catch (Exception e) {
	 *           e.printStackTrace(); } OPSTServerBean serverBean =
	 *           getServerV2(serverId);
	 *           argServerBean.setAvailabilityZone(serverBean
	 *           .getOS_EXT_AZ_availability_zone());
	 *           argServerBean.setName(serverBean.getName());
	 *           argServerBean.setFlavorsId(serverBean.getFlavor().getId());
	 *           argServerBean.setImageId(serverBean.getImage().getId());
	 *           List<OPSTNetworkBean> networkList =
	 *           oPSTNetworkService.getNetworkListV2(); List netList = new
	 *           ArrayList(); if (networkList != null) { for (int i = 0; i <
	 *           serverBean.getAddresses().size(); i++) { for (int j = 0; j <
	 *           networkList.size(); j++) {
	 *           if(networkList.get(j).getName().equals
	 *           (serverBean.getAddresses().get(i).getNetName())){
	 *           netList.add(networkList.get(j).getId()); } } } }
	 *           argServerBean.setNetworkId(netList);
	 *           List<OPSTSecurityGroupBean> securityGroups =
	 *           serverBean.getSecurityGroup(); List securList = new
	 *           ArrayList(); if (securityGroups != null) { for (int i = 0; i <
	 *           securityGroups.size(); i++) {
	 *           securList.add(securityGroups.get(i).getName()); } }
	 *           argServerBean.setSecurityGroups(securList); String creatInfo =
	 *           null; if(serverBean.getName()!="" &&
	 *           serverBean.getFlavor().getId
	 *           ()!=""&&serverBean.getImage().getId()!=""&&netList.size()!=0){
	 *           creatInfo = createServerV2(argServerBean); } return creatInfo;
	 *           }
	 *//*

	@Override
	public String getServerIdByHostIp(String argHostIp, String tenantId, String tokenId) {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_ServersListDetails);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(response);
		// 获取server list
		JSONArray servers = jsonObj.getJSONArray(OPSTComputeConst.SERVERBODY);
		String serverId = null;
		for (int i = 0; i < servers.size(); i++) {
			JSONObject obj1 = servers.getJSONObject(i).getJSONObject("addresses");
			JSONArray obj2 = obj1.names();
			for (int j = 0; j < obj2.size(); j++) {
				String netName = (String) obj2.get(j);
				JSONArray netInfo = obj1.getJSONArray(netName);
				JSONObject obj3 = netInfo.getJSONObject(0);
				if (argHostIp.equals(obj3.getString("addr"))) {
					serverId = servers.getJSONObject(i).get("id").toString();
				}
			}
		}
		return serverId;
	}

	*//**
	 * 获得系统计算服务信息
	 *//*
	@Override
	public List<OPSTServiceBean> getServicesInfo(String tenantId, String tokenId) {
		List<OPSTServiceBean> serviceList = new ArrayList<OPSTServiceBean>();
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		// NETWORK_HOST
		// String networkHost =
		// p.getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		// String tokentId = this.getTokenId(request);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_OS_Services); // novaHost
																										// 计算服务API
		// String strNetworkApi = "/v2.0/agents"; // networkHost 网络代理API
		// 拼接URL 'http://192.168.1.23:35357' novaHost
		String strOrgUrl = novaHost + strServerApi;
		// String strOrgUrl = networkHost + strNetworkApi;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!response.equals("")) {
			JSONObject jsonObj = JSONObject.fromObject(response);
			// 获取service list
			JSONArray services = jsonObj.getJSONArray(OPSTComputeConst.SERVICEBODY);
			for (int i = 0; i < services.size(); i++) {
				serviceList.add(this.jsonObjToOPSTServiceBean(services.getJSONObject(i)));
			}
		}
		return serviceList;
	}

	*//**
	 * jsonObjToOPSTServiceBean
	 * 
	 * @param service
	 * @return
	 *//*
	private OPSTServiceBean jsonObjToOPSTServiceBean(JSONObject service) {
		OPSTServiceBean sb = new OPSTServiceBean();
		if (null != service.getString("status")) {
			sb.setStatus(service.getString("status"));
		}
		if (null != service.getString("binary")) {
			sb.setBinary(service.getString("binary"));
		}
		if (null != service.getString("zone")) {
			sb.setZone(service.getString("zone"));
		}
		if (null != service.getString("state")) {
			sb.setState(service.getString("state"));
		}
		if (null != service.getString("host")) {
			sb.setHost(service.getString("host"));
		}
		return sb;
	}

	*//**
	 * 获得系统网络代理信息
	 *//*
	@Override
	public List<OPSTAgentBean> getAgentsInfo(String tenantId, String tokenId) {
		List<OPSTAgentBean> agentList = new ArrayList<OPSTAgentBean>();
		// 获取参数信息
		// String novaHost =
		// p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		// NETWORK_HOST
		String networkHost = p.getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		// String tenantId;
		// String tokentId = this.getTokenId(request);
		// String strServerApi =
		// p.getProperty(OPSTPropertyKeyConst.GET_OS_Services);
		// // novaHost 计算服务API
		String strNetworkApi = p.getProperty(OPSTPropertyKeyConst.GET_Network_agents); // networkHost
																											// 网络代理API
		// 拼接URL 'http://192.168.1.23:35357' novaHost
		// String strOrgUrl = novaHost + strServerApi;
		String strOrgUrl = networkHost + strNetworkApi;
		String strDestUrl = strOrgUrl;
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(response);
		// 获取service list
		JSONArray agents = jsonObj.getJSONArray(OPSTComputeConst.AGENTBODY);
		for (int i = 0; i < agents.size(); i++) {
			agentList.add(this.jsonObjToOPSTAgentBean(agents.getJSONObject(i)));
		}
		return agentList;
	}

	*//**
	 * jsonObjToOPSTAgentBean
	 * 
	 * @param agent
	 * @return
	 *//*
	private OPSTAgentBean jsonObjToOPSTAgentBean(JSONObject agent) {
		OPSTAgentBean ab = new OPSTAgentBean();
		if (null != agent.getString("admin_state_up")) {
			ab.setAdminStateUp(agent.getString("admin_state_up"));
		}
		if (null != agent.getString("agent_type")) {
			ab.setAgentType(agent.getString("agent_type"));
		}
		if (null != agent.getString("alive")) {
			ab.setAlive(agent.getString("alive"));
		}
		if (null != agent.getString("binary")) {
			ab.setBinary(agent.getString("binary"));
		}
		if (null != agent.getString("host")) {
			ab.setHost(agent.getString("host"));
		}
		return ab;
	}

	public OPSTTenantQuotasBean getTenantQuotasV2EXT(String tenantId, String tokenId, String tenant_id) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_TENANTQUOTASBYTENANTID);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replaceFirst("\\{tenant_id\\}", tenant_id).replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		OPSTTenantQuotasBean bean;
		try {
			JSONObject jsonObj = JSONObject.fromObject(response).getJSONObject("quota_set");
			bean = this.jsonObjToUserQuotasV2Bean(jsonObj);
		} catch (JSONException e) {
			throw new OPSTOperationException("获取getTenantQuotasV2EXT的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return bean;
	}

	public String updateTenantQuotasV2EXT(OPSTTenantQuotasBean opstBean, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.POST_TENANTQUOTASUPDATE);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replaceFirst("\\{tenant_id\\}", tenantId);
		strDestUrl = strDestUrl.replace(OPSTComputeConst.PARAM_TENANTID, opstBean.getTenant_id());
		JSONObject requestBody = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		// {
		// "quota_set": {
		// "force": "True",
		// "instances": 9
		// }
		// }
		jsonObj.element("metadata_items", opstBean.getMetadata_items());
		jsonObj.element("cores", opstBean.getCores());
		jsonObj.element("instances", opstBean.getInstances());
		jsonObj.element("injected_files", opstBean.getInjected_files());
		jsonObj.element("injected_file_content_bytes", opstBean.getInjected_file_content_bytes());
		// jsonObj.element("volumes", opstBean.getVolumes());
		// jsonObj.element("volume_snapshots", opstBean.getVolume_snapshots());
		// jsonObj.element("volume_snapshots_size",
		// opstBean.getVolume_snapshots_size());
		jsonObj.element("ram", opstBean.getRam());
		jsonObj.element("security_groups", opstBean.getSecurity_groups());
		jsonObj.element("security_group_rules", opstBean.getSecurity_group_rules());
		jsonObj.element("floating_ips", opstBean.getFloating_ips());
		// jsonObj.element("networks",opstBean.getNetworks());
		// jsonObj.element("ports",opstBean.getPorts());
		// jsonObj.element("routers",opstBean.getRouters());
		// jsonObj.element("subnets",opstBean.getSubnets());
		// jsonObj.element("fixed_ips",opstBean.getFixed_ips());
		// jsonObj.element("key_pairs",opstBean.getKey_pairs());

		requestBody.element("quota_set", jsonObj);
		String response = "";
		try {
			response = HttpClientUtil.updateMethod(tokenId, strDestUrl, requestBody.toString());
			if (!response.equals("")) {
				JSONObject returnjsonObj = JSONObject.fromObject(response);
				if (!returnjsonObj.containsKey("quota_set")) {
					String str = StringUtil.jsonErrorToErrorMessage(response);
					return str;
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}

	public String deleteTenantQuotasV2EXT(String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strServerApi = p.getProperty(OPSTPropertyKeyConst.DELETE_TENANTQUOTASDELETE);
		// 拼接URL
		String strOrgUrl = novaHost + strServerApi;
		String strDestUrl = strOrgUrl.replaceFirst("\\{tenant_id\\}", tenantId).replace(OPSTComputeConst.PARAM_TENANTID, tenantId);
		// 查询Server信息
		String response = "";
		try {
			response = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (!response.equals("")) {
				String str = StringUtil.jsonErrorToErrorMessage(response);
				return str;
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}

	private OPSTTenantQuotasBean jsonObjToUserQuotasV2Bean(JSONObject obj) {
		OPSTTenantQuotasBean bean = new OPSTTenantQuotasBean();
		if (obj.containsKey("cores")) {
			bean.setCores(obj.getInt("cores"));
		}
		if (obj.containsKey("fixed_ips")) {
			bean.setFixed_ips(obj.getInt("fixed_ips"));
		}
		if (obj.containsKey("floating_ips")) {
			bean.setFloating_ips(obj.getInt("floating_ips"));
		}
		if (obj.containsKey("id")) {
			bean.setId(obj.getString("id"));
		}
		if (obj.containsKey("injected_file_content_bytes")) {
			bean.setInjected_file_content_bytes(obj.getInt("injected_file_content_bytes"));
		}
		if (obj.containsKey("injected_files")) {
			bean.setInjected_files(obj.getInt("injected_files"));
		}
		if (obj.containsKey("instances")) {
			bean.setInstances(obj.getInt("instances"));
		}
		if (obj.containsKey("key_pairs")) {
			bean.setKey_pairs(obj.getInt("key_pairs"));
		}
		if (obj.containsKey("metadata_items")) {
			bean.setMetadata_items(obj.getInt("metadata_items"));
		}
		if (obj.containsKey("ram")) {
			bean.setRam(obj.getInt("ram"));
		}
		if (obj.containsKey("security_group_rules")) {
			bean.setSecurity_group_rules(obj.getInt("security_group_rules"));
		}
		if (obj.containsKey("security_groups")) {
			bean.setSecurity_groups(obj.getInt("security_groups"));
		}
		return bean;
	}

	// 创建实例备份
	public String createServerBackupActionV2EXT(String serId, String backupName, String backupType, String backupRotation, String tenantId,
			String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL /v2/{tenant_id}/servers/{server_id}/action
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serId);
		String actionJson = "{\"createBackup\":{\"name\":\"" + backupName + "\",\"backup_type\":\"" + backupType + "\",\"rotation\":"
				+ backupRotation + "}}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			if (response != null && !response.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e1) {
			e1.printStackTrace();
			throw e1;
		}
		return null;
	}

	*//**
	 * Thread类实现的多线程程序 类描述: 类名称：TestThread 创建时间：2015年2月8日 下午4:39:37
	 *//*
	public class CallableThread implements Callable<String> {

		private String address;
		private String name;

		public CallableThread(String name) {
			this.name = name;
		}

		public String call() throws OPSTBaseException {
			if (this.name != null) {
				String[] serInf = this.name.split(";");
				String serverId = serInf[0];
				String tenantId = serInf[1];
				String tokenId = serInf[2];
				while (true) {
					// 根据ID查询虚机信息
					OPSTServerBean serverInfos = getServerV2(serverId, tenantId, tokenId);
					if (serverInfos.getAddresses().size() != 0) {
						// 将虚拟机信息添加到数据库中
						address = serverInfos.getAddresses().get(0).getAddr();
						break;
					}
				}
			}
			return this.address;
		}
	}

	*//**
	 * 修改实例状态
	 *//*
	@Override
	public boolean resetServerStatus(String serverId, String tenantId, String tokenId) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"os-resetState\" : {\"state\" : \"active\" }}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			if (response == null || response.equals("")) {
				return true;
			}
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	
	*//**
	 * 实例迁移
	 *//*
	@Override
	public boolean instanceMigrateV2(String tokenId,String serverId, String tenantId)throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"migrate\" :\"null\"}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			if (response == null || response.equals("")) {
				return true;
			}
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	@Override
	public boolean instanceLiveMigrateV2(String tokenId, String serverId,
		String tenantId, String hostId, String overCommits,
		String blockMigrates) throws OPSTBaseException {
		// 获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String action = p.getProperty(OPSTPropertyKeyConst.POST_Action);
		// 拼接URL
		String strOrgUrl = novaHost + action;
		String strDestUrl = strOrgUrl.replace(OPSTComputeConst.PARAM_TENANTID, tenantId).replace(OPSTComputeConst.PARAM_SERVERID, serverId);
		// 调整主机大小
		String actionJson = "{\"os-migrateLive\" : {\"host\":\"" + hostId + "\",\"block_migration\":\"" + blockMigrates + "\",\"disk_over_commit\":\"" + overCommits + "\"}}";
		String response = "";
		try {
			response = HttpClientUtil.postMethod(tokenId, strDestUrl, actionJson);
			if (response == null || response.equals("")) {
				return true;
			}
		} catch (OPSTBaseException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	@Override
	public List<OPSTFlavorBean> getFlavorsListV2(String tenantId, String tokenId)
			throws OPSTBaseException {
		// TODO Auto-generated method stub
		return null;
	}*/
}













