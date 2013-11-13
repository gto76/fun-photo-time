package si.gto76.javaphotoeditor.dialogs;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public abstract class MyDialog extends JFrame {

	private static final long serialVersionUID = 3084765862658349147L;
	protected JPanel p;
	protected JDialog dlg;
	protected JOptionPane op;
	
	// TODO 2 da si zapomne pozicijo
	// TODO 2 problem ce pritisnes drugam
    public MyDialog(String title) {
    	init(title, BoxLayout.X_AXIS);
    }
	public MyDialog( String title, int orientation ) {
		init(title, orientation);
    }
	public MyDialog( String title, int orientation, int x, int y ) {
		init(title, orientation);
    	dlg.setSize(x, y);
    }
	private void init(String title, int orientation) {
		p = new JPanel();
    	p.setLayout(new BoxLayout(p, orientation));
    	
    	op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.OK_CANCEL_OPTION);
    	dlg = op.createDialog(this, title);
	}
	
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
    	if ( op.getValue() == null ) return true;
    	int option = ((Integer)op.getValue()).intValue();
		if (option == JOptionPane.CANCEL_OPTION) return true;
		else return false;
    }
    
    abstract public BufferedImage getProcessedImage(); //NEW

}
