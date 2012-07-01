package com.gmi.nordborglab.browser.client.gin;

import com.gmi.nordborglab.browser.client.mvp.home.HomePresenter;
import com.gmi.nordborglab.browser.client.mvp.home.dashboard.DashboardPresenter;
import com.gmi.nordborglab.browser.client.mvp.main.MainPagePresenter;
import com.gmi.nordborglab.browser.client.resources.MainResources;
import com.gmi.nordborglab.browser.client.ui.SimpleTabPanel;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.gmi.nordborglab.browser.client.mvp.diversity.DiversityPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentsOverviewPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentsOverviewTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeListPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeDetailPresenter;


@GinModules({ClientModule.class})
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
}
