package com.gmi.nordborglab.browser.client.gin;

import com.gmi.nordborglab.browser.client.ClientPlaceManager;
import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalytics;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.gmi.nordborglab.browser.client.manager.ExperimentManager;
import com.gmi.nordborglab.browser.client.manager.HelperManager;
import com.gmi.nordborglab.browser.client.manager.PhenotypeManager;
import com.gmi.nordborglab.browser.client.mvp.ParameterizedParameterTokenFormatter;
import com.gmi.nordborglab.browser.client.mvp.main.MainPagePresenter;
import com.gmi.nordborglab.browser.client.mvp.main.MainPageView;
import com.gmi.nordborglab.browser.client.mvp.home.HomePresenter;
import com.gmi.nordborglab.browser.client.mvp.home.HomeView;
import com.gmi.nordborglab.browser.client.mvp.home.dashboard.DashboardPresenter;
import com.gmi.nordborglab.browser.client.mvp.home.dashboard.DashboardView;
import com.gmi.nordborglab.browser.client.mvp.main.UserInfoPresenter;
import com.gmi.nordborglab.browser.client.mvp.main.UserInfoView;
import com.gmi.nordborglab.browser.client.resources.MainResources;
import com.gmi.nordborglab.browser.shared.service.AppUserFactory;
import com.gmi.nordborglab.browser.shared.service.CustomRequestFactory;
import com.google.gwt.core.client.GWT;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.gmi.nordborglab.browser.client.mvp.diversity.DiversityPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.DiversityView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentsOverviewPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentsOverviewView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentDetailView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentsOverviewTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentsOverviewTabView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.ExperimentDetailTabView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeListPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeListView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeDetailTabView;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.diversity.experiments.PhenotypeDetailView;

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

		bind(MainResources.class).in(Singleton.class);
		bind(ExperimentManager.class).in(Singleton.class);
		bind(PhenotypeManager.class).in(Singleton.class);
		bind(HelperManager.class).in(Singleton.class);
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
	}

	@Provides
	@Singleton
	public CustomRequestFactory createCustomRequestFactory(EventBus eventBus) {

		CustomRequestFactory factory = GWT.create(CustomRequestFactory.class);
		factory.initialize(eventBus);
		return factory;
	}

}
