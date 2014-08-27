package si.gto76.funphototime.mymenu;

import javax.swing.JMenu;


public class MyMenu extends JMenu
				implements MyMenuInterface {
	
	private static final long serialVersionUID = -5243862216542202503L;
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
