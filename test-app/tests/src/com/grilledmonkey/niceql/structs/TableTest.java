package com.grilledmonkey.niceql.structs;

import android.test.AndroidTestCase;

public class TableTest extends AndroidTestCase {
	private static final String TABLE_NAME = "table";

	public void testConstructor() {
		Table table = new Table(TABLE_NAME);
		assertEquals(TABLE_NAME, table.getName());
	}
}
