package com.grilledmonkey.niceql.types;

public class Real implements DataType {
	private double value;
	
	public Real() {
		// Nothing to do here (:
	}
	
	public Real(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return(value);
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String getSql() {
		return(String.valueOf(value));
	}
}
