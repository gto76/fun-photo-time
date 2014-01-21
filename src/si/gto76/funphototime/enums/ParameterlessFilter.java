package si.gto76.funphototime.enums;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.SpatialFilters;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.filterthreads2.Greyscale1Thread2;
import si.gto76.funphototime.filterthreads2.HistogramEqualizationThread2;
import si.gto76.funphototime.filterthreads2.NegativeThread2;
import si.gto76.funphototime.filterthreads2.ThresholdingThread2;

public enum ParameterlessFilter {
	NEGATIV,
	GREYSCALE,
	BLUR,
	RELIEF,
	SHARPEN,
	EDGE,
	MEDIAN,
	HISTOGRAM_EQ,
	SMART_BIN;
	
	public BufferedImage getFilteredImage(BufferedImage image) {
		switch (this) {
	    	case NEGATIV:
	    		return Filters.negativ(image);  
	    	case GREYSCALE:
	    		return Filters.greyScale1(image);
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
	    	case HISTOGRAM_EQ:
	    		return Filters.histogramEqualization(image);
	    	case SMART_BIN:
	    		return Filters.smartBinarize(image);
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = 7199345466977708588L;
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
	    	/* BLUR, RELIEF, SHARPEN, EDGE and MEDIAN dont have threads, because zoomed image
	    	 * has to be created from originalImage and not directly from zoomed one.*/
	    	case HISTOGRAM_EQ:
				double[] histogram = Utility.getHistogram(imgIn);
				int[] mappingArray = Filters.calculateMappingArrayForEqualization(histogram);
				new HistogramEqualizationThread2(imgIn, frameOut, mappingArray);
	    		break;
	    	case SMART_BIN:
            	BufferedImage img = SpatialFilters.blur(imgIn);
            	double[] his = Utility.getHistogram(img);
            	int sedlo = Filters.findSaddle(his);
            	new ThresholdingThread2(imgIn, sedlo, frameOut);
            	break;
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = 821403174133489165L;
	    		};
		}
	}
	
}
