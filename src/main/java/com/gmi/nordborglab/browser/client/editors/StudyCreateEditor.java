package com.gmi.nordborglab.browser.client.editors;

import java.util.List;

import com.gmi.nordborglab.browser.client.ui.ValidationValueBoxEditorDecorator;
import com.gmi.nordborglab.browser.shared.proxy.AlleleAssayProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProtocolProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProxy;
import com.gmi.nordborglab.browser.shared.proxy.UnitOfMeasureProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.gwt.ui.client.EntityProxyKeyProvider;
import com.google.web.bindery.requestfactory.gwt.ui.client.ProxyRenderer;

public class StudyCreateEditor extends Composite implements Editor<StudyProxy>{

	private static StudyCreateEditorUiBinder uiBinder = GWT
			.create(StudyCreateEditorUiBinder.class);

	interface StudyCreateEditorUiBinder extends
			UiBinder<Widget, StudyCreateEditor> {
	}
	
	@UiField  ValidationValueBoxEditorDecorator<String> name; 
	@UiField  ValueBoxEditorDecorator<String> producer;
	@UiField(provided=true) ValueListBox<StudyProtocolProxy> protocol;
	@Path("alleleAssay") @UiField(provided=true) ValueListBox<AlleleAssayProxy> genotype;
	

	public StudyCreateEditor() {
		protocol = new ValueListBox<StudyProtocolProxy>(new ProxyRenderer<StudyProtocolProxy>(null) {

			@Override
			public String render(StudyProtocolProxy object) {
				return object == null ? "" : object.getAnalysisMethod();
			}
			
		}, new EntityProxyKeyProvider<StudyProtocolProxy>());
		genotype = new ValueListBox<AlleleAssayProxy>(new ProxyRenderer<AlleleAssayProxy>(null) {

			@Override
			public String render(AlleleAssayProxy object) {
				return object == null ? "": object.getName();
			}
		}, new EntityProxyKeyProvider<AlleleAssayProxy>());
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setAcceptableValues(List<StudyProtocolProxy> protocolValues,List<AlleleAssayProxy> genotypeValues) {
		protocol.setAcceptableValues(protocolValues);
		genotype.setAcceptableValues(genotypeValues);
	}
	
	public HandlerRegistration addGenotypeChangeHandler(ValueChangeHandler<AlleleAssayProxy> handler)  {
		return genotype.addValueChangeHandler(handler);
	}

}
