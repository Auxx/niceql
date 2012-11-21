package com.grilledmonkey.niceql.types;

public class Integer implements DataType {
	private int value;
	
	public Integer() {
		// Nothing to do here (:
	}
	
	public Integer(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return(value);
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String getSql() {
		return(String.valueOf(value));
	}
}
