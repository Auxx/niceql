package com.grilledmonkey.niceql.interfaces;

/**
 * Interface representing SQLite index. Descendants may
 * initialize the way they want and must implement getters and setters to
 * operate index related data.
 *
 * @author Aux
 *
 */

public interface SqlIndex extends SqlStatement {

	/**
	 * Adds specified column to index. Column may be any SqlColumn descendant.
	 *
	 * @param column to add to index
	 */
	public void addColumn(SqlColumn column);

	/**
	 * Adds specified column to index. Indices only care about column names,
	 * so you can add it with string. Syntax is NOT checked!
	 *
	 * @param column to add to index
	 */
	public void addColumn(String column);

	/**
	 * Returns name of index as it was passed in constructor.
	 *
	 * @return name of index
	 */
	public String getName();

	/**
	 * Returns name of table for which index is created.
	 *
	 * @return name of related table
	 */
	public String getTableName();

	/**
	 * Returns TRUE if index is unique.
	 * @return uniqueness of index
	 */
	public boolean isUnique();

}
