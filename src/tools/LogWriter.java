package tools;

import java.util.logging.Logger;

public class LogWriter extends Logger {
	
	
	
	protected LogWriter(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		// TODO Auto-generated constructor stub
	}

	public static void writeException(Exception e) {
		//TODO exception trace logging, stop application with popup
	}

}
