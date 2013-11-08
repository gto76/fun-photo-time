package si.gto76.javaphotoeditor.filterthreads3;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.SpatialFilters;


public class MedianThread3 extends FilterThread3 {
	
	public MedianThread3( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int x, int y) {
		return SpatialFilters.getMedian(imgIn, x, y);
	}
	
}
