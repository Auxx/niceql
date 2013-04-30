package com.grilledmonkey.niceql.structs;

import com.grilledmonkey.niceql.interfaces.SqlColumn;

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

public class Column extends SqlColumn {
	private final String name, type;
	private final boolean notNull, isPrimary;
	private final int intType;

	/**
	 * Creates PRIMARY KEY for table definition
	 */
	public Column() {
		this.name = PRIMARY_KEY;
		this.type = INTEGER;
		this.intType = TYPE_INTEGER;
		this.notNull = false;
		this.isPrimary = true;
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

		if(INTEGER.equals(type))
			intType = TYPE_INTEGER;
		else if(TEXT.equals(type))
			intType = TYPE_TEXT;
		else if(REAL.equals(type))
			intType = TYPE_REAL;
		else if(BLOB.equals(type))
			intType = TYPE_BLOB;
		else
			intType = TYPE_UNDEFINED;
	}

	/**
	 * Returns SQL statement for current column.
	 *
	 * @return generated SQL code
	 */
	public String getSql() {
		StringBuilder result = new StringBuilder(getNameEscaped());
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

	public boolean isNotNull() {
		return(notNull);
	}

	public boolean isPrimary() {
		return(isPrimary);
	}

	public String getNameEscaped() {
		return(escape(name));
	}

}
