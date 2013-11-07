package si.gto76.javaphotoeditor.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class ThresholdingThread extends FilterThread {
	
	private int value;
	
	public ThresholdingThread( BufferedImage imgIn, BufferedImage imgOut, int value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getThresholding1(rgb, value); //change it to 1!!!
	}
	
}
