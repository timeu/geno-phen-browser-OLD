package com.gmi.nordborglab.browser.shared.proxy;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gmi.nordborglab.browser.server.domain.cdv.Study;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=Study.class, locator=SpringEntitiyLocator.class)
public interface StudyProxy extends EntityProxy {
	
	public Long getId();
	
	@NotNull
	public StudyProtocolProxy getProtocol();
	public void setProtocol(StudyProtocolProxy protocol);
	
	@NotNull
	public AlleleAssayProxy getAlleleAssay();
	public void setAlleleAssay(AlleleAssayProxy alleleAssay);
	
	@NotNull
	@Size(min=5)
	public String getName();
	public void setName(String name);
	
	public String getProducer();
	public void setProducer(String producer);
	
	public Date getStudyDate();
	public void setStudyDate(Date date);
	
	public Boolean isDone();
	public void setIsDone(Boolean isDone);

	public AccessControlEntryProxy getUserPermission();
	public boolean isOwner();
	
}
