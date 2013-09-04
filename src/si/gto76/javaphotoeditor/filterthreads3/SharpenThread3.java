package si.gto76.javaphotoeditor.filterthreads3;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.SpatialFilters;


public class SharpenThread3 extends FilterThread3 {
	
	public SharpenThread3( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int x, int y) {
		return SpatialFilters.getSharpen(imgIn, x, y);
	}
	
}
