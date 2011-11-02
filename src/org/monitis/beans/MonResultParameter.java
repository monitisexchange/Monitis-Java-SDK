package org.monitis.beans;

import java.util.ArrayList;
import java.util.List;

import org.monitis.enums.DataType;
import org.monitis.exception.MonitisException;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public class MonResultParameter extends URLObject {

	private String name;
	private String displayName;
	private String uom;
	private DataType dataType;
	
	/**
	 * 
	 * @param name
	 * @param displayName
	 * @param uom
	 * @param dataType
	 */
	public MonResultParameter(String name, String displayName, String uom, DataType dataType) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.uom = uom;
		this.dataType = dataType;
	}
	
	/**
	 * 
	 * @param urlString
	 * @throws MonitisException
	 */
	public MonResultParameter(String urlString) throws MonitisException {
		super();
		String[] fields = urlString.split(KEY_VALUE_SEPARATOR);
		this.name = StringUtils.urlDecode(fields[0]);
		this.displayName = StringUtils.urlDecode(fields[1]);
		this.uom = StringUtils.urlDecode(fields[2]);
		this.dataType = DataType.valueOf(Integer.parseInt(fields[3]));
	}

	public String toUrlString() throws MonitisException {
		return StringUtils.urlEncode(name)+KEY_VALUE_SEPARATOR+
		   StringUtils.urlEncode(displayName)+KEY_VALUE_SEPARATOR+
		   StringUtils.urlEncode(uom)+KEY_VALUE_SEPARATOR+
		   dataType.getId();
	}

	public static List<MonResultParameter> getObjectList(String str) throws MonitisException{
		String [] urlStringParts = str.split(URLObject.DATA_SEPARATOR);
		List<MonResultParameter> params = new ArrayList<MonResultParameter>();
		for (String urlStr : urlStringParts){
			params.add(new MonResultParameter(urlStr));
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
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}


	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

}
