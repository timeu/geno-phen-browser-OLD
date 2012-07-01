package com.gmi.nordborglab.browser.client.mvp.diversity;

import java.util.List;

import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.manager.HelperManager;
import com.gmi.nordborglab.browser.client.mvp.ParameterizedPlaceRequest;
import com.gmi.nordborglab.browser.client.mvp.main.MainPagePresenter;
import com.gmi.nordborglab.browser.shared.proxy.BreadcrumbItemProxy;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class DiversityPresenter extends
	Presenter<DiversityPresenter.MyView, DiversityPresenter.MyProxy> {

	public interface MyView extends View {
		void clearBreadcrumbs(int size);
		void setBreadcrumbs(int index, String title,String historyToken);
		void setTitle(String title);
	}
	

	@ProxyCodeSplit
	public interface MyProxy extends Proxy<DiversityPresenter> {
	}
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	private final PlaceManager placeManager;
	private final HelperManager helperManager;
	protected String titleType = null;
	protected Long titleId = null;

	@Inject
	public DiversityPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final PlaceManager placeManager, final HelperManager helperManager) {
		super(eventBus, view, proxy);
		this.placeManager = placeManager;
		this.helperManager = helperManager;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetMainContent, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		setTitle();
	}
	
	private void setTitle() {
		PlaceRequest request = placeManager.getCurrentPlaceRequest();
		String type = null;
		String title ="Experiments";
		if (request.matchesNameToken(NameTokens.experiments)) 
			getView().clearBreadcrumbs(0);
		if (request.matchesNameToken(NameTokens.experiment) || request.matchesNameToken(NameTokens.phenotypes) ) {
			type = "experiment";
		}
		else if (request.matchesNameToken(NameTokens.phenotype)) {
			title = "Phenotype";
			type = "phenotype";
		}
		getView().setTitle(title);
		Long id = null;
		try {
			id = Long.parseLong(request.getParameter("id", null));
		}
		catch (Exception e ) {
			
		}
		if (!titleUpdateRequired(type, id))
			return;
		helperManager.getBreadcrumbs(new Receiver<List<BreadcrumbItemProxy>>() {
			
			@Override
			public void onSuccess(List<BreadcrumbItemProxy> response) {
				getView().clearBreadcrumbs(response.size());
				getView().setBreadcrumbs(0, "ALL", placeManager.buildHistoryToken(new PlaceRequest(NameTokens.experiments)));
				for (int i=0;i<response.size();i++) {
					BreadcrumbItemProxy item = response.get(i);
					String nameToken = null;
					if (item.getType().equals("experiment"))
						nameToken = NameTokens.experiment;
					else if (item.getType().equals("phenotype"))
						nameToken = NameTokens.phenotype ;
					PlaceRequest request = new ParameterizedPlaceRequest(nameToken).with("id", item.getId().toString());
					getView().setBreadcrumbs(i+1, item.getText(),placeManager.buildHistoryToken(request));
				}
			}
		},id,type);
	}
	
	protected boolean titleUpdateRequired(String type,Long id) {
		boolean required = false;
		if (type != null) {
			if (!type.equals(titleType)) {
				if (id != null)
					required = true;
			}
			else if (id != null && !id.equals(titleId)) {
				required = true;
			}
		}
		titleType = type;
		titleId = id;
		return required;
	}
}
