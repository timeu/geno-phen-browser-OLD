package com.gmi.nordborglab.browser.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmi.nordborglab.browser.server.domain.observation.ObsUnit;

public interface ObsUnitRepository extends JpaRepository<ObsUnit, Long> {

	@Query("SELECT DISTINCT o FROM ObsUnit o JOIN o.traits as t JOIN t.traitUom as uom WHERE uom.id = :phenotypeId")
	Page<ObsUnit> findByPhenotypeId(@Param("phenotypeId") Long phenotypeId,Pageable page);

}
