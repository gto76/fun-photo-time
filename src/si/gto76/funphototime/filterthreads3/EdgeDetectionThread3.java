package si.gto76.funphototime.filterthreads3;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.SpatialFilters;


public class EdgeDetectionThread3 extends FilterThread3 {
	
	public EdgeDetectionThread3( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int x, int y) {
		return SpatialFilters.getEdge(imgIn, x, y);
	}
	
}
