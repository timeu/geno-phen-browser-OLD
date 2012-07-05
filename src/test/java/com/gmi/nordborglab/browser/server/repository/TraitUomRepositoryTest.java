package com.gmi.nordborglab.browser.server.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.domain.phenotype.UnitOfMeasure;
import com.gmi.nordborglab.browser.server.testutils.BaseTest;


public class TraitUomRepositoryTest extends BaseTest {
	
	@Resource
	protected TraitUomRepository repository;
	
	@Test
	public void testFindById() {
		TraitUom actual = repository.findOne(1L);
		assertNotNull("did not find expected entity", actual);
		assertEquals((double)1L, (double)actual.getId(),0L);
	}
	
	@Test
	public void testDeleteById() {
		repository.delete(1L);
		TraitUom deleted = repository.findOne(1L);
		assertNull("delete did not work", deleted);
	}
	
	@Test
	public void testCreate() {
		TraitUom created = new TraitUom();
		created.setLocalTraitName("test");
		TraitUom actual = repository.save(created);
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("local trait name is incorrect", "test",actual.getLocalTraitName());
	}
	
	@Test
	public void testCreateWithAllRelationships() {
		TraitUom created = createTraitUomWithAllDependencies();
		TraitUom actual = repository.save(created);
		assertTraitUom(actual);
	}
	
	@Test
	public void testRetrieveAllByExperiment() {
		List<TraitUom> traits = repository.findByExperimentId(1L);
		assertEquals(107, traits.size());
	}
	
	@Test
	public void testCountObsUnitsByPhenotypeId() {
		Long count = repository.countObsUnitsByPhenotypeId(1L);
		assertEquals(new Long(167L), count);
	}
	
	@Test
	public void testCountStudiesByPhenotypeId() {
		Long count = repository.countStudiesByPhenotypeId(1L);
		assertEquals(new Long(1), count);
	}
	
	@Test
	public void testFindByStudyId() {
		TraitUom trait = repository.findByStudyId(1L);
		assertNotNull("trait not found",trait);
		assertEquals("id of trait is wrong",1L, trait.getId().longValue());
	}
	
	public static TraitUom createTraitUomWithAllDependencies() {
		TraitUom created = new TraitUom();
		created.setLocalTraitName("test");
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setUnitType("test");
		created.setUnitOfMeasure(unitOfMeasure);
		return created;
	}
	
	public static void assertTraitUom(TraitUom actual) {
		assertNotNull("create did not work",actual);
		assertNotNull("couldn't generate id",actual.getId());
		assertEquals("name is incorrect", "test",actual.getLocalTraitName());
		assertNotNull("Experiment is not set", actual.getUnitOfMeasure());
		assertEquals("unit type for UnitOfMeasure incorrect", "test",actual.getUnitOfMeasure().getUnitType());
	}
	
	
	
}
