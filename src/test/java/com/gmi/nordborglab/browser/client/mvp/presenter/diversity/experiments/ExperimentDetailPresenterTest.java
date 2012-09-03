package com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.acls.domain.BasePermission;

import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.events.PlaceRequestEvent;
import com.gmi.nordborglab.browser.client.manager.ExperimentManager;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter.State;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.ExperimentDetailView.ExperimentDisplayDriver;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.experiments.ExperimentDetailView.ExperimentEditDriver;
import com.gmi.nordborglab.browser.client.testutils.PresenterTestBase;
import com.gmi.nordborglab.browser.client.testutils.PresenterTestModule;
import com.gmi.nordborglab.browser.client.testutils.RequestFactoryHelper;
import com.gmi.nordborglab.browser.client.testutils.SecurityUtils;
import com.gmi.nordborglab.browser.client.testutils.proxys.ExperimentProxyHelper;
import com.gmi.nordborglab.browser.server.domain.observation.Experiment;
import com.gmi.nordborglab.browser.server.security.CustomAccessControlEntry;
import com.gmi.nordborglab.browser.server.service.ExperimentService;
import com.gmi.nordborglab.browser.shared.proxy.ExperimentProxy;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.Tab;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ExperimentDetailPresenterTest extends PresenterTestBase {

	public static class Module extends PresenterTestModule {

		@Override
		protected void configurePresenterTest() {
			bind(ExperimentManager.class).in(Singleton.class);
		}
	}
	
	@Inject
	ExperimentDetailPresenter presenter;
	
	@Inject
	ExperimentDetailPresenter.MyView view;
	
	@Mock
	Tab tab;
	
	@Mock
	ExperimentDisplayDriver experimentDisplayDriver;
	
	@Mock
	ExperimentEditDriver experimentEditDriver;
	
	@Captor
	ArgumentCaptor<ExperimentProxy> captor;
	
	ExperimentService service;
	
	Experiment experiment;
	
	@Inject
	CurrentUser currentUser;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = RequestFactoryHelper.getService(ExperimentService.class);
		experiment = new Experiment();
		experiment.setId(1L);
		experiment.setName("test");
        experiment.setOriginator("test");
        experiment.setComments("test");
        experiment.setDesign("test");
        experiment.setIsOwner(true);
        when(tab.getText()).thenReturn("Experiment");
        when(service.findExperiment(1L)).thenReturn(experiment);
        when(presenter.getProxy().getTab()).thenReturn(tab);
        when(view.getExperimentDisplayDriver()).thenReturn(experimentDisplayDriver);
        when(view.getExperimentEditDriver()).thenReturn(experimentEditDriver);
	}
	
	@Ignore
	@Test
	public void testLoadExperimentFromManualReveal() {
		PlaceRequest request = new PlaceRequest(NameTokens.experiment).with("id","1");
		presenter.prepareFromRequest(request);
		assertEquals(experiment.getId(), presenter.experiment.getId());
		assertEquals(experiment.getName(),presenter.experiment.getName());
		assertEquals(experiment.getDesign(),presenter.experiment.getDesign());
		assertEquals(experiment.getOriginator(),presenter.experiment.getOriginator());
		assertEquals(experiment.getComments(),presenter.experiment.getComments());
		presenter.onBind();
		presenter.onReset();
		verify(tab).setTargetHistoryToken(placeManager.buildRelativeHistoryToken(0));
		verify(view).setState(State.DISPLAYING,presenter.getPermission());
		verify(experimentDisplayDriver).display(captor.capture());
		ExperimentProxy receivedExperiment = captor.getValue();
		assertEquals(receivedExperiment, presenter.experiment);
	}
	
	@Test
	public void testRevealErrorPageWhenWrongUrl() {
		PlaceRequest request = new PlaceRequest(NameTokens.experiment).with("id","asd");
		presenter.prepareFromRequest(request);
		verify(service,never()).findExperiment(anyLong());
		verify(presenter.getProxy()).manualRevealFailed();
	}
	
	@Ignore
	@Test
	public void testInstantRevealWhenPresenterAlreadyLoaded() {
		ExperimentProxy experimentProxy = ExperimentProxyHelper.createProxy();
		when(experimentProxy.getId()).thenReturn(1L);
		presenter.experiment = experimentProxy;
		PlaceRequest request = new PlaceRequest(NameTokens.experiment).with("id","1");
		presenter.prepareFromRequest(request);
		verify(service,never()).findExperiment(anyLong());
		presenter.onBind();
		presenter.onReset();
		verify(tab).setTargetHistoryToken(placeManager.buildRelativeHistoryToken(0));
		verify(view).setState(State.DISPLAYING,presenter.getPermission());
		verify(experimentDisplayDriver).display(captor.capture());
		ExperimentProxy receivedExperiment = captor.getValue();
		assertEquals(receivedExperiment, presenter.experiment);
	}
	
	@Ignore
	@Test
	public void testAnnonymousUserHasNoPermissions() {
		assertEquals(0,presenter.getPermission());
	}
	
	@Test
	public void testUserWithNoPermission() {
		currentUser.setAppUser(SecurityUtils.createUser());
		PlaceRequest request = new PlaceRequest(NameTokens.experiment).with("id","1");
		presenter.prepareFromRequest(request);
		assertEquals(0,presenter.getPermission());
	}
	
	@Test
	public void testUserWithPermission() {
		currentUser.setAppUser(SecurityUtils.createUser());
		CustomAccessControlEntry ace = new CustomAccessControlEntry(1L,BasePermission.READ.getMask(),true);
		experiment.setUserPermission(ace);
		PlaceRequest request = new PlaceRequest(NameTokens.experiment).with("id","1");
		presenter.prepareFromRequest(request);
		int permission = presenter.getPermission(); 
		assertEquals(experiment.getUserPermission().getMask(), permission);
	}
	
	/*@Test
	public void testCheckPermissions() {
	
		AclExperimentIdentityProxy acl = mock(AclExperimentIdentityProxy.class);
		AclExperimentEntryProxy aceAdmin = mock(AclExperimentEntryProxy.class);
		Set<AclExperimentEntryProxy> aces = new HashSet<AclExperimentEntryProxy>();
		when(acl.getEntries()).thenReturn(aces);
		ExperimentProxy experimentProxy = ExperimentProxyHelper.createProxy();
		when(experimentProxy.getId()).thenReturn(1L);
		presenter.experiment = experimentProxy;
		assertEquals(false, presenter.hasDeletePermission());
		when(experimentProxy.getAcl()).thenReturn(acl);
		assertEquals(false, presenter.hasEditPermission());
		assertEquals(false, presenter.hasDeletePermission());
	}*/
	
	@Test
	@Ignore
	public void testRevealOverviewPageWhenNoExperimentId() {
		PlaceRequest request = new PlaceRequest(NameTokens.experiment);
		presenter.prepareFromRequest(request);
		verify(placeManager).revealRelativePlace(-1);
	}
}
