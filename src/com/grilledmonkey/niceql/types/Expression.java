package com.grilledmonkey.niceql.types;

public class Expression implements DataType {
	private String value;
	
	public Expression() {
		// Nothing to do here (:
	}
	
	public Expression(String value) {
		this.value = value;
	}

	public String getValue() {
		return(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getSql() {
		return(value);
	}
}
