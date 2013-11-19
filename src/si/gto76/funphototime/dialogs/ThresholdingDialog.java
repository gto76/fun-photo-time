package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.ThresholdingThread;


public class ThresholdingDialog extends FilterDialogWithSlider  {

	private static final long serialVersionUID = 259871197416707102L;

	public ThresholdingDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Threshold");
	}
	
	public void stateChanged(ChangeEvent e)  {
		actionWhenStateChanged();
	}

	protected void actionWhenStateChanged() {
		stopActiveThread();
		filterThread = new ThresholdingThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public int getValues() {
    	int value = sld.getValue();
    	return  (value + 100) / 2;
	}
    
}


