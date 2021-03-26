package hrm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@DiscriminatorValue("HQ")
public class CompanyHQ extends Company {

}
