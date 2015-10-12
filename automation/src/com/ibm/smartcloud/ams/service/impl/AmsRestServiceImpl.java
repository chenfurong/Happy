package com.ibm.smartcloud.ams.service.impl;

import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.smartcloud.ams.constants.AMS2KeyUtil;
import com.ibm.smartcloud.ams.service.AmsRestService;
import com.ibm.smartcloud.ams.util.AmsClient;
import com.ibm.smartcloud.ams.util.AmsV2ClientHttpClient4Impl;
import com.ibm.smartcloud.core.util.EncodeUtil;
import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;

@Service("amsRestServiceImpl")
public class AmsRestServiceImpl implements AmsRestService {
	AmsClient amsClient = new AmsV2ClientHttpClient4Impl();
	ObjectMapper om = new ObjectMapper();

	public List<String> getCmdInfoByAddr(String names, String addrs,String exec)throws OPSTBaseException  {
		// 获取参数信息
		String hdiskHost = cloudCfg.getProperty(OPSTPropertyKeyConst.AMS2_HOST);
		String hdiskApi = cloudCfg.getProperty(OPSTPropertyKeyConst.POST_ams2_service_cmd);
		// 拼接URL
		String strOrgUrl = hdiskHost + hdiskApi;
		// 构建json
		ObjectNode node = om.createObjectNode();
		node.put("name", names);
		node.put("type", "shell");
		node.put("exec", exec);
		node.put("async", false);
		node.set("node", om.createObjectNode().put("host", addrs).put("addr", addrs));
		node.set("cred", om.createObjectNode().put("user", "root").put("pass", "passw0rd"));		
		String response = "";
		try {
			response = HttpClientUtil.postMethod(strOrgUrl, node.toString());
			System.out.println("response::"+response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				Object resp = jsonObj.get("msg");
				String msg = resp.toString();
				//解析
				String[] lines = msg.split("\\\n");
				List<String> list = Arrays.asList(lines);
				return list; 
			}
		} catch (Exception e) {
			throw new OPSTOperationException("获取HDisks的时候出现处理json数据异常，类型为：" + e.getMessage());
		}
		return null;
	}
	
	
	@Override
	public ObjectNode postWASRun(ArrayNode hostnode,ObjectNode crednode,ObjectNode info) {
		// 获取参数信息
		String hdiskHost = cloudCfg.getProperty(OPSTPropertyKeyConst.AMS2_HOST);
		String hdiskApi = cloudCfg.getProperty(OPSTPropertyKeyConst.POST_ams2_service_run);
		// 拼接URL
		String strOrgUrl = hdiskHost + hdiskApi;
		System.out.println("url::"+strOrgUrl);
		// 构建json
		ObjectNode task = om.createObjectNode();
		ArrayNode jobs = om.createArrayNode();
		task.put("name", "aix71-was");
		task.set("jobs", jobs);		
		ArrayNode names = AMS2KeyUtil.getArrayNodeByKey("job_name");	//job名称的集合
		ObjectNode stepobj = this.getStepObjectNode();//step集合	
		for(JsonNode jnode : names){			
			ObjectNode job = (ObjectNode) jnode;
			String name = job.get("name").asText();
			ArrayNode stepnames = (ArrayNode) stepobj.get(name);			
			for(JsonNode sn : stepnames){
				ObjectNode jobnode = om.createObjectNode();
				ArrayNode steps = null;
				String sname = sn.get("name").asText();
				String jobname = name+"-"+sname;				
				jobnode.put("name", jobname);
				jobnode.put("type", "series");
				if(sname.equals("scripts")){
					steps = getScripts(hostnode, crednode, jobname);
				}else if(sname.equals("put-hostname")){
					steps = getPutHostname(hostnode, crednode, jobname);
				}else if(sname.equals("set-hostname")){
					steps = getSetHostname(hostnode, crednode, jobname);
				}else if(sname.equals("put-hosts")){
					steps = getPutHosts(hostnode, crednode, jobname);
				}else if(sname.equals("gen-hosts")){
					steps = getGenHosts(hostnode, crednode, jobname);
				}else if(sname.equals("set-hosts")){
					steps = getSetHosts(hostnode, crednode, jobname);
				}else if(sname.equals("files")){
					steps = getFiles(hostnode, crednode, jobname,"file_name","file_path");
				}else if(sname.equals("chmod")){
					steps = getChmod(hostnode, crednode, jobname);
				}else if(sname.equals("prepare.was.ksh")){
					steps = getPrepareWasKsh(hostnode, crednode, jobname);
				}else if(sname.equals("inst.was.ksh")){
					steps = getInstWasKsh(hostnode, crednode, jobname,info);
				}else if(sname.equals("createProfiles.was.ksh")){
					steps = getCreateProfilesWasKsh(hostnode, crednode, jobname);
				}
				jobnode.set("steps", steps);
				jobs.add(jobnode);						
				
			}
		}
		System.out.println("jobs::"+jobs);
		System.out.println("task::"+task);
		String response = "";
		try {
			response = HttpClientUtil.postMethod(strOrgUrl, task.toString());
			System.out.println("response::"+response);
			return (ObjectNode)om.readTree(response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ObjectNode postDB2Run(ArrayNode hostnode,ObjectNode crednode,String info,String type, String nfsON) {
		
		//设值是否立即执行标识
		boolean flag = false ;
		if("yes".equals(type)){
			flag = true;
		}else if("no".equals(type)){
			flag = false;
		}
		//设值是否立即执行标识
		/*boolean nfsFlag = false ;
		if("yes".equals(nfsON)){
			nfsFlag = true;
		}else if("no".equals(nfsON)){
			nfsFlag = false;
		}*/
		// 获取参数信息
		String hdiskHost = cloudCfg.getProperty(OPSTPropertyKeyConst.AMS2_HOST);
		String hdiskApi = cloudCfg.getProperty(OPSTPropertyKeyConst.POST_ams2_service_run);
		// 拼接URL
		String strOrgUrl = hdiskHost + hdiskApi;
		System.out.println("url::"+strOrgUrl);
		// 构建json
		ObjectNode task = om.createObjectNode();
		ArrayNode jobs = om.createArrayNode();
		task.put("name", "aix71-db2");
		task.put("immediate", true);
		task.set("jobs", jobs);	
		ArrayNode names;
		if(flag){
			 names = AMS2KeyUtil.getArrayNodeByKey("db2_job_name");	//自动执行,job名称的集合
		}else {
			 names = AMS2KeyUtil.getArrayNodeByKey("db2_job_name_sub");	//手动执行job名称的集合
		}
		ObjectNode stepobj = this.getDb2StepObjectNode();//step集合	
		for(JsonNode jnode : names){			
			ObjectNode job = (ObjectNode) jnode;
			String name = job.get("name").asText();
			ArrayNode stepnames = (ArrayNode) stepobj.get(name);			
			for(JsonNode sn : stepnames){
				ObjectNode jobnode = om.createObjectNode();
				ArrayNode steps = null;
				String sname = sn.get("name").asText();
				String jobname = name+"-"+sname;				
				jobnode.put("name", jobname);
				jobnode.put("type", "series");
				if(sname.equals("scripts")){
					steps = getScripts(hostnode, crednode, jobname);
				}else if(sname.equals("put-hosts")){
					steps = getPutHosts(hostnode, crednode, jobname);
				}else if(sname.equals("put-hostname")){
					steps = getPutHostname(hostnode, crednode, jobname);
				}else if(sname.equals("files")){
					steps = getFiles(hostnode, crednode, jobname,"db2_file_name","db2_file_path");
				}else if(sname.equals("prepare.db2.lst")){
					steps = getPrepareDb2Lst(hostnode, crednode, jobname,info);
				}else if(sname.equals("chmod")){
					steps = getChmod(hostnode, crednode, jobname);
				}else if(sname.equals("set-hostname")){
					steps = getSetHostname(hostnode, crednode, jobname);
				}else if(flag && sname.equals("prepare.db2.ksh")){
					steps = getPrepareDb2Ksh(hostnode, crednode, jobname);
				}else if(flag && sname.equals("install.db2.ksh")){
					steps = getInstallDb2Ksh(hostnode, crednode, jobname);
				}else if(flag && sname.equals("mount.nfs.ksh")){
					steps = getNFSDb2Ksh(hostnode, crednode, jobname);
				}
				jobnode.set("steps", steps);
				jobs.add(jobnode);						
				
			}
		}
		System.out.println("jobs::"+jobs);
		System.out.println("task::"+task);
		String response = "";
		try {
			response = HttpClientUtil.postMethod(strOrgUrl, task.toString());
			System.out.println("response::"+response);
			return (ObjectNode)om.readTree(response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	private ArrayNode getNFSDb2Ksh(ArrayNode hostnode,ObjectNode crednode,String name) {
		
		ArrayNode filesteps = om.createArrayNode();	
		ArrayNode raws = getraws(hostnode, crednode);	
		
		for(int i=0;i<raws.size();i++){
			ObjectNode rs = (ObjectNode) raws.get(i);
			ObjectNode step = om.createObjectNode();
			step.set("node", rs.get("node"));
			step.set("cred", rs.get("cred"));
			step.put("async", true);
			step.put("exec", "cd "+amsCfg.getProperty("script_path_sub")+" && ./"+amsCfg.getProperty("db2_nfs_step_name"));
			step.put("type", "scmd");
			step.put("name", name+"#"+rs.get("node").get("host").asText());
			filesteps.add(step);
		}				
		return filesteps;
	}


	public ObjectNode savePVCNode(ObjectNode node,String url){
		try {
			JsonNode jsonnode = amsClient.save(node, url);
			return (ObjectNode)jsonnode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayNode getList(ArrayNode sort, JsonNode query, String url) {
		try {
			return amsClient.list(sort, query, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		

	/** 提供封装json的拼装方法 开始**/
	private ObjectNode getcred(ObjectNode crednode) {		
		ObjectNode default_cred = AMS2KeyUtil.getCredDefaultUserNode();
		ObjectNode cred = crednode==null?(ObjectNode)default_cred.get("default"):(ObjectNode)crednode.get("host");								
		return cred;
	}
	private ArrayNode getraws(ArrayNode hostnode,ObjectNode crednode) {		
		ArrayNode stephosts = om.createArrayNode();
		for(JsonNode h : hostnode){
			ObjectNode step = om.createObjectNode();
			step.set("cred", getcred(crednode));										
			step.set("node", om.createObjectNode().put("host", 
					h.get("host").asText()).put("addr", h.get("addr").asText()));
			
			stephosts.add(step);			
		}
		return stephosts;
	}
	
	/*private ArrayNode gethostList(ArrayNode hostnode) {		
		ArrayNode hosts = om.createArrayNode();
		for(JsonNode h : hostnode){
			ObjectNode node = om.createObjectNode();
			node.put("host", h.get("host").asText());
			node.put("addr", h.get("addr").asText());			
			hosts.add(node);
		}
		return hosts;
	}*/
		
	/*private ArrayNode getHosts(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;						
			steps.set("hosts", gethostList(hostnode));
			steps.put("async", true);
			steps.put("exec", "hosts.helper.sh");
			steps.put("type", "shos");
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}*/
	
	private ArrayNode getScripts(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			//steps.put("exec", "mkdir -p /script");
			steps.put("exec", "mkdir -p "+amsCfg.getProperty("script_path_sub"));
			steps.put("type", "scmd");
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	private ArrayNode getPutHostname(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			//steps.put("exec", "/script/hostname.helper.sh");
			steps.put("exec", amsCfg.getProperty("script_path")+amsCfg.getProperty("hostname_sh"));
			steps.put("type", "sput");
			//steps.put("file", "/home/cloudm/works/tcloud2-ams/shell/hostname.helper.sh");
			steps.put("file", amsCfg.getProperty("db2_helpers_file_path")+amsCfg.getProperty("hostname_sh"));
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	private ArrayNode getSetHostname(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			//steps.put("exec", "cd /script && sh ./hostname.helper.sh "+rs.get("node").get("host").asText());
			steps.put("exec", "cd "+amsCfg.getProperty("script_path_sub")+" && sh ./"+amsCfg.getProperty("hostname_sh")+" "+rs.get("node").get("host").asText());
			steps.put("type", "scmd");
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	private ArrayNode getPutHosts(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			//steps.put("exec", "/script/hosts.helper.sh");
			steps.put("exec", amsCfg.getProperty("script_path")+amsCfg.getProperty("hosts_sh"));
			steps.put("type", "sput");
			//steps.put("file", "/home/cloudm/works/tcloud2-ams/shell/hosts.helper.sh");
			steps.put("file", amsCfg.getProperty("db2_helpers_file_path")+amsCfg.getProperty("hosts_sh"));
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	private ArrayNode getGenHosts(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		String encodestr = getGenHostsBase64Encode(hostnode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			steps.put("exec", "/script/hosts.tmp");
			steps.put("type", "scat");
			steps.put("text", "data:text/plain;base64,"+encodestr);
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	private String getGenHostsBase64Encode(ArrayNode hostnode){
		String str = "";
		for(JsonNode h : hostnode){			
			str = str+h.get("addr").asText()+"\t"+h.get("host").asText()+"\n";
		}
		System.out.println(str.toString());
		String encodestr = EncodeUtil.encode(str.trim());
		System.out.println(encodestr);
		return encodestr;
	}
	
	
	private ArrayNode getSetHosts(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			steps.put("exec", "cd /script && sh ./hosts.helper.sh /script/hosts.tmp /etc/hosts");
			steps.put("type", "scmd");
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	private ArrayNode getFiles(ArrayNode hostnode,ObjectNode crednode,String name,String filenamekey,String filepath) {	
		ArrayNode filesteps = om.createArrayNode();
		String filepath1 = amsCfg.getProperty(filepath);
		ArrayNode filenames = AMS2KeyUtil.getArrayNodeByKey(filenamekey);		
		ArrayNode raws = getraws(hostnode, crednode);		
		for(JsonNode fn : filenames){
			String filename = fn.get("name").asText();		
			for(JsonNode rs : raws){
				ObjectNode step = om.createObjectNode();	
				step.set("node", rs.get("node"));
				step.set("cred", rs.get("cred"));
				step.put("async", true);
				//step.put("exec", "/script/" + filename);
				step.put("exec", amsCfg.getProperty("script_path") + filename);
				step.put("type", "sput");
				step.put("file", filepath1 + filename);
				step.put("name", name+"#"+rs.get("node").get("host").asText()+"#"+filename);
				filesteps.add(step);
			}
		}
		return filesteps;
	}
	
	private ArrayNode getChmod(ArrayNode hostnode,ObjectNode crednode,String name) {		
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			//steps.put("exec", "chmod +x /script/*.ksh && chmod +x /script/*.sh");
			steps.put("exec", "chmod +x "+amsCfg.getProperty("script_path")+"*.ksh && chmod +x /script/*.sh");
			steps.put("type", "scmd");
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
		}		
		return raws;
	}
	
	
	private ArrayNode getPrepareWasKsh(ArrayNode hostnode,ObjectNode crednode,String name) {	
		ArrayNode filesteps = om.createArrayNode();	
		ArrayNode raws = getraws(hostnode, crednode);		
		String ftp = AMS2KeyUtil.getFTPNode();
		
		for(JsonNode rs : raws){
			ObjectNode step = om.createObjectNode();
			step.set("node", rs.get("node"));
			step.set("cred", rs.get("cred"));
			step.put("async", true);
			step.put("exec", "cd /script && ./prepare.was.ksh " + ftp);
			step.put("type", "scmd");
			step.put("name", name+"#"+rs.get("node").get("host").asText());
			filesteps.add(step);
		}				
		return filesteps;
	}
	
	private ArrayNode getInstWasKsh(ArrayNode hostnode,ObjectNode crednode,String name,ObjectNode wasinfo) {	
		ArrayNode filesteps = om.createArrayNode();	
		String url = amsCfg.getProperty("was_install_path");
		if(wasinfo!=null){
			url = wasinfo.get("url")==null?url:wasinfo.get("url").asText();
		}
		ArrayNode raws = getraws(hostnode, crednode);
		for(JsonNode rs : raws){
			ObjectNode step = om.createObjectNode();
			step.set("node", rs.get("node"));
			step.set("cred", rs.get("cred"));
			step.put("async", true);
			step.put("exec", "cd /script && ./inst.was.ksh "+url);
			step.put("type", "scmd");
			step.put("name", name+"#"+rs.get("node").get("host").asText());
			filesteps.add(step);
		}				
		return filesteps;
	}
	
	private ArrayNode getCreateProfilesWasKsh(ArrayNode hostnode,ObjectNode crednode,String name) {	
		ArrayNode filesteps = om.createArrayNode();	
		ArrayNode raws = getraws(hostnode, crednode);	
		String hostname = "";
		for(JsonNode rs : hostnode){
			if("cell".equals(rs.get("conf").get("was").asText())){
				hostname = rs.get("host").asText();
				break;
			}
		}
		for(JsonNode rs : raws){
			ObjectNode step = om.createObjectNode();
			String was = "";
			for(JsonNode hn : hostnode){
				if(hn.get("host").asText().equals(rs.get("node").get("host").asText())&&
					  hn.get("addr").asText().equals(rs.get("node").get("addr").asText())){
					was = hn.get("conf").get("was").asText();
					break;
				}
			}
			step.set("node", rs.get("node"));
			step.set("cred", rs.get("cred"));
			step.put("async", true);
			step.put("exec", "cd /script && ./createProfiles.was.ksh "+was+" "+hostname+" 8879");
			step.put("type", "scmd");
			step.put("name", name+"#"+rs.get("node").get("host").asText());
			filesteps.add(step);
		}				
		return filesteps;
	}

	private ArrayNode getPrepareDb2Lst(ArrayNode hostnode,ObjectNode crednode,String name,String info){
		ArrayNode filesteps = om.createArrayNode();	
		ArrayNode raws = getraws(hostnode, crednode);			
		String encodestr = EncodeUtil.encode(info.trim());
		for(JsonNode rs : raws){
			ObjectNode steps = (ObjectNode)rs;			
			steps.put("async", true);
			//steps.put("exec", "/script/prepare.db2.lst");
			steps.put("exec", amsCfg.getProperty("script_path")+amsCfg.getProperty("prepare_lst"));
			steps.put("type", "scat");
			steps.put("text", "data:text/plain;base64,"+encodestr);
			steps.put("name", name+"#"+rs.get("node").get("host").asText());
			filesteps.add(steps);
		}		
		return filesteps;
	}
	private ArrayNode getPrepareDb2Ksh(ArrayNode hostnode,ObjectNode crednode,String name) {	
		ArrayNode filesteps = om.createArrayNode();	
		ArrayNode raws = getraws(hostnode, crednode);	
		
		for(int i=0;i<raws.size();i++){
			ObjectNode rs = (ObjectNode) raws.get(i);
			ObjectNode step = om.createObjectNode();
			step.set("node", rs.get("node"));
			step.set("cred", rs.get("cred"));
			step.put("async", true);
			//step.put("exec", "cd /script && ./prepare.db2.ksh "+hostnode.get(i).get("role").asText());
			step.put("exec", "cd "+amsCfg.getProperty("script_path_sub")+" && ./"+amsCfg.getProperty("db2_install_step_name")+" "+hostnode.get(i).get("host").asText());
			step.put("type", "scmd");
			step.put("name", name+"#"+rs.get("node").get("host").asText());
			filesteps.add(step);
		}				
		return filesteps;
	}
	private ArrayNode getInstallDb2Ksh(ArrayNode hostnode,ObjectNode crednode,String name) {	
		ArrayNode filesteps = om.createArrayNode();	
		
		ArrayNode raws = getraws(hostnode, crednode);
		ObjectNode step = om.createObjectNode();
		step.set("node", raws.get(0).get("node"));
		step.set("cred", raws.get(0).get("cred"));
		step.put("async", true);
		//step.put("exec", "cd /script && ./install.db2.ksh ha1");
		step.put("exec", "cd "+amsCfg.getProperty("script_path_sub")+" && ./"+amsCfg.getProperty("db2_cluster_step_name")+" "+hostnode.get(0).get("host").asText());
		step.put("type", "scmd");
		step.put("name", name+"#"+raws.get(0).get("node").get("host").asText());
		filesteps.add(step);			
		return filesteps;
	}
	
	private ObjectNode getStepObjectNode(){
		ObjectNode stepobj = om.createObjectNode();
		ArrayNode prepare_steps = AMS2KeyUtil.getArrayNodeByKey("prepare_step_name");		
		ArrayNode install_steps = AMS2KeyUtil.getArrayNodeByKey("install_step_name");		
		ArrayNode cluster_steps = AMS2KeyUtil.getArrayNodeByKey("cluster_step_name");	
		stepobj.set("prepare", prepare_steps);
		stepobj.set("install", install_steps);
		stepobj.set("cluster", cluster_steps);
		return stepobj;
	}
	private ObjectNode getDb2StepObjectNode(){
		ObjectNode stepobj = om.createObjectNode();
		ArrayNode prepare_steps = AMS2KeyUtil.getArrayNodeByKey("db2_prepare_step_name");		
		ArrayNode install_steps = AMS2KeyUtil.getArrayNodeByKey("db2_install_step_name");		
		ArrayNode cluster_steps = AMS2KeyUtil.getArrayNodeByKey("db2_cluster_step_name");
		ArrayNode nfs_steps = AMS2KeyUtil.getArrayNodeByKey("db2_nfs_step_name");
		stepobj.set("prepare", prepare_steps);
		stepobj.set("install", install_steps);
		stepobj.set("cluster", cluster_steps);
		stepobj.set("nfs", nfs_steps);
		return stepobj;
	}
	/** 提供封装json的拼装方法 结束**/
	

}