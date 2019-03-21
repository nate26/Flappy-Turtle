package tools;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import controller.GlobalSettings;

public class LogWriter {// extends Logger {
	
	private JTextArea logs;
	private String logText = "";
	
	//protected 
	public LogWriter() {//String name, String resourceBundleName) {
		//super(name, resourceBundleName);
		// TODO Auto-generated constructor stub
		
		JFrame window = new JFrame("Logs");
		window.setSize(300, 500);
		window.setLocation(GlobalSettings.WINDOW_WIDTH + 20, 50);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(false);
		window.setFocusTraversalKeysEnabled(false);
		
		logs = new JTextArea();
		logs.setEditable(false);
		JScrollPane scroll = new JScrollPane(logs);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		window.add(scroll);

		window.setVisible(true);
	}
	
	public void log(String s) {
		this.logText = this.logText + s + "\n";
		logs.setText(this.logText);
	}

	public static void writeException(Exception e) {
		//TODO exception trace logging, stop application with popup
	}

}
