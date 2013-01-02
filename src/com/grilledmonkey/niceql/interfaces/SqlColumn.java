package com.grilledmonkey.niceql.interfaces;

/**
 * Abstract class representing SQLite column. Descendants may
 * initialize the way they want and must implement getters to retrieve
 * column related data.
 *
 * @author Aux
 *
 */

public abstract class SqlColumn implements SqlStatement {
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
	 * Returns name of column as it was passed in constructor.
	 *
	 * @return name of column
	 */
	public abstract String getName();

	/**
	 * Returns type of column as it was passed in constructor.
	 *
	 * @return type of column
	 */
	public abstract String getType();

	/**
	 * Returns type of column as it was passed in constructor as int value.
	 * For switches and arrays (:
	 *
	 * @return numerical type of column
	 */
	public abstract int getIntType();

	/**
	 * Returns name of column in SQLite-escaped format.
	 *
	 * @return escaped column name
	 */
	public abstract String getNameEscaped();

	/**
	 * Returns TRUE if column NOT NULL.
	 *
	 * @return NOT NULL state
	 */
	public abstract boolean isNotNull();

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

}
