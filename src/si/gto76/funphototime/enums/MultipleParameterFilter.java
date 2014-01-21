package si.gto76.funphototime.enums;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.dialogs.BitPlaneDialog;
import si.gto76.funphototime.dialogs.Brightness1Dialog;
import si.gto76.funphototime.dialogs.ColorsDialog;
import si.gto76.funphototime.dialogs.ContrastDialog;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsDouble;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsInts;
import si.gto76.funphototime.dialogs.FilterDialogWithSliderDouble;
import si.gto76.funphototime.dialogs.GammaDialog;
import si.gto76.funphototime.dialogs.HistogramStretchingDialog;
import si.gto76.funphototime.dialogs.SaturationDialog;
import si.gto76.funphototime.dialogs.ThresholdingDialog;
import si.gto76.funphototime.filterthreads2.BitPlaneThread2;
import si.gto76.funphototime.filterthreads2.Brightness1Thread2;
import si.gto76.funphototime.filterthreads2.ColorsThread2;
import si.gto76.funphototime.filterthreads2.ContrastThread2;
import si.gto76.funphototime.filterthreads2.GammaThread2;
import si.gto76.funphototime.filterthreads2.HistogramStretchingThread2;
import si.gto76.funphototime.filterthreads2.SaturationThread2;
import si.gto76.funphototime.filterthreads2.ThresholdingThread2;

public enum MultipleParameterFilter {
	HISTOGRAM_STRETCHING,
	COLOR_BALANCE;

	public FilterDialogThatReturnsInts getDialog(MyInternalFrame frame) {
		switch (this) {
	    	case HISTOGRAM_STRETCHING:
	    		double[] histogram = Utility.getHistogram(frame.getImg());
            	BufferedImage histogramImg = Utility.getHistogramImage(histogram);
	    		return new HistogramStretchingDialog(frame, histogramImg);
	    	case COLOR_BALANCE:
	    		return new ColorsDialog(frame);
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = -2115640049630620261L;
	    		};
		}
    }
	
	public void createThread(BufferedImage imgIn, int[] values, MyInternalFrame frameOut ) {
		switch (this) {
	    	case HISTOGRAM_STRETCHING:
	    		new HistogramStretchingThread2(imgIn, values, frameOut);
	    		break;
	    	case COLOR_BALANCE:
	    		new ColorsThread2(imgIn, values, frameOut);
	    		break;
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = -2877266507139641775L;
	    		};
		}
	}
	
}
