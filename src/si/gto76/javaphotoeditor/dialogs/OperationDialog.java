package si.gto76.javaphotoeditor.dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import si.gto76.javaphotoeditor.MyInternalFrame;


public abstract class OperationDialog extends MyDialog 
									implements ActionListener{
	
	protected BufferedImage imgIn1, imgIn2, imgOut;
	protected MyInternalFrame selectedFrame1, selectedFrame2;
	protected JDesktopPane desktop;
	protected JComboBox combo1, combo2;
	
	
	public OperationDialog( JDesktopPane desktop, String title, String symbol ) {
		super(title);
		
		this.desktop = desktop;
		
		//Naredi komponente in jih "ugradi" v dialog
		
		//Vse internal frejme iz desktopa nalozi v tabelo
		JInternalFrame[] allFrames = desktop.getAllFrames();
		
		//potem jih dodeli vsakmu komboboksu
		combo1 = new JComboBox(allFrames);
		combo2 = new JComboBox(allFrames);
		
		combo1.setPreferredSize(new Dimension(90, 20));
		combo2.setPreferredSize(new Dimension(90, 20));
		//combo1.getComponentPopupMenu().setPreferredSize(new Dimension(150, 50));
		
		combo1.addActionListener(this);
		combo2.addActionListener(this);
		
		JComponent[] components = new JComponent[] {
			combo1,
			new JLabel(symbol),
			combo2
		};
		
		/*
		JComponent[] components = new JComponent[3];
		components[0] = combo1;
		components[1] = new JLabel(symbol);
		components[2] = combo2;
		*/
		
		//dlg.setPreferredSize(new Dimension(400, 60));
		//p.setPreferredSize(new Dimension(400, 60));
		//op.setPreferredSize(new Dimension(400, 60));
		
		addComponents(components);
		//OperationDialog.this.setSize(new Dimension(600, 60));
		
	}
	
	public void actionPerformed(ActionEvent e) {
		//ko izberemo v kombo boksu en okvir ga izbere, da ga vidmo
        JComboBox cb = (JComboBox)e.getSource();
        try {
        	((MyInternalFrame) cb.getSelectedItem()).setSelected(true);
        } catch (java.beans.PropertyVetoException f) {}
    }
    
    public BufferedImage getProcessedImage() {
		imgIn1 = ((MyInternalFrame) combo1.getSelectedItem()).getImg();
		imgIn2 = ((MyInternalFrame) combo2.getSelectedItem()).getImg();
		imgOut = operation(imgIn1, imgIn2);
		return imgOut;  	
    }
    
    public BufferedImage getProcessedOrigImage() {
		imgIn1 = ((MyInternalFrame) combo1.getSelectedItem()).getOriginalImg();
		imgIn2 = ((MyInternalFrame) combo2.getSelectedItem()).getOriginalImg();
		imgOut = operation(imgIn1, imgIn2);
		return imgOut;  	
    }
    
    public int getZoom() {
    	return ((MyInternalFrame) combo1.getSelectedItem()).getZoom();
    }
    
    abstract protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgOn2);
	
}
