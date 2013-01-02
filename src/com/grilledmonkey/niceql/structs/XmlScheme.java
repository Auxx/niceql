package com.grilledmonkey.niceql.structs;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

/**
 * This is the same class as Scheme, but it introduces static parse method
 * which will parse specified XML file and create new instance of Scheme based
 * on that XML. You can either use any instantiated XmlPullParser or Android
 * XML resource ID.
 * <p>
 * Version you specify in constructor will be overridden by <i>version</i> attribute
 * of <i>scheme</i> tag if found. Meaning it is used as a fallback value.
 * <p>
 * XML structure is shown in README.md
 *
 * @author Aux
 *
 */
public class XmlScheme extends Scheme {
	private static final String ROOT_TAG = "scheme";
	private static final String TABLE_TAG = "table";
	private static final String SQL_TAG = "sql";
	private static final String COLUMN_TAG = "column";
	private static final String INDEX_TAG = "index";
	private static final String SEED_TAG = "seed";

	private static final String VERSION_ATTR = "version";
	private static final String NAME_ATTR = "name";
	private static final String TYPE_ATTR = "type";
	private static final String NOT_NULL_ATTR = "notNull";
	private static final String IS_UNIQUE_ATTR = "isUnique";
	private static final String WITH_PK_ATTR = "withPrimaryKey";

	private static final String TRUE_VALUE = "true";

	/**
	 * Parses specified XML file and returns Scheme based on that XML.
	 * Version 1 will be passed to newly created Scheme instance.
	 *
	 * @param xml to parse
	 * @return Scheme ready to be used
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static Scheme parse(XmlPullParser xml) throws XmlPullParserException, IOException {
		return(parse(1, xml));
	}

	/**
	 * Parses specified XML Android resource and returns Scheme based on that.
	 * Version 1 will be passed to newly created Scheme instance.
	 *
	 * @param context which contains resources (your Activity)
	 * @param resId to parse
	 * @return Scheme ready to be used
	 * @throws XmlPullParserException
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static Scheme parse(Context context, int resId) throws NotFoundException, XmlPullParserException, IOException {
		return(parse(1, context, resId));
	}

	/**
	 * Parses specified XML Android resource and returns Scheme based on that.
	 * Version will be passed to newly created Scheme instance.
	 *
	 * @param version of database scheme
	 * @param context which contains resources (your Activity)
	 * @param resId to parse
	 * @return Scheme ready to be used
	 * @throws XmlPullParserException
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static Scheme parse(int version, Context context, int resId) throws NotFoundException, XmlPullParserException, IOException {
		return(parse(version, context.getResources().getXml(resId)));
	}

	/**
	 * Parses specified XML file and returns Scheme based on that XML.
	 * Version will be passed to newly created Scheme instance.
	 *
	 * @param version of database scheme
	 * @param xml to parse
	 * @return Scheme ready to be used
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static Scheme parse(int version, XmlPullParser xml) throws XmlPullParserException, IOException {
		Scheme result = null;
		int eventType = xml.getEventType();
		while(eventType != XmlPullParser.END_DOCUMENT) {
			switch(eventType) {
				case XmlPullParser.CDSECT:
					Log.i("NQL", "CDSECT");
					break;
				case XmlPullParser.COMMENT:
					Log.i("NQL", "COMMENT");
					break;
				case XmlPullParser.DOCDECL:
					Log.i("NQL", "DOCDECL");
					break;
				case XmlPullParser.END_DOCUMENT:
					Log.i("NQL", "END_DOCUMENT");
					break;
				case XmlPullParser.END_TAG:
					Log.i("NQL", "END_TAG: </" + xml.getName() + ">");
					break;
				case XmlPullParser.ENTITY_REF:
					Log.i("NQL", "ENTITY_REF");
					break;
				case XmlPullParser.IGNORABLE_WHITESPACE:
					Log.i("NQL", "IGNORABLE_WHITESPACE");
					break;
				case XmlPullParser.PROCESSING_INSTRUCTION:
					Log.i("NQL", "PROCESSING_INSTRUCTION");
					break;
				case XmlPullParser.START_DOCUMENT:
					Log.i("NQL", "START_DOCUMENT");
					break;
				case XmlPullParser.START_TAG:
					Log.i("NQL", "START_TAG: <" + xml.getName() + ">");

					if(ROOT_TAG.equals(xml.getName()))
						result = parseRoot(version, xml);
					break;
				case XmlPullParser.TEXT:
					Log.i("NQL", "TEXT: " + xml.getText());
					break;
			}
			eventType = xml.next();
		}
		return(result);
	}

	private static Integer toInt(String value) {
		Integer result;
		try {
			result = Integer.valueOf(value);
		}
		catch(Exception e) {
			result = null;
		}
		return(result);
	}

	private static Scheme parseRoot(int version, XmlPullParser xml) throws XmlPullParserException, IOException {
		Integer v = version;
		String xmlVersion = xml.getAttributeValue(null, VERSION_ATTR);
		if(xmlVersion != null) {
			v = toInt(xmlVersion);
			if(v == null)
				v = version;
		}

		Scheme result = new Scheme(v);

		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG) {
			switch(eventType) {
				case XmlPullParser.START_TAG:
					if(TABLE_TAG.equals(xml.getName()))
						parseTable(result, xml);
					else if(SQL_TAG.equals(xml.getName()))
						parseSql(result, xml);
					break;
			}
			eventType = xml.next();
		}

		return(result);
	}

	private static void parseTable(Scheme scheme, XmlPullParser xml) throws XmlPullParserException, IOException {
		Table table = null;
		String name = xml.getAttributeValue(null, NAME_ATTR);
		if(name != null)
			table = new Table(name, TRUE_VALUE.equals(xml.getAttributeValue(null, WITH_PK_ATTR)));

		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG) {
			if(eventType == XmlPullParser.START_TAG) {
				if(COLUMN_TAG.equals(xml.getName()))
					parseColumn(table, xml);
				else if(INDEX_TAG.equals(xml.getName()))
					parseIndex(table, xml);
				else if(SEED_TAG.equals(xml.getName()))
					parseSeed(table, xml);
			}
			eventType = xml.next();
		}

		if(table != null)
			scheme.addTable(table);
	}

	private static void parseSql(Scheme scheme, XmlPullParser xml) throws XmlPullParserException, IOException {
		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG) {
			if(eventType == XmlPullParser.TEXT)
				scheme.addArbitrarySql(xml.getText());
			eventType = xml.next();
		}
	}

	private static void parseColumn(Table table, XmlPullParser xml) throws XmlPullParserException, IOException {
		String name = xml.getAttributeValue(null, NAME_ATTR);
		String type = xml.getAttributeValue(null, TYPE_ATTR);
		if(name != null && type != null)
			table.addColumn(new Column(name, type, TRUE_VALUE.equals(xml.getAttributeValue(null, NOT_NULL_ATTR))));

		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG)
			eventType = xml.next();
	}

	private static void parseIndex(Table table, XmlPullParser xml) throws XmlPullParserException, IOException {
		Index index = null;
		String name = xml.getAttributeValue(null, NAME_ATTR);
		if(name != null)
			index = new Index(name, table, TRUE_VALUE.equals(xml.getAttributeValue(null, IS_UNIQUE_ATTR)));

		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG) {
			if(eventType == XmlPullParser.START_TAG) {
				if(COLUMN_TAG.equals(xml.getName()))
					parseIndexColumn(index, xml);
			}
			eventType = xml.next();
		}

		if(index != null)
			table.addIndex(index);
	}

	private static void parseSeed(Table table, XmlPullParser xml) throws XmlPullParserException, IOException {
		ContentValues seed = new ContentValues();

		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG) {
			if(eventType == XmlPullParser.START_TAG) {
				if(COLUMN_TAG.equals(xml.getName()))
					parseSeedColumn(seed, xml);
			}
			eventType = xml.next();
		}

		if(seed.size() > 0)
			table.addSeed(seed);
	}

	private static void parseIndexColumn(Index index, XmlPullParser xml) throws XmlPullParserException, IOException {
		String name = xml.getAttributeValue(null, NAME_ATTR);
		if(name != null)
			index.addColumn(name);

		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG)
			eventType = xml.next();
	}

	private static void parseSeedColumn(ContentValues seed, XmlPullParser xml) throws XmlPullParserException, IOException {
		String name = xml.getAttributeValue(null, NAME_ATTR);
		int eventType = xml.next();
		while(eventType != XmlPullParser.END_TAG) {
			if(name != null && eventType == XmlPullParser.TEXT)
				seed.put(name, xml.getText());
			eventType = xml.next();
		}
	}

}