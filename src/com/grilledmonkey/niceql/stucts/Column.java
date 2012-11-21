package com.grilledmonkey.niceql.stucts;

public class Column {
	private final String name, type;
	private final boolean notNull;
	private final boolean isPrimary;

	public static final String PRIMARY_KEY = "_id";
	public static final String INTEGER = "INTEGER";
	public static final String TEXT = "TEXT";
	public static final String REAL = "REAL";
	public static final String BLOB = "BLOB";

	public Column() {
		this.name = PRIMARY_KEY;
		this.type = INTEGER;
		this.notNull = false;
		this.isPrimary = true;
	}

	public Column(String name, String type, boolean notNull) {
		this.name = name;
		this.type = type;
		this.notNull = notNull;
		this.isPrimary = false;
	}

	public String getSql() {
		StringBuilder result = new StringBuilder(name);
		result.append(" " + type);
		if(isPrimary)
			result.append("  PRIMARY KEY AUTOINCREMENT");
		else if(notNull)
			result.append(" NOT NULL");
		return(result.toString());
	}
}
