package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.ContrastThread;


public class ContrastDialog extends FilterDialogWithSliderDouble  {
	
	public ContrastDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Contrast");
	}
	
	public void stateChanged(ChangeEvent e)  {
		stopActiveThread();
		filterThread = new ContrastThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public double getValues() {
    	//from 0 to 2.0
    	return (double) sld.getValue() / 100.0 + 1.0;
    }
    
}

