package com.ibm.smartcloud.openstack.nova.controller;

import java.util.Properties;

import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.PropertyUtil;

/** 
 * @Title:Test     
 * @Description:
 * @Auth:LiangRui   
 * @CreateTime:2015年6月11日 上午10:33:43       
 * @version V1.0
 */
public class HttpTest {
	static Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	/**
	 * @Title: main
	 * @Description:
	 * @param @param args
	 * @author LiangRui
	 * @throws
	 * @Time 2015年6月11日 上午10:33:43
	 */
	public static void main(String[] args) {
		// 获取参数信息
		//String novaHost = p.getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		//String strServerApi = p.getProperty(OPSTPropertyKeyConst.GET_resourceList);
		// 拼接URL
		String tokenId = "8cc8163390ae4573a43623aa45cd36ae";
		String strInfo = "https://10.28.0.230/powervc/openstack/compute/v2/1bbd5cde46034082be3d60ee8413967f/servers/detail";
		// String strInfo = "https://www.alipay.com/";
		// 查询Server信息
		/*try {
			// boo = HttpUtil.post(strInfo, null);
			JsonNode jn = HttpUtil.query(om.createObjectNode().put("url", strInfo));
			System.out.println(jn);
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		String response = "";
		try {
			response = HttpClientUtil.getMethod(tokenId,strInfo);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
