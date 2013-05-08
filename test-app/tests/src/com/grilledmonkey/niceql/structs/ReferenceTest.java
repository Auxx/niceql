package com.grilledmonkey.niceql.structs;

import android.test.AndroidTestCase;

import com.grilledmonkey.niceql.interfaces.SqlColumn;

public class ReferenceTest extends AndroidTestCase {
	private static final String TABLE_NAME = "table";
	private static final String OPTIONS = "DEFERRABLE";

	public void testConstructor() {
		Reference reference = new Reference(TABLE_NAME);
		assertEquals(TABLE_NAME, reference.getTableName());
		assertNull(reference.getOptions());

		reference = new Reference(TABLE_NAME, OPTIONS);
		assertEquals(TABLE_NAME, reference.getTableName());
		assertEquals(OPTIONS, reference.getOptions());
	}

	public void testGetSql() {
		Reference reference = new Reference(TABLE_NAME, OPTIONS);
		reference.addColumn("user_id");
		reference.addColumn(new Column("group_id", SqlColumn.INTEGER));
		assertEquals("REFERENCES table(\"user_id\", \"group_id\") DEFERRABLE", reference.getSql());

		reference = new Reference(TABLE_NAME);
		assertNull(reference.getSql());
	}
}
