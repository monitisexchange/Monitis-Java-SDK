package org.monitis.api.monitor;

import java.util.HashMap;

import org.monitis.api.APIObject;
import org.monitis.beans.Response;
import org.monitis.enums.OutputType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.Constants;

/**
 * 
 * @author ngaspary
 *
 */
public class CloudInstance extends APIObject{

	public CloudInstance(String apiKey, String secretKey){
		super(apiKey, secretKey, Constants.API_URL);
	}
	
	public CloudInstance(){
	}
	
	public enum CloudAction{
		cloudInstances(),
		cloudInstanceInfo();
	}
	
	public Response getInstances(OutputType output, Integer timezoneoffset) throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if(timezoneoffset != null) params.put("timezoneoffset", timezoneoffset);
		Response resp = makeGetRequest(CloudAction.cloudInstances, params);
		return resp;
	}
	
	public Response getCloudInstanceInfo(Integer timezone, String type, Integer instanceId, OutputType output)  throws MonitisException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		if (timezone != null) params.put("timezone", timezone);
		params.put("type", type);
		params.put("instanceId", instanceId);
		Response resp = makeGetRequest(CloudAction.cloudInstanceInfo, params);
		return resp;
	}
	
	public static void main(String[] args) {
		try {
			CloudInstance cloud = new CloudInstance();
			Response resp;
			resp = cloud.getInstances(OutputType.XML, 0);
			System.out.println(resp);
			resp = cloud.getCloudInstanceInfo(0, "rackspace", 5301, OutputType.XML);
			System.out.println(resp);
		} catch (MonitisException e) {
			e.printStackTrace();
		}
	}
	
	

}
