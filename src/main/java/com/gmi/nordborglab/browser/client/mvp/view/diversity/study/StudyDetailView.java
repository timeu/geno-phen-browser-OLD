package com.gmi.nordborglab.browser.client.mvp.view.diversity.study;

import com.gmi.nordborglab.browser.client.editors.StudyDisplayEditor;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter.State;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study.StudyDetailPresenter;
import com.gmi.nordborglab.browser.shared.proxy.StudyProxy;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.gwtplatform.mvp.client.ViewImpl;

public class StudyDetailView extends ViewImpl implements
		StudyDetailPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, StudyDetailView> {
	}
	
	public interface StudyDisplayDriver extends RequestFactoryEditorDriver<StudyProxy, StudyDisplayEditor> {}
	
	protected final StudyDisplayDriver displayDriver;
	protected State state = State.DISPLAYING;
	@UiField StudyDisplayEditor studyDisplayEditor;
	
	@Inject
	public StudyDetailView(final Binder binder, final StudyDisplayDriver displayDriver) {
		widget = binder.createAndBindUi(this);
		this.displayDriver = displayDriver;
		this.displayDriver.initialize(studyDisplayEditor);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public StudyDisplayDriver getDisplayDriver() {
		return displayDriver;
	}

	@Override
	public void setState(State state, int permission) {
		this.state = state;
		studyDisplayEditor.setVisible(state == State.DISPLAYING);
		if (permission > 0 ) {
//			//experimentEditEditor.setVisible((state == State.EDITING || state == State.SAVING) && (permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
//			edit.setVisible(state == State.DISPLAYING && 
//					(permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
//			save.setVisible(state == State.EDITING && (permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
//			cancel.setVisible(state == State.EDITING && (permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
//			delete.setVisible(state == State.EDITING && (permission & AccessControlEntryProxy.DELETE) == AccessControlEntryProxy.DELETE);
		}
		
	}
}
