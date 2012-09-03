package com.gmi.nordborglab.browser.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.AppData;
import com.gmi.nordborglab.browser.server.domain.BreadcrumbItem;
import com.gmi.nordborglab.browser.server.domain.cdv.Study;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.repository.ExperimentRepository;
import com.gmi.nordborglab.browser.server.repository.StudyRepository;
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
	
	@Resource
	private StudyRepository studyRepository;
	
	@Before
	public void setUp() {
		
		
	}
	
	@After
	public void clearContext() {
		SecurityUtils.clearContext();
	}
	
	
	@Test
	public void testExperimentBreadcrumbs() {
		Experiment experiment = experimentRepository.findOne(1L);
		List<BreadcrumbItem> breadcrumbs = service.getBreadcrumbs(1L, "experiment");
		
		
		assertNotNull(breadcrumbs);
		assertEquals(1, breadcrumbs.size());
		BreadcrumbItem experimentItem = breadcrumbs.get(0);
		
		assertEquals(experiment.getId(),experimentItem.getId());
		assertEquals(experiment.getName(),experimentItem.getText());
		assertEquals("experiment",experimentItem.getType());
	}
	
	@Test
	public void testPhenotypeBreadCrumbs() {
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
	
	@Test
	public void testStudyBreadcrumbs() {
		Experiment experiment = experimentRepository.findOne(1L);
		TraitUom trait = traitUomRepository.findOne(1L);
		Study study = studyRepository.findOne(1L);
		List<BreadcrumbItem> breadcrumbs = service.getBreadcrumbs(1L, "study");
		
		
		assertNotNull(breadcrumbs);
		assertEquals(3, breadcrumbs.size());
		BreadcrumbItem experimentItem = breadcrumbs.get(0);
		
		assertEquals(experiment.getId(),experimentItem.getId());
		assertEquals(experiment.getName(),experimentItem.getText());
		assertEquals("experiment",experimentItem.getType());
		
		BreadcrumbItem phenotypeItem = breadcrumbs.get(1);
		assertEquals(trait.getId(),phenotypeItem.getId());
		assertEquals(trait.getLocalTraitName(),phenotypeItem.getText());
		assertEquals("phenotype",phenotypeItem.getType());
		
		BreadcrumbItem studyItem = breadcrumbs.get(2);
		assertEquals(study.getId(),studyItem.getId());
		assertEquals(study.getName(),studyItem.getText());
		assertEquals("study",studyItem.getType());
	}
	
	@Test
	public void testStudyWizardBreadcrumbs() {
		Experiment experiment = experimentRepository.findOne(1L);
		TraitUom trait = traitUomRepository.findOne(1L);
		List<BreadcrumbItem> breadcrumbs = service.getBreadcrumbs(1L, "studywizard");
		
		
		assertNotNull(breadcrumbs);
		assertEquals(3, breadcrumbs.size());
		BreadcrumbItem experimentItem = breadcrumbs.get(0);
		
		assertEquals(experiment.getId(),experimentItem.getId());
		assertEquals(experiment.getName(),experimentItem.getText());
		assertEquals("experiment",experimentItem.getType());
		
		BreadcrumbItem phenotypeItem = breadcrumbs.get(1);
		assertEquals(trait.getId(),phenotypeItem.getId());
		assertEquals(trait.getLocalTraitName(),phenotypeItem.getText());
		assertEquals("phenotype",phenotypeItem.getType());
		
		BreadcrumbItem studyItem = breadcrumbs.get(2);
		assertEquals(trait.getId(),studyItem.getId());
		assertEquals("New Study",studyItem.getText());
		assertEquals("studywizard",studyItem.getType());
	}
	
	@Test
	public void testGetAppData() {
		AppData data = service.getAppData();
		assertNotNull("could not retrive AppData",data);
		assertNotNull("Could not retrieve StatisticTypeList",data.getStatisticTypeList());
		assertTrue("no elements found for StatisticTypeList",data.getStatisticTypeList().size()>0);
		
		assertNotNull("Could not retrieve StudyProtocolList",data.getStudyProtocolList());
		assertTrue("no elements found for StudyProtocolList",data.getStudyProtocolList().size()>0);
		
		assertNotNull("Could not retrieve AlleleAssayList",data.getAlleleAssayList());
		assertTrue("no elements found for StatisticTypeList",data.getAlleleAssayList().size()>0);
		
		assertNotNull("Could not retrieve UnitOfMeasureList",data.getUnitOfMeasureList());
		assertTrue("no elements found for UnitOfMeasureList",data.getUnitOfMeasureList().size()>0);
	}
	 
}
