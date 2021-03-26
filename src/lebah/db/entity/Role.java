package lebah.db.entity;

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
@Table(name="roles")
public class Role {
	
	@Id @Column(name="id", length=200)
	private String id;
	@Column(length=100)
	private String name;
	
	public Role() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public Role(String id, String name) {
		setId(id);
		setName(name);
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

	@Override
	public boolean equals(Object o) {
		Role r = (Role) o;
		return r.getId().equals(getId());
	}
	
	@Override
	public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
	}
	

}
