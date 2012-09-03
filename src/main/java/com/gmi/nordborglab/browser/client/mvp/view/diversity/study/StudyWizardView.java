package com.gmi.nordborglab.browser.client.mvp.view.diversity.study;

import java.util.List;

import com.gmi.nordborglab.browser.client.editors.StudyCreateEditor;
import com.gmi.nordborglab.browser.client.mvp.handlers.StudyWizardUiHandlers;
import com.gmi.nordborglab.browser.client.mvp.presenter.diversity.study.StudyWizardPresenter;
import com.gmi.nordborglab.browser.client.resources.FlagMap;
import com.gmi.nordborglab.browser.client.ui.FlagCell;
import com.gmi.nordborglab.browser.client.ui.WizardPanel;
import com.gmi.nordborglab.browser.shared.proxy.AlleleAssayProxy;
import com.gmi.nordborglab.browser.shared.proxy.ObsUnitProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProtocolProxy;
import com.gmi.nordborglab.browser.shared.proxy.StudyProxy;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.gwt.ui.client.EntityProxyKeyProvider;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class StudyWizardView extends ViewWithUiHandlers<StudyWizardUiHandlers> implements
		StudyWizardPresenter.MyView {

	private final Widget widget;
	
	public interface StudyCreateDriver extends RequestFactoryEditorDriver<StudyProxy, StudyCreateEditor> {}

	public interface Binder extends UiBinder<Widget, StudyWizardView> {	}
	
	public interface MyStyle extends CssResource {
		String emptyDataGridWidget();
		String loadingIndicator();
	}
	
	private final ScheduledCommand layoutCmd = new ScheduledCommand() {
    	public void execute() {
    		layoutScheduled = false;
		    forceLayout();
		}
    };
    
	private boolean layoutScheduled = false;
	
	@UiField WizardPanel wizard;
	@UiField StudyCreateEditor studyCreateEditor;
	@UiField HTMLPanel piechartContainer;
	@UiField(provided=true) DataGrid<ObsUnitProxy> missingGenotypesGrid;
	@UiField MyStyle style;
	@UiField(provided=true) SimplePager pager;
	private PieChart dataAccessionPiechart;
	private final StudyCreateDriver studyCreateDriver;
	private HTMLPanel emptyDataGridWidget = new HTMLPanel("There are no plants with missing genotypes");
	private HTMLPanel loadingIndicatorWidget = new HTMLPanel("Select a genotype dataset");
	

	@Inject
	public StudyWizardView(final Binder binder,final StudyCreateDriver studyCreateDriver,
			final FlagMap flagMap) {
		this.studyCreateDriver =studyCreateDriver;
		
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, true, 100, true);
		missingGenotypesGrid = new DataGrid<ObsUnitProxy>(new EntityProxyKeyProvider<ObsUnitProxy>());
		
		widget = binder.createAndBindUi(this);
		this.studyCreateDriver.initialize(studyCreateEditor);
		emptyDataGridWidget.setStylePrimaryName(style.emptyDataGridWidget());
		loadingIndicatorWidget.setStylePrimaryName(style.loadingIndicator());
		
		pager.setDisplay(missingGenotypesGrid);
		missingGenotypesGrid.setEmptyTableWidget(emptyDataGridWidget);
		missingGenotypesGrid.setLoadingIndicator(loadingIndicatorWidget);
		
		wizard.addCancelButtonClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().onCancel();
			}
		});
		
		wizard.addNextButtonClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().onNext();
			}
		});
		wizard.addPreviousButtonClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().onPrevious();
				
			}
		});
		studyCreateEditor.addGenotypeChangeHandler(new ValueChangeHandler<AlleleAssayProxy>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<AlleleAssayProxy> event) {
				getUiHandlers().onGenotypeChange(event.getValue());
			}
		});
		initDataGrid(flagMap);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public StudyCreateDriver getStudyCreateDriver() {
		return studyCreateDriver;
	}

	@Override
	public void setAcceptableValues(
			List<StudyProtocolProxy> studyProtocolValues,
			List<AlleleAssayProxy> alleleAssayValues) {
		studyCreateEditor.setAcceptableValues(studyProtocolValues, alleleAssayValues);
	}

	@Override
	public void setPreviousStep() {
		wizard.previousStep();
		
	}

	@Override
	public void setNextStep() {
		wizard.nextStep();
	}

	@Override
	public void showAccessionGenotypeOverlapChart(int numberOfObsUnitsWithGenotype,int numberOfObsUnitsWithoutGenotype) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Type");
		dataTable.addColumn(ColumnType.NUMBER, "Plants");
		dataTable.addRows(2);
		dataTable.setValue(0, 0, "Genotype");
		dataTable.setValue(0, 1, numberOfObsUnitsWithGenotype);
		dataTable.setValue(1, 0, "No genotype");
		dataTable.setValue(1, 1, numberOfObsUnitsWithoutGenotype);
		piechartContainer.clear();
		dataAccessionPiechart = new PieChart(dataTable, createPieChartOptions(false));
		piechartContainer.add(dataAccessionPiechart);
	}
	
	private PieChart.PieOptions createPieChartOptions(boolean isblank) {
		PieChart.PieOptions options = PieChart.PieOptions.create();
		options.setHeight(400);
		options.setWidth(600);
		options.setTitle("Genotype overlap");
		if (isblank) {
			options.setColors("#888");
		}
		return options;
	}
	
	@Override
	public void showBlankPieChart() {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Type");
		dataTable.addColumn(ColumnType.NUMBER, "Plants");
		dataTable.addRows(1);
		dataTable.setValue(0, 0, "Select a genotype dataset");
		dataTable.setValue(0, 1, 1);
		piechartContainer.clear();
		dataAccessionPiechart = new PieChart(dataTable, createPieChartOptions(true));
		piechartContainer.add(dataAccessionPiechart);
	}
	
	private void forceLayout() {
		if (!widget.isAttached() || !widget.isVisible())
			return;
		showBlankPieChart();
	}
	
	@Override
	public void scheduledLayout() {
	    if (widget.isAttached() && !layoutScheduled) {
	      layoutScheduled = true;
	      Scheduler.get().scheduleDeferred(layoutCmd);
	    }
	}

	@Override
	public void showMissingGenotypes(List<ObsUnitProxy> missingGenotypes) {
		// TODO Auto-generated method stub
		
	}
	
	private void initDataGrid(FlagMap flagMap) {
		
		missingGenotypesGrid.addColumn(new Column<ObsUnitProxy, String>(new FlagCell(flagMap)) {
			@Override
			public String getValue(ObsUnitProxy object) {
				String icon = null;
				try {
					 icon = object.getStock().getPassport().getCollection().getLocality().getOrigcty();
				}
				catch (NullPointerException e) {
					
				}
				return icon;
			}
		},"");
		
		missingGenotypesGrid.addColumn(new Column<ObsUnitProxy, String>(new TextCell()) {
			@Override
			public String getValue(ObsUnitProxy object) {
				return object.getName();
			}
		},"Name");
		
		
		
		missingGenotypesGrid.addColumn(new Column<ObsUnitProxy,String>(new TextCell()) {

			@Override
			public String getValue(ObsUnitProxy object) {
				return object.getSeason();
			}
			
		},"Season");
		
		missingGenotypesGrid.addColumn(new Column<ObsUnitProxy,String>(new TextCell()) {

			@Override
			public String getValue(ObsUnitProxy object) {
				// TODO Auto-generated method stub
				return object.getBlock();
			}
			
		},"Block");
		
		missingGenotypesGrid.addColumn(new Column<ObsUnitProxy,String>(new TextCell()) {

			@Override
			public String getValue(ObsUnitProxy object) {
				// TODO Auto-generated method stub
				return object.getRep();
			}
			
		},"Rep");
	}

	@Override
	public HasData<ObsUnitProxy> getMissingGenotypesDisplay() {
		return missingGenotypesGrid;
	}

	@Override
	public void resetMissingGenotypesDataGrid() {
		missingGenotypesGrid.setVisibleRangeAndClearData(missingGenotypesGrid.getVisibleRange(), false);
		missingGenotypesGrid.setRowCount(1);
	}
	
}
