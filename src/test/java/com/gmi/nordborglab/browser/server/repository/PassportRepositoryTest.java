package com.gmi.nordborglab.browser.server.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.germplasm.AccessionCollection;
import com.gmi.nordborglab.browser.server.domain.germplasm.Passport;
import com.gmi.nordborglab.browser.server.domain.germplasm.Taxonomy;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;


public class PassportRepositoryTest extends BaseTest{

	@Resource
	protected PassportRepository repository;
	
	@Test
	public void testFindById() {
		Passport passport = repository.findOne(1L);
		assertNotNull("did not find expected entity", passport);
		assertEquals((double)1L, (double)passport.getId(),0L);
		assertNotNull("did not find attached Taxonomy",passport.getTaxonomy());
		assertNotNull("did not find attached Collection",passport.getCollection());
	}
	
	@Test
	public void testDeleteById() {
		repository.delete(1L);
		Passport deleted = repository.findOne(1L);
		assertNull("delete did not work", deleted);
	}
	
	@Test
	public void testCreate() {
		Passport created = new Passport();
		created.setAccename("test");
		Passport actual = repository.save(created);
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("name is correct", "test",actual.getAccename());
	}
	
	@Test
	public void testCreateWithAllRelationships() {
		Passport created = new Passport();
		created.setAccename("test");
		AccessionCollection collection = new AccessionCollection();
		created.setCollection(collection);
		Taxonomy taxonomy = new Taxonomy();
		taxonomy.setCommonName("test");
		created.setTaxonomy(taxonomy);
		Passport actual = repository.save(created);
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("name is correct", "test",actual.getAccename());
		assertNotNull("Taxonomy is not set", actual.getTaxonomy());
		assertNotNull("Collecting is not set",actual.getCollection());
		assertEquals("wrong CommonName for Taxonomy", "test",actual.getTaxonomy().getCommonName());
	}
}
