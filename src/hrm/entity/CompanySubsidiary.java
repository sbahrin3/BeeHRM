package hrm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@DiscriminatorValue("SUB")
public class CompanySubsidiary extends Company {

}
