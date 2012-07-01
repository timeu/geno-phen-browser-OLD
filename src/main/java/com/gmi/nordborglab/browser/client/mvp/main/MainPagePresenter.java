package com.gmi.nordborglab.browser.client.mvp.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gmi.nordborglab.browser.client.CurrentUser;
import com.gmi.nordborglab.browser.client.NameTokens;
import com.gmi.nordborglab.browser.client.events.DisplayNotificationEvent;
import com.gmi.nordborglab.browser.shared.proxy.AppUserProxy;
import com.gmi.nordborglab.browser.shared.service.AppUserFactory;
import com.google.common.collect.ImmutableMap;
import com.google.gwt.dev.util.Name;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class MainPagePresenter extends
		Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

	public interface MyView extends View {
		String getUserData();
		void showUserInfo(AppUserProxy user);
		void setActiveNavigationItem(MENU menu);
		void showNotification(String caption, String message, int level,
				int duration);
	}
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();
	public static final Object TYPE_SetUserInfoContent = new Object();

	@ProxyStandard
	public interface MyProxy extends Proxy<MainPagePresenter> {
	}
	
	private final UserInfoPresenter userInfoPresenter;
	private final AppUserFactory appUserFactory;
	final CurrentUser currentUser;
	private final PlaceManager placeManager;
	public enum MENU {HOME,DIVERSITY,GERMPLASM,GENOTYPE}
	

	@Inject
	public MainPagePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy,final UserInfoPresenter userInfoPresenter,
			final AppUserFactory appUserFactory,final CurrentUser currentUser, 
			final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.userInfoPresenter = userInfoPresenter;
		this.appUserFactory = appUserFactory; 
		this.currentUser = currentUser;
		this.placeManager = placeManager;
	}

	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		String userData = getView().getUserData();
		if (userData != null) {
			try {
				AutoBean<AppUserProxy> userBean = AutoBeanCodex.decode(appUserFactory, AppUserProxy.class, userData);
				currentUser.setAppUser(userBean.as());
			}
			catch (Exception e ) {
				Logger logger = Logger.getLogger("");
				logger.log(Level.SEVERE, "Autobean decoding", e);
			}
		}
		registerHandler(getEventBus().addHandler(DisplayNotificationEvent.getType(), new DisplayNotificationEvent.DisplayNotificationHandler() {
			
			@Override
			public void onDisplayNotifcation(DisplayNotificationEvent event) {
				getView().showNotification(event.getCaption(), event.getMessage(), event.getLevel(), event.getDuration());
			}
		}));
		//setInSlot(TYPE_SetUserInfoContent, userInfoPresenter);
	}
	
	private MENU getParentMenuFromRequest(PlaceRequest request) {
		MENU menu = MENU.HOME;
		if (request.matchesNameToken(NameTokens.experiment) ||
			request.matchesNameToken(NameTokens.experiments) || request.matchesNameToken(NameTokens.phenotypes))
			menu = MENU.DIVERSITY;
		return menu;
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		getView().showUserInfo(currentUser.getAppUser());
		PlaceRequest request = placeManager.getCurrentPlaceRequest();
		getView().setActiveNavigationItem(getParentMenuFromRequest(request));;
	}
	
	@Override 
	protected void onUnbind() {
		super.onUnbind();
		clearSlot(TYPE_SetUserInfoContent);
	}
}
