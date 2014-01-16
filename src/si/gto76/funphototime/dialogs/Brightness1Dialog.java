package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.Brightness1Thread;

public class Brightness1Dialog extends FilterDialogWithSliderDouble  {
	
	public Brightness1Dialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Brightness");
	}
	
	public void stateChanged(ChangeEvent e)  {
		stopActiveThread();
		filterThread = new Brightness1Thread(imgIn, imgOut, getValues(), selectedFrame);
	}

    public double getValues() {
    	//from 0 to 2.0
    	return (double) sld.getValue() / 100.0 + 1.0;
    }
}

