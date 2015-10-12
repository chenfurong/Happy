package com.ibm.smartcloud.openstack.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class FindHypervisorTypeUtil {
	static final Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	
	
	public static Map<String,String> getDiskFormat()
	{
		//Properties p = PropertyUtil.getResourceFile("config/cloud.properties");
		String hypervisor_type_of_disk = p.getProperty("hypervisor_type_of_disk");
		String[] strings=hypervisor_type_of_disk.split(";");
		Map<String, String> map = new HashMap<String, String>();
		for (String string : strings) {
			String[] strings2 = string.split(":");
			map.put(strings2[0], strings2[1]);
		}
		return map;
	}
	public static Map<String,String> getHypervisorType()
	{
		//Properties p = PropertyUtil.getResourceFile("config/cloud.properties");
		String hypervisor_type_list = p.getProperty("hypervisor_type_list");
		String[] strings=hypervisor_type_list.split(";");
		Map<String, String> map = new HashMap<String, String>();
		for (String string : strings) {
			String[] strings2 = string.split(":");
			map.put(strings2[0], strings2[1]);
		}
		return map;
	}
	
	public static List<String> getImageOsType()
	{
		//Properties p = PropertyUtil.getResourceFile("config/cloud.properties");
		String hypervisor_type_list = p.getProperty("image_os_type");
		String[] strings=hypervisor_type_list.split(";");
		List<String> list = new ArrayList<String>();
		for (String string : strings) {
			list.add(string);
		}
		return list;
	}
}
