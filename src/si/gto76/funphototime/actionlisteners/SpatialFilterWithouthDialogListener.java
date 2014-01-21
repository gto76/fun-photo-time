package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.Zoom;
import si.gto76.funphototime.enums.NoDialogFilter;

public class SpatialFilterWithouthDialogListener implements ActionListener{
	FunPhotoTimeFrame mainFrame;
	NoDialogFilter fi;

	public SpatialFilterWithouthDialogListener(NoDialogFilter fi, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.fi = fi;
    }
	
    public void actionPerformed(ActionEvent e) {
    	if (mainFrame.isMemoryCritical())
			return;
		
    	try { 
	    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
	    	BufferedImage origImgIn = Utility.waitForOriginalImage(frameIn);
		    
	    	BufferedImage origImgOut = fi.getFilteredImage(origImgIn);
	    	int zoom = frameIn.getZoom();
	    	
	    	if ( frameIn.getZoom() == 100 ) {
	    		mainFrame.createZoomedFrame(origImgOut, frameIn);
	    	}
	    	else {
	    		BufferedImage zoomedImgOut = Zoom.out(origImgOut, zoom);
	    		MyInternalFrame frameOut = mainFrame.createZoomedFrame(zoomedImgOut, frameIn);
	    		frameOut.setOriginalImg(origImgOut);
	    	}
    	} catch (OutOfMemoryError g) {
	    	mainFrame.outOfMemoryMessage();
		}
    }
    
}