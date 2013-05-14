package com.grilledmonkey.niceql.interfaces;


public interface SqlMigration extends SqlMultiStatement {
	public void addStatement(String sql);
	public void setVersion(int version);
	public int getVersion();
}
