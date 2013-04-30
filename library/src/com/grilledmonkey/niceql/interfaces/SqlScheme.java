package com.grilledmonkey.niceql.interfaces;

import java.util.List;

import android.content.ContentValues;

/**
 * Interface representing SQLite scheme. Descendants may
 * initialize the way they want and must implement getters to retrieve
 * table related data. Scheme contains table definition, arbitrary SQL
 * for initialization and seeds.
 *
 * @author Aux
 *
 */

public interface SqlScheme extends SqlMultiStatement {

	/**
	 * Returns scheme version.
	 *
	 * @return scheme version
	 */
	public int getVersion();

	/**
	 * Returns list of associated tables.
	 *
	 * @return list of tables
	 */
	public List<SqlTable> getTables();

	/**
	 * Returns list of arbitrary SQL statements.
	 *
	 * @return list of SQL statements
	 */
	public List<String> getArbitrarySql();

	/**
	 * Returns list of associated seeds from all tables. Use SQLiteDatabase
	 * to insert them.
	 *
	 * @return list of seeds
	 */
	public List<ContentValues> getSeeds();

	/**
	 * Adds new table to scheme.
	 *
	 * @param table to add
	 */
	public void addTable(SqlTable table);

	/**
	 * Adds new arbitrary SQL statement needed for proper initialization.
	 *
	 * @param sql string containing single SQL statement
	 */
	public void addArbitrarySql(String sql);

}
