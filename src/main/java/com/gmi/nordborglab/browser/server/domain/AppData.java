package com.gmi.nordborglab.browser.server.domain;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.cdv.StudyProtocol;
import com.gmi.nordborglab.browser.server.domain.genotype.AlleleAssay;
import com.gmi.nordborglab.browser.server.domain.phenotype.StatisticType;
import com.gmi.nordborglab.browser.server.domain.phenotype.UnitOfMeasure;

public class AppData {
	
	protected List<UnitOfMeasure> unitOfMeasureList;
	protected List<StatisticType> statisticTypeList;
	protected List<AlleleAssay> alleleAssayList;
	protected List<StudyProtocol> studyProtocolList;
		
	public AppData() {}

	public List<UnitOfMeasure> getUnitOfMeasureList() {
		return unitOfMeasureList;
	}

	public void setUnitOfMeasureList(List<UnitOfMeasure> unitOfMeasureList) {
		this.unitOfMeasureList = unitOfMeasureList;
	}

	public List<StatisticType> getStatisticTypeList() {
		return statisticTypeList;
	}

	public void setStatisticTypeList(List<StatisticType> statisticTypeList) {
		this.statisticTypeList = statisticTypeList;
	}

	public List<AlleleAssay> getAlleleAssayList() {
		return alleleAssayList;
	}

	public void setAlleleAssayList(List<AlleleAssay> alleleAssayList) {
		this.alleleAssayList = alleleAssayList;
	}

	public List<StudyProtocol> getStudyProtocolList() {
		return studyProtocolList;
	}

	public void setStudyProtocolList(List<StudyProtocol> studyProtocolList) {
		this.studyProtocolList = studyProtocolList;
	}
	
}
