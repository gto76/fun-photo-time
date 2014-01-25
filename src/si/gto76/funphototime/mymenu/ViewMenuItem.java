package si.gto76.funphototime.mymenu;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;


class ViewMenuItem extends JMenuItem implements Comparable<JInternalFrame>,
															MyMenuInterface {
	private static final long serialVersionUID = 333892455020266595L;
	public final JInternalFrame internalFrame;
	
	ViewMenuItem(String name, JInternalFrame internalFrame) {
		super(name);
		this.internalFrame = internalFrame;
	}
	
	public int compareTo(JInternalFrame otherFrame) {
		if (internalFrame.equals(otherFrame))
			return 0;
		else
			return -1;
	}
	
	// MyMenuInterface:
	public int noOfOperands() {
		return 0;
	}
}