package si.gto76.javaphotoeditor.enums;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads2.Greyscale1Thread2;
import si.gto76.javaphotoeditor.filterthreads2.NegativeThread2;
import si.gto76.javaphotoeditor.filterthreads3.BlurThread3;
import si.gto76.javaphotoeditor.filterthreads3.EdgeDetectionThread3;
import si.gto76.javaphotoeditor.filterthreads3.MedianThread3;
import si.gto76.javaphotoeditor.filterthreads3.ReliefThread3;
import si.gto76.javaphotoeditor.filterthreads3.SharpenThread3;

public enum NoDialogFilter {
	NEGATIV,
	GREYSCALE,
	BLUR,
	RELIEF,
	SHARPEN,
	EDGE,
	MEDIAN;

	public BufferedImage getFilteredImage(BufferedImage image) {
		switch (this) {
	    	case NEGATIV:
	    		return Filtri.negativ(image);  
	    	case GREYSCALE:
	    		return Filtri.greyScale(image);
	    	case BLUR:
	    		return SpatialFilters.blur(image);
	    	case RELIEF:
	    		return SpatialFilters.relief(image);
	    	case SHARPEN:
	    		return SpatialFilters.sharpen(image);
	    	case EDGE:
	    		return SpatialFilters.edgeDetection(image);
	    	case MEDIAN:
	    		return SpatialFilters.median(image);
	    	default:
	    		throw new ExceptionInInitializerError() {
	    		};
		}
    }
	
	public void createThread(BufferedImage imgIn, MyInternalFrame frameOut ) {
		switch (this) {
	    	case NEGATIV:
	    		new NegativeThread2(imgIn, frameOut);
	    		break;
	    	case GREYSCALE:
	    		new Greyscale1Thread2(imgIn, frameOut);
	    		break;
	    	case BLUR:
	    		new BlurThread3(imgIn, frameOut);
	    		break;
	    	case RELIEF:
	    		new ReliefThread3(imgIn, frameOut);
	    		break;
	    	case SHARPEN:
	    		new SharpenThread3(imgIn, frameOut);
	    		break;
	    	case EDGE:
	    		new EdgeDetectionThread3(imgIn, frameOut);
	    		break;
	    	case MEDIAN:
	    		new MedianThread3(imgIn, frameOut);
	    		break;
	    	default:
	    		throw new ExceptionInInitializerError() {
	    		};
		}
	}
	
}
