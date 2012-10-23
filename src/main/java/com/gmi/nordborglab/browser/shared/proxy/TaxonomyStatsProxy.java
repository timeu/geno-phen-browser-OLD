package com.gmi.nordborglab.browser.shared.proxy;

import com.gmi.nordborglab.browser.server.domain.TaxonomyStats;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=TaxonomyStats.class)
public interface TaxonomyStatsProxy extends ValueProxy {

	public String getGeoChartData();
	public String getSampStatData();
	public String getAlleleAssayData();
	public String getStockGenerationData();
}
