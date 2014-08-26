package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.enums.ParameterlessFilter;

public class ParameterlessFilterListener implements ActionListener{
	FunPhotoTimeFrame mainFrame;
	ParameterlessFilter fi;

	public ParameterlessFilterListener(ParameterlessFilter fi, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.fi = fi;
    }
	
    public void actionPerformed(ActionEvent e) {
    	if (mainFrame.isMemoryCritical())
			return;
		
    	try {
	    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
	    	//naredi nov frame ki zaenkrat vsebuje samo zumirano obdelano sliko brez originala
	    	
    		BufferedImage filteredImage = fi.getFilteredImage(mainFrame.getSelectedBufferedImage());
    		MyInternalFrame frameOut = mainFrame.createZoomedFrame(filteredImage, frameIn);
        	
        	if ( frameIn.getZoom() != 100 ) {
        		// Makes thread that waits for original image to be created.
        		Thread t = new Thread(new WaitingThread(frameIn, frameOut, fi));
                t.start();
            }
	    } catch (OutOfMemoryError g) {
	    	mainFrame.outOfMemoryMessage();
		}
    }
    
    private static class WaitingThread implements Runnable {
    	MyInternalFrame frameIn;
    	MyInternalFrame frameOut;
    	ParameterlessFilter fi;
	    public WaitingThread(MyInternalFrame frameIn, MyInternalFrame frameOut,
				ParameterlessFilter fi) {
			this.frameIn = frameIn;
			this.frameOut = frameOut;
			this.fi = fi;
		}

		public void run() {
			BufferedImage imgIn = Utility.waitForOriginalImage(frameIn);
    		// Makes thread that will filter the original image.
        	fi.createThread(imgIn, frameOut);
	    }
    }
    
}