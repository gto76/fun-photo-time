package si.gto76.funphototime;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;

import org.omg.CosNaming.IstringHelper;

public class ViewMenuUtil implements ActionListener {
	private static final String MENU_NAME = "Window";

	public void createViewMenuItem(JFrame mainFrame, JInternalFrame internalFrame) {
		JMenu menu = getVievMenu(mainFrame);
		//removeCloseAllElement(menu);		
		if (menu == null) return;
		ViewMenuItem menuItem = new ViewMenuItem(internalFrame.getTitle(), internalFrame);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		refreshMenuItems(mainFrame);
		//addCloseAllElement(menu);
	}

	private void removeCloseAllElement(JMenu menu) {
		MenuElement lastSubElement = null, beforeLast = null;
		for (MenuElement menuItem : menu.getSubElements()) {
			JPopupMenu popUp = (JPopupMenu) menuItem;
			for (MenuElement subElement : popUp.getSubElements()) {
				beforeLast = lastSubElement;
				lastSubElement = subElement;
				/*
				if (subElement instanceof MyMenuItem) {
					if (lastSubElement != null)
						menu.remove((JMenuItem) lastSubElement);
					menu.remove((JMenuItem) subElement);
					return;
				}
				lastSubElement = subElement;
				*/
			}
		}
		if (beforeLast != null)
			menu.remove((JMenuItem) beforeLast);
		menu.remove((JMenuItem) lastSubElement);
		return;
	}
	
	private void addCloseAllElement(JMenu menu) {
	    MyMenuItem menuWindowCloseall = new MyMenuItem(1);
        menuWindowCloseall.setText("Close All");
        menu.addSeparator();
        menu.add(menuWindowCloseall);
	}

	void removeViewMenuItem(JFrame mainFrame, JInternalFrame internalFrame) {
		JMenu menu = getVievMenu(mainFrame);
		if (menu == null) return;
		for (MenuElement menuItem : menu.getSubElements()) {
			JPopupMenu popUp = (JPopupMenu) menuItem;
			for (MenuElement subElement : popUp.getSubElements()) {
				if (subElement instanceof MyMenuItem)
					continue;
				ViewMenuItem viewMenuItem = (ViewMenuItem) subElement;
				if (viewMenuItem.compareTo(internalFrame) == 0) {
					popUp.remove(viewMenuItem);
					refreshMenuItems(mainFrame);
					return;
				}
			}
		}
	}
	
	public void refreshMenuItems(JFrame mainFrame) {
		boolean first = true;
		boolean next = false;
		ViewMenuItem viewMenuItem = null;
		ViewMenuItem lastMenuItem = null;
		JMenu menu = getVievMenu(mainFrame);
		if (menu == null) return;
		for (MenuElement menuItem : menu.getSubElements()) {
			JPopupMenu popUp = (JPopupMenu) menuItem;
			for (MenuElement subElement : popUp.getSubElements()) {
				if (subElement instanceof MyMenuItem)
					continue;
				lastMenuItem = viewMenuItem;
				viewMenuItem = (ViewMenuItem) subElement;
				viewMenuItem.setAccelerator(null);
				
				if (next) {
					viewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
					next = false;
				}
				
				JDesktopPane desktop = ((FunPhotoTimeFrame) mainFrame).desktop; 
				if (viewMenuItem.compareTo(desktop.getSelectedFrame()) == 0) {
					//viewMenuItem.setText("> "+viewMenuItem.getText());
					if (!first)
						lastMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, ActionEvent.SHIFT_MASK));
					next = true;
				}
				
				if (first) {
					viewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0));
					first = false;
				} 
			}
		}
		if (!first) {
			viewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0));
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