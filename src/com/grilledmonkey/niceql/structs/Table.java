package com.grilledmonkey.niceql.structs;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.text.TextUtils;

public class Table {
	private final String name;
	private final List<Column> columns = new LinkedList<Column>();
	private final List<Index> indices = new LinkedList<Index>();
	private final List<ContentValues> seeds = new LinkedList<ContentValues>();

	public Table(String name, boolean withPrimaryKey) {
		this.name = name;
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

	public void addSeed(ContentValues seed) {
		seeds.add(seed);
	}

	public List<Column> getColumns() {
		return(columns);
	}

	public List<ContentValues> getSeeds() {
		return(seeds);
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

//		if(seeds.size() > 0) {
//			size = seeds.size();
//			for(int i = 0; i < size; i++) {
//
////				queryList.add(seeds.get(i).getSql());
//			}
//		}

		return(queryList);
	}
}
