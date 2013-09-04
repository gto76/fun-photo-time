package si.gto76.javaphotoeditor.dialogs;

import java.awt.Component;
import java.lang.reflect.Array;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import si.gto76.javaphotoeditor.Filtri;


abstract class MyDialog extends JFrame {
	protected JPanel p;
	protected JDialog dlg;
	protected JOptionPane op;
	
	public MyDialog( String title ) {
		p = new JPanel();
    	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
    	
    	op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.OK_CANCEL_OPTION);
    	dlg = op.createDialog(this, title );
    }
    
    public MyDialog( String title, boolean orientation ) {
		p = new JPanel();
		//ce je orientation true nalaga komponente po x osi,
		//drugace pa po y
		if ( orientation )
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		else
    		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    		
    	op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.OK_CANCEL_OPTION);
    	dlg = op.createDialog(this, title );
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
    	int option = ((Integer)op.getValue()).intValue();
		if (option == JOptionPane.CANCEL_OPTION) return true;
		else return false;
    }

}
