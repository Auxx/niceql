package com.grilledmonkey.niceql.stucts;

public class Column {
	private final String name, type;
	private final boolean notNull;
	private final boolean isPrimary;
	private int intType;

	public static final String PRIMARY_KEY = "_id";
	public static final String INTEGER = "INTEGER";
	public static final String TEXT = "TEXT";
	public static final String REAL = "REAL";
	public static final String BLOB = "BLOB";

	public static final int TYPE_UNDEFINED = 0;
	public static final int TYPE_INTEGER = 1;
	public static final int TYPE_TEXT = 2;
	public static final int TYPE_REAL = 3;
	public static final int TYPE_BLOB = 4;

	public Column() {
		this.name = PRIMARY_KEY;
		this.type = INTEGER;
		this.notNull = false;
		this.isPrimary = true;
		syncType();
	}

	public Column(String name, String type, boolean notNull) {
		this.name = name;
		this.type = type;
		this.notNull = notNull;
		this.isPrimary = false;
		syncType();
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

	public String getName() {
		return(name);
	}

	public String getType() {
		return(type);
	}

	public int getIntType() {
		return(intType);
	}

	public String getNameEscaped() {
		return(escape(name));
	}

	public static String escape(String value) {
		return('"' + value + '"');
	}

	private void syncType() {
		if(INTEGER.equals(type))
			intType = TYPE_INTEGER;
		else if(TEXT.equals(type))
			intType = TYPE_TEXT;
		else if(REAL.equals(type))
			intType = TYPE_REAL;
		else if(BLOB.equals(type))
			intType = TYPE_BLOB;
	}
}
