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
@Table(name="modules")
public class Module {
	
	@Id @Column(name="id", length=200)
	private String id;
	@Column(length=100)
	private String title;
	@Column(length=100)
	private String className;
	@Column(length=20)
	private String flag;
	
	public Module() {
		
	}
	
	public Module(String className, String title, String flag) {
		setId(className);
		setClassName(className);
		setTitle(title);
		setFlag(flag);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
