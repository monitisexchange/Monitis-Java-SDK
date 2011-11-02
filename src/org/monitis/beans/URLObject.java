package org.monitis.beans;

import java.util.HashMap;
import java.util.List;

import org.monitis.exception.MonitisException;
import org.monitis.utils.StringUtils;

/**
 * 
 * @author ngaspary
 *
 */
public abstract class URLObject {
	
	public static final String DATA_SEPARATOR = ";"; 
	
	public static final String KEY_VALUE_SEPARATOR = ":"; 
	
	public abstract String toUrlString() throws MonitisException;
	
	public static String toUrlString(List<? extends URLObject> list) throws MonitisException{
		String str = "";
		for (URLObject obj : list){
			str += obj.toUrlString()+DATA_SEPARATOR;
		}
		if(str.length()>0){
			str = str.substring(0, str.length()-DATA_SEPARATOR.length());
		}
		return StringUtils.urlEncode(str);
	}
	
	public static String mapToURLString(HashMap map) throws MonitisException{
		String str = "";
		for (Object key : map.keySet()){
			str += StringUtils.urlEncode(key.toString())+KEY_VALUE_SEPARATOR+
				   StringUtils.urlEncode(map.get(key).toString())+DATA_SEPARATOR; 
		}
		if(str.length()>0){
			str = str.substring(0, str.length()-DATA_SEPARATOR.length());
		}
		return StringUtils.urlEncode(str);
	}
}
