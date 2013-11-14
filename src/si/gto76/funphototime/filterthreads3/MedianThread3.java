package si.gto76.funphototime.filterthreads3;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.SpatialFilters;


public class MedianThread3 extends FilterThread3 {
	
	public MedianThread3( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int x, int y) {
		return SpatialFilters.getMedian(imgIn, x, y);
	}
	
}
