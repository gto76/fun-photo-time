package si.gto76.funphototime.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class ColorsThread extends FilterThread {
	
	private int[] value;
	
	public ColorsThread( BufferedImage imgIn, BufferedImage imgOut, int[] value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getColors(rgb, value); 
	}
	
}
