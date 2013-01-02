package com.grilledmonkey.niceql.structs;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;

/**
 * This is the same class as Scheme, but it introduces static parse method
 * which will parse specified XML file and create new instance of Scheme based
 * on that XML. You can either use any instantiated XmlPullParser or Android
 * XML resource ID.
 *
 * @author Aux
 *
 */
public class XmlScheme extends Scheme {

	/**
	 * Parses specified XML file and returns Scheme based on that XML.
	 * Version 1 will be passed to newly created Scheme instance.
	 *
	 * @param xml to parse
	 * @return Scheme ready to be used
	 */
	public static Scheme parse(XmlPullParser xml) {
		return(parse(1, xml));
	}

	/**
	 * Parses specified XML Android resource and returns Scheme based on that.
	 * Version 1 will be passed to newly created Scheme instance.
	 *
	 * @param context which contains resources (your Activity)
	 * @param resId to parse
	 * @return Scheme ready to be used
	 */
	public static Scheme parse(Context context, int resId) {
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
	 */
	public static Scheme parse(int version, Context context, int resId) {
		return(parse(version, context.getResources().getXml(resId)));
	}

	/**
	 * Parses specified XML file and returns Scheme based on that XML.
	 * Version will be passed to newly created Scheme instance.
	 *
	 * @param version of database scheme
	 * @param xml to parse
	 * @return Scheme ready to be used
	 */
	public static Scheme parse(int version, XmlPullParser xml) {
		return(null);
	}

}
