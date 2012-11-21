package com.grilledmonkey.niceql.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.grilledmonkey.niceql.types.DataType;

public class MappedList {
	protected final Map<String, DataType> map = new HashMap<String, DataType>();
	
	public MappedList() {
		// Nothing to do here (:
	}
	
	public void put(String key, DataType value) {
		map.put(key, value);
	}
	
	public DataType get(String key) {
		return(map.get(key));
	}
	
	public String getCommaList() {
		// TODO add caching?
		// TODO add checking for proper key names?
		List<String> array = new ArrayList<String>();
		for(Map.Entry<String, DataType> item: map.entrySet())
			array.add("\"" + item.getKey() + "\" = " + item.getValue().getSql());
		return(TextUtils.join(", ", array));
	}
	
	public String[] getSplitList() {
		// TODO add caching?
		// TODO add checking for proper key names?
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(Map.Entry<String, DataType> item: map.entrySet()) {
			keys.add("\"" + item.getKey() + "\"");
			values.add(item.getValue().getSql());
		}
		String[] result = new String[2];
		result[0] = TextUtils.join(", ", keys);
		result[1] = TextUtils.join(", ", values);
		return(result);
	}
}
