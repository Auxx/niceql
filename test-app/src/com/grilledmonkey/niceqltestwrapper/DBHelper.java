package com.grilledmonkey.niceqltestwrapper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String LOG_TAG = "TEST";

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		Log.w(LOG_TAG, "DBHelper::constructor()");
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		Log.w(LOG_TAG, "DBHelper.onCreate()");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int from, int to) {
		Log.w(LOG_TAG, "DBHelper.onUpgrade(db, " + from + ", " + to + ")");
	}

}
