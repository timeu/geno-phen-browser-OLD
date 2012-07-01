package com.gmi.nordborglab.browser.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.BreadcrumbItem;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.repository.ExperimentRepository;
import com.gmi.nordborglab.browser.server.repository.TraitUomRepository;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;
import com.gmi.nordborglab.browser.server.testutils.SecurityUtils;

public class HelperServiceTest extends BaseTest {
	
	@Resource
	private HelperService service;
	
	@Resource
	private ExperimentRepository experimentRepository;
	
	@Resource
	private TraitUomRepository traitUomRepository;
	
	@Before
	public void setUp() {
		
		
	}
	
	@After
	public void clearContext() {
		SecurityUtils.clearContext();
	}
	
	@Test
	public void testBreadCrumbs() {
		Experiment experiment = experimentRepository.findOne(1L);
		TraitUom trait = traitUomRepository.findOne(1L);
		List<BreadcrumbItem> breadcrumbs = service.getBreadcrumbs(1L, "phenotype");
		
		assertNotNull(breadcrumbs);
		assertEquals(2, breadcrumbs.size());
		BreadcrumbItem experimentItem = breadcrumbs.get(0);
		
		assertEquals(experiment.getId(),experimentItem.getId());
		assertEquals(experiment.getName(),experimentItem.getText());
		assertEquals("experiment",experimentItem.getType());
		
		BreadcrumbItem phenotypeItem = breadcrumbs.get(1);
		assertEquals(trait.getId(),phenotypeItem.getId());
		assertEquals(trait.getLocalTraitName(),phenotypeItem.getText());
		assertEquals("phenotype",phenotypeItem.getType());
	}
	
	 
}
