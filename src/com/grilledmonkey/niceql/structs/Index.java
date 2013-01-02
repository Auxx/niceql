package com.grilledmonkey.niceql.structs;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.grilledmonkey.niceql.interfaces.SqlColumn;
import com.grilledmonkey.niceql.interfaces.SqlIndex;

/**
 * Instances of this class represent definition for single SQLite index
 * in a very simple way. Only name, table, columns and uniqueness can
 * be specified.
 *
 * @author Aux
 *
 */

public class Index implements SqlIndex {
	private final String name, table;
	private final boolean isUnique;
	private final List<Object> columns = new ArrayList<Object>();

	/**
	 * Creates empty index with specified attributes. Use addColumn() method
	 * to add columns to index.
	 *
	 * @param name of index
	 * @param table for which index is created
	 * @param isUnique indicates if index is unique
	 */
	public Index(String name, String table, boolean isUnique) {
		this.name = name;
		this.table = table;
		this.isUnique = isUnique;
	}

	/**
	 * Creates empty index with specified attributes. Index created this way
	 * will not be unique. Use addColumn() method to add columns to index.
	 *
	 * @param name of index
	 * @param table for which index is created
	 */
	public Index(String name, String table) {
		this(name, table, false);
	}

	public void addColumn(SqlColumn column) {
		columns.add(column);
	}

	public void addColumn(String column) {
		columns.add(column);
	}

	public String getName() {
		return(name);
	}

	public String getTableName() {
		return(table);
	}

	public boolean isUnique() {
		return(isUnique);
	}

	/**
	 * Returns SQL statement for current index.
	 *
	 * @return generated SQL code
	 */
	public String getSql() {
		if(columns.size() == 0)
			return(null);

		StringBuilder result = new StringBuilder("CREATE ");
		if(isUnique)
			result.append("UNIQUE ");
		result.append("INDEX ");
		result.append(name);
		result.append(" ON ");
		result.append(table);
		result.append(" (");

		int size = columns.size();
		String[] columnSql = new String[size];
		for(int i = 0; i < size; i++) {
			Object item = columns.get(i);
			if(item instanceof String)
				columnSql[i] = SqlColumn.escape((String)item);
			else if(item instanceof SqlColumn)
				columnSql[i] = ((SqlColumn)columns.get(i)).getNameEscaped();
		}
		result.append(TextUtils.join(", ", columnSql));

		result.append(");");
		return(result.toString());
	}

}
