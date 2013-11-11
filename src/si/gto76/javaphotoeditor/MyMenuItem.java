package si.gto76.javaphotoeditor;

import javax.swing.JMenuItem;
import si.gto76.javaphotoeditor.dialogs.MyMenuInterface;


class MyMenuItem extends JMenuItem
					implements MyMenuInterface {
	
	private static final long serialVersionUID = -3854336471073348117L;
	private int noOfOperands;
	
	public MyMenuItem(int noOfOperands) {
		super();
		this.noOfOperands = noOfOperands;
	}
	
	public MyMenuItem(String text, int noOfOperands) {
		super(text);
		this.noOfOperands = noOfOperands;
	}
	
	public int noOfOperands() {
		return noOfOperands;
	}
}
