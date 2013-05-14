package com.grilledmonkey.niceql.interfaces;

import java.util.List;

import android.content.ContentValues;

import com.grilledmonkey.niceql.structs.ForeignKey;

/**
 * Interface representing SQLite table. Descendants may
 * initialize the way they want and must implement getters to retrieve
 * table related data.
 *
 * @author Aux
 *
 */

public interface SqlTable extends SqlMultiStatement {

	/**
	 * Returns name of table as it was passed in constructor.
	 *
	 * @return name of table
	 */
	public String getName();

	/**
	 * Returns list of columns associated with current table.
	 *
	 * @return list of columns
	 */
	public List<SqlColumn> getColumns();

	/**
	 * Returns list of indices associated with current table.
	 *
	 * @return list of indices
	 */
	public List<SqlIndex> getIndices();

	/**
	 * Returns list of seeds associated with current table.
	 *
	 * @return list of seeds
	 */
	public List<ContentValues> getSeeds();

	/**
	 * Adds new column to table definition. <b>NO error checks performed!</b>
	 *
	 * @param column
	 */
	public void addColumn(SqlColumn column);

	/**
	 * Adds new index to table definition. <b>NO error checks performed!</b>
	 *
	 * @param index
	 */
	public void addIndex(SqlIndex index);

	/**
	 * Adds new seed value to table definition. <b>NO error checks performed!</b>
	 *
	 * @param seed
	 */
	public void addSeed(ContentValues seed);

	/**
	 * Adds new foreign key to table definition. <b>NO error checks performed!</b>
	 * 
	 * @param foreignKey
	 */
	public void addForeignKey(ForeignKey foreignKey);

	/**
	 * Returns a list of all foreign keys.
	 * 
	 * @return list
	 */
	public List<ForeignKey> getForeignKeys();

}
