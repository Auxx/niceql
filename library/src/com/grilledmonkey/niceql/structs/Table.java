package com.grilledmonkey.niceql.structs;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.text.TextUtils;

import com.grilledmonkey.niceql.interfaces.SqlColumn;
import com.grilledmonkey.niceql.interfaces.SqlIndex;
import com.grilledmonkey.niceql.interfaces.SqlTable;

/**
 * Instances of this class represent definition for single SQLite table with
 * all columns, optional indices and seeds. Seeds are values to be inserted
 * upon table creation. PRIMARY KEY is automatically added to list of
 * columns as a first entry if needed.
 * <p>
 * <b>Warning!</b> No error checks are performed!
 *
 * @author Aux
 *
 */
public class Table implements SqlTable {
	private final String name;
	private final List<SqlColumn> columns = new LinkedList<SqlColumn>();
	private final List<SqlIndex> indices = new LinkedList<SqlIndex>();
	private final List<ContentValues> seeds = new LinkedList<ContentValues>();
	private final List<ForeignKey> fks = new LinkedList<ForeignKey>();

	/**
	 * Creates table definition with PRIMARY KEY and name as specified.
	 *
	 * @param name of the table
	 */
	public Table(String name) {
		this(name, true);
	}

	/**
	 * Creates table definition with name and PRIMARY KEY as specified.
	 *
	 * @param name of the table
	 * @param withPrimaryKey TRUE if PK is needed
	 */
	public Table(String name, boolean withPrimaryKey) {
		this.name = name;
		if(withPrimaryKey)
			columns.add(new Column());
	}

	public String getName() {
		return(name);
	}

	public void addColumn(SqlColumn column) {
		columns.add(column);
	}

	public void addIndex(SqlIndex index) {
		indices.add(index);
	}

	public void addSeed(ContentValues seed) {
		seeds.add(seed);
	}

	public List<SqlColumn> getColumns() {
		return(columns);
	}

	public List<SqlIndex> getIndices() {
		return(indices);
	}

	public List<ContentValues> getSeeds() {
		return(seeds);
	}

	/**
	 * Returns SQL code to be used to create current table with full
	 * table definition, indices definitions but <b>without</b> seeds.
	 * It is much better to use SQLiteDatabase to insert values, so use
	 * getSeeds() with it!
	 */
	public List<String> getSql() {
		List<String> queryList = new LinkedList<String>();

		StringBuilder result = new StringBuilder("CREATE TABLE " + name + "(");

		int size = columns.size();
		String[] columnSql = new String[size];
		for(int i = 0; i < size; i++)
			columnSql[i] = columns.get(i).getSql();
		result.append(TextUtils.join(", ", columnSql));

		if(fks.size() > 0) {
			for(ForeignKey fk: fks) {
				String sql = fk.getSql();
				if(!TextUtils.isEmpty(sql)) {
					result.append(", ").append(sql);
				}
			}
		}

		result.append(");");
		queryList.add(result.toString());

		if(indices.size() > 0) {
			size = indices.size();
			for(int i = 0; i < size; i++)
				queryList.add(indices.get(i).getSql());
		}

		return(queryList);
	}

	@Override
	public void addForeignKey(ForeignKey foreignKey) {
		fks.add(foreignKey);
	}

	@Override
	public List<ForeignKey> getForeignKeys() {
		return(fks);
	}
}
