package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.germplasm.Passport;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=Passport.class, locator=SpringEntitiyLocator.class)
public interface PassportProxy extends EntityProxy {

	public Long getId();
	
    public TaxonomyProxy getTaxonomy();
    public void setTaxonomy(TaxonomyProxy taxonomy);
    
    public AccessionCollectionProxy getCollection();
    public void setCollection(AccessionCollectionProxy collection);
    
    public String getAccename();
    public void setAccename(String accename);
    
    public String getSource();
    public void setSource(String source);
    
    public String getAccenumb();
    public void setAccenumb(String accenumb);
    
    
    public SampStatProxy getSampstat();
    public void setSampstat(SampStatProxy sampstat);
    
    
    public String getComments();
    public void setComments(String comments);
}
