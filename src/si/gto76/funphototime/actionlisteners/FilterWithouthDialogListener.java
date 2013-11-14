package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.enums.NoDialogFilter;

public class FilterWithouthDialogListener implements ActionListener{
	FunPhotoTimeFrame mainFrame;
	NoDialogFilter fi;

	public FilterWithouthDialogListener(NoDialogFilter fi, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.fi = fi;
    }
	
    public void actionPerformed(ActionEvent e) {
    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
    	//naredi nov frame ki zaenkrat vsebuje samo zumirano obdelano sliko brez originala
    	MyInternalFrame frameOut = mainFrame.createZoomedFrame(fi.getFilteredImage(mainFrame.getSelectedBufferedImage()), frameIn);
    	
    	if ( frameIn.getZoom() != 100 ) {
        	//naredi nit, ki bo filtrirala originalmno sliko
        	fi.createThread(frameIn.getOriginalImg(), frameOut);
        }
    }
}