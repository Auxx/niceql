package com.grilledmonkey.niceql.helpers;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

public class ConditionList {
	public static final String AND = " AND ";
	public static final String OR = " OR ";
	
	private final List<Object> conditions = new ArrayList<Object>();
	private String separator = AND;
	
	public void add(String condition) {
		this.conditions.add(condition);
	}
	
	public void add(ConditionList conditions) {
		this.conditions.add(conditions);
	}
	
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	public int size() {
		return(conditions.size());
	}
	
	public String getSql() {
		int size = conditions.size();
		List<String> array = new ArrayList<String>();
		for(int i = 0; i < size; i++) {
			Object item = conditions.get(i);
			if(item instanceof String)
				array.add((String)item);
			else if(item instanceof ConditionList)
				array.add("(" + ((ConditionList)item).getSql() + ")");
		}
		return(TextUtils.join(separator, array));
	}
	
	public static boolean isEmpty(ConditionList list) {
		return(list == null || list.size() == 0);
	}
}
