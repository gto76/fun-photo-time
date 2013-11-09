package si.gto76.javaphotoeditor;

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

import si.gto76.javaphotoeditor.dialogs.MyMenuInterface;

public class ViewMenuUtil implements ActionListener {  
	
    public void createViewMenuItem(JFrame parent,JInternalFrame child) {  
        JMenuBar menuBar = parent.getJMenuBar();  
        MenuElement[] menuElements = menuBar.getSubElements();  
        for (MenuElement menuElement : menuElements) {  
            if (menuElement instanceof JMenu) {  
                JMenu menu = (JMenu)menuElement;  
                if ("Window".equalsIgnoreCase(menu.getText())) {  
                    ViewMenuItem menuItem = new ViewMenuItem(child.getTitle(), child);  
                    menuItem.addActionListener(this);  
                    menu.add(menuItem);  
                }  
            }  
        }  
    }     
    
    public void removeViewMenuItem(JFrame parent,JInternalFrame child) {  
        JMenuBar menuBar = parent.getJMenuBar();  
        MenuElement[] menuElements = menuBar.getSubElements();  
        for (MenuElement menuElement : menuElements) {  
            if (menuElement instanceof JMenu) {
                JMenu menu = (JMenu) menuElement;  
                if ("Window".equalsIgnoreCase(menu.getText())) {  
                	for (MenuElement win : menu.getSubElements()) {
                		JPopupMenu w = (JPopupMenu) win;
                		for (MenuElement men : w.getSubElements()) {
	                		ViewMenuItem a = (ViewMenuItem) men;
	                		if (a.compareTo(child) == 0) {  
	                			w.remove(a);
	                			return;
	                		}
                		}
                	}  
                }  
            }  
        }  
    }  
  
    class ViewMenuItem extends JMenuItem implements Comparable<JInternalFrame>,
    										MyMenuInterface {  
        private JInternalFrame childFrame;  
        
        ViewMenuItem (String childName, JInternalFrame childFrame){  
            super(childName);  
            this.childFrame = childFrame;  
        }  
        public int compareTo(JInternalFrame o) {  
            if(childFrame.equals(o))  
                return 0;  
            else  
                return -1;  
        }
        // Interface
		public int noOfOperands() {
			return 0;
		}  
    } 
    
    public void actionPerformed(ActionEvent e) {  
        ViewMenuItem menuItem = (ViewMenuItem)e.getSource();  
        JPopupMenu popMenu = (JPopupMenu)menuItem.getParent();  
        JMenu viewMenu = (JMenu)popMenu.getInvoker();  
        if ("Window".equalsIgnoreCase(viewMenu.getText())) {  
            MenuElement[] menuElements = popMenu.getSubElements();  
            for (MenuElement menuElement : menuElements) {  
                if (menuElement instanceof JMenuItem) {  
                    ViewMenuItem internalFrame = (ViewMenuItem)menuElement;  
                    if (internalFrame.compareTo(menuItem.childFrame) == 0) {  
                        try {
							internalFrame.childFrame.setSelected(true);
						} catch (PropertyVetoException e1) {
							e1.printStackTrace();
						};  
                    }
                }  
            }  
        }  
    }  
    
}  