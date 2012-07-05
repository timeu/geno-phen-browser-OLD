package com.gmi.nordborglab.browser.client.gin;

import at.gmi.nordborglab.widgets.geneviewer.client.datasource.DataSource;
import at.gmi.nordborglab.widgets.geneviewer.client.datasource.LocalStorageImpl;
import at.gmi.nordborglab.widgets.geneviewer.client.datasource.LocalStorageImpl.TYPE;
import at.gmi.nordborglab.widgets.geneviewer.client.datasource.impl.JBrowseCacheDataSourceImpl;

import com.gmi.nordborglab.browser.client.ClientPlaceManager;
import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.ParameterizedParameterTokenFormatter;
import com.gmi.nordborglab.browser.client.manager.CdvManager;
import com.gmi.nordborglab.browser.client.manager.ExperimentManager;
import com.gmi.nordborglab.browser.client.manager.HelperManager;
import com.gmi.nordborglab.browser.client.manager.ObsUnitManager;
import com.gmi.nordborglab.browser.client.manager.PhenotypeManager;
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
import com.gmi.nordborglab.browser.client.mvp.presenter.main.UserInfoPresenter;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.DiversityView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.ExperimentDetailTabView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.ExperimentDetailView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.ExperimentsOverviewTabView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.ExperimentsOverviewView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.PhenotypeListView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.phenotype.ObsUnitView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.phenotype.PhenotypeDetailTabView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.phenotype.PhenotypeDetailView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.phenotype.StudyListView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.study.StudyDetailView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.study.StudyGWASPlotView;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.study.StudyTabView;
import com.gmi.nordborglab.browser.client.mvp.view.home.HomeView;
import com.gmi.nordborglab.browser.client.mvp.view.home.dashboard.DashboardView;
import com.gmi.nordborglab.browser.client.mvp.view.main.MainPageView;
import com.gmi.nordborglab.browser.client.mvp.view.main.UserInfoView;
import com.gmi.nordborglab.browser.client.resources.FlagMap;
import com.gmi.nordborglab.browser.client.resources.MainResources;
import com.gmi.nordborglab.browser.shared.service.AppUserFactory;
import com.gmi.nordborglab.browser.shared.service.CustomRequestFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.gwtplatform.dispatch.client.actionhandler.caching.Cache;
import com.gwtplatform.dispatch.client.actionhandler.caching.DefaultCacheImpl;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalytics;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		//install(new DefaultModule(ClientPlaceManager.class));
		
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
	    //bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
	    bind(RootPresenter.class).asEagerSingleton();
	    bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
	    bind(PlaceManager.class).to(ClientPlaceManager.class).in(Singleton.class);
	    
		bind(TokenFormatter.class).to(ParameterizedParameterTokenFormatter.class).in(Singleton.class);
		bind(CurrentUser.class).asEagerSingleton();
		bind(Cache.class).to(DefaultCacheImpl.class).in(Singleton.class);

		bind(MainResources.class).in(Singleton.class);
		bind(ExperimentManager.class).in(Singleton.class);
		bind(PhenotypeManager.class).in(Singleton.class);
		bind(HelperManager.class).in(Singleton.class);
		bind(ObsUnitManager.class).in(Singleton.class);
		bind(CdvManager.class).in(Singleton.class);
		bind(FlagMap.class).in(Singleton.class);
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);

		bind(AppUserFactory.class).asEagerSingleton();

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
				HomeView.class, HomePresenter.MyProxy.class);

		bindPresenterWidget(UserInfoPresenter.class,
				UserInfoPresenter.MyView.class, UserInfoView.class);

		bindPresenter(DashboardPresenter.class,
				DashboardPresenter.MyView.class, DashboardView.class,
				DashboardPresenter.MyProxy.class);

		bindPresenter(DiversityPresenter.class,
				DiversityPresenter.MyView.class, DiversityView.class,
				DiversityPresenter.MyProxy.class);

		bindPresenter(ExperimentsOverviewPresenter.class,
				ExperimentsOverviewPresenter.MyView.class,
				ExperimentsOverviewView.class,
				ExperimentsOverviewPresenter.MyProxy.class);

		bindPresenter(ExperimentDetailPresenter.class,
				ExperimentDetailPresenter.MyView.class,
				ExperimentDetailView.class,
				ExperimentDetailPresenter.MyProxy.class);

		

		bindPresenter(ExperimentsOverviewTabPresenter.class,
				ExperimentsOverviewTabPresenter.MyView.class,
				ExperimentsOverviewTabView.class,
				ExperimentsOverviewTabPresenter.MyProxy.class);

		bindPresenter(ExperimentDetailTabPresenter.class,
				ExperimentDetailTabPresenter.MyView.class,
				ExperimentDetailTabView.class,
				ExperimentDetailTabPresenter.MyProxy.class);

		bindPresenter(PhenotypeListPresenter.class,
				PhenotypeListPresenter.MyView.class, PhenotypeListView.class,
				PhenotypeListPresenter.MyProxy.class);

		bindPresenter(PhenotypeDetailTabPresenter.class,
				PhenotypeDetailTabPresenter.MyView.class,
				PhenotypeDetailTabView.class,
				PhenotypeDetailTabPresenter.MyProxy.class);

		bindPresenter(PhenotypeDetailPresenter.class,
				PhenotypeDetailPresenter.MyView.class,
				PhenotypeDetailView.class,
				PhenotypeDetailPresenter.MyProxy.class);

		bindPresenter(ObsUnitPresenter.class, ObsUnitPresenter.MyView.class,
				ObsUnitView.class, ObsUnitPresenter.MyProxy.class);

		bindPresenter(StudyListPresenter.class,
				StudyListPresenter.MyView.class, StudyListView.class,
				StudyListPresenter.MyProxy.class);

		bindPresenter(StudyTabPresenter.class, StudyTabPresenter.MyView.class,
				StudyTabView.class, StudyTabPresenter.MyProxy.class);

		bindPresenter(StudyDetailPresenter.class,
				StudyDetailPresenter.MyView.class, StudyDetailView.class,
				StudyDetailPresenter.MyProxy.class);

		bindPresenter(StudyGWASPlotPresenter.class,
				StudyGWASPlotPresenter.MyView.class, StudyGWASPlotView.class,
				StudyGWASPlotPresenter.MyProxy.class);
	}

	@Provides
	@Singleton
	public CustomRequestFactory createCustomRequestFactory(EventBus eventBus) {

		CustomRequestFactory factory = GWT.create(CustomRequestFactory.class);
		factory.initialize(eventBus);
		return factory;
	}
	
	@Provides
	@Singleton
	public DataSource createJBrowseDataSource(){
		at.gmi.nordborglab.widgets.geneviewer.client.datasource.Cache cache = null;
		if (Storage.isSupported()) {
			try {
				cache = new LocalStorageImpl(TYPE.SESSION);
			}
			catch (Exception e) {}
		}
		else {
			cache = new at.gmi.nordborglab.widgets.geneviewer.client.datasource.DefaultCacheImpl();
		}
		return new JBrowseCacheDataSourceImpl("/gwas/",cache);
	}
}
