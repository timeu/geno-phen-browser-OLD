package com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study;

import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.ParameterizedPlaceRequest;
import com.gmi.nordborglab.browser.client.TabDataDynamic;
import com.gmi.nordborglab.browser.client.events.LoadStudyEvent;
import com.gmi.nordborglab.browser.client.events.LoadingIndicatorEvent;
import com.gmi.nordborglab.browser.client.manager.CdvManager;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter.State;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.study.StudyDetailView.StudyDisplayDriver;
import com.gmi.nordborglab.browser.shared.proxy.StudyProxy;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.TabData;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;

public class StudyDetailPresenter extends
		Presenter<StudyDetailPresenter.MyView, StudyDetailPresenter.MyProxy> {

	
	public interface MyView extends View {

		StudyDisplayDriver getDisplayDriver();

		void setState(State displaying, int permissionMask);
	}
	
	protected StudyProxy study;
	protected boolean fireLoadEvent;
	protected final PlaceManager placeManager; 
	protected final CdvManager cdvManager;
	protected final CurrentUser currentUser;

	@ProxyCodeSplit
	@NameToken(NameTokens.study)
	@TabInfo(label="Overview",priority=0,container=StudyTabPresenter.class)
	public interface MyProxy extends TabContentProxyPlace<StudyDetailPresenter> {
	}

	@Inject
	public StudyDetailPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final PlaceManager placeManager,
			final CdvManager cdvManager, 
			final CurrentUser currentUser) {
		super(eventBus, view, proxy);
		this.placeManager = placeManager;
		this.cdvManager = cdvManager;
		this.currentUser = currentUser;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, StudyTabPresenter.TYPE_SetTabContent, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReset() {
		super.onReset();
		if (fireLoadEvent) {
			fireLoadEvent = false;
			fireEvent(new LoadStudyEvent(study));
		}
		getView().getDisplayDriver().display(study);
		getView().setState(State.DISPLAYING,currentUser.getPermissionMask(study.getUserPermission()));
		getProxy().getTab().setTargetHistoryToken(placeManager.buildHistoryToken(placeManager.getCurrentPlaceRequest()));
		LoadingIndicatorEvent.fire(this, false);
	}
	
	@Override
	public boolean useManualReveal() {
		return true;
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);
		LoadingIndicatorEvent.fire(this, true);
		Receiver<StudyProxy> receiver = new Receiver<StudyProxy>() {
			@Override
			public void onSuccess(StudyProxy response) {
				study = response;
				fireLoadEvent = true;
				getProxy().manualReveal(StudyDetailPresenter.this);
			}

			@Override
			public void onFailure(ServerFailure error) {
				getProxy().manualRevealFailed();
				placeManager.revealPlace(new PlaceRequest(NameTokens.experiments));
			}
		};
		try {
			Long studyId = Long.valueOf(placeRequest.getParameter("id",
					null));
			if (study == null || !study.getId().equals(studyId)) {
				cdvManager.findOne(receiver, studyId);
			} else {
				getProxy().manualReveal(StudyDetailPresenter.this);
			}
		} catch (NumberFormatException e) {
			getProxy().manualRevealFailed();
			placeManager.revealPlace(new PlaceRequest(NameTokens.experiments));
		}
	}
	
	@ProxyEvent
	public void onLoad(LoadStudyEvent event) {
		study = event.getStudy();
		PlaceRequest request = new ParameterizedPlaceRequest(getProxy().getNameToken()).with("id",study.getId().toString());
		String historyToken  = placeManager.buildHistoryToken(request);
		TabData tabData = getProxy().getTabData();
		getProxy().changeTab(new TabDataDynamic(tabData.getLabel(), tabData.getPriority(), historyToken));
	}
	
}
