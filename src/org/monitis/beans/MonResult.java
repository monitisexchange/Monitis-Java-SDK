package org.monitis.beans;

import org.monitis.exception.MonitisException;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public class MonResult extends URLObject {

	private String paramName;
	private String paramValue;
	
	
	/**
	 * 
	 * @param monitorId
	 * @param paramName
	 * @param paramValue
	 * @param checkTime
	 */
	public MonResult(String paramName, String paramValue) {
		super();
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	/**
	 * 
	 * @param urlString
	 * @throws MonitisException
	 */
	public MonResult(String urlString) throws MonitisException {
		super();
		String[] fields = urlString.split(KEY_VALUE_SEPARATOR);
		this.paramName = StringUtils.urlDecode(fields[0]);
		this.paramValue = StringUtils.urlDecode(fields[1]);
	}
	
	/**
	 * 
	 */
	public String toUrlString() throws MonitisException {
		return
			StringUtils.urlEncode(paramName)+KEY_VALUE_SEPARATOR+
			StringUtils.urlEncode(paramValue);
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
