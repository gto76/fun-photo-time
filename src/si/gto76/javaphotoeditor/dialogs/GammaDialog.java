package si.gto76.javaphotoeditor.dialogs;

import javax.swing.event.ChangeEvent;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads.GammaThread;


public class GammaDialog extends FilterDialogWithSliderDouble  {
	
	public GammaDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Gamma");
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
		filterThread = new GammaThread(imgIn, imgOut, getValues(), selectedFrame );
	}
    
    public double getValues() {
    	//from 0 to 120
    	double value = (double)  (((((double)sld.getValue() - 60) / 100)+1)*60);
    	////System.out.println(value);
    	double retValue = calculateGamma(
    			((double) value) / 10);
    	return (retValue);
    }
    
    private double calculateGamma(double x) {
	    //vhodne vrednosti, ki so od 0 do 12 preslika v izhodne, ki
	    //naj bi bile od 0 do neskoncno, 6 -> 1
    	return Math.exp(-0.9+0.1825*x+0.05*x*x);
    }
    
}
