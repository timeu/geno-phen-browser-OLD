package com.gmi.nordborglab.browser.client.mvp.diversity.experiments;

import com.gwtplatform.mvp.client.Tab;
import com.gwtplatform.mvp.client.TabData;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gmi.nordborglab.browser.client.mvp.TabDataDynamic;
import com.gmi.nordborglab.browser.client.ui.SimpleTabPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ExperimentDetailTabView extends ViewImpl implements
		ExperimentDetailTabPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ExperimentDetailTabView> {
	}
	
	@UiField SimpleTabPanel tabContainer;

	@Inject
	public ExperimentDetailTabView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ExperimentDetailTabPresenter.TYPE_SetTabContent) {
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
		if (tabData instanceof TabDataDynamic) 
			historyToken = ((TabDataDynamic)tabData).getHistoryToken();
		tabContainer.changeTab(tab, tabData, historyToken);
	}
}
