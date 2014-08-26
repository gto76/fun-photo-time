package si.gto76.funphototime.mymenu;

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
import javax.swing.KeyStroke;
import javax.swing.MenuElement;

import si.gto76.funphototime.FunPhotoTimeFrame;

public class ViewMenuUtil {
	private static final String MENU_NAME = "Window";

	public static void createItem(JFrame mainFrame, JInternalFrame internalFrame) {
		JMenu menu = getViewMenu(mainFrame);
		if (menu == null) return;
		ViewMenuItem menuItem = new ViewMenuItem(internalFrame.getTitle(), internalFrame);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewMenuItem menuItem = (ViewMenuItem) e.getSource();
				try {
					menuItem.internalFrame.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		refreshItems(mainFrame);
	}

	public static void removeItem(JFrame mainFrame, JInternalFrame internalFrame) {
		JMenu menu = getViewMenu(mainFrame);
		if (menu == null) return;
		for (MenuElement menuItem : menu.getSubElements()) {
			JPopupMenu popUp = (JPopupMenu) menuItem;
			for (MenuElement subElement : popUp.getSubElements()) {
				if (!(subElement instanceof ViewMenuItem) /*MyMenuItem*/)
					continue;
				ViewMenuItem viewMenuItem = (ViewMenuItem) subElement;
				if (viewMenuItem.compareTo(internalFrame) == 0) {
					popUp.remove(viewMenuItem);
					refreshItems(mainFrame);
					return;
				}
			}
		}
	}
	
	public static void refreshItems(JFrame mainFrame) {
		boolean first = true;
		boolean next = false;
		ViewMenuItem viewMenuItem = null;
		ViewMenuItem lastMenuItem = null;
		JMenu menu = getViewMenu(mainFrame);
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
	
	/*
	 * COMMON
	 */
	
	private static JMenu getViewMenu(JFrame mainFrame) {
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
