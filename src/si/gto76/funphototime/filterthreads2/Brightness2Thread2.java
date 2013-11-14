package si.gto76.funphototime.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class Brightness2Thread2 extends FilterThread2 {
	
	private int value;
	
	public Brightness2Thread2( BufferedImage imgIn, int value, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getBrightness2(rgb, value);
	}

}
