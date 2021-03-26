package lebah.module;

import lebah.portal.action.Command;
import lebah.portal.action.LebahModule;

public class HelloCalculatorModule extends LebahModule {
	
	private String path = "/demo/calculator";
	
	@Override
	public void preProcess() {
		try {
			Thread.sleep(500);
		} catch ( Exception e ) {
			
		}
	}

	@Override
	public String start() {
		return path + "/start.vm";
	}
	
	@Command("calculate")
	public String calculate() throws Exception {
		int number1 = Integer.parseInt(getParam("number1"));
		int number2 = Integer.parseInt(getParam("number2"));
		int result = 0;
		String operation = getParam("operation");
		if ( "add".equals(operation))
			result = number1 + number2;
		else if ( "substract".equals(operation))
			result = number1 - number2;
		else if ( "multiply".equals(operation))
			result = number1 * number2;
		
		context.put("number1", number1);
		context.put("number2", number2);
		context.put("result", result);
		
		return path + "/start.vm";
	}

}
