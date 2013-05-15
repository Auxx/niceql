package com.grilledmonkey.niceql.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlParser {
	private final XmlPullParser xml;
	private final int depth;
	private final XmlParser.OnTagListener listener;

	public XmlParser(XmlPullParser xml) {
		this(xml, null);
	}

	public XmlParser(XmlPullParser xml, XmlParser.OnTagListener listener) {
		this.xml = xml;
		this.depth = this.xml.getDepth();
		this.listener = listener;
	}

	public void run() throws XmlPullParserException, IOException {
		while(needsIteration()) {
			xml.next();
		}
	}

	private boolean needsIteration() throws XmlPullParserException {
		int eventType = xml.getEventType();

		switch(eventType) {
			case XmlPullParser.END_DOCUMENT:
				return(false);
			case XmlPullParser.END_TAG:
				if(depth == xml.getDepth()) {
					return(false);
				}
				break;
			case XmlPullParser.START_TAG:
				if(listener != null) {
					listener.onTag(xml.getName(), xml);
				}
				break;
		}

		return(true);
	}

	public static interface OnTagListener {
		public void onTag(String tagName, XmlPullParser xml);
	}
}
