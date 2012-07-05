package com.gmi.nordborglab.browser.client.mvp.view.diversity.phenotype;

import com.gmi.nordborglab.browser.client.editors.PhenotypeDisplayEditor;
import com.gmi.nordborglab.browser.client.mvp.handlers.PhenotypeDetailUiHandlers;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.experiments.ExperimentDetailPresenter.State;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.phenotype.PhenotypeDetailPresenter;
import com.gmi.nordborglab.browser.shared.proxy.AccessControlEntryProxy;
import com.gmi.nordborglab.browser.shared.proxy.PhenotypeProxy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class PhenotypeDetailView extends ViewWithUiHandlers<PhenotypeDetailUiHandlers> implements
		PhenotypeDetailPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PhenotypeDetailView> {
	}
	public interface PhenotypeDisplayDriver extends RequestFactoryEditorDriver<PhenotypeProxy, PhenotypeDisplayEditor> {}
	
	@UiField PhenotypeDisplayEditor phenotypeDisplayEditor;
	@UiField ToggleButton edit;
	@UiField ToggleButton save;
	@UiField Anchor cancel;
	@UiField Anchor delete;
	
	private final PhenotypeDisplayDriver displayDriver;
	private State state = State.DISPLAYING;

	@Inject
	public PhenotypeDetailView(final Binder binder,final PhenotypeDisplayDriver displayDriver) {
		widget = binder.createAndBindUi(this);
		this.displayDriver = displayDriver;
		this.displayDriver.initialize(phenotypeDisplayEditor);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public PhenotypeDisplayDriver getDisplayDriver() {
		return displayDriver;
	}



	@Override
	public void setState(State state,int permission) {
		this.state = state;
		phenotypeDisplayEditor.setVisible(state == State.DISPLAYING);
		if (permission > 0 ) {
			//experimentEditEditor.setVisible((state == State.EDITING || state == State.SAVING) && (permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
			edit.setVisible(state == State.DISPLAYING && 
					(permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
			save.setVisible(state == State.EDITING && (permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
			cancel.setVisible(state == State.EDITING && (permission & AccessControlEntryProxy.WRITE) == AccessControlEntryProxy.WRITE);
			delete.setVisible(state == State.EDITING && (permission & AccessControlEntryProxy.DELETE) == AccessControlEntryProxy.DELETE);
		}
	}

	@Override
	public State getState() {
		return state;
	}

//	@Override
//	public ExperimentDisplayDriver getExperimentDisplayDriver() {
//		return experimentDisplayDriver;
//	}
	
	@UiHandler("edit")
	public void onEdit(ClickEvent e) {
		if (state == State.DISPLAYING) {
			getUiHandlers().onEdit();
		}
	}
	
	@UiHandler("delete")
	public void onDelete(ClickEvent e) {
		if (state == State.EDITING) {
			if (Window.confirm("Do you really want to delete the Experiment?")) 
				getUiHandlers().onDelete();
		}
	}
	
	@UiHandler("save") 
	public void onSave(ClickEvent e) {
		if (state == State.EDITING) {
			getUiHandlers().onSave();
		}
	}
	
	@UiHandler("cancel") 
	public void onCancel(ClickEvent e) {
		if (state == State.EDITING) {
			getUiHandlers().onCancel();
		}
	}
}
