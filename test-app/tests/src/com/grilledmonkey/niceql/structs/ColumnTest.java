package com.grilledmonkey.niceql.structs;

import android.test.AndroidTestCase;

import com.grilledmonkey.niceql.interfaces.SqlColumn;

public class ColumnTest extends AndroidTestCase {
	private static final String COLUMN_NAME = "title";

	public void testConstructor() {
		Column column = new Column();
		assertEquals(SqlColumn.PRIMARY_KEY, column.getName());
		assertEquals('"' + SqlColumn.PRIMARY_KEY + '"', column.getNameEscaped());
		assertEquals(SqlColumn.INTEGER, column.getType());
		assertEquals(SqlColumn.TYPE_INTEGER, column.getIntType());
		assertFalse(column.isNotNull());
		assertTrue(column.isPrimary());
	}

	public void testContructorWithParams() {
		Column column = new Column(COLUMN_NAME, SqlColumn.TEXT, true);
		assertEquals(COLUMN_NAME, column.getName());
		assertEquals('"' + COLUMN_NAME + '"', column.getNameEscaped());
		assertEquals(SqlColumn.TEXT, column.getType());
		assertEquals(SqlColumn.TYPE_TEXT, column.getIntType());
		assertTrue(column.isNotNull());
		assertFalse(column.isPrimary());
	}

	public void testGetSql() {
		assertEquals("\"_id\" INTEGER  PRIMARY KEY AUTOINCREMENT", new Column().getSql());
		assertEquals("\"title\" TEXT NOT NULL", new Column(COLUMN_NAME, SqlColumn.TEXT, true).getSql());
	}
}
