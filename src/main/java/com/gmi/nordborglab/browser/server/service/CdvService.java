package com.gmi.nordborglab.browser.server.service;

import com.gmi.nordborglab.browser.server.domain.cdv.Study;
import com.gmi.nordborglab.browser.server.domain.pages.StudyPage;

public interface CdvService {
	
	public StudyPage findStudiesByPhenotypeId(Long id,int start, int size);
	
	public Study findStudy(Long id);
}
