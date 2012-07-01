package com.gmi.nordborglab.browser.server.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.pages.TraitUomPage;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;
import com.gmi.nordborglab.browser.server.testutils.SecurityUtils;


public class TraitUomServiceTest extends BaseTest {
	
	@Resource
	private TraitUomService traitUomService;

	@Before
	public void setUp() {
	}
	
	@After
	public void clearContext() {
		SecurityUtils.clearContext();
	}
	
	
	@Test
	public void testFindPhenotypesByExperiment() {
		SecurityUtils.setAnonymousUser();
		TraitUomPage page = traitUomService.findPhenotypesByExperiment(1L, 0, 50);
		assertEquals(107L, page.getTotalElements());
		assertEquals(page.getNumberOfElements(), 50);
		assertEquals(50, page.getContent().size());
	}
	
	@Test
	public void testCountPhenotypesByExperiment() {
		SecurityUtils.setAnonymousUser();
		int count = traitUomService.countPhenotypeByExperimentCount(1L);
		assertEquals(107, count);
	}
}
