package si.gto76.funphototime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

public class ViewMenuUtil implements ActionListener {
	private static final String MENU_NAME = "Window";

	public void createViewMenuItem(JFrame mainFrame, JInternalFrame internalFrame) {
		JMenu menu = getVievMenu(mainFrame);
		if (menu == null) return;
		ViewMenuItem menuItem = new ViewMenuItem(internalFrame.getTitle(), internalFrame);
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	public void removeViewMenuItem(JFrame mainFrame, JInternalFrame internalFrame) {
		JMenu menu = getVievMenu(mainFrame);
		if (menu == null) return;
		for (MenuElement menuItem : menu.getSubElements()) {
			JPopupMenu popUp = (JPopupMenu) menuItem;
			for (MenuElement subElement : popUp.getSubElements()) {
				ViewMenuItem viewMenuItem = (ViewMenuItem) subElement;
				if (viewMenuItem.compareTo(internalFrame) == 0) {
					popUp.remove(viewMenuItem);
					return;
				}
			}
		}
	}

	class ViewMenuItem extends JMenuItem implements Comparable<JInternalFrame>,
			MyMenuInterface {
		private static final long serialVersionUID = 333892455020266595L;
		private JInternalFrame internalFrame;

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

	public void actionPerformed(ActionEvent e) {
		ViewMenuItem menuItem = (ViewMenuItem) e.getSource();
		try {
			menuItem.internalFrame.setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}
	
	private static JMenu getVievMenu(JFrame mainFrame) {
		JMenuBar menuBar = mainFrame.getJMenuBar();
		MenuElement[] menuElements = menuBar.getSubElements();
		for (MenuElement menuElement : menuElements) {
			if (menuElement instanceof JMenu) {
				JMenu menu = (JMenu) menuElement;
				String name = menu.getText();
				if (name.equalsIgnoreCase(MENU_NAME)) {
					return menu;				
				}
			}
		}
		return null;
	}

}