package hrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//TODO

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="skill_level")
public class SkillLevel {
	
	@Id @Column(name="id", length=200)
	private String id;
	@Column(length=100)
	private String name;

}
