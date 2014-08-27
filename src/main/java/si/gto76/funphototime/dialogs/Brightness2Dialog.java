package si.gto76.funphototime.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.Brightness2Thread;


public class Brightness2Dialog extends FilterDialogWithSlider  {
	
	public Brightness2Dialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Brightness2");
	}
	
	public void stateChanged(ChangeEvent e)  {
		stopActiveThread();
		filterThread = new Brightness2Thread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public int getValues() {
    	//from -255 to 255
    	return (int) (sld.getValue() * 2.55);
    }
    
}
