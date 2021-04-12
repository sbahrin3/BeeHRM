package hrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */

@Entity
@Table(name="state")
public class State {
	
	@Id @Column(length=50)
	private String id;
	@Column(length=100)
	private String name;
	
	
	/*
	 * 1 - Saturday and Sunday
	 * 2 - Friday and Saturday
	 * 3 - Sunday only
	 * 4 - Saturday only
	 * 5 - Friday only
	 */
	private int weekendType;
	
	@ManyToOne
	private Country country;
	
	public State() {
		
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getWeekendType() {
		return weekendType;
	}

	public void setWeekendType(int weekendType) {
		this.weekendType = weekendType;
	}
	
	

}
