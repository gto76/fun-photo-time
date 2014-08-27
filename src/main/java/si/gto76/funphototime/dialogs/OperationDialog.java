package si.gto76.funphototime.dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Utility;


public abstract class OperationDialog extends MyDialog 
									implements ActionListener{
	
	protected BufferedImage imgIn1, imgIn2, imgOut;
	protected MyInternalFrame selectedFrame1, selectedFrame2;
	protected JDesktopPane desktop;
	protected JComboBox<JInternalFrame> combo1;
    protected JComboBox<JInternalFrame> combo2;
	
	public OperationDialog( JDesktopPane desktop, String title, String symbol ) {
		super(title);
		//Vse internal frejme iz desktopa nalozi v tabelo
		this.desktop = desktop;
		JInternalFrame[] allFrames = desktop.getAllFrames();
		
		//potem jih dodeli vsakmu komboboksu
		combo1 = new JComboBox<JInternalFrame>(allFrames);
		combo2 = new JComboBox<JInternalFrame>(allFrames);
		
		//if more than one frame exist
		if (allFrames.length > 1) {
			combo2.setSelectedIndex(1);
		}
		
		combo1.setPreferredSize(new Dimension(90, 20));
		combo2.setPreferredSize(new Dimension(90, 20));
		
		combo1.addActionListener(this);
		combo2.addActionListener(this);
		
		JComponent[] components = new JComponent[] {
			combo1,
			new JLabel(symbol),
			combo2
		};
		
		int longestName = getLongestName(allFrames);
		int width = longestName * 8 * 2 + 100;
		
		dlg.setSize(width, 104);
		addComponents(components);
	}
	
	private int getLongestName(JInternalFrame[] allFrames) {
		int longest = 0;
		for (JInternalFrame frame : allFrames) {
			MyInternalFrame myFrame = (MyInternalFrame) frame;
			int length = myFrame.getTitle().length();
			if (length > longest)
				longest = length;
		}
		return longest;
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
		imgIn1 = Utility.waitForOriginalImage((MyInternalFrame) combo1.getSelectedItem());
		imgIn2 = Utility.waitForOriginalImage((MyInternalFrame) combo2.getSelectedItem());
		imgOut = operation(imgIn1, imgIn2);
		return imgOut;  	
    }
    
    public String getName() {
    	String name = ((MyInternalFrame) combo1.getSelectedItem()).getFileName();
    	//		+ " "
    	//		+ ((MyInternalFrame) combo2.getSelectedItem()).getFileName();
    	return name;
    }
    
    public int getFirstZoom() {
    	return getZoom(combo1);
    }

    public int getSecondZoom() {
    	return getZoom(combo2);
    }
    
    private int getZoom(JComboBox<JInternalFrame> combo) {
    	return ((MyInternalFrame) combo.getSelectedItem()).getZoom();
    }
    
    public MyInternalFrame getFirstFrame() {
    	return (MyInternalFrame) combo1.getSelectedItem();
    }
    
    abstract protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2);
	
}
