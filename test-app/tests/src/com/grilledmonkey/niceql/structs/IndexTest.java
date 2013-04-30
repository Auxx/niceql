package com.grilledmonkey.niceql.structs;

import android.test.AndroidTestCase;

import com.grilledmonkey.niceql.interfaces.SqlColumn;

public class IndexTest extends AndroidTestCase {
	private static final String INDEX_NAME = "idx";
	private static final String TABLE_NAME = "tbl";

	public void testConstructor() {
		Index index = new Index(INDEX_NAME, TABLE_NAME, true);
		assertEquals(INDEX_NAME, index.getName());
		assertEquals(TABLE_NAME, index.getTableName());
		assertTrue(index.isUnique());

		index = new Index(INDEX_NAME, TABLE_NAME, false);
		assertFalse(index.isUnique());
	}

	public void testGetSql() {
		Index index = new Index(INDEX_NAME, TABLE_NAME, true);
		index.addColumn(SqlColumn.PRIMARY_KEY);
		assertEquals("CREATE UNIQUE INDEX idx ON tbl (\"_id\");", index.getSql());

		index = new Index(INDEX_NAME, TABLE_NAME, false);
		index.addColumn("order_key");
		index.addColumn("is_visible");
		assertEquals("CREATE INDEX idx ON tbl (\"order_key\", \"is_visible\");", index.getSql());

	}
}
