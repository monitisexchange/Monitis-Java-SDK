package org.monitis.beans;

import java.io.Serializable;
import java.util.Date;

import org.monitis.utils.StringUtils;
import org.monitis.utils.TimeUtility;

/**
 * 
 * @author ngaspary
 *
 * Bean used for keeping response of an http request.
 * Includes reponse text, status code and creation date.
 */
public class Response implements Serializable{

	private int statusCode;
	private String responseText;
	private Date created_on;

	/**
	 * 
	 * @return
	 */
	public int getStatusCode() {
		return statusCode;
	}
	
	/**
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getResponseText(){
		return responseText;
	}
	
	/**
	 * 
	 * @param responseText
	 * @param statusCode
	 */
	public Response(String responseText, int statusCode){
		this.responseText = responseText;
		this.statusCode = statusCode;
		created_on = TimeUtility.getNowByGMT().getTime();
	}
	
	/**
	 * 
	 * @param serealizedText: string returned by toString() method
	 */
	public Response(String serealizedText){
		String [] parts = serealizedText.split("\t");
		long time = Long.parseLong(parts[0]);
		created_on = new Date();
		created_on.setTime(time);
		statusCode = Integer.parseInt(parts[1]);
		responseText = StringUtils.arrayToString(parts, "\t", 2, parts.length);
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getCreatedOn(){
		return created_on;
	}
	
	/**
	 * toString is overwriten to allow to save this object in redable text format 
	 */	
	public String toString() {
		return created_on.getTime()+"\t"+statusCode+"\t"+responseText;
	}
}