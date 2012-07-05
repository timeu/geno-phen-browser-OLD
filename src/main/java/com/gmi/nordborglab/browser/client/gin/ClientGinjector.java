package com.gmi.nordborglab.browser.client.gin;

import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.DiversityPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentsOverviewPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentsOverviewTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.PhenotypeListPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype.ObsUnitPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype.PhenotypeDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype.PhenotypeDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype.StudyListPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study.StudyDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study.StudyGWASPlotPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study.StudyTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.home.HomePresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.home.dashboard.DashboardPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.main.MainPagePresenter;
import com.gmi.nordborglab.browser.client.resources.MainResources;
import com.gmi.nordborglab.browser.client.ui.SimpleTabPanel;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;


@GinModules({ClientDispatchModule.class,ClientModule.class})
public interface ClientGinjector extends Ginjector{
	EventBus getEventBus();
	MainResources getMainResources();
	PlaceManager getPlaceManager();
	Provider<MainPagePresenter> getMainPagePresenter();
	Provider<HomePresenter> getHomePresenter();
	AsyncProvider<DashboardPresenter> getDashboardPresenter();
	AsyncProvider<DiversityPresenter> getDiversityPresenter();
	AsyncProvider<ExperimentsOverviewPresenter> getExperimentsOverviewPresenter();
	AsyncProvider<ExperimentDetailPresenter> getExperimentDetailPresenter();
	SimpleTabPanel getSimpleTabPanel();
	AsyncProvider<ExperimentsOverviewTabPresenter> getExperimentsOverviewTabPresenter();
	AsyncProvider<ExperimentDetailTabPresenter> getExperimentDetailTabPresenter();
	AsyncProvider<PhenotypeListPresenter> getPhenotypeListPresenter();
	AsyncProvider<PhenotypeDetailTabPresenter> getPhenotypeDetailTabPresenter();
	AsyncProvider<PhenotypeDetailPresenter> getPhenotypeDetailPresenter();
	AsyncProvider<ObsUnitPresenter> getObsUnitPresenter();
	AsyncProvider<StudyListPresenter> getStudyListPresenter();
	AsyncProvider<StudyTabPresenter> getStudyTabPresenter();
	AsyncProvider<StudyDetailPresenter> getStudyDetailPresenter();
	AsyncProvider<StudyGWASPlotPresenter> getStudyGWASPlotPresenter();
}
