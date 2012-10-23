package com.gmi.nordborglab.browser.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.visualization.client.DataTable;

public class DataTableUtils {
	
	public static DataTable createDataTableFromString(String json) {
		JavaScriptObject jsData = JsonUtils.safeEval(json);
		DataTable dataTable = DataTable.create(jsData);
		return dataTable;
	}

}
