package si.gto76.javaphotoeditor.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads.ThresholdingThread;


public class ThresholdingDialog extends FilterDialogWithSlider  {
	
	public ThresholdingDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Thresholding");
		
		//System.out.println("-1");
		//sld.fireStateChanged();
		//actionWhenStateChanged();
		//System.out.println("0");
	}
	
	public void stateChanged(ChangeEvent e)  {
		actionWhenStateChanged();
	}

	protected void actionWhenStateChanged() {
		//ko se slider premakne prvo pogleda ce ze obstaja
		//kaksna nit in jo prekine
		//System.out.println("1");
		if ( filterThread != null ) {
			filterThread.t.interrupt();
			try {
				filterThread.t.join();
			}
			catch ( InterruptedException f ) {}
		}
		//System.out.println("2");
		//nato naredi novo nit
		filterThread = new ThresholdingThread(imgIn, imgOut, getValues(), selectedFrame);
		//System.out.println("3");	
	}
    
    public int getValues() {
    	//from 0 to 400, 100 in the midle
    	//ko je value vecji od nic zacne return value trikrat hitrej narascat
    	int value = sld.getValue();
    	return  (value + 100) / 2;
    	
	}
    
}


