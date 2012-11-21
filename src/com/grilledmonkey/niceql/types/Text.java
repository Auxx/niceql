package com.grilledmonkey.niceql.types;

public class Text implements DataType {
	private String value;
	protected String escapeCache;
	
	public Text() {
		// Nothing to do here (:
	}
	
	public Text(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return(value);
	}

	public void setValue(String value) {
		this.value = value;
		escapeCache = null;
	}

	protected String escape() {
		if(escapeCache == null) {
			if(value == null)
				escapeCache = "NULL";
			else
				escapeCache = value.replace("'", "''");
		}
		return(escapeCache);
	}
	
	@Override
	public String getSql() {
		return("'" + escape() + "'");
	}
}
