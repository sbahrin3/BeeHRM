package lebah.db.entity;

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
@Table(name="role_modules")
public class RoleModule {
	
	@Id @Column(name="id", length=200)
	private String id;
	@ManyToOne @JoinColumn(name="role_id")
	private Role role;
	@ManyToOne @JoinColumn(name="module_id")
	private Module module;
	
	public RoleModule() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public RoleModule(Role role, Module module) {
		setId(lebah.util.UIDGenerator.getUID());
		setRole(role);
		setModule(module);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	
	

}
