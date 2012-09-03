package com.gmi.nordborglab.browser.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class ValidationDecorator extends Composite {

	private static ValidationDecoratorUiBinder uiBinder = GWT
			.create(ValidationDecoratorUiBinder.class);

	interface ValidationDecoratorUiBinder extends
			UiBinder<Widget, ValidationDecorator> {
	}
	
	interface MyStyle extends CssResource {
		String errorBox();
	}
	
	@UiField DivElement warnIcon;
	@UiField DivElement errorIcon;
	@UiField DivElement helpLabel;
	@UiField DivElement errorLabel;
	@UiField SimplePanel contents;
	@UiField LabelElement label;
	@UiField MyStyle style;


	public ValidationDecorator() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void addWidget(Widget widget) {
		contents.add(widget);
		String uid = DOM.createUniqueId();
	    widget.getElement().setPropertyString("id", uid);
	    label.setHtmlFor(uid);
	}
	
	public void setHelpLabel(String label) {
		Display display = Display.NONE;
		if (label!=null &&  !label.equals("")) {
			display = Display.BLOCK;
		}
		helpLabel.getStyle().setDisplay(display);
	}
	
	public void setError(String error) {
		Display display = Display.NONE;
		if (error!=null &&  !error.equals("")) {
			display = Display.BLOCK;
			contents.getWidget().addStyleName(style.errorBox());
		}
		else {
			contents.getWidget().removeStyleName(style.errorBox());
		}
		errorLabel.getStyle().setDisplay(display);
		errorLabel.setInnerText(error);
		errorIcon.getStyle().setDisplay(display);
		warnIcon.getStyle().setDisplay(display);
	}

}
