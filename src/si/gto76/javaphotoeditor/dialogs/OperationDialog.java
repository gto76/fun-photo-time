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
	
	private static final long serialVersionUID = 4986931956064747068L;
	protected BufferedImage imgIn1, imgIn2, imgOut;
	protected MyInternalFrame selectedFrame1, selectedFrame2;
	protected JDesktopPane desktop;
	protected JComboBox combo1, combo2;
	
	public OperationDialog( JDesktopPane desktop, String title, String symbol ) {
		super(title);
		//Vse internal frejme iz desktopa nalozi v tabelo
		this.desktop = desktop;
		JInternalFrame[] allFrames = desktop.getAllFrames();
		
		//potem jih dodeli vsakmu komboboksu
		combo1 = new JComboBox(allFrames);
		combo2 = new JComboBox(allFrames);
		
		combo1.setPreferredSize(new Dimension(90, 20));
		combo2.setPreferredSize(new Dimension(90, 20));
		
		combo1.addActionListener(this);
		combo2.addActionListener(this);
		
		JComponent[] components = new JComponent[] {
			combo1,
			new JLabel(symbol),
			combo2
		};
		
		dlg.setSize(400, 104);
		addComponents(components);
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
    
    public String getName() {
    	String name = ((MyInternalFrame) combo1.getSelectedItem()).getFileName()
    			+ " "
    			+ ((MyInternalFrame) combo2.getSelectedItem()).getFileName();
    	return name;
    }
    
    public int getZoom() {
    	return ((MyInternalFrame) combo1.getSelectedItem()).getZoom();
    }
    
    abstract protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2);
	
}
