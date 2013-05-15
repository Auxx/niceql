package com.grilledmonkey.niceql.xml;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.test.AndroidTestCase;
import android.util.Log;

public class XmlParserTest extends AndroidTestCase implements XmlParser.OnTagListener {
	private String xxx = "";

	public void testRun() throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser xml = factory.newPullParser();
		xml.setInput(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<scheme version=\"1\">" +
				"<table name=\"articles\" withPrimaryKey=\"true\">" +
				"<column name=\"title\" type=\"TEXT\" notNull=\"true\" />" +
				"<column name=\"text\" type=\"TEXT\" />" +
				"</table>" +
				"</scheme>"));
		XmlParser parser = new XmlParser(xml, this);
		parser.run();
		assertEquals("", xxx);
	}

	@Override
	public void onTag(String tagName, XmlPullParser xml) {
		xxx += " / " + tagName;
		System.out.println("zzz");
		Log.w("XXX", "Tag name: " + tagName);
	}
}
