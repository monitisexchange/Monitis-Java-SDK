package org.monitis.api.monitor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.enums.Platform;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public class Agent extends APIObject{

	public Agent(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	public Agent(){}
	
	public enum AgentAction{
		agents(),
		allAgentsSnapshot(),
		agentSnapshot(),
		agentInfo(),
		internalMonitors,
		deleteAgents,
		downloadAgent;
	}
	
	public Response getAgents(String keyRegExp, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if(keyRegExp != null) params.put("keyRegExp", StringUtils.urlDecode(keyRegExp));
		Response resp = makeGetRequest(AgentAction.agents, params);
		return resp;
	}
	
	public Response getAllAgentsSnapshot(Integer timezone, Platform platform, String tag, OutputType output)  throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (timezone != null){
			params.put("timezone", timezone);
		}
		if(tag != null) params.put("tag", StringUtils.urlEncode(tag));
		params.put("platform", platform);
		Response resp = makeGetRequest(AgentAction.allAgentsSnapshot, params);
		return resp;
	}
	
	public Response getAgentSnapshot(Integer timezone, String agentKey, Integer agentId, OutputType output)  throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (timezone != null){
			params.put("timezone", timezone);
		}
		if(agentKey!=null)params.put("agentKey", StringUtils.urlDecode(agentKey));
		if(agentId!=null)params.put("agentId", agentId);
		Response resp = makeGetRequest(AgentAction.agentSnapshot, params);
		return resp;
	}

	public Response getAgentInfo(Integer agentId, boolean loadTests, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("agentId", agentId);
		params.put("loadTests", loadTests);
		return makeGetRequest(AgentAction.agentInfo, params);
	}
	
	public Response getInternalMonitors(String tag, String tagRegExp, String types, OutputType output)  throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (tagRegExp != null)params.put("tagRegExp", tagRegExp);
		if (tag != null)params.put("tag", tag);
		if (types != null)params.put("types", types);
		Response resp = makeGetRequest(AgentAction.internalMonitors, params);
		return resp;
	}
	
	public Response deleteAgents(Integer [] agentIds, String keyRegExp) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(agentIds != null) params.put("agentIds", StringUtils.join(agentIds, ","));
		if(keyRegExp != null) params.put("keyRegExp", keyRegExp);
		return makePostRequest(AgentAction.deleteAgents, params);
	}
	
	public void downloadAgent(String platform, String fileName)throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("platform", platform);
		makePostRequest(AgentAction.downloadAgent, params, fileName);
	}
		
	public static void main(String[] args) {
		Agent monitor;
		try {
			Response resp = null;
			monitor = new Agent();
			
			resp = monitor.getAgents(null, OutputType.JSON);
			System.out.println(resp);
			JSONArray agents = new JSONArray(resp.getResponseText());
			Integer agentId = agents.getJSONObject(0).getInt("id");
			resp = monitor.deleteAgents(new Integer[]{7826}, null);
			System.out.println(resp);
			resp = monitor.getAllAgentsSnapshot(0, Platform.LINUX, null, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getAgentSnapshot(0, null, agentId, OutputType.XML); //us-wst vm_eu_bkp US-WEST-ONE-TIME transeventdb
			System.out.println(resp);
			resp = monitor.getAgentInfo(agentId, false, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getInternalMonitors(null, null, "process,cpu,memory,drive,load", OutputType.XML);
			System.out.println(resp);
			monitor.downloadAgent("linux32", "D://mtis.tar.gz");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
