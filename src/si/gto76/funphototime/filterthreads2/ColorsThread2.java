package si.gto76.funphototime.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class ColorsThread2 extends FilterThread2 {
	
	private int[] value;
	
	public ColorsThread2( BufferedImage imgIn, int[] value, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getColors(rgb, value);
	}

}
