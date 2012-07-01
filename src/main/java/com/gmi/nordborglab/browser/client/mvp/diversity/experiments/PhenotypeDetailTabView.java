package com.gmi.nordborglab.browser.client.mvp.diversity.experiments;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PhenotypeDetailTabView extends BaseTabContainerView implements
		PhenotypeDetailTabPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PhenotypeDetailTabView> {
	}

	@Inject
	public PhenotypeDetailTabView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
