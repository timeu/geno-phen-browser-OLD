package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.AppData;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=AppData.class)
public interface AppDataProxy extends ValueProxy {

	public List<UnitOfMeasureProxy> getUnitOfMeasureList();
	public void setUnitOfMeasureList(List<UnitOfMeasureProxy> unitOfMeasures);
	
	public List<StatisticTypeProxy> getStatisticTypeList();
	public void setStatisticTypeList(List<StatisticTypeProxy> statisticTypeList);
	
	
	public List<StudyProtocolProxy> getStudyProtocolList();
	public void setStudyProtocolList(List<StudyProtocolProxy> studyProtocolList);
	
	public List<AlleleAssayProxy> getAlleleAssayList();
	public void setAlleleAssayList(List<AlleleAssayProxy> alleleAssayList);
	
}
