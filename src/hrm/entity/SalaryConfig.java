package hrm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="salary_config")
public class SalaryConfig {
	
	
	@Id @Column(name="id", length=100)
	private String id;
	
	@Column(length=20)
	private String code;
	
	@Column(length=200)
	private String description;
	
	@OneToMany  (fetch = FetchType.LAZY, mappedBy="salaryConfig")
	private List<SalaryDeductionItem> deductions = new ArrayList<>();
	
	@OneToMany  (fetch = FetchType.LAZY, mappedBy="salaryConfig")
	private List<SalaryAllowance> allowances = new ArrayList<>();
	
	private double grossAmount;
	private double netAmount;
	
	public SalaryConfig() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SalaryDeductionItem> getDeductions() {
		return deductions;
	}

	public void setDeductions(List<SalaryDeductionItem> deductions) {
		this.deductions = deductions;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}



	public List<SalaryAllowance> getAllowances() {
		return allowances;
	}

	public void setAllowances(List<SalaryAllowance> allowances) {
		this.allowances = allowances;
	}

	public double getNetAmount() {
		calculateNetAmount();
		return netAmount;
	}

	public double calculateDeductionAmount() {
		double total = 0.0d;
		for ( SalaryDeductionItem item : deductions  ) {
			double rate = item.getRate();
			double amountDeduct = grossAmount * rate;
			total += amountDeduct;
		}
		return total;
	}
	
	public double calculateAllowanceAmount() {
		double total = 0.0d;
		for ( SalaryAllowance allowance : allowances) {
			total += allowance.getAmount();
		}
		return total;
	}
	

	private void calculateNetAmount() {
		netAmount = grossAmount + calculateAllowanceAmount() - calculateDeductionAmount();
	}
		

}
