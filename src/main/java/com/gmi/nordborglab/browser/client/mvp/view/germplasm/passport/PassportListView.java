package com.gmi.nordborglab.browser.client.mvp.view.germplasm.passport;

import com.gwtplatform.mvp.client.ViewImpl;
import com.gmi.nordborglab.browser.client.mvp.presenter.germplasm.passport.PassportListPresenter;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PassportListView extends ViewImpl implements
		PassportListPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PassportListView> {
	}

	@Inject
	public PassportListView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
