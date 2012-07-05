package com.gmi.nordborglab.browser.client;

public class NameTokens {

	public static final String home = "home";
	public static final String experiments = "experiments";
	public static final String experiment = "experiment/{id}/overview";
	public static final String phenotypes = "experiment/{id}/phenotypes";
	public static final String phenotype = "phenotype/{id}/overview";
	public static final String obsunit = "phenotype/{id}/obsunits";
	public static final String studylist = "phenotype/{id}/studylist";
	public static final String study = "study/{id}/overview";
	public static final String studygwas = "study/{id}/studygwas";

	public static String getHome() {
		return home;
	}

	public static String getExperiments() {
		return experiments;
	}

	public static String getExperiment() {
		return experiment;
	}

	public static String getPhenotypes() {
		return phenotypes;
	}

	public static String getPhenotype() {
		return phenotype;
	}

	public static String getObsunit() {
		return obsunit;
	}

	public static String getStudylist() {
		return studylist;
	}

	public static String getStudy() {
		return study;
	}

	public static String getStudygwas() {
		return studygwas;
	}
	
	
}
