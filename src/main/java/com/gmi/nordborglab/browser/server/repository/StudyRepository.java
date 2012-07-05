package com.gmi.nordborglab.browser.server.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmi.nordborglab.browser.server.domain.cdv.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

	@Query("SELECT DISTINCT s FROM Study s JOIN s.traits as t JOIN t.traitUom as uom WHERE uom.id = :phenotypeId")
	Page<Study> findByPhenotypeId(@Param("phenotypeId") Long phenotypeId, Pageable pageRequest);

}
