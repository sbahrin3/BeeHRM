package misc;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hrm.entity.Employee;
import hrm.entity.EmployeeLeave;
import hrm.module.Util;
import lebah.db.entity.Persistence;

public class LeaveTest {
	
	public static void main(String[] args) {
		
		Persistence db = Persistence.db();
		
		String id = "d3d5bb87ca71e40d6c32a0cae7da087ec9e9ec3d";
		
		Employee employee = db.find(Employee.class, id);
		System.out.println(employee.getName());
		
		List<EmployeeLeave> employeeLeaves = employee.getEmployeeLeaves();
		
		Date startOfYear = Util.toDate("01/01/2021");
		Date endOfYear = Util.toDate("31/12/2021");
		
		employeeLeaves =  employeeLeaves.stream()
			.filter(l -> l.getApproveFromDate() != null && l.getApproveFromDate().after(startOfYear) && l.getApproveFromDate().before(endOfYear))
			.collect(Collectors.toList());
			
		
		
	}

}
