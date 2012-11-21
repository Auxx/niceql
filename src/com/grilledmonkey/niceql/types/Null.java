package com.grilledmonkey.niceql.types;

public class Null implements DataType {
	public Null() {
		// Nothing to do here (:
	}
	
	@Override
	public String getSql() {
		return("NULL");
	}
}
