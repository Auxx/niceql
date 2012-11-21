package com.grilledmonkey.niceql.builders;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.grilledmonkey.niceql.stucts.Column;

public class TableBuilder {
	private final String name;
	private final List<Column> columns;

	public TableBuilder(String name, boolean withPrimaryKey) {
		this.name = name;
		columns = new ArrayList<Column>();
		if(withPrimaryKey)
			columns.add(new Column());
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	public String getSql() {
		int size = columns.size();
		String[] columnSql = new String[size];
		StringBuilder result = new StringBuilder("CREATE TABLE " + name + "(");
		for(int i = 0; i < size; i++)
			columnSql[i] = columns.get(i).getSql();
		result.append(TextUtils.join(", ", columnSql));
		result.append(");");
		return(result.toString());
	}
}
