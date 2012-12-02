package com.grilledmonkey.niceql.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

import com.grilledmonkey.niceql.builders.TableBuilder;
import com.grilledmonkey.niceql.stucts.Column;

public class Record {
	private final TableBuilder table;
	private final SQLiteDatabase db;
	private final Map<String, Column> columns;
	private final Map<String, Object> cells;

	public Record(TableBuilder table, SQLiteDatabase db) {
		this.table = table;
		this.db = db;
		this.columns = new HashMap<String, Column>();
		this.cells = new HashMap<String, Object>();

		List<Column> cols = table.getColumns();
		for(Column item: cols) {
			this.columns.put(item.getName(), item);
		}
	}

	// TODO Throw exceptions everywhere?
	// TODO make auto-conversion optional?
	public void put(String key, String value) {
		if(getColumnType(key) == Column.TYPE_TEXT)
			cells.put(key, value);
	}

	public void put(String key, double value) {
		if(getColumnType(key) == Column.TYPE_REAL)
			cells.put(key, value);
		else if(getColumnType(key) == Column.TYPE_TEXT)
			cells.put(key, String.valueOf(value));
		else if(getColumnType(key) == Column.TYPE_INTEGER)
			cells.put(key, (int)value);
	}

	public void put(String key, int value) {
		if(getColumnType(key) == Column.TYPE_INTEGER)
			cells.put(key, value);
		else if(getColumnType(key) == Column.TYPE_TEXT)
			cells.put(key, String.valueOf(value));
		else if(getColumnType(key) == Column.TYPE_REAL)
			cells.put(key, (double)value);
	}

	public void put(String key, boolean value) {
		if(getColumnType(key) == Column.TYPE_INTEGER)
			cells.put(key, value ? 1 : 0);
	}

	protected int getColumnType(String key) {
		Column column = columns.get(key);
		if(column != null)
			return(column.getIntType());
		else
			return(Column.TYPE_UNDEFINED);
	}
}
