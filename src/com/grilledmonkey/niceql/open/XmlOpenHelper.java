package com.grilledmonkey.niceql.open;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;

import com.grilledmonkey.niceql.builders.TableBuilder;
import com.grilledmonkey.niceql.stucts.Column;
import com.grilledmonkey.niceql.stucts.Index;

public class XmlOpenHelper extends TableOpenHelper {
	private static final String ROOT_TAG = "table";
	private static final String COLUMN_TAG = "column";
	private static final String INDEX_TAG = "index";
	private static final String ATTR_TABLE_NAME = "name";
	private static final String ATTR_TABLE_WITH_PK = "withPrimaryKey";
	private static final String ATTR_COLUMN_NAME = "name";
	private static final String ATTR_COLUMN_TYPE = "type";
	private static final String ATTR_COLUMN_NOT_NULL = "notNull";
	private static final String ATTR_INDEX_NAME = "name";
	private static final String ATTR_INDEX_IS_UNIQUE = "isUnique";
	private static final String VALUE_TRUE = "true";

//	private String tableName;

	protected XmlOpenHelper(Context context, String databaseName, TableBuilder table) {
		super(context, databaseName, table);
	}

	//	public List<String> getSql() {
	//		return(table.getSql());
	//	}

	public static XmlOpenHelper parse(Context context, String databaseName, int xmlId) {
		return(parse(context, databaseName, context.getResources().getXml(xmlId)));
	}

	public static XmlOpenHelper parse(Context context, String databaseName, XmlPullParser xml) {
		XmlOpenHelper instance = null;
		try {
			int eventType = xml.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					int depth = xml.getDepth();
					if(depth == 1) {
						if(ROOT_TAG.equals(xml.getName())) {
							String name = xml.getAttributeValue(null, ATTR_TABLE_NAME);
							boolean withPrimaryKey = VALUE_TRUE.equals(xml.getAttributeValue(null, ATTR_TABLE_WITH_PK));
							instance = new XmlOpenHelper(context, databaseName, new TableBuilder(name, withPrimaryKey));
						}
						else {
							// TODO Throw exception
							return(null);
						}
					}
					else if(depth > 1 && instance != null) {
						instance.processStartTag(xml);
					}
				}
				eventType = xml.next();
			}
		}
		catch(XmlPullParserException e) {
			// TODO: handle exception
			Log.w("SC", "XmlPullParserException");
		}
		catch(IOException e) {
			// TODO: handle exception
			Log.w("SC", "IOException");
		}
		return(instance);
	}

	protected void processStartTag(XmlPullParser xml) throws XmlPullParserException, IOException {
		int depth = xml.getDepth();

		if(depth == 2) {
			String tagName = xml.getName();
			if(COLUMN_TAG.equals(tagName)) {
				processColumn(xml);
			}
			else if(INDEX_TAG.equals(tagName)) {
				processIndex(xml);
			}
		}
	}

	protected void processColumn(XmlPullParser xml) {
		table.addColumn(new Column(xml.getAttributeValue(null, ATTR_COLUMN_NAME),
				xml.getAttributeValue(null, ATTR_COLUMN_TYPE),
				VALUE_TRUE.equals(xml.getAttributeValue(null, ATTR_COLUMN_NOT_NULL))));
	}

	protected void processIndex(XmlPullParser xml) throws XmlPullParserException, IOException {
		Index index = new Index(xml.getAttributeValue(null, ATTR_INDEX_NAME),
				table.getName(),
				VALUE_TRUE.equals(xml.getAttributeValue(null, ATTR_INDEX_IS_UNIQUE)));
		processIndexColumns(xml, index);
		table.addIndex(index);
	}

	protected void processIndexColumns(XmlPullParser xml, Index index) throws XmlPullParserException, IOException {
		int eventType;
		while((eventType = xml.next()) != XmlPullParser.END_DOCUMENT) {
			int depth = xml.getDepth();
			switch(eventType) {
				case XmlPullParser.START_TAG:
					if(depth == 3 && COLUMN_TAG.equals(xml.getName()))
						index.addColumn(xml.getAttributeValue(null, ATTR_COLUMN_NAME));
					break;
				case XmlPullParser.END_TAG:
					if(depth == 2)
						break;
					break;
			}
		}
	}
}
