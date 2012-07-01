package com.gmi.nordborglab.browser.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.TabData;

public class SimpleTab extends BaseTab {


	interface Binder extends UiBinder<Widget, SimpleTab> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiConstructor
	public SimpleTab(TabData tabData) {
		super(tabData);
		initWidget(binder.createAndBindUi(this));
		setText(tabData.getLabel());
	}

}
