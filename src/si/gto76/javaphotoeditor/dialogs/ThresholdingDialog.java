package si.gto76.javaphotoeditor.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads.ThresholdingThread;


public class ThresholdingDialog extends FilterDialogWithSlider  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 259871197416707102L;

	public ThresholdingDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Threshold");
	}
	
	public void stateChanged(ChangeEvent e)  {
		actionWhenStateChanged();
	}

	protected void actionWhenStateChanged() {
		//ko se slider premakne prvo pogleda ce ze obstaja
		//kaksna nit in jo prekine
		if ( filterThread != null ) {
			filterThread.t.interrupt();
			try {
				filterThread.t.join();
			}
			catch ( InterruptedException f ) {}
		}
		filterThread = new ThresholdingThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public int getValues() {
    	//from 0 to 400, 100 in the midle
    	//ko je value vecji od nic zacne return value trikrat hitrej narascat
    	int value = sld.getValue();
    	return  (value + 100) / 2;
	}
    
}


