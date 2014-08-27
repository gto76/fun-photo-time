package si.gto76.funphototime.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class Brightness2Thread extends FilterThread {
	
	private int value;
	
	public Brightness2Thread( BufferedImage imgIn, BufferedImage imgOut, int value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getBrightness2(rgb, value);
	}
	

}
