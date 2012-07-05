package com.gmi.nordborglab.browser.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmi.nordborglab.browser.server.domain.BreadcrumbItem;
import com.gmi.nordborglab.browser.server.domain.cdv.Study;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.phenotype.TraitUom;
import com.gmi.nordborglab.browser.server.repository.ExperimentRepository;
import com.gmi.nordborglab.browser.server.repository.StudyRepository;
import com.gmi.nordborglab.browser.server.repository.TraitUomRepository;
import com.gmi.nordborglab.browser.server.service.HelperService;

@Service
@Transactional(readOnly = true)
public class HelperServiceImpl implements HelperService {
	
	@Resource
	private ExperimentRepository experimentRepository;
	
	@Resource
	private TraitUomRepository traitUomRepository;
	
	@Resource
	private StudyRepository studyRepository;
	

	@Override
	public List<BreadcrumbItem> getBreadcrumbs(Long id, String object) {
		List<BreadcrumbItem> breadcrumbs = new ArrayList<BreadcrumbItem>();
		if (object.equals("experiment") || object.equals("phenotypes")) {
			Experiment exp = experimentRepository.findOne(id);
			breadcrumbs.add(new BreadcrumbItem(exp.getId(),exp.getName(),"experiment"));
		}
		else if (object.equals("phenotype")) {
			Experiment exp = experimentRepository.findByPhenotypeId(id);
			TraitUom traitUom = traitUomRepository.findOne(id);
		    breadcrumbs.add(new BreadcrumbItem(exp.getId(),exp.getName(),"experiment"));
		    breadcrumbs.add(new BreadcrumbItem(traitUom.getId(),traitUom.getLocalTraitName(),"phenotype"));
		}
		else if (object.equals("study")) {
			Study study = studyRepository.findOne(id);
			TraitUom trait = study.getTraits().get(0).getTraitUom();
			Experiment exp = study.getTraits().get(0).getObsUnit().getExperiment();
			breadcrumbs.add(new BreadcrumbItem(exp.getId(),exp.getName(),"experiment"));
		    breadcrumbs.add(new BreadcrumbItem(trait.getId(),trait.getLocalTraitName(),"phenotype"));
		    breadcrumbs.add(new BreadcrumbItem(study.getId(),study.getName(),"study"));
		}
		return breadcrumbs;
	}

}
