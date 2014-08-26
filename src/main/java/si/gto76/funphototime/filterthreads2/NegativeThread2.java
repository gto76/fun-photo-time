package si.gto76.funphototime.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class NegativeThread2 extends FilterThread2 {
	
	public NegativeThread2( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getNegativ(rgb);
	}
	
}
