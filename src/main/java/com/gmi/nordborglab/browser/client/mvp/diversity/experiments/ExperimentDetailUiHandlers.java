package com.gmi.nordborglab.browser.client.mvp.diversity.experiments;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ExperimentDetailUiHandlers extends UiHandlers {
	public void onEdit();

	public void onSave();

	public void onCancel();

	public void onDelete();
}
