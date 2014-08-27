package si.gto76.funphototime.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class BitPlaneThread2 extends FilterThread2 {
	
	private int value;
	
	public BitPlaneThread2( BufferedImage imgIn, double value, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
		this.value = (int) value;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getBitPlane(rgb, value);
	}

}
