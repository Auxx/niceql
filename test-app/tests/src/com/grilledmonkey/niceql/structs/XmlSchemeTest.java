package com.grilledmonkey.niceql.structs;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.test.AndroidTestCase;

public class XmlSchemeTest extends AndroidTestCase {
	public void testParseSimpleTable() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<scheme version=\"1\">" +
				"<table name=\"articles\" withPrimaryKey=\"true\">" +
				"<column name=\"title\" type=\"TEXT\" notNull=\"true\" />" +
				"<column name=\"text\" type=\"TEXT\" />" +
				"</table>" +
				"</scheme>"));
		Scheme scheme = XmlScheme.parse(parser);

		List<String> sql = scheme.getSql();
		assertEquals(1, sql.size());
		assertEquals("CREATE TABLE articles(\"_id\" INTEGER  PRIMARY KEY AUTOINCREMENT, \"title\" TEXT NOT NULL, \"text\" TEXT);", sql.get(0));
	}

	public void testParseWithSeeds() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<scheme version=\"1\">" +
				"<table name=\"articles\" withPrimaryKey=\"true\">" +
				"<column name=\"title\" type=\"TEXT\" notNull=\"true\" />" +
				"<column name=\"text\" type=\"TEXT\" />" +
				"<seed>" +
				"<column name=\"title\">First article</column>" +
				"<column name=\"text\">Hello world!</column>" +
				"</seed>" +
				"<seed>" +
				"<column name=\"title\">More stuff to come!</column>" +
				"<column name=\"text\">Yeah!</column>" +
				"</seed>" +
				"</table>" +
				"</scheme>"));
		Scheme scheme = XmlScheme.parse(parser);

		List<String> sql = scheme.getSql();
		assertEquals(1, sql.size());

		List<ContentValues> seeds = scheme.getSeeds();
		assertEquals(2, seeds.size());
		assertEquals("First article", seeds.get(0).get("title"));
		assertEquals("Yeah!", seeds.get(1).get("text"));
	}

	public void testParseRawSql() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<scheme version=\"1\">" +
				"<sql>" +
				"SELECT 1 FROM articles" +
				"</sql>" +
				"<sql>" +
				"SELECT 2 FROM articles" +
				"</sql>" +
				"</scheme>"));
		Scheme scheme = XmlScheme.parse(parser);

		List<String> sql = scheme.getSql();
		assertEquals(2, sql.size());
		assertEquals("SELECT 2 FROM articles", sql.get(1));
	}

	public void testParseWithFK() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<scheme version=\"1\">" +
				"<table name=\"articles\" withPrimaryKey=\"true\">" +
				"<column name=\"title\" type=\"TEXT\" notNull=\"true\" />" +
				"<column name=\"author_id\" type=\"INTEGER\" />" +
				"<foreignKey>" +
				"<reference table=\"authors\">" +
				"<column name=\"id\">" +
				"</column>" +
				"</reference>" +
				"<column name=\"author_id\">" +
				"</column>" +
				"</foreignKey>" +
				"</table>" +
				"</scheme>"));
		Scheme scheme = XmlScheme.parse(parser);

		List<String> sql = scheme.getSql();
		assertEquals(1, sql.size());
		assertEquals("CREATE TABLE articles(\"_id\" INTEGER  PRIMARY KEY AUTOINCREMENT, \"title\" TEXT NOT NULL, \"author_id\" INTEGER, FOREIGN KEY(\"author_id\") REFERENCES authors(\"id\"));", sql.get(0));
	}
}
