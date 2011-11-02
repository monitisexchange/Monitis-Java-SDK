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
public class CustomAgentJob extends APIObject{

	public CustomAgentJob(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.CUSTOM_MONITOR_API_URL);
	}
	
	public CustomAgentJob(){
		super(Constants.CUSTOM_MONITOR_API_URL);
	}
	
	public enum CustomAgentJobAction{
		getJobs,
		addJob,
		editJob,
		deleteJob;
	}
	
	public Response getJobs(Integer agentId, OutputType output) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("output", output);
		Response resp = makeGetRequest(CustomAgentJobAction.getJobs, params);
		return resp;
	}
	
	
	public Response addJob(Integer agentId, Integer monitorId, String name, String periodicity, String jobParams) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", StringUtils.urlEncode(name));
		params.put("periodicity", StringUtils.urlEncode(periodicity));
		if(params != null) params.put("params", StringUtils.urlEncode(jobParams));
		if (monitorId != null) params.put("monitorId", monitorId);
		params.put("agentId", agentId);
		return makePostRequest(CustomAgentJobAction.addJob, params);
	}
	
	public Response editJob(Integer jobId, String name, String periodicity, String jobParams, Integer activeFlag) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", jobId);
		if(name != null) params.put("name", StringUtils.urlEncode(name));
		if(params != null) params.put("params", StringUtils.urlEncode(jobParams));
		if(activeFlag != null )params.put("activeFlag", activeFlag);
		if(periodicity != null )params.put("periodicity", StringUtils.urlEncode(periodicity));
		return makePostRequest(CustomAgentJobAction.editJob, params);
	}
	
	public Response deleteJobs(Integer [] jobIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("jobIds", StringUtils.join(jobIds, ","));
		return makePostRequest(CustomAgentJobAction.deleteJob, params);
	}
	
	
	public static void main(String[] args) throws JSONException {
		CustomAgentJob job;
		try {
			Response resp = null;
			job = new CustomAgentJob();
			Integer agentid = 43;
			resp = job.addJob(agentid, 1453, "my ob 5-2", "3 min interval", "url=monitiiiii.tu");
			System.out.println(resp);
			Integer id = new org.json.JSONObject(resp.getResponseText()).getInt("data");
			resp = job.getJobs(agentid, OutputType.XML);
			System.out.println(resp);
			resp = job.editJob(id, null, null, "url=monitis.com", 100);
			System.out.println(resp);
			resp = job.deleteJobs(new Integer[]{id});
			System.out.println(resp);

		} catch (MonitisException e) {
			e.printStackTrace();
		}
	}
	
	

}
