package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.dialogs.FilterDialogWithSliderDouble;
import si.gto76.funphototime.enums.Filter;

public class FilterDialogWithSliderDoubleListener implements ActionListener{
	FunPhotoTimeFrame mainFrame;
	Filter fi;

	public FilterDialogWithSliderDoubleListener(Filter fi, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.fi = fi;
    }
	
	public void actionPerformed(ActionEvent e) {
    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
    	FilterDialogWithSliderDouble dialog = fi.getDialog(frameIn); //right CAST
    	
    	if (!dialog.wasCanceled()) {
    		double values = dialog.getValues(); //right VALUE TYPE
    		MyInternalFrame frameOut = mainFrame.createZoomedFrame(dialog.getProcessedImage(), frameIn);
    		if ( frameIn.getZoom() != 100 ) { // NEW!!!
    			// Makes thread that waits for original image to be created.
        		Thread t = new Thread(new WaitingThread(frameIn, frameOut, fi, values));
                t.start();
    		}
    	}
    	dialog.resetOriginalImage();
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
