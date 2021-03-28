package hrm.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Embeddable
public class Salary {
	
	private double basicAmount;
	
	@Transient
	private double grossAmount;
	@Transient
	private double netAmount;
	
	@OneToOne @JoinColumn(name="salary_config_id")
	private SalaryConfig salaryConfig; 
	
	
	public double getBasicAmount() {
		return basicAmount;
	}

	public void setBasicAmount(double basicAmount) {
		this.basicAmount = basicAmount;
	}
	
	public double calculateAllowanceAmount() {
		double total = 0.0d;
		for ( SalaryAllowance allowance : salaryConfig.getAllowances() ) {
			total += allowance.getAmount();
		}
		return total;
	}
	
	public double getNetAmount() {
		netAmount = grossAmount - calculateDeductionAmount();
		return netAmount;
	}
	
	public double getGrossAmount() {
		grossAmount = basicAmount + calculateAllowanceAmount();
		return grossAmount;
	}

	public double calculateDeductionAmount() {
		double grossAmount_ = getGrossAmount();
		double total = 0.0d;
		for ( SalaryDeductionItem item : salaryConfig.getDeductions()  ) {
			double rate = item.getRate();
			double amountDeduct = item.getDeductionType() == 0 ? basicAmount * rate : grossAmount_ * rate;;
			total += amountDeduct;
		}
		return total;
	}
	
	public SalaryConfig getSalaryConfig() {
		if ( salaryConfig == null ) salaryConfig = new SalaryConfig();
		return salaryConfig;
	}

	public void setSalaryConfig(SalaryConfig salaryConfig) {
		this.salaryConfig = salaryConfig;
	}
	


}
