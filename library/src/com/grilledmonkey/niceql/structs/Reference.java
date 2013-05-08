package com.grilledmonkey.niceql.structs;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.grilledmonkey.niceql.interfaces.SqlColumn;
import com.grilledmonkey.niceql.interfaces.SqlReference;

public class Reference implements SqlReference {
	private final String table, options;
	private final List<Object> columns = new ArrayList<Object>();

	public Reference(String table) {
		this(table, null);
	}

	public Reference(String table, String options) {
		this.table = table;
		this.options = options;
	}

	public void addColumn(SqlColumn column) {
		columns.add(column);
	}

	public void addColumn(String column) {
		columns.add(column);
	}

	public String getTableName() {
		return(table);
	}

	public String getOptions() {
		return(options);
	}

	/**
	 * Returns SQL statement for current index.
	 *
	 * @return generated SQL code
	 */
	public String getSql() {
		if(columns.size() == 0) {
			return(null);
		}

		StringBuilder result = new StringBuilder("REFERENCES ").append(table).append("(");

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

		result.append(")");
		if(!TextUtils.isEmpty(options)) {
			result.append(" ").append(options);
		}

		return(result.toString());
	}
}
