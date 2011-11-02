package org.monitis.beans;

import java.util.ArrayList;
import java.util.List;

import org.monitis.beans.URLObject;
import org.monitis.enums.DataType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.StringUtils;


/**
 * 
 * @author ngaspary
 *
 */
public class MonitorParameter extends URLObject{
	
	private String name;
	private String displayName = "";
	private String value;
	private DataType dataType = DataType.STRING;
	private boolean isHidden;
	
	/**
	 * 
	 * @param name
	 * @param displayName
	 * @param value
	 * @param dataType
	 * @param isHidden
	 */
	public MonitorParameter(String name, String displayName, String value,
			DataType dataType, boolean isHidden) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.value = value;
		this.dataType = dataType;
		this.isHidden = isHidden;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	public MonitorParameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public MonitorParameter(String urlString) throws MonitisException {
		super();
		String[] fields = urlString.split(KEY_VALUE_SEPARATOR);
		this.name = StringUtils.urlDecode(fields[0]);
		this.displayName = StringUtils.urlDecode(fields[1]);
		this.value = StringUtils.urlDecode(fields[2]);
		this.dataType = DataType.valueOf(Integer.parseInt(fields[3]));
		this.isHidden = Boolean.parseBoolean(fields[4]);
	}
	
	public String toUrlString() throws MonitisException{
		return StringUtils.urlEncode(name)+KEY_VALUE_SEPARATOR+
			   StringUtils.urlEncode(displayName)+KEY_VALUE_SEPARATOR+
			   StringUtils.urlEncode(value)+KEY_VALUE_SEPARATOR+
			   dataType.getId()+KEY_VALUE_SEPARATOR+
			   isHidden;
	}
	
	public static List<MonitorParameter> getObjectList(String str) throws MonitisException{
		String [] urlStringParts = str.split(URLObject.DATA_SEPARATOR);
		List<MonitorParameter> params = new ArrayList<MonitorParameter>();
		for (String urlStr : urlStringParts){
			params.add(new MonitorParameter(urlStr));
		}
		return params;
	}
	
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @return the dataType
	 */
	public DataType getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the isHidden
	 */
	public boolean isHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden the isHidden to set
	 */
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
}
