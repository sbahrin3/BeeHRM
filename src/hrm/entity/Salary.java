package hrm.entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Embeddable
public class Salary {
	
	private double basicAmount;
	private double grossAmount;
	private double netAmount;
	
	@ManyToOne @JoinColumn(name="salary_config_id")
	private SalaryConfig salaryConfig; 
	
	public double getBasicAmount() {
		return basicAmount;
	}

	public void setBasicAmount(double basicAmount) {
		this.basicAmount = basicAmount;
	}
	
	public String getBasicAmountStr() {
		return new DecimalFormat("#.00").format(basicAmount);
	}
	
	public double calculateAllowanceAmount() {
		double total = 0.0d;
		for ( SalaryAllowance allowance : salaryConfig.getAllowances() ) {
			total += allowance.getAmount();
		}
		return total;
	}
	
	
	
	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public double getNetAmount() {
		return netAmount;
	}
	
	public String getNetAmountStr() {
		return new DecimalFormat("#.00").format(netAmount);
	}
	
	public String getGrossAmountStr() {
		return new DecimalFormat("#.00").format(grossAmount);
	}
	
	public double getGrossAmount() {
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
