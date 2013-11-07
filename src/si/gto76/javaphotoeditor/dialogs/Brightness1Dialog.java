package si.gto76.javaphotoeditor.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads.Brightness1Thread;


public class Brightness1Dialog extends FilterDialogWithSliderDouble  {
	
	public Brightness1Dialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Brightness");
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
		filterThread = new Brightness1Thread(imgIn, imgOut, getValues(), selectedFrame);
	}

    public double getValues() {
    	//from 0 to 2.0
    	return (double) sld.getValue() / 100.0 + 1.0;
    }
}

