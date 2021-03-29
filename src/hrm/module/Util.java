package hrm.module;

public class Util {
	
	public static double getDouble(String value) {
		double d = 0.0d;
		if ( value.equals("") ) return d;
		d = Double.parseDouble(value);
		return d;
	}
	
	public static int getInt(String value) {
		int d = 0;
		if ( value.equals("") ) return d;
		d = Integer.parseInt(value);
		return d;
	}

}
