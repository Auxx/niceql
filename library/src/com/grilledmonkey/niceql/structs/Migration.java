package com.grilledmonkey.niceql.structs;

import java.util.LinkedList;
import java.util.List;

import com.grilledmonkey.niceql.interfaces.SqlMigration;

public class Migration implements SqlMigration {
	private final List<String> sql = new LinkedList<String>();
	private int version;

	public Migration() {
		this(1);
	}

	public Migration(int version) {
		this.version = version;
	}

	@Override
	public List<String> getSql() {
		return(sql);
	}

	@Override
	public void addStatement(String sql) {
		this.sql.add(sql);
	}

	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int getVersion() {
		return(version);
	}

}
