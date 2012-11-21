package com.grilledmonkey.niceql.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.grilledmonkey.niceql.helpers.ConditionList;

import android.text.TextUtils;

public class QueryBuilder {
	private final int queryType;
	private final String table;
	private List<String> columns;
	private String order = null;
	private int limit = 0, offset = 0;
	private ConditionList where;
	private Map<String, String> params;

	public static final int SELECT = 0;
	public static final int UPDATE = 1;
	public static final int INSERT = 2;
	public static final int DELETE = 3;

	public QueryBuilder(int queryType, String table) {
		this.queryType = queryType;
		this.table = table;
		columns = new ArrayList<String>();
		columns.add("*");
	}

	public void selectColumns(List<String> columns) {
		this.columns = columns;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setLimit(int limit) {
		setLimit(limit, 0);
	}

	public void setLimit(int limit, int offset) {
		this.limit = limit;
		this.offset = offset;
	}

	public void setWhere(ConditionList where) {
		this.where = where;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	private StringBuilder getOrderSql() {
		StringBuilder result = new StringBuilder();
		if(!TextUtils.isEmpty(order)) {
			result.append(" ORDER BY ");
			result.append(order);
		}
		return(result);
	}

	private StringBuilder getLimitSql() {
		StringBuilder result = new StringBuilder();
		if(limit > 0) {
			result.append(" LIMIT ");
			result.append(limit);
			if(offset > 0) {
				result.append(" OFFSET ");
				result.append(offset);
			}
		}
		return(result);
	}

	private StringBuilder getWhere() {
		StringBuilder result = new StringBuilder();
		if(!ConditionList.isEmpty(where)) {
			result.append(" WHERE ");
			result.append(where.getSql());
		}
		return(result);
	}

	// TODO use MappedList
	public String makeCommaList() {
		List<String> array = new ArrayList<String>();
		for(Map.Entry<String, String> i: params.entrySet())
			array.add(i.getKey() + " = '" + i.getValue() + "'");
		return(TextUtils.join(", ", array));
	}

	// TODO use MappedList
	public String[] makeInsertList() {
		String[] result = new String[2];
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(Map.Entry<String, String> i: params.entrySet()) {
			keys.add(i.getKey());
			values.add(i.getValue());
		}
		result[0] = TextUtils.join(", ", keys);
		result[1] = TextUtils.join(", ", values);
		return(result);
	}

	public String getSql() {
		StringBuilder result = new StringBuilder();
		switch(queryType) {
			case SELECT:
				result.append("SELECT ");
				result.append(TextUtils.join(", ", columns));
				result.append(" FROM ");
				result.append(table);
				result.append(getWhere());
				result.append(getOrderSql());
				result.append(getLimitSql());
				result.append(";");
				break;

			case DELETE:
				result.append("SELECT FROM ");
				result.append(table);
				result.append(getWhere());
				result.append(getOrderSql());
				result.append(getLimitSql());
				result.append(";");
				break;

			case UPDATE:
				if(params != null && !params.isEmpty()) {
					result.append("UPDATE ");
					result.append(table);
					result.append(" SET ");
					result.append(makeCommaList());
					result.append(getWhere());
					result.append(getOrderSql());
					result.append(getLimitSql());
					result.append(";");
				}
				break;

			case INSERT:
				if(params != null && !params.isEmpty()) {
					String[] kv = makeInsertList();
					result.append("INSERT INTO ");
					result.append(table);
					result.append("(");
					result.append(kv[0]);
					result.append(") VALUES(");
					result.append(kv[1]);
					result.append(");");
				}
				break;
		}
		return(result.toString());
	}
}