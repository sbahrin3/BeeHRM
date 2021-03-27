package hrm.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Embeddable
public class Salary {
	
	private int year;
	private int month;
	
	@Temporal(TemporalType.DATE)
	private Date payDate;
	

}
