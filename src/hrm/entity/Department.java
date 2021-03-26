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
@Table(name="department")
public class Department {
	
	@Id @Column(name="id", length=100)
	private String id;
	@Column(length=150)
	private String name;

}
