package hrm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "salary_deduction_items",
            joinColumns = {@JoinColumn(name = "salary_config_id")},
            inverseJoinColumns = {@JoinColumn(name = "deduction_id")}
    )
	private List<SalaryDeductionItem> deductions;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "salary_allowance_items",
            joinColumns = {@JoinColumn(name = "salary_config_id")},
            inverseJoinColumns = {@JoinColumn(name = "allowance_id")}
    )	private List<SalaryAllowance> allowances;
	
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
		if ( deductions == null ) deductions = new ArrayList<>();
		return deductions;
	}

	public void setDeductions(List<SalaryDeductionItem> deductions) {
		this.deductions = deductions;
	}

	public List<SalaryAllowance> getAllowances() {
		if ( allowances == null ) allowances = new ArrayList<>();
		return allowances;
	}

	public void setAllowances(List<SalaryAllowance> allowances) {
		this.allowances = allowances;
	}


	

	


		

}
