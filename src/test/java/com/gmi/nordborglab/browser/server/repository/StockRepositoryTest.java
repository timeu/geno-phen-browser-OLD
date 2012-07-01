package com.gmi.nordborglab.browser.server.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.germplasm.Generation;
import com.gmi.nordborglab.browser.server.domain.germplasm.Stock;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;


public class StockRepositoryTest extends BaseTest{
	
	@Resource
	protected StockRepository repository;

	@Test
	public void testFindById() {
		Stock actual = repository.findOne(1L);
		assertNotNull("did not find expected entity", actual);
		assertEquals((double)1L, (double)actual.getId(),0L);
	}
	
	@Test
	public void testDeleteById() {
		repository.delete(1L);
		Stock deleted = repository.findOne(1L);
		assertNull("delete did not work", deleted);
	}
	
	@Test
	public void testCreate() {
		Stock created = new Stock();
		created.setStockSource("test");
		Stock actual = repository.save(created);
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("Common name is correct", "test",actual.getStockSource());
	}
	
	@Test
	public void testCreateWithAllRelationships() {
		Stock created = new Stock();
		created.setStockSource("test");
		Generation generation = new Generation();
		generation.setIcisId("test");
		created.setGeneration(generation);
		Stock actual = repository.save(created);
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertNotNull("Generation is not set", actual.getGeneration());
		assertEquals("Icis of Generation is not correct", "test",actual.getGeneration().getIcisId());
	}
}
