package si.gto76.javaphotoeditor.enums;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.dialogs.Brightness1Dialog;
import si.gto76.javaphotoeditor.dialogs.ContrastDialog;
import si.gto76.javaphotoeditor.dialogs.FilterDialogWithSliderDouble;
import si.gto76.javaphotoeditor.dialogs.GammaDialog;
import si.gto76.javaphotoeditor.dialogs.SaturationDialog;
import si.gto76.javaphotoeditor.filterthreads2.Brightness1Thread2;
import si.gto76.javaphotoeditor.filterthreads2.ContrastThread2;
import si.gto76.javaphotoeditor.filterthreads2.GammaThread2;
import si.gto76.javaphotoeditor.filterthreads2.SaturationThread2;

public enum Filter {
	CONTRAST,
	GAMMA,
	SATURATION,
	BRIGHTNESS;

	public FilterDialogWithSliderDouble getDialog(MyInternalFrame frame) {
		switch (this) {
	    	case CONTRAST:
	    		return new ContrastDialog(frame);
	    	case GAMMA:
	    		return new GammaDialog(frame);
	    	case SATURATION:
	    		return new SaturationDialog(frame);
	    	case BRIGHTNESS:
	    		return new Brightness1Dialog(frame);
	    	default:
	    		throw new ExceptionInInitializerError() {
	    		};
		}
    }
	
	public void createThread(BufferedImage imgIn, double value, MyInternalFrame frameOut ) {
		switch (this) {
	    	case CONTRAST:
	    		new ContrastThread2(imgIn, value, frameOut);
	    		break;
	    	case GAMMA:
	    		new GammaThread2(imgIn, value, frameOut);
	    		break;
	    	case SATURATION:
	    		new SaturationThread2(imgIn, value, frameOut);
	    		break;
	    	case BRIGHTNESS:
	    		new Brightness1Thread2(imgIn, value, frameOut);
	    		break;
	    	default:
	    		throw new ExceptionInInitializerError() {
	    		};
		}
	}
	
}
