package lebah.module;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class AppProperties {
	
    private static ResourceBundle rb;
    
    static {
        try {
            rb = ResourceBundle.getBundle("app");
        } catch ( MissingResourceException e ) {
            System.out.println(e.getMessage()); 
        }
    }
    
    public static String valueOf(String key) {
    	String value = "/Users/Admin/uploads";
        try {
            value = rb.getString(key);
        } catch ( MissingResourceException e ) { 
            System.out.println("Recource - " + e.getMessage()); 
        }
        
        return value;
    }
	
    public static String uploadDir() {
        return valueOf("uploads");
    }
    
    public static String tempPdfDir() {
    	return valueOf("tempPdfDir");
    }
    

    
    public static void main(String[] args) throws Exception {
    	System.out.println(AppProperties.uploadDir());
    }

}
