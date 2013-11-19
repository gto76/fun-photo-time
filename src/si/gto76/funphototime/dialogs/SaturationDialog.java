package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.SaturationThread;


public class SaturationDialog extends FilterDialogWithSliderDouble {
	
	private static final long serialVersionUID = 1658711370471287133L;

	public SaturationDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Saturation");
	}
	
	public void stateChanged(ChangeEvent e)  {
		stopActiveThread();
		filterThread = new SaturationThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public double getValues() {
    	//from 0 to 400, 100 in the midle
    	//ko je value vecji od nic zacne return value trikrat hitrej narascat
    	int value = sld.getValue();
    	if ( value < 0 ) 
    		return (double) (value + 100) / 100;
    	else {
    		return (double) (value * 3 + 100) / 100;
    		
		}
	}
    
}


