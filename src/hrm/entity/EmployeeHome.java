package hrm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="employee_home")
public class EmployeeHome extends Location {
	
	public EmployeeHome() {
		setId(lebah.util.UIDGenerator.getUID());
	}

}
