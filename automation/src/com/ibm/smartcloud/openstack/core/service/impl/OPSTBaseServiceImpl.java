package com.ibm.smartcloud.openstack.core.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ibm.smartcloud.openstack.core.constants.OPSTHttpclientConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTAuthorizedException;
import com.ibm.smartcloud.openstack.core.service.OPSTBaseService;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTtokenBean;

public class OPSTBaseServiceImpl<T> implements OPSTBaseService<T> {
	private ObjectMapper om = new ObjectMapper();
	Properties propertyUtil = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	public Properties getPropertyUtil() {
		return propertyUtil;
	}

	public void setPropertyUtil(Properties propertyUtil) {
		this.propertyUtil = propertyUtil;
	}

	@Override
	public OPSTtokenBean getToken() throws OPSTAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OPSTtokenBean getAdminToken() throws OPSTAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> convertList(Class<T> clazz, String tokenId, String url, String httpMethod, String bodyString, String arrayName) throws Exception {

		String response = "";

		if (OPSTHttpclientConst.HTTP_METHOD_GET.equals(httpMethod)) {
			response = HttpClientUtil.getMethod(tokenId, url);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_POST.equals(httpMethod)) {
			response = HttpClientUtil.postMethod(tokenId, url, bodyString);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_PUT.equals(httpMethod)) {
			response = HttpClientUtil.putMethod(tokenId, url);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_DELETE.equals(httpMethod)) {
			response = HttpClientUtil.deleteMethod(tokenId, url);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_UPDATE.equals(httpMethod)) {
			response = HttpClientUtil.updateMethod(tokenId, url, bodyString);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_PATCH.equals(httpMethod)) {
			response = HttpClientUtil.patchMethod(tokenId, url, bodyString);
		}

		CollectionType ct = om.getTypeFactory().constructCollectionType(LinkedList.class, clazz);
		JsonNode jsonNode;

		jsonNode = om.readTree(response);
		if(arrayName==null)
		{
			return om.convertValue(jsonNode, ct);
		}
		else
		{
			ArrayNode arrayNode = (ArrayNode) jsonNode.get(arrayName);
			return om.convertValue(arrayNode, ct);
		}
		

	}

	public T convert(Class<T> clazz, String tokenId, String url, String httpMethod, String bodyString) throws Exception {

		String response = "";

		if (OPSTHttpclientConst.HTTP_METHOD_GET.equals(httpMethod)) {
			response = HttpClientUtil.getMethod(tokenId, url);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_POST.equals(httpMethod)) {
			response = HttpClientUtil.postMethod(tokenId, url, bodyString);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_PUT.equals(httpMethod)) {
			response = HttpClientUtil.putMethod(tokenId, url);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_DELETE.equals(httpMethod)) {
			response = HttpClientUtil.deleteMethod(tokenId, url);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_UPDATE.equals(httpMethod)) {
			response = HttpClientUtil.updateMethod(tokenId, url, bodyString);
		}
		if (OPSTHttpclientConst.HTTP_METHOD_PATCH.equals(httpMethod)) {
			response = HttpClientUtil.patchMethod(tokenId, url, bodyString);
		}

		JsonNode jsonNode;

		jsonNode = om.readTree(response);
		return om.convertValue(jsonNode, clazz);

	}

}
