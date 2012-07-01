package com.gmi.nordborglab.browser.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmi.nordborglab.browser.server.domain.germplasm.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
