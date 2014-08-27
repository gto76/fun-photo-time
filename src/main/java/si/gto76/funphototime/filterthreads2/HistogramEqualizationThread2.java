package si.gto76.funphototime.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class HistogramEqualizationThread2 extends FilterThread2 {

	int[] mappingArray;
	
	public HistogramEqualizationThread2( BufferedImage imgIn, MyInternalFrame selectedFrame, int[] mappingArray ) {
		super(imgIn, selectedFrame);
		//double[] histogram = Utility.getHistogram(imgIn);
		//int[] mappingArray = Filtri.calculateMappingArrayForEqualization(histogram);
		this.mappingArray = mappingArray;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getHistogramMapping(rgb, mappingArray);
	}
	
}
