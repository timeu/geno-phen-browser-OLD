package com.gmi.nordborglab.browser.shared.proxy;

import java.util.List;

import com.gmi.nordborglab.browser.server.domain.germplasm.Taxonomy;
import com.gmi.nordborglab.browser.server.service.SpringEntitiyLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=Taxonomy.class, locator=SpringEntitiyLocator.class)
public interface TaxonomyProxy extends EntityProxy {

	public Long getId();
	
	public String getGenus();
	public void setGenus(String genus);
	
	public String getSpecies();
	public void setSpecies(String species);
	
	public String getSubspecies();
	public void setSubspecies(String subspecies);
	
	public String getSubtaxa();
	public void setSubtaxa(String subtaxa);
	
	public String getRace();
	public void setRace(String race);
	
	public String getPopulation();
	public void setPopulation(String population);
	
	public String getCommonName();
	public void setCommonName(String commonName);
	
	public String getTermAccession();
	public void setTermAccession(String termAccession);
	
	public List<AlleleAssayProxy> getAlleleAssays();
}
