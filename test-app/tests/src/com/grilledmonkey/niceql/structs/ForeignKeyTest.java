package com.grilledmonkey.niceql.structs;

import android.test.AndroidTestCase;

public class ForeignKeyTest extends AndroidTestCase {
	public void testConstructor() {
		Reference reference = new Reference("table");
		ForeignKey fk = new ForeignKey(reference);
		assertEquals(reference, fk.getReference());
	}

	public void testGetSql() {
		Reference reference = new Reference("users");
		reference.addColumn("id");
		ForeignKey fk = new ForeignKey(reference);
		fk.addColumn("user_id");
		assertEquals("FOREIGN KEY(\"user_id\") REFERENCES users(\"id\")", fk.getSql());

		fk = new ForeignKey(reference);
		assertNull(fk.getSql());
	}
}
