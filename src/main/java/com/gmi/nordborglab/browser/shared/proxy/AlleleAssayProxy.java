package com.gmi.nordborglab.browser.shared.proxy;

import java.util.Date;

import com.gmi.nordborglab.browser.server.domain.genotype.AlleleAssay;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=AlleleAssay.class, locator=SpringEntitiyLocator.class)
public interface AlleleAssayProxy extends EntityProxy {

	public Long getId();
	
	public ScoringTechTypeProxy getScoringTechType();
	
	public PolyTypeProxy getPolyType();
	
	public String getName();
	public void setName(String name);
	
	public String getProducer();
	public void setProducer(String producer);
	
	public String getComments();
	public void setComments(String comments);
	
	public Date getAssayDate();
	public void setAssayDate(Date assayDate);
}
