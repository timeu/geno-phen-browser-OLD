package com.gmi.nordborglab.browser.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmi.nordborglab.browser.server.domain.germplasm.Taxonomy;

public interface TaxonomyRepository extends JpaRepository<Taxonomy, Long> {

}
