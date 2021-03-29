package hrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="salary_deduction_item")
public class SalaryDeductionItem {
	
	@Id @Column(name="id", length=100)
	private String id;
	
	@Column(length=50)
	private String name; //eg. TAX, SOCSO, INSURANCE, EPF, OTHER FUND
	
	@Column(length=200)
	private String description;
			
	private int useRate;
	private int rate; //rate in percent.  50 mean .50
	private double amount; //deduct amount if not using rate
	
	private int rateOnBasicOnly; 
		
	@ManyToOne @JoinColumn(name="salary_config_id")
	private SalaryConfig salaryConfig;
	
	public SalaryDeductionItem() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public SalaryConfig getSalaryConfig() {
		return salaryConfig;
	}

	public void setSalaryConfig(SalaryConfig salaryConfig) {
		this.salaryConfig = salaryConfig;
	}

	public boolean isUseRate() {
		return useRate == 1;
	}

	public void setUseRate(boolean useRate) {
		this.useRate = useRate ? 1 : 0;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isRateOnBasicOnly() {
		return rateOnBasicOnly == 1;
	}

	public void setRateOnBasicOnly(boolean rateOnBasicOnly) {
		this.rateOnBasicOnly = rateOnBasicOnly ? 1 : 0;
	}


	

}
