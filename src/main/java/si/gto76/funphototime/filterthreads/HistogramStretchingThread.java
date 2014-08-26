package si.gto76.funphototime.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;

public class HistogramStretchingThread extends FilterThread {
	private int[] bounds; // Probably not neaded
	private int[] mappingArray;
	
	public HistogramStretchingThread( BufferedImage imgIn, BufferedImage imgOut, int[] bounds, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.bounds = bounds;
		//mappingArray = Filtri.calculateMappingArrayForStretching(bounds[0], bounds[1]);
	}
		
	protected int filtriraj(int rgb) {
		// Wait till mappingArray is calculated
		if ( mappingArray == null ) { 
			mappingArray = Filters.calculateMappingArrayForStretching(bounds[0], bounds[1]);
		}
		int rgbOut = Filters.getHistogramMapping(rgb, mappingArray);
		return rgbOut;
	}

}
