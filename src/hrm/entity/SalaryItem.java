package hrm.entity;

import java.text.DecimalFormat;

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
@Table(name="salary_item")
public class SalaryItem {
	
	@Id @Column(name="id", length=100)
	private String id;
	
	private int orderNo;
	
	@Column(length=100)
	private String name;
	@Column(length=200)
	private String description;
	
	private int type; // 0 - addon, 1 - substract
	private double amount;
	
	@ManyToOne @JoinColumn(name="employeeJob_id")
	public EmployeeJob employeeJob;
	
	public SalaryItem() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public SalaryItem(int orderNo, String name, String description, double amount) {
		setId(lebah.util.UIDGenerator.getUID());
		this.orderNo = orderNo;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.type = amount < 0 ? 1 : 0;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	
	public String getAmountStr() {
		if ( amount < 0 )
			return new DecimalFormat("#.00").format(-amount);
		return new DecimalFormat("#.00").format(amount);
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public EmployeeJob getEmployeeJob() {
		return employeeJob;
	}

	public void setEmployeeJob(EmployeeJob employeeJob) {
		this.employeeJob = employeeJob;
	}

	
	
}
