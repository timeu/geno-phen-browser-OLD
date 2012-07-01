package com.gmi.nordborglab.browser.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmi.nordborglab.browser.server.domain.phenotype.Trait;

public interface TraitRepository extends JpaRepository<Trait, Long> {

}
