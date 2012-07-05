package com.gmi.nordborglab.browser.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;

public interface TraitUomRepository extends JpaRepository<TraitUom, Long> {

	@Query("SELECT t FROM TraitUom t JOIN t.traits as trait JOIN trait.obsUnit as o JOIN o.experiment as e JOIN t.acl as acl WHERE EXISTS ( FROM AclTraitUomEntry ace WHERE ace.objectIdentity = acl AND ace.sid.sid IN (:sids) AND mod(ace.mask/:permission,2)=1) AND e.id = :experimentId")
	public Page<TraitUom> findByExperimentId(
			@Param("experimentId") Long experimentId,
			@Param("sids") List<String> sids,
			@Param("permission") Integer permission, Pageable page);
	
	@Query("SELECT DISTINCT t FROM TraitUom t JOIN t.traits as trait JOIN trait.obsUnit as o JOIN o.experiment as e WHERE e.id = :experimentId order by t.id")
	public List<TraitUom> findByExperimentId(@Param("experimentId") Long experimentId);
	
	@Query("SELECT DISTINCT t FROM TraitUom t JOIN t.traits as trait JOIN trait.studies as s WHERE s.id = :studyId")
	public TraitUom findByStudyId(@Param("studyId") Long studyId);
	
	@Query("SELECT COUNT(DISTINCT o) FROM ObsUnit o JOIN o.traits as t JOIN t.traitUom as uom WHERE uom.id = :phenotypeId")
	Long countObsUnitsByPhenotypeId(@Param("phenotypeId") Long phenotypeId);
	
	@Query("SELECT COUNT(DISTINCT s) FROM Study s JOIN s.traits as t JOIN t.traitUom as uom WHERE uom.id = :phenotypeId")
	Long countStudiesByPhenotypeId(@Param("phenotypeId") Long phenotypeId);
	
	
}
