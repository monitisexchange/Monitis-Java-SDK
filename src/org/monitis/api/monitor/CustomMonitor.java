package org.monitis.api.monitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.monitis.beans.MonResult;
import org.monitis.beans.MonResultParameter;
import org.monitis.beans.MonitorParameter;
import org.monitis.beans.Response;
import org.monitis.beans.URLObject;
import org.monitis.enums.DataType;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;
import org.monitis.utils.TimeUtility;

/**
 * 
 * @author ngaspary
 *
 */
public class CustomMonitor extends BaseMonitor{
	
	public enum CustomMonitoraction{
		addAdditionalResults,
		getAdditionalResults
	}
	
	/**
	 * 
	 * @param apiKey
	 * @param secretKey
	 * @throws MonitisException
	 */
	public CustomMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.CUSTOM_MONITOR_API_URL);
	}
	
	public CustomMonitor(){
		super(Constants.CUSTOM_MONITOR_API_URL);
	}
	
	/**
	 * 
	 * @param name
	 * @param tag
	 * @param monitorParams
	 * @param resultParams
	 * @return
	 * @throws MonitisException
	 */
	public Response addMonitor(Integer customUserAgentId, String name, String tag, String type, List<MonitorParameter> monitorParams, 
			List<MonResultParameter> resultParams, List<MonResultParameter> additionalResultParams) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		if (monitorParams != null) {
			params.put("monitorParams", URLObject.toUrlString(monitorParams));
		}
		params.put("resultParams", URLObject.toUrlString(resultParams));
		if (additionalResultParams != null){
			params.put("additionalResultParams", URLObject.toUrlString(additionalResultParams));
		}
		if(customUserAgentId!= null) params.put("customUserAgentId", customUserAgentId);
		params.put("name", StringUtils.urlEncode(name));
		params.put("tag", StringUtils.urlEncode(tag));
		if(type != null) params.put("type", StringUtils.urlEncode(type));
		Response resp = makePostRequest(MonitorAction.addMonitor, params);
		return resp;
	}
	
	/**
	 * 
	 * @param monitorId
	 * @param name
	 * @param tag
	 * @param monitorParams
	 * @return
	 * @throws MonitisException
	 */
	public Response editMonitor(Integer monitorId, String name, String tag, 
			List<MonitorParameter> monitorParams) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		if (monitorParams != null) {
			params.put("monitorParams", URLObject.toUrlString(monitorParams));
		}
		params.put("name", StringUtils.urlEncode(name));
		params.put("monitorId", monitorId);
		params.put("tag", StringUtils.urlEncode(tag));
		Response resp = makePostRequest(MonitorAction.editMonitor, params);
		return resp;
	}
	
			
	/**
	 * 
	 * @param results
	 * @return
	 * @throws MonitisException
	 */
	public Response addResult(Integer monitorId, Long checktime, List<MonResult> results) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("results", URLObject.toUrlString(results));
		params.put("monitorId", monitorId);
		params.put("checktime", checktime);
		Response resp = makePostRequest(MonitorAction.addResult, params);
		return resp;
	}
	
	/**
	 * 
	 * @param tag
	 * @param output
	 * @return
	 * @throws MonitisException
	 */
	public Response getMonitors(String tag, String type, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		if (tag != null){
			params.put("tag", StringUtils.urlEncode(tag));
		}
		if (type != null){
			params.put("type", StringUtils.urlEncode(type));
		}
		params.put("output", output);
		Response resp = makeGetRequest(MonitorAction.getMonitors, params);
		return resp;
	}
	
	/**
	 * 
	 * @param tag
	 * @param output
	 * @return
	 * @throws MonitisException
	 */
	public Response getAdditionalResults(Integer monitorId, Long checktime) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", monitorId);
		params.put("checktime", checktime);
		Response resp = makeGetRequest(CustomMonitoraction.getAdditionalResults, params);
		return resp;
	}
	
	/**
	 * 
	 * @param results
	 * @return
	 * @throws MonitisException
	 */
	public Response addAdditionalResults(Integer monitorId,  JSONArray jsArray, Long checktime) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", monitorId);
		params.put("results", jsArray.toString());
		params.put("checktime", checktime.toString());
		Response resp = makePostRequest(CustomMonitoraction.addAdditionalResults, params);
		return resp;
	}
	
	
	/**
	 * 
	 * @param monitorId
	 * @param output
	 * @param excludeHidden
	 * @return
	 * @throws MonitisException
	 */
	public Response getMonitorInfo(Integer monitorId, OutputType output, 
			boolean excludeHidden) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(getMonitorIdString(), monitorId);
		params.put("output", output);
		params.put("excludeHidden", excludeHidden);
		Response resp = makeGetRequest(getAction(MonitorAction.getMonitorInfo), params);
		return resp;
	}
	
	public static void main(String[] args) throws MonitisException, JSONException {
		
			CustomMonitor customMonitor = new CustomMonitor();
			
			Response response = null;
			//add monitor
			List<MonitorParameter> monitorParams = new ArrayList<MonitorParameter>();
			monitorParams.add(new MonitorParameter("url", "URL", "monitis.com", DataType.STRING, true));
			monitorParams.add(new MonitorParameter("steps", "Steps", "5", DataType.INTEGER, true));
						
			List<MonResultParameter> resultParams = new ArrayList<MonResultParameter>();
			
			resultParams.add(new MonResultParameter("response", "Response", "ms", DataType.INTEGER));
			resultParams.add(new MonResultParameter("status", "Status", "", DataType.STRING));
			
			
			List<MonResultParameter> addResultParams = new ArrayList<MonResultParameter>();
			addResultParams.add(new MonResultParameter("command", "Command", "", DataType.STRING));
			addResultParams.add(new MonResultParameter("duration", "Duration", "ms", DataType.INTEGER));
			
			response = customMonitor.addMonitor(null, "transaction_from_plugin_4", "transaction", "transaction", monitorParams, resultParams, addResultParams);
			System.out.println(response);
			Integer monitorId = new org.json.JSONObject(response.getResponseText()).getInt("data");
			
			//edit monitor
			monitorParams = new ArrayList<MonitorParameter>();
			monitorParams.add(new MonitorParameter("aaa", "a nn aaa", "78", DataType.INTEGER, true));
			response = customMonitor.editMonitor(monitorId, "log parser_with_ad", "logparser", monitorParams);
			System.out.println(response);
			
			//add result
			Long now = TimeUtility.getNowByGMT().getTime().getTime();
			List<MonResult> results = new ArrayList<MonResult>();
			results.add(new MonResult("response", "25"));
			results.add(new MonResult("status", "ok"));
			System.out.println(URLObject.toUrlString(results));
			response = customMonitor.addResult(monitorId, now, results);
			System.out.println(response);
			try {
				JSONArray addResults = new JSONArray("[{coomand:'open(montiis.com)',duration:10},{command:'click(myId)',duration:2}]");
				response = customMonitor.addAdditionalResults(monitorId, addResults, now);
				System.out.println(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response = customMonitor.getMonitors(null, "type :; typik", OutputType.XML);
			System.out.println(response);
			response = customMonitor.getMonitorInfo(monitorId, OutputType.XML, false);
			System.out.println(response);
			response = customMonitor.getMonitorResults(monitorId, 2011, 7, 2, 300, OutputType.XML);
			System.out.println(response);
			response = customMonitor.getAdditionalResults(monitorId, now);
			System.out.println(response);
			response = customMonitor.deleteMonitors(new Integer[]{monitorId});
			System.out.println(response);
	}
	
}
