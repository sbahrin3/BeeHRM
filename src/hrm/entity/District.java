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
@Table(name="district")
public class District {
	
	@Id @Column(length=50)
	private String id;
	@Column(length=100)
	private String name;
	
	@ManyToOne
	private State state;
	
	public District() {
		
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	

}
