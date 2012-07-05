package com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.manager.ExperimentManager;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentsOverviewPresenter;
import com.gmi.nordborglab.browser.client.testutils.PresenterTestBase;
import com.gmi.nordborglab.browser.client.testutils.PresenterTestModule;
import com.gmi.nordborglab.browser.client.testutils.RequestFactoryHelper;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.domain.pages.ExperimentPage;
import com.gmi.nordborglab.browser.server.service.ExperimentService;
import com.gmi.nordborglab.browser.shared.proxy.ExperimentProxy;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ExperimentsOverviewPresenterTest extends PresenterTestBase {
	
	public static class Module extends PresenterTestModule {

		@Override
		protected void configurePresenterTest() {
			bind(ExperimentManager.class).in(Singleton.class);
		}
	}
	
	@Inject
	ExperimentsOverviewPresenter presenter;
	
	@Inject
	ExperimentsOverviewPresenter.MyView view;
	
	@Mock 
	HasData<ExperimentProxy> display;
	
	List<Experiment> experiments;
	
	@Captor
	ArgumentCaptor<List<ExperimentProxy>> captor;
	
	ExperimentService service;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = RequestFactoryHelper.getService(ExperimentService.class);
		when(view.getDisplay()).thenReturn(display);
		when(display.getVisibleRange()).thenReturn(new Range(0, 50));
		experiments = new ArrayList<Experiment>();
		Experiment exp = new Experiment();
		exp.setName("test");
		experiments.add(exp);
		when(service.findByAcl(0,50)).thenReturn(new ExperimentPage(experiments, new PageRequest(0, 50), 100L));
	}
	
	@Test
	public void retrieveAndDisplayExperimentsOnReset() {
		
		presenter.onBind();
		verify(view,atLeastOnce()).getDisplay();
		assertEquals(1, presenter.dataProvider.getDataDisplays().size());
		verify(service).findByAcl(0, 50);
		verify(display).setRowCount(100,true);
		verify(display).setRowData(eq(0), captor.capture());
		List<ExperimentProxy> receivedExperiments = captor.getValue();
		assertEquals(experiments.size(), receivedExperiments.size());
		assertEquals(experiments.get(0).getName(), receivedExperiments.get(0).getName());
	}


	@Test
	public void testLoadExperimentNavigateToNewPlace() {
		ExperimentProxy experiment = mock(ExperimentProxy.class);
		when(experiment.getId()).thenReturn(1L);
		presenter.onBind();
		presenter.loadExperiment(experiment);
		PlaceRequest request = new PlaceRequest(NameTokens.experiment).with("id", experiment.getId().toString());
		verify(placeManager).revealPlace(request);
	}
	
}
