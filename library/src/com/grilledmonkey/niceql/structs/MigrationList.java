package com.grilledmonkey.niceql.structs;

import android.util.SparseArray;

import com.grilledmonkey.niceql.interfaces.SqlMigration;
import com.grilledmonkey.niceql.interfaces.SqlMigrationList;

public class MigrationList implements SqlMigrationList {
	SparseArray<SqlMigration> migrations = new SparseArray<SqlMigration>();

	@Override
	public SqlMigration getMigration(int version) {
		return(migrations.get(version));
	}

	@Override
	public boolean addMigration(SqlMigration migration) {
		int version = migration.getVersion();
		if(migrations.get(version) == null) {
			migrations.put(version, migration);
			return(true);
		}
		else {
			return(false);
		}
	}

}
