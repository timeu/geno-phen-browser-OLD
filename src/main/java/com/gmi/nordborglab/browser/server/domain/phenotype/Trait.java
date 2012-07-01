package com.gmi.nordborglab.browser.server.domain.phenotype;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gmi.nordborglab.browser.server.domain.BaseEntity;
import com.gmi.nordborglab.browser.server.domain.observation.ObsUnit;

@Entity
@Table(name="div_trait",schema="phenotype")
@AttributeOverride(name="id", column=@Column(name="div_trait_id"))
@SequenceGenerator(name="idSequence", sequenceName="phenotype.div_trait_div_trait_id_seq")
public class Trait extends BaseEntity {

	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="div_trait_uom_id")
	private TraitUom traitUom;
	
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="div_statistic_type_id")
	private StatisticType statisticType;
	
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="div_obs_unit_id")
	private ObsUnit obsUnit;
	
	private String value;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_measured;
	
	public Trait() { }

	public TraitUom getTraitUom() {
		return traitUom;
	}

	public void setTraitUom(TraitUom traitUom) {
		this.traitUom = traitUom;
	}

	public StatisticType getStatisticType() {
		return statisticType;
	}

	public void setStatisticType(StatisticType statisticType) {
		this.statisticType = statisticType;
	}

	public ObsUnit getObsUnit() {
		return obsUnit;
	}

	public void setObsUnit(ObsUnit obsUnit) {
		this.obsUnit = obsUnit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDateMeasured() {
		return date_measured;
	}

	public void setDateMeasured(Date dateMeasured) {
		this.date_measured = dateMeasured;
	}
	
	
}
