package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsDouble;
import si.gto76.funphototime.dialogs.FilterDialogWithSliderDouble;
import si.gto76.funphototime.enums.Filter;

public class FilterDialogReturningDoubleListener implements ActionListener {
	FunPhotoTimeFrame mainFrame;
	Filter fi;

	public FilterDialogReturningDoubleListener(Filter fi, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.fi = fi;
    }
	
	public void actionPerformed(ActionEvent e) {
		if (mainFrame.isMemoryCritical())
			return;

		try {
	    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
	    	FilterDialogThatReturnsDouble dialog = fi.getDialog(frameIn);
	    	if (!dialog.wasCanceled()) {
	    		double values = dialog.getDouble();
	    		MyInternalFrame frameOut = mainFrame.createZoomedFrame(dialog.getProcessedImage(), frameIn);
	    		if ( frameIn.getZoom() != 100 ) { // NEW!!!
	    			// Makes thread that waits for original image to be created.
	        		Thread t = new Thread(new WaitingThread(frameIn, frameOut, fi, values));
	                t.start();
	    		}
	    	}
	    	dialog.resetOriginalImage();
		} catch (OutOfMemoryError g) {
	    	mainFrame.outOfMemoryMessage();
		}
    }
	
    private static class WaitingThread implements Runnable {
    	MyInternalFrame frameIn;
    	MyInternalFrame frameOut;
    	Filter fi;
    	double values;
	    public WaitingThread(MyInternalFrame frameIn, MyInternalFrame frameOut,
	    		Filter fi, double values) {
			this.frameIn = frameIn;
			this.frameOut = frameOut;
			this.fi = fi;
			this.values = values;
		}

		public void run() {
			BufferedImage imgIn = Utility.waitForOriginalImage(frameIn);
    		// Makes thread that will filter the original image.
    		fi.createThread(imgIn, values , frameOut);
	    }
    }
	
}
