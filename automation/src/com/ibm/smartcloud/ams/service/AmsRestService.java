package com.ibm.smartcloud.ams.service;

import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.smartcloud.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;

public interface AmsRestService {
	
	Properties amsCfg = PropertyUtil.getResourceFile("config/properties/ams2.properties");
	Properties cloudCfg = PropertyUtil.getResourceFile("config/properties/cloud.properties");

	public List<String> getCmdInfoByAddr(String names, String addrs,String exec)throws OPSTBaseException;
	
	public ObjectNode postWASRun(ArrayNode hostNode,ObjectNode credNode,ObjectNode info);
	
	public ObjectNode postDB2Run(ArrayNode hostnode,ObjectNode crednode,String info, String type, String nfsON);
	
	public ObjectNode savePVCNode(ObjectNode node,String url);
	
	public ArrayNode getList(ArrayNode sort, JsonNode query, String url);
	
}