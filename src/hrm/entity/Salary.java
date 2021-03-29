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
			double amountDeduct = 0;
			
			if ( item.isUseRate() ) 
				amountDeduct = item.isRateOnBasicOnly() ? basicAmount * item.getRate() : grossAmount_ * item.getRate();
			else 
				amountDeduct = item.getAmount();
			
					
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
