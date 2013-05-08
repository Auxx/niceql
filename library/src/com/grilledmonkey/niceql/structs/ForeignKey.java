package com.grilledmonkey.niceql.structs;

import java.util.LinkedList;
import java.util.List;

import android.text.TextUtils;

import com.grilledmonkey.niceql.interfaces.SqlColumn;
import com.grilledmonkey.niceql.interfaces.SqlForeignKey;
import com.grilledmonkey.niceql.interfaces.SqlReference;

public class ForeignKey implements SqlForeignKey {
	private final SqlReference reference;
	private final List<Object> columns = new LinkedList<Object>();

	public ForeignKey(SqlReference reference) {
		this.reference = reference;
	}

	public void addColumn(SqlColumn column) {
		columns.add(column);
	}

	public void addColumn(String column) {
		columns.add(column);
	}

	public SqlReference getReference() {
		return(reference);
	}

	@Override
	public String getSql() {
		if(columns.size() == 0 || reference == null) {
			return(null);
		}

		String refSql = reference.getSql();
		if(refSql == null) {
			return(null);
		}

		StringBuilder result = new StringBuilder("FOREIGN KEY(");

		// TODO Refactor this shit
		int size = columns.size();
		String[] columnSql = new String[size];
		for(int i = 0; i < size; i++) {
			Object item = columns.get(i);
			if(item instanceof String)
				columnSql[i] = SqlColumn.escape((String)item);
			else if(item instanceof SqlColumn)
				columnSql[i] = ((SqlColumn)columns.get(i)).getNameEscaped();
		}
		result.append(TextUtils.join(", ", columnSql)).append(") ").append(refSql);

		return(result.toString());
	}

}
