package com.gmi.nordborglab.browser.server.service;

import com.gmi.nordborglab.browser.server.domain.pages.ObsUnitPage;

public interface ObsUnitService {

	public ObsUnitPage findObsUnits(Long id,int start, int size);
}
