package si.gto76.javaphotoeditor.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads.Brightness2Thread;


public class Brightness2Dialog extends FilterDialogWithSlider  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8530046270541381978L;

	public Brightness2Dialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Brightness2");
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
		filterThread = new Brightness2Thread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public int getValues() {
    	//from -255 to 255
    	return (int) (sld.getValue() * 2.55);
    }
    
}
