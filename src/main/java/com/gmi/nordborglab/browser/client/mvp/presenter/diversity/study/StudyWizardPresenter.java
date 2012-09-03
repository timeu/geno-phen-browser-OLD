package com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.IsLoggedInGatekeeper;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.ParameterizedPlaceRequest;
import com.gmi.nordborglab.browser.client.TabDataDynamic;
import com.gmi.nordborglab.browser.client.events.LoadPhenotypeEvent;
import com.gmi.nordborglab.browser.client.events.LoadingIndicatorEvent;
import com.gmi.nordborglab.browser.client.gin.ClientGinjector;
import com.gmi.nordborglab.browser.client.manager.CdvManager;
import com.gmi.nordborglab.browser.client.manager.ObsUnitManager;
import com.gmi.nordborglab.browser.client.manager.PhenotypeManager;
import com.gmi.nordborglab.browser.client.mvp.handlers.StudyWizardUiHandlers;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype.PhenotypeDetailTabPresenter;
import com.gmi.nordborglab.browser.client.mvp.view.diversity.study.StudyWizardView.StudyCreateDriver;
import com.gmi.nordborglab.browser.shared.proxy.AlleleAssayProxy;
import com.gmi.nordborglab.browser.shared.proxy.ObsUnitProxy;
import com.gmi.nordborglab.browser.shared.proxy.PhenotypeProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProtocolProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProxy;
import com.gmi.nordborglab.browser.shared.service.CdvRequest;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.TabData;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;

public class StudyWizardPresenter extends
		Presenter<StudyWizardPresenter.MyView, StudyWizardPresenter.MyProxy> implements StudyWizardUiHandlers{

	public interface MyView extends View,HasUiHandlers<StudyWizardUiHandlers> {

		StudyCreateDriver getStudyCreateDriver();
		void setAcceptableValues(List<StudyProtocolProxy> studyProtocolValues,List<AlleleAssayProxy> alleleAssayValues);
		void setPreviousStep();
		void setNextStep();
		void showAccessionGenotypeOverlapChart(int numberOfObsUnitsWithGenotype,int numberOfObsUnitsWithoutGenotype);
		void showBlankPieChart();
		void scheduledLayout();
		void showMissingGenotypes(List<ObsUnitProxy> missingGenotypes);
		HasData<ObsUnitProxy> getMissingGenotypesDisplay();
		void resetMissingGenotypesDataGrid();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.studywizard)
	@UseGatekeeper(IsLoggedInGatekeeper.class)
	public interface MyProxy extends TabContentProxyPlace<StudyWizardPresenter> {
	}
	
	@TabInfo(container = PhenotypeDetailTabPresenter.class)
	 static TabData getTabLabel(ClientGinjector ginjector) {
	    // Priority = 1000, means it will be the right-most tab in the home tab
	    TabDataDynamic tabData = new TabDataDynamic("New Study", 1000,"",
	        ginjector.getLoggedInGatekeeper());
	    tabData.setHasAccess(false);
	    return tabData;
	 }
	
	private StudyProxy study;
	protected PhenotypeProxy phenotype;
	protected Long phenotypeId;
	private final CdvManager cdvManager;
	private final PhenotypeManager phenotypeManager;
	private final ObsUnitManager obsUnitManager;
	private final CurrentUser currentUser;
	private boolean fireLoadEvent = false;
	private final PlaceManager placeManager;
	private final Validator validator;
	private Map<Long,List<ObsUnitProxy>> studyStats  = new HashMap<Long, List<ObsUnitProxy>>();
	private ListDataProvider<ObsUnitProxy> missingGenotypesDataProvider = new ListDataProvider<ObsUnitProxy>();

	@Inject
	public StudyWizardPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final CdvManager cdvManager, 
			final CurrentUser currentUser, 
			final PhenotypeManager phenotypeManager,final PlaceManager placeManager,
			final ObsUnitManager obsUnitManager) {
		super(eventBus, view, proxy);
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		getView().setUiHandlers(this);
		this.cdvManager =  cdvManager;
		this.obsUnitManager = obsUnitManager;
		this.currentUser = currentUser;
		this.phenotypeManager = phenotypeManager;
		this.placeManager = placeManager;
		getView().setAcceptableValues(currentUser.getAppData().getStudyProtocolList(),currentUser.getAppData().getAlleleAssayList());
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, PhenotypeDetailTabPresenter.TYPE_SetTabContent, this);
	}
	
	@Override
	protected void onReset() {
		if (fireLoadEvent) {
			fireEvent(new LoadPhenotypeEvent(phenotype));
			fireLoadEvent = false;
		}
		LoadingIndicatorEvent.fire(this, false);
		CdvRequest ctx = cdvManager.getContext();
		study = ctx.create(StudyProxy.class);
		getView().getStudyCreateDriver().edit(study, ctx);
		getView().scheduledLayout();
		//ctx.create(study).with("userPermission").to(receiver);
	}

	@Override
	protected void onBind() {
		super.onBind();
		missingGenotypesDataProvider.addDataDisplay(getView().getMissingGenotypesDisplay());
		getView().resetMissingGenotypesDataGrid();
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest placeRequest) {
		super.prepareFromRequest(placeRequest);
		LoadingIndicatorEvent.fire(this, true);
		try {
			final Long phenotypeIdToLoad = Long.valueOf(placeRequest.getParameter("id",null));
			if (!phenotypeIdToLoad.equals(phenotypeId)) {
				phenotypeId = phenotypeIdToLoad;
			}
			if (phenotype == null || !phenotype.getId().equals(phenotypeIdToLoad)) {
				phenotypeManager.getContext().findPhenotype(phenotypeIdToLoad).with("userPermission").fire(new Receiver<PhenotypeProxy>() {
	
					@Override
					public void onSuccess(PhenotypeProxy response) {
						phenotype = response;
						fireLoadEvent = true;
						getProxy().manualReveal(StudyWizardPresenter.this);
					}
				});
			}
			else {
				getProxy().manualReveal(StudyWizardPresenter.this);
			}
		} catch (NumberFormatException e) {
			getProxy().manualRevealFailed();
			placeManager.revealPlace(new ParameterizedPlaceRequest(NameTokens.experiments));
		}
	}
	
	@Override
	public boolean useManualReveal() {
		return true;
	}
	
	@ProxyEvent
	void onLoad(LoadPhenotypeEvent event) {
		boolean hasAccess = true;
		phenotype = event.getPhenotype();
		if (!phenotype.getId().equals(phenotypeId)) { 
			hasAccess = false;
			study = null;
			studyStats.clear();
			getView().resetMissingGenotypesDataGrid();
		}
		PlaceRequest request = new ParameterizedPlaceRequest(getProxy().getNameToken()).with("id", phenotype.getId().toString());
		String historyToken  = placeManager.buildHistoryToken(request);
		TabData tabData = getProxy().getTabData();
		TabDataDynamic newTabData = new TabDataDynamic("New Study", tabData.getPriority(), historyToken);
		newTabData.setHasAccess(hasAccess);
		getProxy().changeTab(newTabData);
	}

	@Override
	public void onCancel() {
		PlaceRequest request = new ParameterizedPlaceRequest(NameTokens.studylist).with("id", phenotype.getId().toString());
		placeManager.revealPlace(request);
		study = null;
		studyStats.clear();
		getView().resetMissingGenotypesDataGrid();
		TabDataDynamic tabData = (TabDataDynamic)getProxy().getTabData();
		if (tabData != null) {
			tabData.setHasAccess(false);
			getProxy().changeTab(tabData);
		}
		
	}

	@Override
	public void onNext() {
		getView().getStudyCreateDriver().flush();
	    @SuppressWarnings("unchecked")
		Set<ConstraintViolation<?>> violations = ( Set<ConstraintViolation<?>>)(Set)validator.validate(study,Default.class);
	    if (!violations.isEmpty()) {
	    	getView().getStudyCreateDriver().setConstraintViolations(violations);
	    }
	    else {
	    	getView().setNextStep();
	    }
	}

	@Override
	public void onPrevious() {
		getView().setPreviousStep();
	}

	@Override
	public void onGenotypeChange(final AlleleAssayProxy value) {
		if (value == null) { 
			getView().scheduledLayout();
			getView().resetMissingGenotypesDataGrid();
		}
		else {
			final int numberOfObsUnits = phenotype.getNumberOfObsUnits().intValue();
			List<ObsUnitProxy> missingGenotypes = studyStats.get(value.getId());
			if (missingGenotypes != null) {
				getView().showAccessionGenotypeOverlapChart(numberOfObsUnits-missingGenotypes.size(), missingGenotypes.size());
				missingGenotypesDataProvider.setList(missingGenotypes);
				getView().showMissingGenotypes(missingGenotypes);
			}
			else {
				obsUnitManager.findObsUnitsWithNoGenotype(new Receiver<List<ObsUnitProxy>>() {
	
					@Override
					public void onSuccess(List<ObsUnitProxy> response) {
						studyStats.put(value.getId(), response);
						getView().showAccessionGenotypeOverlapChart(numberOfObsUnits-response.size(),response.size());
						getView().showMissingGenotypes(response);
						missingGenotypesDataProvider.setList(response);
					}
					
				}, phenotypeId, value.getId());
			}
		}
	}
}
