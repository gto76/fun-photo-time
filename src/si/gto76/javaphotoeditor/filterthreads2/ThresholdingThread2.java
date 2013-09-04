package si.gto76.javaphotoeditor.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class ThresholdingThread2 extends FilterThread2 {
	
	private int value;
	
	public ThresholdingThread2( BufferedImage imgIn, int value, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getThresholding(rgb, value);
	}
	

}
