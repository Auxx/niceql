package com.grilledmonkey.niceql.interfaces;

public interface SqlReference extends SqlStatement {

	/**
	 * Adds specified column to reference. Column may be any SqlColumn descendant.
	 *
	 * @param column to add to index
	 */
	public void addColumn(SqlColumn column);

	/**
	 * Adds specified column to reference. References only care about column names,
	 * so you can add it with string. Syntax is NOT checked!
	 *
	 * @param column to add to index
	 */
	public void addColumn(String column);

	/**
	 * Returns name of referenced table.
	 *
	 * @return name of related table
	 */
	public String getTableName();

	/**
	 * Returns options string set in the constructor
	 * 
	 * @return string containing options
	 */
	public String getOptions();

}
