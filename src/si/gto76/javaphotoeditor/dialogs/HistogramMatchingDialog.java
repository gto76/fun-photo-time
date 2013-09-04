package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.Utility;


public class HistogramMatchingDialog extends MyDialog {
	
	private BufferedImage imgIn, imgOut;
	private JDesktopPane desktop;
	private JComboBox combo;
	private MyInternalFrame selectedFrame;
	
	public HistogramMatchingDialog( JDesktopPane desktop, MyInternalFrame selectedFrame  ) {
		super("Histogram Matching", false);
		
		this.selectedFrame = selectedFrame;
		imgIn = selectedFrame.getImg();
		
		//Vse internal frejme iz desktopa nalozi v tabelo
		//in jih dodeli komboboksu
		JInternalFrame[] allFrames = desktop.getAllFrames();
		combo = new JComboBox(allFrames);
		
		JComponent[] components = new JComponent[] {
			new JLabel("Get Histogram From:"),
			combo
		};
		
		addComponents(components);
		
	}
	
	public BufferedImage getProcessedImage() {
    	BufferedImage imgHis = ((MyInternalFrame) combo.getSelectedItem()).getImg();
		double [] histogram = Utility.getHistogram(imgHis);
		imgOut = Filtri.histogramMatching(imgIn, histogram);
		
		return imgOut;
    }
    
}
