package si.gto76.javaphotoeditor.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;

public class HistogramStretchingThread extends FilterThread {
	private int[] bounds; // Probably not neaded
	private int[] mappingArray;
	
	public HistogramStretchingThread( BufferedImage imgIn, BufferedImage imgOut, int[] bounds, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.bounds = bounds;
		//System.out.println("Bounds: " + bounds[0] + " " + bounds[1]);
		//mappingArray = Filtri.calculateMappingArrayForStretching(bounds[0], bounds[1]);
	}
		
	protected int filtriraj(int rgb) {
		// Wait till mappingArray is calculated
		if ( mappingArray == null ) { 
			mappingArray = Filtri.calculateMappingArrayForStretching(bounds[0], bounds[1]);
		}
		int rgbOut = Filtri.getHistogramStretching(rgb, mappingArray);
		return rgbOut;
	}

}
