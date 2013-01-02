package com.grilledmonkey.niceql.stucts;

import com.grilledmonkey.niceql.interfaces.SqlStatement;

/**
 * Instances of this class represent definition for single SQLite table column.
 * Constructor without parameters creates PRIMARY KEY as defined in SQLite.
 * <p>
 * <b>Warning!</b> No error checks are performed!
 * <p>
 * Supported types:
 * <ul>
 * <li>INTEGER</li>
 * <li>TEXT</li>
 * <li>REAL</li>
 * <li>BLOB</li>
 * </ul>
 * <p>
 * All columns should have name and type. NOT NULL is optional.
 *
 * @author Aux
 *
 */

public class Column implements SqlStatement {
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

	/**
	 * Creates PRIMARY KEY for table definition
	 */
	public Column() {
		this.name = PRIMARY_KEY;
		this.type = INTEGER;
		this.notNull = false;
		this.isPrimary = true;
		syncType();
	}

	/**
	 * Creates column of specified name and type setting NOT NULL attribute to FALSE.
	 * <p>
	 * No error checks are performed, so you can use whatever strings you like.
	 * This is kind of future-compliant, but error-prone. It is advised to use
	 * type constants provided by class.
	 *
	 * @param name is the name of column, which should follow SQLite rules
	 * @param type SQLite supported type of column
	 */
	public Column(String name, String type) {
		this(name, type, false);
	}

	/**
	 * Creates column of specified name, type and NOT NULL attribute.
	 * <p>
	 * No error checks are performed, so you can use whatever strings you like.
	 * This is kind of future-compliant, but error-prone. It is advised to use
	 * type constants provided by class.
	 *
	 * @param name is the name of column, which should follow SQLite rules
	 * @param type SQLite supported type of column
	 * @param notNull indicates if column is NOT NULL
	 */
	public Column(String name, String type, boolean notNull) {
		this.name = name;
		this.type = type;
		this.notNull = notNull;
		this.isPrimary = false;
		syncType();
	}

	/**
	 * Returns SQL statement for current column.
	 *
	 * @return generated SQL code
	 */
	public String getSql() {
		StringBuilder result = new StringBuilder(name);
		result.append(" " + type);
		if(isPrimary)
			result.append("  PRIMARY KEY AUTOINCREMENT");
		else if(notNull)
			result.append(" NOT NULL");
		return(result.toString());
	}

	/**
	 * Returns name of column as it was passed in constructor.
	 *
	 * @return name of column
	 */
	public String getName() {
		return(name);
	}

	/**
	 * Returns type of column as it was passed in constructor.
	 *
	 * @return type of column
	 */
	public String getType() {
		return(type);
	}

	/**
	 * Returns type of column as it was passed in constructor as int value.
	 * For switches and arrays (:
	 *
	 * @return numerical type of column
	 */
	public int getIntType() {
		return(intType);
	}

	/**
	 * Returns name of column in SQLite-escaped format.
	 *
	 * @return escaped column name
	 */
	public String getNameEscaped() {
		return(escape(name));
	}

	/**
	 * Escapes any string like the name of column. Input string is NOT checked
	 * for correct syntax.
	 *
	 * @param value any string
	 * @return escaped value
	 */
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
