package si.gto76.funphototime.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.Filters;
import si.gto76.funphototime.MyInternalFrame;


public class ThresholdingThread extends FilterThread {
	
	private int value;
	
	public ThresholdingThread( BufferedImage imgIn, BufferedImage imgOut, double value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = (int) value;
	}
		
	protected int filtriraj(int rgb) {
		return Filters.getThresholding2(rgb, value); 
	}
	
}
