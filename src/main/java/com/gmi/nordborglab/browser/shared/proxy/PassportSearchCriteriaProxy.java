package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.PassportSearchCriteria;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(PassportSearchCriteria.class)
public interface PassportSearchCriteriaProxy extends ValueProxy {
	
	public Long getPassportId();
	public void setPassportId(Long passportId);
	
	public Long getSampStatId();
	public void setSampStatId(Long sampStatId);
	
	public String getAccName();
	public void setAccName(String accName);

	public List<String> getCountries();
	public void setCountries(List<String> countries);
	
	public String getCollector();
	public void setCollector(String collector);
	
	public String getAccNumber();
	public void setAccNumber(String accNumber);
	
	public String getSource();
	public void setSource(String source);
	
	public List<Long> getAlleleAssayIds();
	public void setAlleleAssayIds(List<Long> alleleAssayIds);
}
