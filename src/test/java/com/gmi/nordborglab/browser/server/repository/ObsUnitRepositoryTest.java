package com.gmi.nordborglab.browser.server.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.germplasm.Stock;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.observation.Locality;
import com.gmi.nordborglab.browser.server.domain.observation.ObsUnit;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;


public class ObsUnitRepositoryTest extends BaseTest{
	
	@Resource
	protected ObsUnitRepository repository;
	

	@Test
	public void testFindById() {
		ObsUnit actual = repository.findOne(1L);
		assertNotNull("did not find expected entity", actual);
		assertEquals((double)1L, (double)actual.getId(),0L);
	}
	
	@Test
	public void testDeleteById() {
		repository.delete(1L);
		ObsUnit deleted = repository.findOne(1L);
		assertNull("delete did not work", deleted);
	}
	
	@Test
	public void testCreate() {
		ObsUnit created = new ObsUnit();
		created.setName("test");
		ObsUnit actual = repository.save(created);
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("Common name is correct", "test",actual.getName());
	}
	
	@Test
	public void testCreateWithAllRelationships() {
		ObsUnit created = createObsUnitWithAllDependencies();
		ObsUnit actual = repository.save(created);
		assertObsUnit(actual);
	}
	
	
	public static ObsUnit createObsUnitWithAllDependencies() {
		ObsUnit created = new ObsUnit();
		created.setName("test");
		Stock stock = new Stock();
		stock.setStockSource("test");
		created.setStock(stock);
		Locality locality = new Locality();
		locality.setLocalityName("test");
		created.setLocality(locality);
		Experiment experiment = new Experiment();
		experiment.setName("test");
		created.setExperiment(experiment);
		return created;
	}
	
	public static void assertObsUnit(ObsUnit actual) {
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("name is incorrect", "test",actual.getName());
		assertNotNull("Experiment is not set", actual.getExperiment());
		assertEquals("wrong name for Experiment", "test",actual.getExperiment().getName());
		assertNotNull("Locality is not set",actual.getLocality());
		assertEquals("wrong name for Locality", "test",actual.getLocality().getLocalityName());
		assertNotNull("Stock is not set",actual.getStock());
		assertEquals("wrong name for Stock", "test",actual.getStock().getStockSource());
	}
}
