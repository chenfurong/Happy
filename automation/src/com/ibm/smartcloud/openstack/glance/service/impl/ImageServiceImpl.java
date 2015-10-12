package com.ibm.smartcloud.openstack.glance.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.glance.bean.ImagesBean;
import com.ibm.smartcloud.openstack.glance.constants.OPSTImageConst;
import com.ibm.smartcloud.openstack.glance.service.ImageService;

/**
 * @Title:ImageServiceImpl     
 * @Description: ImageServiceImpl
 * @Auth:LiangRui   
 * @CreateTime:2015年6月15日 下午3:24:24       
 * @version V1.0
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	
	@Override
	public List<ImagesBean> getAllImageList(String tenantId,String tokenId){
		List<ImagesBean> imgList = new ArrayList<ImagesBean>();
		//获取参数信息
		String strImageHost;
		String strImageApi ;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageApi = p.getProperty(OPSTPropertyKeyConst.GET_ImagesList);
		//  /v2/images 
		String url = strImageHost+strImageApi;
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId, url);
		} catch (OPSTBaseException e1) {
			e1.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(response);
		// 获取images list
		JSONArray images = jsonObj.getJSONArray(OPSTImageConst.IMAGES);
		for (int i = 0; i < images.size(); i++) {
			imgList.add(this.jsonObjToImageBeanV2(images.getJSONObject(i)));
		}
		return imgList;
	}
	
	/**
	 * 根据ID查询镜像详细
	 *//*
	@Override
	public OPSTImageBean getImageDetailV2(String argImageId,String tenantId,String tokenId) throws OPSTBaseException{
		
		//定义返回结果
		OPSTImageBean oPSTImageBean = null;
		//获取参数信息
		String strImageHost;
		String strImageDetailAPI;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.GET_ImagesDetails);
		//  /v2/images/​{image_id}
		String strUrl = strImageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId);
		String response = "";
		//解析Json结构
		try {
			response = HttpClientUtil.getMethod(tokenId, strDesUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			JSONObject jsonObj = JSONObject.fromObject(response);
			oPSTImageBean = this.jsonObjToImageBeanV2(jsonObj);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取指定image详细信息的时候出现处理json数据异常，类型为："+e1.getMessage());
		}
		return oPSTImageBean;
	}
	
	
	 * 详情(定制属性)
	 
	@Override
	public OPSTImageBean getImagesDetailsV1(String argImageId,String tenantId,String tokenId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTImageBean opstImageBean = null;
		//获取参数信息
		String strImageHost ;
		String strImageDetailAPI;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.GET_ImagesDetailsV1);
		//  /v2/{tenant_id}/images/​{image_id}
		String strUrl = strImageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId);
		String response = "";
		try{
			response = HttpClientUtil.getMethod(tokenId, strDesUrl);
		}catch (OPSTBaseException e) {
			throw e;
		}
		try{
			if(response.equals(methodError)){
				return opstImageBean;
			}
			JSONObject jsonObj = JSONObject.fromObject(response);
			opstImageBean = this.jsonObjToImageBeanV2(jsonObj);
		}catch (JSONException e1) {
			throw new OPSTOperationException("获取指定image详细信息的时候出现处理json数据异常，类型为："+e1.getMessage());
		}
		return opstImageBean;
	}
	
	*//**
	 * 根据Url创建Image
<<<<<<< HEAD
	 *//*
	@Override
=======
	 
	@Override
>>>>>>> 48cbc6521f776357f3ec9287a9c4d116a4108722
	public String createImageByUrlV2(OPSTImageBean argOPSTImageBean,String tenantId,String tokenId) {
		//获取参数信息
		String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String openstackAuthHost = p.getProperty(OPSTPropertyKeyConst.OPENSTACK_AUTHHOST);
		String openstackUsername = p.getProperty(OPSTPropertyKeyConst.OPENSTACK_USERNAME);
		String openstackPassword = p.getProperty(OPSTPropertyKeyConst.OPENSTACK_PASSWORD);
		// 根据Url进行创建
		//glance --debug image-create --name='testmyimage' --public --container-format=ovf --disk-format=qcow2  --file /var/lib/glance/images/1161b60c-e191-4080-a3c3-9e96155638a1 --os-username=$OS_USERNAME 
		Ssh2Util ssh2 = new Ssh2Util();
		Connection conn = ssh2.getConnection(novaHost, openstackUsername, openstackPassword);
		List<String> cmds = new ArrayList<String>();
		String source = "source /root/keystonerc";
		cmds.add(source);
		String cmd = "glance --os-username="+openstackUsername+" --os-password="+openstackPassword+" --os-tenant-id="+tenantId+" --os-auth-url="+openstackAuthHost+"/v2.0" +
				" image-create --name='"+argOPSTImageBean.getName()+"' --public --container-format="+argOPSTImageBean.getContainer_format()+" --disk-format="+argOPSTImageBean.getDisk_format()+"  --file "+argOPSTImageBean.getFile();
		logger.info("create image:"+cmd);
		cmds.add(cmd);
		String logPath = File.separator+StringUtil.randomGUID()+".txt";
		logger.info("log path:"+logPath);
		InstallAppThread install = new InstallAppThread(conn, cmds, logPath);
		install.start();
		boolean finish = install.isFinish();
		try{
			while(true){
				Thread.sleep(4000);
				finish = install.isFinish();
				//如果已经安装完成或者安装时间超过60分钟，退出进程
				if(finish){
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String openstackImageId = "";
		File file = new File(logPath);
		BufferedReader bw;
		try {
			bw = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bw.readLine()) != null) {
				if (line.trim().contains("| id               |")) {
					System.out.println(line.trim());
					line = line.replace("|", "");
					line = line.replace("id", "");
					openstackImageId =line.trim();
					break;
				}
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return openstackImageId;
	}
	
	*//**
	 * 根据File创建Image
	 *//*
	@Override
	public OPSTImageBean createImageByFileV2(OPSTImageBean argOPSTImageBean,String tenantId,String tokenId) throws OPSTBaseException {
		//定义返回结果
		OPSTImageBean oPSTImageBean = null;
		//获取参数信息
		String imageHost ;
		String strImageDetailAPI ;		
		imageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.POST_createImage);
		String strUrl = imageHost + strImageDetailAPI; 
		// 根据File进行创建
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("x-image-meta-container_format", "bare");
		headerMap.put("x-image-meta-is_public", "True");
		headerMap.put("Content-Type", "application/octet-stream");
		headerMap.put("x-image-meta-disk_format", "iso");
		headerMap.put("x-image-meta-name", argOPSTImageBean.getName());
		if(argOPSTImageBean.getHypervisor_type()!=null){
			headerMap.put("x-image-meta-property-hypervisor_type", argOPSTImageBean.getHypervisor_type());
		}
		if(argOPSTImageBean.getOs_type()!=null){
			headerMap.put("x-image-meta-property-os_type", argOPSTImageBean.getOs_type());
		}
		
	//	headerMap.put("Transfer-Encoding", "chunked");
	//	headerMap.put("content-type", imagePath);
	//	content-type: (image file)
		//String json = "{\"file\":\""+imagePath+"\"}";
		String strResponse = "";
		JSONObject jsonObj;
		try{
			strResponse = HttpClientUtil.postMethod(tokenId, strUrl, null,headerMap,argOPSTImageBean.getFile());
//			File file = new File(argOPSTImageBean.getFile());
			//File file = new File(argOPSTImageBean.getFile());
//			File file = new File("C:\\Users\\IBM_ADMIN\\workspace\\demo\\msCloud\\WebRoot\\upload\\ubuntu-13.10-server-amd64.iso");
	//		File file = new File("C:\\Project\\TCloudApp\\WebRoot\\upload\\ubuntu-13.10-server-amd64.iso");
			//URL fileurl = new URL("http://192.168.0.129:8080/msCloud/upload/ubuntu-13.10-server-amd64.iso");
			if(!strResponse.contains(OPSTImageConst.IMAGE)){
				String error = strResponse.substring(strResponse.indexOf("\n\n")+2, strResponse.lastIndexOf("\n\n"));
//				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			jsonObj = JSONObject.fromObject(strResponse);
			JSONObject ImageResponse = jsonObj.getJSONObject(OPSTImageConst.IMAGE);
			oPSTImageBean = this.jsonObjToImageBeanV2(ImageResponse);
		}catch (JSONException e) {
			e.printStackTrace();
			throw new OPSTOperationException("执行createImageByFileV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return oPSTImageBean;
	}

	*//**
	 * 根据ID修改镜像信息
	 *//*
	@Override
	public String updateImageByIdV2(OPSTImageBean argOPSTImageBean,String tenantId,String tokenId) throws OPSTBaseException {
		//获取参数信息
		String imageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		//根据File进行创建  /v1/images/​{image_id}
		String strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.PUT_updateImageById);
		String strUrl = imageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argOPSTImageBean.getId());
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/octet-stream");
		headerMap.put("x-image-meta-name", argOPSTImageBean.getName());
		if ("snapshot".equals(argOPSTImageBean.getImage_type())) {
			headerMap.put("x-image-meta-property-image_type", argOPSTImageBean.getImage_type());
		}
		headerMap.put("x-image-meta-is_public", argOPSTImageBean.getIs_public().toString());
		headerMap.put("x-image-meta-protected", argOPSTImageBean.getImgProtected().toString());
		if(argOPSTImageBean.getDescription()!=null&&!argOPSTImageBean.getDescription().equals("-")&&!argOPSTImageBean.getDescription().equals("")){
			headerMap.put("x-image-meta-property-description", StringUtil.encode(argOPSTImageBean.getDescription()));
		}
		if(argOPSTImageBean.getHypervisor_type()!=null){
			headerMap.put("x-image-meta-property-hypervisor_type", argOPSTImageBean.getHypervisor_type());
		}
		if(argOPSTImageBean.getOs_type()!=null){
			headerMap.put("x-image-meta-property-os_type", argOPSTImageBean.getOs_type());
		}
		if(argOPSTImageBean.getOs_name()!=null){
			headerMap.put("x-image-meta-property-os_name", argOPSTImageBean.getOs_name());
		}
		//修改镜像时: 不清除property已有的属性值
		headerMap.put("x-glance-registry-purge-props", String.valueOf(Boolean.FALSE));
		
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDesUrl, null, headerMap);
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTImageConst.IMAGE)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return null;
	}
	
	*//**
	 * 删除指定的image。
	 * 注：删除已添加异常
	 * 当错误删除时，弹出提示框：操作失败
	 * @param imageId
	 * @throws Exception 
	 *//*
	@Override
	public String deleteImageV2(String argImageId,String tenantId,String tokenId) throws OPSTBaseException {
		//指定删除的id不能为空
		if(argImageId == null){
			//参数错误
			throw new OPSTOperationException("deleteImageV2的时候没有获取到标识出现异常");
		}
		//获取参数信息
		String imageHost;
		String strdeleteAPI;
		imageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);		
		strdeleteAPI = p.getProperty(OPSTPropertyKeyConst.DELETE_Images);
		// /v2/images/​{image_id}​
		String strUrl = imageHost + strdeleteAPI;
		String strDestUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId);
		
		String strResponse;
		try{
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		return strResponse;
	}
	
	/**
	 * jsonObjToImageBeanV2
	 * @param image
	 * @return
	 */
	private ImagesBean jsonObjToImageBeanV2(JSONObject image){
		ImagesBean ib = new ImagesBean();
		if(image.containsKey(OPSTImageConst.IMAGE_ID)){
			ib.setId(image.getString(OPSTImageConst.IMAGE_ID));
		}else{
			ib.setId("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_NAME)){
			ib.setName(image.getString(OPSTImageConst.IMAGE_NAME));
		}else{
			ib.setName("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_STATUS)){
			ib.setStatus(image.getString(OPSTImageConst.IMAGE_STATUS));
		}else{
			ib.setStatus("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_CONTAINER_FORMAT)){
			ib.setContainer_format(image.getString(OPSTImageConst.IMAGE_CONTAINER_FORMAT));
		}else{
			ib.setContainer_format("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_CREATED_AT)){
			ib.setCreated_at(image.getString(OPSTImageConst.IMAGE_CREATED_AT));
		}else{
			ib.setCreated_at("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_UPDATE_AT)){
			ib.setUpdate_at(image.getString(OPSTImageConst.IMAGE_UPDATE_AT));
		}else{
			ib.setUpdate_at("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_DISK_FORMAT)){
			ib.setDisk_format(image.getString(OPSTImageConst.IMAGE_DISK_FORMAT));
		}else{
			ib.setDisk_format("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_MIN_DISK)){
			ib.setMin_disk(Integer.parseInt(image.getString(OPSTImageConst.IMAGE_MIN_DISK)));
		}else{
			ib.setMin_disk(0);
		}
		if(image.containsKey(OPSTImageConst.IMAGE_PROTECTED)){
			ib.setImgProtected(Boolean.parseBoolean(image.getString(OPSTImageConst.IMAGE_PROTECTED)));
		}
		if(image.containsKey(OPSTImageConst.IMAGE_MIN_RAM)){
			ib.setMin_ram(Integer.parseInt(image.getString(OPSTImageConst.IMAGE_MIN_RAM)));
		}else{
			ib.setMin_ram(0);
		}
		if(image.containsKey(OPSTImageConst.IMAGE_SIZE)){
			ib.setSize(Long.parseLong(image.getString(OPSTImageConst.IMAGE_SIZE)));
		}else{
			ib.setSize(0);
		}
		if(image.containsKey(OPSTImageConst.IMAGE_VISIBILITY)){
			ib.setVisibility(image.getString(OPSTImageConst.IMAGE_VISIBILITY));
		}
		//虚拟化类型
		if(image.containsKey(OPSTImageConst.IMAGE_HYPERVISOR_TYPE)){
			ib.setHypervisor_type(image.getString(OPSTImageConst.IMAGE_HYPERVISOR_TYPE));
		}else{
			ib.setHypervisor_type("-");
		}
		if(image.containsKey(OPSTImageConst.OWNER)){
			ib.setOwner(image.getString(OPSTImageConst.OWNER));
		}else{
			ib.setOwner("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_CHECKSUM)){
			ib.setChecksum(image.getString(OPSTImageConst.IMAGE_CHECKSUM));
		}else{
			ib.setChecksum("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_CONTAINER_FORMAT)){
			ib.setContainer_format(image.getString(OPSTImageConst.IMAGE_CONTAINER_FORMAT));
		}else{
			ib.setContainer_format("-");
		}
		if(image.containsKey(OPSTImageConst.IMAGE_CREATED_AT)){
			ib.setCreated_at(image.getString(OPSTImageConst.IMAGE_CREATED_AT));
		}else{
			ib.setCreated_at("-");
		}
		return ib;
	}
	
	/*
	@Override
	public void uploadImageFile(String argImageId,String tenantId,String tokenId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadImageFile(String argImageId,String tenantId,String tokenId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTagForImage(String argImageId, String argTag,String tenantId,String tokenId) {
		// TODO Auto-generated method stub	
	}

	@Override
	public List<OPSTImageMemberBean> getMemberListV1(String tenantId,String tokenId) throws OPSTBaseException  {
		List<OPSTImageMemberBean> list = new ArrayList<OPSTImageMemberBean>();
		//获取参数信息
		String strImageHost;
		String strImageDetailAPI;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.GET_IMAGEMENBERLIST);
		//  /v1/images/​{image_id}​/members
		String strUrl = strImageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_OWNERID, tenantId);
		try {
			HttpClientUtil.putMethod(tokenId, strDesUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}	
		
		return list;
	}

	@Override
	public OPSTImageMemberBean getMemberDetailV1(String argImageId, String argTenantId,String tenantId,String tokenId) throws OPSTBaseException {
		OPSTImageMemberBean opstBean = new OPSTImageMemberBean();
		//获取参数信息
		String strImageHost;
		String strImageDetailAPI;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.GET_IMAGEMENBERBYID);
		//  /v1/images/​{image_id}​/members/​{owner_id}
		String strUrl = strImageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId).replace(OPSTImageConst.PARAM_OWNERID, argTenantId);
		try {
			HttpClientUtil.putMethod(tokenId, strDesUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		return opstBean;
	}
	
	@Override
	public void addMemberV1(String argImageId,String argTenantId,String tenantId,String tokenId) throws OPSTBaseException {
		//获取参数信息
		String strImageHost;
		String strImageDetailAPI;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.PUT_CREATEIMAGEMENBER);
		//  /v1/images/​{image_id}​/members/​{owner_id}
		String strUrl = strImageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId).replace(OPSTImageConst.PARAM_OWNERID, argTenantId);
		
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.putMethod(tokenId, strDesUrl);
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTImageConst.OWNER)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}	
	}

	@Override
	public void deleteMemberV1(String argImageId, String argTenantId,String tenantId,String tokenId) throws OPSTBaseException  {
		//获取参数信息
		String strImageHost;
		String strImageDetailAPI;
		strImageHost = p.getProperty(OPSTPropertyKeyConst.IMAGIC_HOST);
		strImageDetailAPI = p.getProperty(OPSTPropertyKeyConst.DELETE_DELETEIMAGEMENBER);
		//  /v1/images/​{image_id}​/members/​{owner_id}
		String strUrl = strImageHost + strImageDetailAPI; 
		String strDesUrl = strUrl.replace(OPSTImageConst.PARAM_IMAGEID, argImageId).replace(OPSTImageConst.PARAM_OWNERID, argTenantId);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDesUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}		
	}

	@Override
	public void setMemberStatus(String argImageId, String argMemberId,
			String argStatus,String tenantId,String tokenId) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void getImageSchemaList(String tenantId,String tokenId) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void getImageSchema(String tenantId,String tokenId) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void getImageSchemaMemberList(String tenantId,String tokenId) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void getImageSchemaMeber(String tenantId,String tokenId) {
		// TODO Auto-generated method stub
	}*/

}