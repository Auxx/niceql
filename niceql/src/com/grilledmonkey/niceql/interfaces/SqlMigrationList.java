package com.grilledmonkey.niceql.interfaces;

public interface SqlMigrationList {
	public SqlMigration getMigration(int version);
	public boolean addMigration(SqlMigration migration);
}
