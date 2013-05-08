package com.grilledmonkey.niceql.structs;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
}
