package com.gmi.nordborglab.browser.client.mvp.diversity.experiments;

import com.gmi.nordborglab.browser.shared.proxy.ExperimentProxy;
import com.gwtplatform.mvp.client.UiHandlers;

public interface ExperimentsOverviewUiHandlers extends UiHandlers {
	void loadExperiment(ExperimentProxy experiment);
}
