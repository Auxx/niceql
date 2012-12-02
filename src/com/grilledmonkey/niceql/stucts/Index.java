package com.grilledmonkey.niceql.stucts;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

public class Index {
	private final String name, table;
	private final boolean isUnique;
	private final List<Object> columns = new ArrayList<Object>();

	public Index(String name, String table, boolean isUnique) {
		this.name = name;
		this.table = table;
		this.isUnique = isUnique;
	}

	public Index(String name, String table) {
		this.name = name;
		this.table = table;
		this.isUnique = false;
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	public void addColumn(String column) {
		columns.add(column);
	}

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
				columnSql[i] = Column.escape((String)item);
			else if(item instanceof Column)
				columnSql[i] = ((Column)columns.get(i)).getNameEscaped();
		}
		result.append(TextUtils.join(", ", columnSql));

		result.append(");");
		return(result.toString());
	}

}
