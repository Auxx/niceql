package com.grilledmonkey.niceql.open;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.grilledmonkey.niceql.builders.TableBuilder;

public class TableOpenHelper extends SQLiteOpenHelper {
	protected TableBuilder table = null;

	public TableOpenHelper(Context context, String databaseName, TableBuilder table) {
		super(context, databaseName, null, 1);
		this.table = table;
	}

	public void setTable(TableBuilder table) {
		this.table = table;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(table != null) {
			db.execSQL(table.getSql());
		}
		else
			return; // TODO Throw exception
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
