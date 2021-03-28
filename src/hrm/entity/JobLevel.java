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
@Table(name="job_level")
public class JobLevel {
	
	@Id @Column(name="id", length=200)
	private String id;
	
	@Column(length=10)
	private String code;
	
	@Column(length=100)
	private String name;
	
	private int levelOrder; //0 - Director, 1 - Top Management, 2 - Middle Management, 3 - Exec. etc... user defined
	
	public JobLevel() {
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

	public int getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(int level) {
		this.levelOrder = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
