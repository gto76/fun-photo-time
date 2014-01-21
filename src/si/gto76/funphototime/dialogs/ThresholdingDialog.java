package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.ThresholdingThread;


public class ThresholdingDialog extends FilterDialogWithSliderDouble  {

	public ThresholdingDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Threshold");
	}
	
	public void stateChanged(ChangeEvent e)  {
		stopActiveThread();
		filterThread = new ThresholdingThread(imgIn, imgOut, getDouble(), selectedFrame);
	}
    
    public double getDouble() {
    	int value = sld.getValue();
    	return (double) ((value + 100) / 2);
	}
    
}


