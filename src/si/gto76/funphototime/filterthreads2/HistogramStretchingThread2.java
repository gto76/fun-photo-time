package si.gto76.funphototime.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;

public class HistogramStretchingThread2 extends FilterThread2 {
	private int[] bounds; // Probably not neaded
	private int[] mappingArray;
	
	public HistogramStretchingThread2( BufferedImage imgIn, int[] bounds, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
		this.bounds = bounds;
		mappingArray = Filters.calculateMappingArrayForStretching(bounds[0], bounds[1]);
	}
		
	protected int filtriraj(int rgb) {
		// Wait till mappingArray is calculated
		if ( mappingArray == null ) { 
			mappingArray = Filters.calculateMappingArrayForStretching(bounds[0], bounds[1]);
		}
		return Filters.getHistogramMapping(rgb, mappingArray);
	}

}
