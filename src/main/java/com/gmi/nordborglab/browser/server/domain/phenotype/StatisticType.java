package com.gmi.nordborglab.browser.server.domain.phenotype;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.gmi.nordborglab.browser.server.domain.BaseEntity;

@Entity
@Table(name="div_statistic_type",schema="phenotype")
@AttributeOverride(name="id", column=@Column(name="div_statistic_type_id"))
@SequenceGenerator(name="idSequence", sequenceName="phenotype.div_statistics_type_div_statistic_type_id_seq")
public class StatisticType extends BaseEntity {
	
	private String stat_type;
	
	public void setStatType(String statType) {
		this.stat_type = statType;
	}
	
	public String getStatType() {
		return this.stat_type;
	}

}
