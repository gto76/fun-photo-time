package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.dialogs.FilterDialog;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsInts;
import si.gto76.funphototime.enums.MultipleParameterFilter;

public abstract class AbstractParameterFilterListener {
	FunPhotoTimeFrame mainFrame;

	public AbstractParameterFilterListener(FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
    }
	
	public void actionPerformed(ActionEvent e) {
		if (mainFrame.isMemoryCritical())
			return;

		try {
	    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
	    	FilterDialog dialog = getDialog(frameIn);
	    	if (!dialog.wasCanceled()) {
	    		storeValues(dialog);
	    		MyInternalFrame frameOut = mainFrame.createZoomedFrame(dialog.getProcessedImage(), frameIn);
	    		if ( frameIn.getZoom() != 100 ) { // NEW!!!
	    			// Makes thread that waits for original image to be created.
	        		Thread t = createThread(frameIn, frameOut); // new Thread(new WaitingThread(frameIn, frameOut, fi, values));
	                t.start();
	    		}
	    	}
	    	dialog.resetOriginalImage();
		} catch (OutOfMemoryError g) {
	    	mainFrame.outOfMemoryMessage();
		}
    }

	class WaitingThread implements Runnable {
    	MyInternalFrame frameIn;
    	MyInternalFrame frameOut;

    	public WaitingThread(MyInternalFrame frameIn, MyInternalFrame frameOut) {
			this.frameIn = frameIn;
			this.frameOut = frameOut;
		}

		public void run() {
			BufferedImage imgIn = Utility.waitForOriginalImage(frameIn);
    		// Makes thread that will filter the original image.
			createFilterThread(imgIn, frameOut);
	    }
    }
	
	abstract protected FilterDialog getDialog(MyInternalFrame frameIn);
    abstract protected void storeValues(FilterDialog dialog);
	abstract protected Thread createThread(MyInternalFrame frameIn, MyInternalFrame frameOut);    
	abstract protected void createFilterThread(BufferedImage imgIn, MyInternalFrame frameOut);
    
}

