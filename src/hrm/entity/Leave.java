package hrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="leave_type")
public class Leave {
	
	@Id @Column(name="id", length=200)
	private String id;
	
	@Column(length=10)
	private String code; //ANNUAL LEAVE, MATERNITY LEAVE, PATERNITY LEAVE,
	
	@Column(length=100)
	private String name;
	
	private int paid;
	
	private int orderNo;
	
	public Leave() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPaid() {
		return paid == 1;
	}

	public void setPaid(boolean paid) {
		this.paid = paid ? 1 : 0;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	

}
