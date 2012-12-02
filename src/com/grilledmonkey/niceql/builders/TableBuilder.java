package com.grilledmonkey.niceql.builders;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.grilledmonkey.niceql.stucts.Column;
import com.grilledmonkey.niceql.stucts.Index;

public class TableBuilder {
	private final String name;
	private final List<Column> columns;
	private final List<Index> indices;

	public TableBuilder(String name, boolean withPrimaryKey) {
		this.name = name;
		indices = new ArrayList<Index>();
		columns = new ArrayList<Column>();
		if(withPrimaryKey)
			columns.add(new Column());
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	public void addIndex(Index index) {
		indices.add(index);
	}

	public String getSql() {
		StringBuilder result = new StringBuilder("CREATE TABLE " + name + "(");

		int size = columns.size();
		String[] columnSql = new String[size];
		for(int i = 0; i < size; i++)
			columnSql[i] = columns.get(i).getSql();
		result.append(TextUtils.join(", ", columnSql));

		result.append(");");

		if(indices.size() > 0) {
			result.append("\n");
			size = indices.size();
			columnSql = new String[size];
			for(int i = 0; i < size; i++)
				columnSql[i] = indices.get(i).getSql();
			result.append(TextUtils.join("\n", columnSql));
		}

		return(result.toString());
	}
}
