package com.gmi.nordborglab.browser.client.mvp.view.germplasm.passport;

import com.gwtplatform.mvp.client.ViewImpl;
import com.gmi.nordborglab.browser.client.mvp.presenter.germplasm.passport.PassportDetailPresenter;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PassportDetailView extends ViewImpl implements
		PassportDetailPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PassportDetailView> {
	}

	@Inject
	public PassportDetailView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
