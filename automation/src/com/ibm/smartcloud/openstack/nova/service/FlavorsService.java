package com.ibm.smartcloud.openstack.nova.service;

import java.util.List;

import com.ibm.smartcloud.openstack.nova.bean.FlavorBean;


public interface FlavorsService {
	
	public List<FlavorBean> getAllFlavorsList(String tenantId,String tokenId);
	
	/*public List<OPSTFlavorBean> getFlavorsListV2Ext(String tenantId,String tokenId) throws OPSTBaseException;

	public List<OPSTFlavorBean> getFlavorsDetailListV2Ext(String tenantId,String tokenId) throws OPSTBaseException;

	public OPSTFlavorBean getFlavorsDetailV2Ext(String argFlavorId,String tenantId,String tokenId) throws OPSTBaseException;
	
	public List<OPSTFlavorExtraBean> getFlavorExtraSpecs(String argFlavorId, String tenantId, String tokenId) throws OPSTBaseException;

	public Boolean createFlavorsV2Ext(OPSTFlavorBean flavorBean,String tenantId,String tokenId) throws OPSTBaseException;

	public Boolean deleteFlavorsV2Ext(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException;

	public void deleteFlavorsAccessV2Ext(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException;

	public Boolean createFlavorsAccessV2Ext(String flavor_id,String tenantName,String tenantId,String tokenId) throws OPSTBaseException;

	public List<OPSTFlavorAccessBean> getFlavorsAccessListV2Ext(String flavor_id,String tenantId,String tokenId) throws OPSTBaseException;

	 */
	
}
