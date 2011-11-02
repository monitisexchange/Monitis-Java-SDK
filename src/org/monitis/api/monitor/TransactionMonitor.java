package org.monitis.api.monitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.monitis.beans.LocationInterval;
import org.monitis.beans.Response;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;
import org.monitis.utils.StringUtils;

public class TransactionMonitor extends BaseMonitor{

	public enum TransactionAction{
		transactionLocations(),
		transactionTests(),
		transactionTestInfo(),
		transactionTestResult(),
		transactionStepResult(),
		transactionStepCapture(),
		transactionStepNet(),
		addTransactionMonitor,
		editTransactionMonitor,
		suspendTransactionMonitor,
		activateTransactionMonitor,
		topTransaction,
		transactionSnapshot;
	}
	public TransactionMonitor(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public TransactionMonitor(){
	}
	
	protected Enum getAction(MonitorAction action) throws MonitisException{
		switch(action){
			case getMonitors: return TransactionAction.transactionTests;	
			case getMonitorInfo: return  TransactionAction.transactionTestInfo;
			case getMonitorResults: return  TransactionAction.transactionTestResult;
			case suspendMonitors: return TransactionAction.suspendTransactionMonitor;
			case activateMonitors: return TransactionAction.activateTransactionMonitor;
		}
		throw new MonitisException("Action is not supported");
	}
	
	protected String getMonitorIdString(){
		return "monitorId";
	}
	
	
	public Response getTransactionStepResult(OutputType output, Integer resultId, int year, int month, int day) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("resultId", resultId);
		params.put("year", year);
		params.put("month", month);
		params.put("day", day);
		return makeGetRequest(TransactionAction.transactionStepResult, params);
	}
	
	public Response getTransactionStepNet(OutputType output, Integer resultId, int year, int month, int day) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("resultId", resultId);
		params.put("year", year);
		params.put("month", month);
		params.put("day", day);
		return makeGetRequest(TransactionAction.transactionStepNet, params);
	}
	
	public Response getTransactionStepCapture(Integer resultId, Integer monitorId, String fileName) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("resultId", resultId);
		params.put("monitorId", monitorId);
		return makeGetRequest(TransactionAction.transactionStepCapture, params, fileName);
	}
	
	public Response getTops(String tag, Integer limit, OutputType output) throws MonitisException {
		return getTops(TransactionAction.topTransaction, tag, limit, false, output);
	}
	
	public Response getLocations(OutputType output) throws MonitisException {
		return makeGetRequest(TransactionAction.transactionLocations, output);
	}
	
	public Response addMonitor(String name, String tag, String url, String data, 
			List<LocationInterval> locationIds, Float uptimeSLA, Float responseSLA) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("tag", tag);
		params.put("url", url);
		params.put("data", data);
		params.put("locationIds", StringUtils.urlEncode(StringUtils.join(locationIds, ",")));
		if(uptimeSLA!=null) params.put("uptimeSLA", uptimeSLA);
		if(uptimeSLA!=null) params.put("responseSLA", responseSLA);
		return makePostRequest(TransactionAction.addTransactionMonitor, params);
	}
	
	public Response editMonitor(Integer monitorId, String name, String tag, String url, String data, 
			List<LocationInterval> locationIds, Float uptimeSLA, Float responseSLA) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("monitorId", monitorId);
		if(name!=null) params.put("name", name);
		if(tag!=null) params.put("tag", tag);
		if(url!=null) params.put("url", url);
		if(data!=null) params.put("data", data);
		if(locationIds!=null) params.put("locationIds", StringUtils.urlEncode(StringUtils.join(locationIds, ",")));
		if(uptimeSLA!=null) params.put("uptimeSLA", uptimeSLA);
		if(uptimeSLA!=null) params.put("responseSLA", responseSLA);
		return makePostRequest(TransactionAction.editTransactionMonitor, params);
	}
	
	public Response getSnapshot(OutputType output, List<Integer> locationIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (locationIds != null){
			params.put("locationIds", StringUtils.join(locationIds, ","));
		}
		return makeGetRequest(TransactionAction.transactionSnapshot, params);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Response resp;
		try {
			TransactionMonitor monitor = new TransactionMonitor();
			List<LocationInterval> locIntervals = new ArrayList<LocationInterval>();
			locIntervals.add(new LocationInterval(1,5));
			locIntervals.add(new LocationInterval(5,10));
			String line = "";
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Documents and Settings\\ngaspary\\Desktop\\trans.txt"));
			String data = "";
			while((line = reader.readLine()) != null){
				data += line;
			}
			resp = monitor.addMonitor("odnoklassniki_transaon", "odnoklass", "odnoklasniki.ru", data, locIntervals, (float)56.9, (float)45.9);
			System.out.println(resp);
			Integer monitorId = new JSONObject(resp.getResponseText()).getJSONObject("data").getInt("testId");
			resp = monitor.getMonitors(OutputType.XML);
			System.out.println(resp);
			resp = monitor.getMonitorInfo(monitorId, OutputType.XML);
			System.out.println(resp);
			List<Integer> locIds = new ArrayList<Integer>();
			locIds.add(19);
			locIds.add(16);
			resp = monitor.getSnapshot(OutputType.XML, null);
			System.out.println(resp);
			resp = monitor.getMonitorResults(monitorId, 2011, 6, 10, 240, locIds, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getTransactionStepResult(OutputType.JSON, 2617, 2011, 5, 30);
			System.out.println(resp);
			resp = monitor.getTransactionStepCapture(25889106, 2617, "D:/a.png");
			System.out.println(resp);
			resp = monitor.getTransactionStepNet(OutputType.JSON, 23343082, 2011, 5, 13);
			System.out.println(resp);
			resp = monitor.activateMonitors(new Integer[]{monitorId}, null);
			System.out.println(resp);
			resp = monitor.suspendMonitors(new Integer[]{monitorId}, null);
			System.out.println(resp);
			resp = monitor.getTops(null, null, OutputType.XML);
			System.out.println(resp);
			resp = monitor.getLocations(OutputType.XML);
			System.out.println(resp);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
