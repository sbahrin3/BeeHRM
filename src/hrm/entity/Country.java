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
@Table(name="country")
public class Country {
	
	@Id @Column(length=50)
	private String id;
	@Column(length=100)
	private String name;
	
	public Country() {
		
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
	
	

}
