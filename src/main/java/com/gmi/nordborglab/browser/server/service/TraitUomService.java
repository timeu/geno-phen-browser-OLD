package com.gmi.nordborglab.browser.server.service;

import com.gmi.nordborglab.browser.server.domain.pages.TraitUomPage;

public interface TraitUomService {
	TraitUomPage findPhenotypesByExperiment(Long id,int page, int size);
	int countPhenotypeByExperimentCount(Long id);
}
