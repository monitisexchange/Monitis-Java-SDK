package org.monitis.enums;


/**
 * 
 * @author ngaspary
 *	
 *	defines types used in Preference enum
 */
public enum DataType {
	BOOLEAN(1),
	INTEGER(2),
	STRING(3), 
	FLOAT(4);
	
	private int id;
	
	DataType(int id){
		this.id = id;
	}
	
	public Object getValue(Object value){
		if(value == null) return null;
		String str = value.toString();
		switch(this){
			case BOOLEAN: return Boolean.parseBoolean(str);
			case INTEGER: return Integer.parseInt(str);
			case STRING: return str;
			case FLOAT: return Float.parseFloat(str);
		}
		return value;
	}

	public static DataType valueOf(int id) {
		for(DataType dataType : values()){
			if (dataType.id == id) return dataType;
		}
		return null;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}