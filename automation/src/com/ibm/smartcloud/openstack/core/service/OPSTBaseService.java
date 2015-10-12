package com.ibm.smartcloud.openstack.core.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTAuthorizedException;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTtokenBean;

public interface OPSTBaseService<T> {

	/**
	 * Get User's OpenStack token
	 * 
	 * @return
	 * @throws AuthorizedException
	 */
	OPSTtokenBean getToken() throws OPSTAuthorizedException;

	OPSTtokenBean getAdminToken() throws OPSTAuthorizedException;

	public List<T> convertList(Class<T> clazz,  String tokenId,String url,String httpMethod,String arrayName,String bodyString) throws Exception;

	public T convert(Class<T> clazz,  String tokenId,String url,String httpMethod,String bodyString) throws Exception;

}
