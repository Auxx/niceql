package com.grilledmonkey.niceql.helpers;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.grilledmonkey.niceql.interfaces.SqlScheme;
import com.grilledmonkey.niceql.interfaces.SqlTable;

public class SchemeOpenHelper extends SQLiteOpenHelper {
	private final SqlScheme scheme;

	public SchemeOpenHelper(Context context, String databaseName, SqlScheme scheme) {
		this(context, databaseName, null, scheme);
	}

	public SchemeOpenHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, SqlScheme scheme) {
		super(context, databaseName, factory, scheme.getVersion());
		this.scheme = scheme;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(scheme != null) {
			List<String> queryList = scheme.getSql();
			for(String query: queryList)
				db.execSQL(query);

			List<SqlTable> tables = scheme.getTables();
			for(SqlTable table: tables) {
				List<ContentValues> seeds = table.getSeeds();
				for(ContentValues item: seeds)
					db.insert(table.getName(), null, item);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
