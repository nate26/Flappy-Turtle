package item;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemsViewEnvironment {
	
	private GenericItem item;
	private JLabel label;
	
	public static void main(String[] args) { new ItemsViewEnvironment(); }
	
	public ItemsViewEnvironment() {
		ItemManager im = new ItemManager();
		item = im.getItems().get(0);
		
		JFrame w = new JFrame("Items Viewing Environment");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setSize(600, 330);
		w.setLayout(null);
		
		JPanel pan = new JPanel();
		pan.setBounds(50, 50, 400, 200);
		pan.setLayout(null);
		w.add(pan);
		
		label = new JLabel();
		label.setBounds(10, 10, 130, 130);
		label.setIcon(item.getIcon());
		pan.add(label);		
		
		w.setVisible(true);
	}
}
