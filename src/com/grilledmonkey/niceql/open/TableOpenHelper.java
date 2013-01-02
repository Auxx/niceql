package com.grilledmonkey.niceql.open;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TableOpenHelper extends SQLiteOpenHelper {
//	protected TableBuilder table = null;

	public TableOpenHelper(Context context, String databaseName/*, TableBuilder table*/) {
		super(context, databaseName, null, 1);
//		this.table = table;
	}

	/*public void setTable(TableBuilder table) {
		this.table = table;
	}

	public String getTableName() {
		return(table.getName());
	}*/

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*if(table != null) {
			List<String> queryList = table.getSql();
			for(String query: queryList)
				db.execSQL(query);
			List<ContentValues> seeds = table.getSeeds();
			if(seeds.size() > 0) {
				for(ContentValues item: seeds)
					db.insert(table.getName(), null, item);
			}
		}
		else
			return; // TODO Throw exception*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
