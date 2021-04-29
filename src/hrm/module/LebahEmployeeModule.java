package hrm.module;

import hrm.entity.Employee;
import lebah.db.entity.User;
import lebah.module.LebahUserModule;

public abstract class LebahEmployeeModule extends LebahUserModule {
	
	Employee employee;
	
	@Override
	public void preProcess() {
		super.preProcess();
		User user = (User) context.get("user");
		employee = user.getEmployee();
		context.put("employee", employee);
	}

}
