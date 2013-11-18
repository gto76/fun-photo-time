package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.Brightness1Thread;


public class Brightness1Dialog extends FilterDialogWithSliderDouble  {
	
	private static final long serialVersionUID = 7001399654400720115L;

	public Brightness1Dialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Brightness");
	}
	
	public void stateChanged(ChangeEvent e)  {
		//ko se slider premakne prvo pogleda ce ze obstaja
		//kaksna nit in jo prekine

        // TODO 20 umaknit duplikacijo v dialogih: actionWhenStateChanged
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

