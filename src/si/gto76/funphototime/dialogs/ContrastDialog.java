package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.ContrastThread;


public class ContrastDialog extends FilterDialogWithSliderDouble  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6828756153131963971L;

	public ContrastDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Contrast");
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
		filterThread = new ContrastThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public double getValues() {
    	//from 0 to 2.0
    	return (double) sld.getValue() / 100.0 + 1.0;
    }
    
}

