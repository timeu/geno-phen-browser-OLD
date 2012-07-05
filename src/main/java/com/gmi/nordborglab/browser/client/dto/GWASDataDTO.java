package com.gmi.nordborglab.browser.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.visualization.client.DataTable;

public class GWASDataDTO {

	protected final double maxScore;
	protected final double bonferroniThreshold;
	protected final List<DataTable> gwasDataTables = new ArrayList<DataTable>();
	protected final List<Integer> chrLengths = new ArrayList<Integer>();

	
	public GWASDataDTO(GWASData data) {
		this.maxScore = data.getMaxScore();
		this.bonferroniThreshold = data.getBonferroniThreshold();
		for (int i =0;i<data.getChrLengths().length();i++) {
			chrLengths.add(data.getChrLengths().get(i));
		}
		
		for (int i =0;i<data.getGWASDataTablesJSON().length();i++) {
			JavaScriptObject gwasData = JsonUtils.safeEval(data.getGWASDataTablesJSON().get(i));
			DataTable dataTable = DataTable.create(gwasData);
			/*dataTable.insertRows(0,1);
			dataTable.setValue(0, 0, 0);
			int index = dataTable.addRow();
			dataTable.setValue(index, 0, chrLengths.get(i));*/
			gwasDataTables.add(dataTable);
		}
	}


	public double getMaxScore() {
		return maxScore;
	}


	public double getBonferroniThreshold() {
		return bonferroniThreshold;
	}


	public List<DataTable> getGwasDataTables() {
		return gwasDataTables;
	}


	public List<Integer> getChrLengths() {
		return chrLengths;
	}
}
