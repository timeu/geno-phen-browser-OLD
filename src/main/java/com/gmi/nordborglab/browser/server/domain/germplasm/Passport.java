package com.gmi.nordborglab.browser.server.domain.germplasm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.gmi.nordborglab.browser.server.domain.BaseEntity;
import com.gmi.nordborglab.browser.server.domain.genotype.Allele;


@Entity 
@Table(name="div_passport", schema="germplasm")
@AttributeOverride(name="id", column=@Column(name="div_passport_id"))
@SequenceGenerator(name="idSequence", sequenceName="germplasm.div_passport_div_passport_id_seq")
public class Passport extends BaseEntity{
	
	@ManyToOne()
	@JoinColumn(name="div_taxonomy_id")
    private Taxonomy taxonomy;
    
    @ManyToOne()
    @JoinColumn(name="div_accession_collecting_id")
    private AccessionCollection collection;
    
    @OneToMany(mappedBy="passport",cascade={CascadeType.PERSIST,CascadeType.MERGE})
    private List<Stock> stocks = new ArrayList<Stock>();
    
    @OneToMany(mappedBy="passport",cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Set<Allele> alleles = new HashSet<Allele>();
    
    @ManyToOne()
    @JoinColumn(name="div_sampstat_id")
    private Sampstat sampstat;
    
    private String accename;
    private String source;
    private String accenumb;
    private String comments;
    
    public Passport() {
    
    }

	public Taxonomy getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}

	public AccessionCollection getCollection() {
		return collection;
	}

	public void setCollection(AccessionCollection collection) {
		this.collection = collection;
	}

	public String getAccename() {
		return accename;
	}

	public void setAccename(String accename) {
		this.accename = accename;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAccenumb() {
		return accenumb;
	}

	public void setAccenumb(String acceNumb) {
		this.accenumb = acceNumb;
	}

	public Sampstat getSampstat() {
		return sampstat;
	}

	public void setSampstat(Sampstat sampstat) {
		this.sampstat = sampstat;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Stock> getStocks() {
		return Collections.unmodifiableList(stocks);
	}

	public Set<Allele> getAlleles() {
		return alleles;
	}
	
}
