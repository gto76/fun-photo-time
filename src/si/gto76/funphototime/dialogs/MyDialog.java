package si.gto76.funphototime.dialogs;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import si.gto76.funphototime.FunPhotoTime;


public abstract class MyDialog {

	protected JPanel p;
	protected JDialog dlg;
	protected JOptionPane op;
	static public Point location = new Point(FunPhotoTime.frame.getLocation().x+100,
			FunPhotoTime.frame.getLocation().y+100);
	
    public MyDialog(String title) {
    	init(title, BoxLayout.X_AXIS);
    }
    
	public MyDialog(String title, int orientation) {
		init(title, orientation);
    }
	
	public MyDialog(String title, int orientation, int x, int y) {
		init(title, orientation);
    	dlg.setSize(x, y);
    }
	
	private void init(String title, int orientation) {
		p = new JPanel();
    	p.setLayout(new BoxLayout(p, orientation));
    	
    	op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.OK_CANCEL_OPTION);
    	
    	dlg = op.createDialog(title);
    	dlg.setAlwaysOnTop(true);
    	dlg.setLocation(location);
    	
    	//dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	// PROTO
        //Object name = escape.getValue(Action.NAME);  
        //p.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), name);  
        //p.getActionMap().put(name,escape);
	}
	
	// PROTO
	/*
	private Action escape = new AbstractAction() {
        { putValue(NAME, "escape"); }  
        public void actionPerformed(ActionEvent e) {  
            JComponent source = (JComponent)e.getSource();  
            Window window = SwingUtilities.getWindowAncestor(source);  
            window.dispose();  
            //Dialog dialog = (Dialog)source.getFocusCycleRootAncestor();  
            //dialog.dispose();  
            //System.out.println("source = " + source.getClass().getName() + "\n" +  
            //                   "source.focusCycleRootAncestor = " +  
            //           source.getFocusCycleRootAncestor().getClass().getName());  
        }  
    };
    */
	
	protected void addComponent(JComponent jComp) {
    	//s to metodo podrazred poda slajder ali kaksno drugo
    	//komponento, kateri potem prisluskuje ali pa tudi ne
    	p.add(jComp);
    	dlg.setVisible(true);
    	dlg.dispose();
    }
    
    protected void addComponents(JComponent[] jComp) {
    	//s to metodo podrazred poda slajder ali kaksno drugo
    	//komponento, kateri potem prisluskuje
    	for ( int i = 0; i < Array.getLength(jComp); i++) {
    		p.add(jComp[i]);
    	}	
    	dlg.setVisible(true);
    	dlg.dispose();
    }

	protected Component getComponent() {
		return	p.getComponent(0);
    }
    
    public boolean wasCanceled() {
    	location = dlg.getLocation();
    	if ( op.getValue() == null ) return true;
    	int option = ((Integer)op.getValue()).intValue();
		if (option == JOptionPane.CANCEL_OPTION) return true;
		else return false;
    }
    
    abstract public BufferedImage getProcessedImage();

}
