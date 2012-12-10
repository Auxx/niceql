package com.grilledmonkey.niceql.builders;

import java.util.ArrayList;
import java.util.LinkedList;
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

	public String getName() {
		return(name);
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	public void addIndex(Index index) {
		indices.add(index);
	}

	public List<Column> getColumns() {
		return(columns);
	}

	public List<String> getSql() {
		List<String> queryList = new LinkedList<String>();

		StringBuilder result = new StringBuilder("CREATE TABLE " + name + "(");

		int size = columns.size();
		String[] columnSql = new String[size];
		for(int i = 0; i < size; i++)
			columnSql[i] = columns.get(i).getSql();
		result.append(TextUtils.join(", ", columnSql));

		result.append(");");
		queryList.add(result.toString());

		if(indices.size() > 0) {
			size = indices.size();
			for(int i = 0; i < size; i++)
				queryList.add(indices.get(i).getSql());
		}

		return(queryList);
	}
}
