package com.ibm.smartcloud.openstack.glance.service;

import java.util.List;
import com.ibm.smartcloud.openstack.glance.bean.ImagesBean;

/**
 * @Title:ImageService     
 * @Description:ImageService
 * @Auth:LiangRui   
 * @CreateTime:2015年6月15日 下午3:23:42       
 * @version V1.0
 */
public interface ImageService {
	
	//Images
	public List<ImagesBean> getAllImageList(String tenantId,String tokenId);
	//public OPSTImageBean createImageByFileV2(OPSTImageBean argOPSTImageBean,String tenantId,String tokenId)throws OPSTBaseException;//byFile
	//public String createImageByUrlV2(OPSTImageBean argOPSTImageBean,String tenantId,String tokenId)throws OPSTBaseException;//byUrl
	//public OPSTImageBean getImageDetailV2(String argImageId,String tenantId,String tokenId)throws OPSTBaseException;
	//public OPSTImageBean getImagesDetailsV1(String argImageId,String tenantId,String tokenId) throws OPSTBaseException;
	//public String updateImageByIdV2(OPSTImageBean argOPSTImageBean,String tenantId,String tokenId)throws OPSTBaseException;
	//public Boolean deleteImageV2(String argImageId) throws OPSTBaseException;
	//public String deleteImageV2(String argImageId,String tenantId,String tokenId) throws OPSTBaseException;
	//public void uploadImageFile(String argImageId,String tenantId,String tokenId)throws OPSTBaseException;
	//public void downloadImageFile(String argImageId,String tenantId,String tokenId)throws OPSTBaseException;
	//public void addTagForImage(String argImageId, String argTag,String tenantId,String tokenId)throws OPSTBaseException;
	//public List<OPSTImageMemberBean> getMemberListV1(String tenantId,String tokenId)throws OPSTBaseException;
	//public void addMemberV1(String argImageId,String argTenantId,String tenantId,String tokenId)throws OPSTBaseException;
	//public OPSTImageMemberBean getMemberDetailV1(String argImageId, String argTenantId,String tenantId,String tokenId)throws OPSTBaseException;
	//public void deleteMemberV1(String argImageId, String argTenantId,String tenantId,String tokenId)throws OPSTBaseException;
	//public void setMemberStatus(String argImageId, String argTenantId, String argStatus,String tenantId,String tokenId)throws OPSTBaseException;
	
	//Image schemas
	//TODO:return type must fix
	//public void getImageSchemaList(String tenantId,String tokenId)throws OPSTBaseException;
	//public void getImageSchema(String tenantId,String tokenId)throws OPSTBaseException;
	//public void getImageSchemaMemberList(String tenantId,String tokenId)throws OPSTBaseException;
	//public void getImageSchemaMeber(String tenantId,String tokenId)throws OPSTBaseException;
}












