package com.gmi.nordborglab.browser.client.mvp.diversity.experiments;

import com.gmi.nordborglab.browser.client.ui.SimpleTabPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Tab;
import com.gwtplatform.mvp.client.TabData;
import com.gwtplatform.mvp.client.ViewImpl;

public class ExperimentsOverviewTabView extends ViewImpl implements
		ExperimentsOverviewTabPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ExperimentsOverviewTabView> {
	}
	
	@UiField SimpleTabPanel tabContainer;

	@Inject
	public ExperimentsOverviewTabView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ExperimentsOverviewTabPresenter.TYPE_SetTabContent) {
			setMainContent(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	private void setMainContent(Widget content) {
		if (content != null) {
			tabContainer.setPanelContent(content);
		}
	}
	
	
	@Override
	public Tab addTab(TabData tabData, String historyToken) {
		return tabContainer.addTab(tabData, historyToken);
	}

	@Override
	public void removeTab(Tab tab) {
		tabContainer.removeTab(tab);
	}

	@Override
	public void removeTabs() {
		tabContainer.removeTabs();
}

	@Override
	public void setActiveTab(Tab tab) {
		tabContainer.setActiveTab(tab);
	}

	@Override
	public void changeTab(Tab tab, TabData tabData, String historyToken) {
		tabContainer.changeTab(tab, tabData, historyToken);
	}
}
