package lebah.module;

import lebah.portal.action.Command;

public class HelloWorldModule extends LebahUserAccessModule {
	
	private String path = "/demo/hello";
	
	
	@Override
	public void preProcess() {
		super.preProcess();
		try {
			Thread.sleep(500);
		} catch ( Exception e ) {
			
		}
	}

	@Override
	public String start() {
		String name = request.getParameter("name");
		context.put("name", name);
		return path + "/start.vm";
	}
	
	@Command("sayHello")
	public String sayHello() throws Exception {
		String name = request.getParameter("name");
		context.put("name", name);
		
		
		return path + "/start.vm";
	}

}
