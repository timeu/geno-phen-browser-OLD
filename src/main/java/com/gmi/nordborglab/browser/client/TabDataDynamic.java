package com.gmi.nordborglab.browser.client;

import com.gwtplatform.mvp.client.TabDataBasic;

public class TabDataDynamic extends TabDataBasic {

	private String historyToken;
	
	public TabDataDynamic(String label, float priority,String historyToken) {
		super(label, priority);
		this.historyToken = historyToken;
	}

	public String getHistoryToken() {
		return historyToken;
	}
}
