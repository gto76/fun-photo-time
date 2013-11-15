package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.SaturationThread;


public class SaturationDialog extends FilterDialogWithSliderDouble {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1658711370471287133L;

	public SaturationDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Saturation");
	}
	
	public void stateChanged(ChangeEvent e)  {
		//ko se slider premakne prvo pogleda ce ze obstaja
		//kaksna nit in jo prekine
		if ( filterThread != null ) {
			filterThread.t.interrupt();
			try {
				filterThread.t.join();
			}
			catch ( InterruptedException f ) {}
		}
		//nato naredi novo nit
		filterThread = new SaturationThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public double getValues() {
    	//from 0 to 400, 100 in the midle
    	//ko je value vecji od nic zacne return value trikrat hitrej narascat
    	int value = sld.getValue();
    	////System.out.println(value);
    	if ( value < 0 ) 
    		return (double) (value + 100) / 100;
    	else {
    		return (double) (value * 3 + 100) / 100;
    		
		}
	}
    
}

