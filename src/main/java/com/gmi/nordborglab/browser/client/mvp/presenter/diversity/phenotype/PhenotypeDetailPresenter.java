package com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype;

import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.ParameterizedPlaceRequest;
import com.gmi.nordborglab.browser.client.TabDataDynamic;
import com.gmi.nordborglab.browser.client.events.LoadExperimentEvent;
import com.gmi.nordborglab.browser.client.events.LoadPhenotypeEvent;
import com.gmi.nordborglab.browser.client.events.LoadingIndicatorEvent;
import com.gmi.nordborglab.browser.client.manager.PhenotypeManager;
import com.gmi.nordborglab.browser.client.mvp.handlers.PhenotypeDetailUiHandlers;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter.State;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.phenotype.PhenotypeDetailView.PhenotypeDisplayDriver;
import com.gmi.nordborglab.browser.shared.proxy.PhenotypeProxy;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtplatform.mvp.client.HasUiHandlers;
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

public class PhenotypeDetailPresenter
		extends
		Presenter<PhenotypeDetailPresenter.MyView, PhenotypeDetailPresenter.MyProxy> implements PhenotypeDetailUiHandlers{

	public interface MyView extends View,HasUiHandlers<PhenotypeDetailUiHandlers> {

		PhenotypeDisplayDriver getDisplayDriver();

		void setState(State state, int permission);

		State getState();
	}
	protected PhenotypeProxy phenotype;
	protected boolean fireLoadEvent;
	protected final PlaceManager placeManager; 
	protected final PhenotypeManager phenotypeManager;
	protected final CurrentUser currentUser;
	
	
	@ProxyCodeSplit
	@NameToken(NameTokens.phenotype)
	@TabInfo(label="Overview",priority=0, container = PhenotypeDetailTabPresenter.class)
	public interface MyProxy extends TabContentProxyPlace<PhenotypeDetailPresenter> {
	}

	@Inject
	public PhenotypeDetailPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final PlaceManager placeManager,
			final PhenotypeManager phenotypeManager, 
			final CurrentUser currentUser) {
		super(eventBus, view, proxy);
		this.placeManager = placeManager;
		this.phenotypeManager = phenotypeManager;
		this.currentUser = currentUser;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this,
				PhenotypeDetailTabPresenter.TYPE_SetTabContent, this);
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
			fireEvent(new LoadPhenotypeEvent(phenotype));
		}
		getView().getDisplayDriver().display(phenotype);
		getView().setState(State.DISPLAYING,currentUser.getPermissionMask(phenotype.getUserPermission()));
		getProxy().getTab().setTargetHistoryToken(placeManager.buildHistoryToken(placeManager.getCurrentPlaceRequest()));
		LoadingIndicatorEvent.fire(this, false);
	}
	
	
	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);
		LoadingIndicatorEvent.fire(this, true);
		Receiver<PhenotypeProxy> receiver = new Receiver<PhenotypeProxy>() {
			@Override
			public void onSuccess(PhenotypeProxy phen) {
				phenotype = phen;
				fireLoadEvent = true;
				getProxy().manualReveal(PhenotypeDetailPresenter.this);
			}

			@Override
			public void onFailure(ServerFailure error) {
				getProxy().manualRevealFailed();
				placeManager.revealPlace(new PlaceRequest(NameTokens.experiments));
			}
		};
		try {
			Long phenotypeId = Long.valueOf(placeRequest.getParameter("id",
					null));
			if (phenotype == null || !phenotype.getId().equals(phenotypeId)) {
				phenotypeManager.findOne(receiver, phenotypeId);
			} else {
				getProxy().manualReveal(PhenotypeDetailPresenter.this);
			}
		} catch (NumberFormatException e) {
			getProxy().manualRevealFailed();
			placeManager.revealPlace(new PlaceRequest(NameTokens.experiments));
		}
	}
	

	@Override
	public void onEdit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean useManualReveal() {
		return true;
	}
	
	@ProxyEvent
	public void onLoadExperiment(LoadPhenotypeEvent event) {
		phenotype = event.getPhenotype();
		PlaceRequest request = new ParameterizedPlaceRequest(getProxy().getNameToken()).with("id",phenotype.getId().toString());
		String historyToken  = placeManager.buildHistoryToken(request);
		TabData tabData = getProxy().getTabData();
		getProxy().changeTab(new TabDataDynamic(tabData.getLabel(), tabData.getPriority(), historyToken));
	}
	
	
}