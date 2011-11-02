package org.monitis.api.monitor;

import java.util.HashMap;
import java.util.List;

import org.monitis.beans.Response;
import org.monitis.enums.MonitorAction;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public abstract class InternalMonitor extends BaseMonitor{

	public enum IntMonitorAction{
		deleteInternalMonitors;
	}
	
	public enum Type{
		PROCESS(1),
		DRIVE(2),
		MEMORY(3),
		HTTP(4),
		PING(5),
		LOAD_AVERAGE(6),
		CPU(7);
		
		private int id;
		
		Type(int id){
			this.id = id;
		}
		
		public int getId(){
			return id;
		}
	}
	
	private Type type;
	
	public InternalMonitor(String apiKey, String secretKey, String apiUrl, Type type){
		super(apiKey, secretKey, apiUrl);
		this.type = type;
	}
	
	public InternalMonitor(Type type){
		this.type = type;
	}
	
	public Type getType(){
		return type;
	}

	public Response deleteMonitors(Integer [] testIds) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type.id);
		params.put("testIds", StringUtils.join(testIds, ","));
		Response resp = makePostRequest(IntMonitorAction.deleteInternalMonitors, params);
		return resp;
	}
	
	public Response getMonitors(Integer agentId, OutputType output) throws MonitisException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("output", output);
		Response resp = makeGetRequest(getAction(MonitorAction.getMonitors), params);
		return resp;
	}
	
}
