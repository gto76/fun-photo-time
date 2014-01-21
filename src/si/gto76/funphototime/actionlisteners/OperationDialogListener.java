package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Operation;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.dialogs.OperationDialog;

public class OperationDialogListener implements ActionListener {
	FunPhotoTimeFrame mainFrame;
	Operation op;
	
	public OperationDialogListener(Operation op, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.op = op;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Zoom problem
		if (mainFrame.isMemoryCritical())
			return;
		
		try {
			OperationDialog dialog = (OperationDialog) op.getDialog(mainFrame.desktop);
	    	if (!dialog.wasCanceled()) {
	    		int firstZoom = dialog.getFirstZoom();
	    		int secondZoom = dialog.getSecondZoom();
	    		// If frames have different zoom, or one of them is actual size.
	    		if ( (firstZoom != secondZoom) || (firstZoom == 100) || (secondZoom == 100) ) {
	    			MyInternalFrame frame = 
	        				mainFrame.createFrame(dialog.getProcessedOrigImage(), dialog.getName());
	        		frame.zoom(firstZoom);
	    		}
	    		else {
	    			MyInternalFrame frame = 
	        				mainFrame.createZoomedFrame(dialog.getProcessedImage(), dialog.getFirstFrame());
	    			// TODO waiting thread
	    			Thread t = new Thread(new WaitingThread(frame, dialog));
	                t.start();
	    		}
	        }
		} catch (OutOfMemoryError g) {
	    	mainFrame.outOfMemoryMessage();
		}
    }
	
    private static class WaitingThread implements Runnable {
    	MyInternalFrame frameOut;
    	OperationDialog dialog;
	    public WaitingThread(MyInternalFrame frameOut, OperationDialog dialog) {
			this.frameOut = frameOut;
			this.dialog = dialog;
		}

		public void run() {
			frameOut.setOriginalImg(dialog.getProcessedOrigImage());
	    }
    }
	
}
