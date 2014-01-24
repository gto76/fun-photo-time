package si.gto76.funphototime.enums;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.dialogs.BitPlaneDialog;
import si.gto76.funphototime.dialogs.Brightness1Dialog;
import si.gto76.funphototime.dialogs.ContrastDialog;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsDouble;
import si.gto76.funphototime.dialogs.GammaDialog;
import si.gto76.funphototime.dialogs.SaturationDialog;
import si.gto76.funphototime.dialogs.ThresholdingDialog;
import si.gto76.funphototime.filterthreads2.BitPlaneThread2;
import si.gto76.funphototime.filterthreads2.Brightness1Thread2;
import si.gto76.funphototime.filterthreads2.ContrastThread2;
import si.gto76.funphototime.filterthreads2.GammaThread2;
import si.gto76.funphototime.filterthreads2.SaturationThread2;
import si.gto76.funphototime.filterthreads2.ThresholdingThread2;

public enum SingleParameterFilter {
	CONTRAST,
	GAMMA,
	SATURATION,
	BRIGHTNESS, 
	THRESHOLDING,
	BIT_PLANE;

	public FilterDialogThatReturnsDouble getDialog(MyInternalFrame frame) {
		switch (this) {
	    	case CONTRAST:
	    		return new ContrastDialog(frame);
	    	case GAMMA:
	    		return new GammaDialog(frame);
	    	case SATURATION:
	    		return new SaturationDialog(frame);
	    	case BRIGHTNESS:
	    		return new Brightness1Dialog(frame);
	    	case THRESHOLDING:
	    		return new ThresholdingDialog(frame);
	    	case BIT_PLANE:
	    		return new BitPlaneDialog(frame);
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = -2115640049630620261L;
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
	    	case THRESHOLDING:
	    		new ThresholdingThread2(imgIn, value, frameOut);
	    		break;
	    	case BIT_PLANE:
	    		new BitPlaneThread2(imgIn, value, frameOut);
	    		break;
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = -2877266507139641775L;
	    		};
		}
	}
	
}
