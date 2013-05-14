package com.grilledmonkey.niceql.structs;

import java.util.List;

import android.test.AndroidTestCase;

import com.grilledmonkey.niceql.interfaces.SqlColumn;

public class TableTest extends AndroidTestCase {
	private static final String TABLE_NAME = "table";

	public void testConstructor() {
		Table table = new Table(TABLE_NAME);
		assertEquals(TABLE_NAME, table.getName());
		assertEquals(1, table.getColumns().size());
		assertEquals(0, table.getIndices().size());
		assertEquals(0, table.getSeeds().size());

		table = new Table(TABLE_NAME, false);
		assertEquals(TABLE_NAME, table.getName());
		assertEquals(0, table.getColumns().size());
	}

	public void testAddColumn() {
		Table table = new Table(TABLE_NAME, false);
		table.addColumn(null);
		table.addColumn(null);
		assertEquals(2, table.getColumns().size());
	}

	public void testAddIndex() {
		Table table = new Table(TABLE_NAME, false);
		table.addIndex(null);
		assertEquals(1, table.getIndices().size());
	}

	public void testAddSeed() {
		Table table = new Table(TABLE_NAME, false);
		table.addSeed(null);
		table.addSeed(null);
		assertEquals(2, table.getSeeds().size());
	}

	public void testAddForeignKey() {
		Table table = new Table(TABLE_NAME, false);
		table.addForeignKey(null);
		table.addForeignKey(null);
		table.addForeignKey(null);
		assertEquals(3, table.getForeignKeys().size());
	}

	public void testGetSql() {
		Table table = new Table(TABLE_NAME);
		table.addColumn(new Column("title", SqlColumn.TEXT));
		table.addColumn(new Column("is_visible", SqlColumn.INTEGER));
		List<String> queries = table.getSql();
		assertEquals(1, queries.size());
		assertEquals("CREATE TABLE table(\"_id\" INTEGER  PRIMARY KEY AUTOINCREMENT, \"title\" TEXT, \"is_visible\" INTEGER);", queries.get(0));

		Index index = new Index("index", table);
		index.addColumn("is_visible");
		table.addIndex(index);
		queries = table.getSql();
		assertEquals(2, queries.size());
	}

	public void testGetSqlWithFK() {
		Table table = new Table(TABLE_NAME);
		table.addColumn(new Column("title", SqlColumn.TEXT));
		table.addColumn(new Column("author_id", SqlColumn.INTEGER));

		Reference reference = new Reference("authors");
		reference.addColumn("id");
		ForeignKey fk = new ForeignKey(reference);
		fk.addColumn("author_id");
		table.addForeignKey(fk);

		List<String> queries = table.getSql();
		assertEquals("CREATE TABLE table(\"_id\" INTEGER  PRIMARY KEY AUTOINCREMENT, \"title\" TEXT, \"author_id\" INTEGER, FOREIGN KEY(\"author_id\") REFERENCES authors(\"id\"));", queries.get(0));
	}
}
