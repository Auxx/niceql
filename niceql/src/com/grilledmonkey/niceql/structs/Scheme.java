package com.grilledmonkey.niceql.structs;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;

import com.grilledmonkey.niceql.interfaces.SqlScheme;
import com.grilledmonkey.niceql.interfaces.SqlTable;

/**
 * Instances of this class represent definition for single SQLite scheme with
 * all tables, arbitrary SQL statements and seeds. Seeds are values to be inserted
 * upon table creation.
 * <p>
 * How to use:
 * <ol>
 * <li>create instance of Scheme;</li>
 * <li>populate scheme with tables and arbitrary statements (seeds will be taken from table definitions);</li>
 * <li>fetch SQL queries by getSql() to create scheme;</li>
 * <li>fetch all seeds by getSeeds() and insert into database with the help of SQLiteDatabase.</li>
 * </ol>
 * <p>
 * <b>Warning!</b> No error checks are performed!
 *
 * @author Aux
 *
 */
public class Scheme implements SqlScheme {
	private final int version;
	private final List<SqlTable> tables = new LinkedList<SqlTable>();
	private final List<String> sql = new LinkedList<String>();

	/**
	 * Creates new empty scheme with version 1.
	 */
	public Scheme() {
		this(1);
	}

	/**
	 * Creates new empty scheme with specified version.
	 *
	 * @param version of current scheme
	 */
	public Scheme(int version) {
		this.version = version;
	}

	@Override
	public int getVersion() {
		return(version);
	}

	/**
	 * Returns SQL code for current scheme. It will contain two sections merged in one list:
	 * <ol>
	 * <li>table definitions;</li>
	 * <li>arbitrary sql statements.</li>
	 * </ol>
	 * Use getSeeds() to get populated list of all associated seeds and insert
	 * them with instance of SQLiteDatabase.
	 *
	 * @return generated SQL code
	 */
	@Override
	public List<String> getSql() {
		List<String> result = new LinkedList<String>();
		for(SqlTable item: tables)
			result.addAll(item.getSql());
		result.addAll(sql);
		return(result);
	}

	@Override
	public List<SqlTable> getTables() {
		return(tables);
	}

	@Override
	public void addTable(SqlTable table) {
		tables.add(table);
	}

	/**
	 * <b>WARNING!</b>
	 * <p>
	 * This is broken at the moment. Need to rethink the way it should
	 * properly work. Right now just iterate through table definitions and
	 * get seeds from each table separately.
	 */
	@Override
	public List<ContentValues> getSeeds() {
		List<ContentValues> result = new LinkedList<ContentValues>();
		for(SqlTable item: tables)
			result.addAll(item.getSeeds());
		return(result);
	}

	@Override
	public List<String> getArbitrarySql() {
		return(sql);
	}

	@Override
	public void addArbitrarySql(String sql) {
		this.sql.add(sql);
	}

}
