package hu.schonherz.administration.web.courier;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hu.schonherz.administration.serviceapi.CourierIncomeService;
import hu.schonherz.administration.serviceapi.dto.CourierIncomeDTO;
import hu.schonherz.administration.web.localization.MessageProvider;

@Named("courierIncomeLazyBean")
@ViewScoped
@EJB(name = "ejb.CourierIncomeService", beanInterface = CourierIncomeService.class)
public class CourierIncomeBean {

	@EJB
	private CourierIncomeService incomeService;

	@Inject
	private LazyCourierIncome lazyDataModel;

	private List<SortMeta> preSortOrder;

	public CourierIncomeService getIncomeService() {
		return incomeService;
	}

	public void setIncomeService(CourierIncomeService incomeService) {
		this.incomeService = incomeService;
	}

	public List<SortMeta> getPreSortOrder() {
		return preSortOrder;
	}

	public void setPreSortOrder(List<SortMeta> preSortOrder) {
		this.preSortOrder = preSortOrder;
	}

	private CourierIncomeDTO selected;

	public CourierIncomeDTO getSelected() {
		return selected;
	}

	public void setSelected(CourierIncomeDTO selected) {
		this.selected = selected;
	}

	public LazyCourierIncome getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyCourierIncome lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public void onCellEdit(CellEditEvent event) {
		FacesMessage msg = null;
		CourierIncomeDTO dto = (CourierIncomeDTO) ((DataTable) event.getComponent()).getRowData();

		CourierIncomeDTO edited = incomeService.getCourierIncomeById(dto.getId());

		Integer newValue = (Integer) event.getNewValue();
		Integer oldValue = (Integer) event.getOldValue();
		if (newValue != null && oldValue != null && edited != null) {
			String columnName = event.getColumn().getClientId();
			if (columnName.contains("actual_cash")) {

				if (edited.getCash() >= newValue) {
					edited.setActualCash(newValue);
					incomeService.save(edited);
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succed", "ok");
				} else
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "failed",
							MessageProvider.getValue("out.invalid_income_value"));
			} else {
				if (edited.getVoucher() >= newValue) {
					incomeService.save(edited);
					edited.setActualVoucher(newValue);
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succed", "ok");
				} else
					msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "failed",
							MessageProvider.getValue("out.invalid_income_value"));

			}
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "failed",
					MessageProvider.getValue("out.invalid_income_value"));

		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void buildSortOrder() {
		preSortOrder = new ArrayList<>();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent column = viewRoot.findComponent("courierIncomeForm:income_table:courier_name");

		SortMeta sm1 = new SortMeta();
		sm1.setSortBy((org.primefaces.component.api.UIColumn) column);
		sm1.setSortField("courierName");
		sm1.setSortOrder(SortOrder.ASCENDING);

		UIComponent column2 = viewRoot.findComponent("courierIncomeForm:income_table:income_date");

		SortMeta sm2 = new SortMeta();
		sm2.setSortBy((org.primefaces.component.api.UIColumn) column2);
		sm2.setSortField("date");
		sm2.setSortOrder(SortOrder.DESCENDING);

		preSortOrder.add(sm1);
		preSortOrder.add(sm2);
	}

}