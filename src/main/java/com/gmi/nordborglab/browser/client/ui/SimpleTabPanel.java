package com.gmi.nordborglab.browser.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.TabData;

public class SimpleTabPanel extends BaseTabPanel {

	public interface Binder extends UiBinder<Widget, SimpleTabPanel> {
	}
	
	private static Binder binder = GWT.create(Binder.class);

	@Inject
	public SimpleTabPanel() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	protected BaseTab createNewTab(TabData tabData) {
		return new SimpleTab(tabData);
	}

}
