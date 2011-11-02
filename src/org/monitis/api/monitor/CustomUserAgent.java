package org.monitis.api.monitor;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public class CustomUserAgent extends APIObject{

	public CustomUserAgent(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.CUSTOM_MONITOR_API_URL);
	}
	
	public CustomUserAgent(){
		super(Constants.CUSTOM_MONITOR_API_URL);
	}
	
	public enum CustomUserAgentAction{
		agentInfo,
		getAgents,
		addAgent,
		editAgent,
		deleteAgent;
	}
	
	public Response getAgents(String type, boolean loadTests, boolean loadParams, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(type != null) params.put("type", type);
		params.put("loadTests", loadTests);
		params.put("loadParams", loadParams);
		params.put("output", output);
		Response resp = makeGetRequest(CustomUserAgentAction.getAgents, params);
		return resp;
	}
	
	public Response getAgentInfo(Integer agentId, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("output", output);
		Response resp = makeGetRequest(CustomUserAgentAction.agentInfo, params);
		return resp;
	}
	
	
	public Response addAgent(String name, String type, JSONObject agentParams, Integer jobPollingInterval, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", StringUtils.urlEncode(name));
		params.put("type", StringUtils.urlEncode(type));
		params.put("params", StringUtils.urlEncode(agentParams.toString()));
		params.put("jobPollingInterval", jobPollingInterval);
		params.put("output", output);
		return makePostRequest(CustomUserAgentAction.addAgent, params);
	}
	
	public Response editAgent(Integer agentId, String name, JSONObject agentParams, Integer jobPollingInterval) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("params", StringUtils.urlEncode(agentParams.toString()));
		params.put("jobPollingInterval", jobPollingInterval);
		return makePostRequest(CustomUserAgentAction.editAgent, params);
	}
	
	public Response deleteAgents(Integer [] agentIds, boolean deleteMonitors) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentIds", StringUtils.join(agentIds, ","));
		params.put("deleteMonitors", deleteMonitors);
		return makePostRequest(CustomUserAgentAction.deleteAgent, params);
	}
	
	
	public static void main(String[] args) throws JSONException {
		CustomUserAgent agent;
		try {
			Response resp = null;
			agent = new CustomUserAgent();
			JSONObject agentParams = new JSONObject();
			agentParams.put("drivelist", "C,D,E");
			agentParams.put("processlist", "skype.exeC, montiis.exe");
			String name = "monitis intral for ngas";
			String type = "internal";
			resp = agent.addAgent(name, type, agentParams, 5, OutputType.XML);
			System.out.println(resp);
			int agentId = new org.json.JSONObject(resp.getResponseText()).getInt("data");
			resp = agent.getAgents(null, true, true, OutputType.XML);
			System.out.println(resp);
			resp = agent.getAgents(type, true, true, OutputType.XML);
			System.out.println(resp);
			resp = agent.getAgentInfo(agentId, OutputType.XML);
			System.out.println(resp);
			agentParams.put("drivelist", "E,F");
			resp = agent.editAgent(agentId, name+"_edit", agentParams, 6);
			System.out.println(resp);
			resp = agent.deleteAgents(new Integer[]{agentId}, true);
			System.out.println(resp);

		} catch (MonitisException e) {
			e.printStackTrace();
		}
	}
	
	

}
