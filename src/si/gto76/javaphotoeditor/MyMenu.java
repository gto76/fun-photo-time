package si.gto76.javaphotoeditor;

import javax.swing.JMenu;
import si.gto76.javaphotoeditor.dialogs.MyMenuInterface;


class MyMenu extends JMenu
				implements MyMenuInterface {
	
	private int noOfOperands;
	
	public MyMenu(int noOfOperands) {
		super();
		this.noOfOperands = noOfOperands;
	}
	
	public MyMenu(String text, int noOfOperands) {
		super(text);
		this.noOfOperands = noOfOperands;
	}
	
	public int noOfOperands() {
		return noOfOperands;
	}
}
