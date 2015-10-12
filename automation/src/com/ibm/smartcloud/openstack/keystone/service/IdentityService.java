package com.ibm.smartcloud.openstack.keystone.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.keystone.bean.AccessBean;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTOSKSADMExtBean;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTProjectBean;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTRoleBean;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTUserBean;
/**
 * @Title:IdentityService     
 * @Description:用户登录认证
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午5:42:34       
 * @version V1.0
 */
public interface IdentityService {
	
	
	//认证
	//public AccessBean createAuthenticatesV2(String userName,String password,String tenantId) throws OPSTBaseException;
	public AccessBean createAuthTokensV3(String userName,String password)throws OPSTBaseException;
	
	
	
	
	
	
	/*public List<OPSTOSKSADMExtBean> getTenantsListV2(String tokenId)throws OPSTBaseException;
	public List<OPSTUserBean> getUserListByTenantIdV2(String tenantId,String tokenId)throws OPSTBaseException;
	//Service catalog
	
	//Endpoints
	
	//Domains
	
	//Projects
	public List<OPSTProjectBean> getProjectListV3(String tokenId)throws OPSTBaseException;
	public OPSTProjectBean getProjectByIdV2(String argProjectId,String tokenId)throws OPSTBaseException;
	public OPSTProjectBean createProjectV3(OPSTProjectBean bean,String tokenId) throws OPSTBaseException;
	public OPSTProjectBean updateProjectByIdV3(OPSTProjectBean bean,String tokenId) throws OPSTBaseException;
	Boolean deleteProjectByIdV3(String projectId,String tokenId) throws OPSTBaseException;
	
	public List<OPSTProjectBean> getProjectListByUserIdV3(String userId,String tokenId)throws OPSTBaseException;
	public List<OPSTRoleBean> getRoleListByProjectIdAndUserIdV3(String projectId,String userId,String tokenId)throws OPSTBaseException;
	
	public String createUserRoleV3(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException;
	public String deleteUserRoleV3(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException;
	
	//Users
	public List<OPSTUserBean> getUserListV2(String tenantId,String tokenId) throws OPSTBaseException;
	public OPSTUserBean createUserV2(OPSTUserBean argUserBean,String tenantId,String tokenId) throws OPSTBaseException;
	public OPSTUserBean updateUserByIdV2(OPSTUserBean argUserBean,String tenantId,String tokenId) throws OPSTBaseException;
	public Boolean deleteUserByIdV2(String userId,String tenantId,String tokenId) throws OPSTBaseException;
	OPSTUserBean getUserByIdV2(String userId,String tenantId,String tokenId) throws OPSTBaseException;
	OPSTUserBean getUserByNameV2(String userName,String tenantId,String tokenId) throws OPSTBaseException;
	
	//user  role
	public String createUserRoleV2(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException;
	public String deleteUserRoleV2(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException;
	//Groups
	
	//Credentials
	
	//Roles
	public List<OPSTOSKSADMExtBean> getRolesListV2(String tokenId)throws OPSTBaseException;

	*/

	
	
	//Policies
	
	
	
}
