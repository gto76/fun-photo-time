package si.gto76.javaphotoeditor.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class SaturationThread extends FilterThread {
	
	private double value;
	
	public SaturationThread( BufferedImage imgIn, BufferedImage imgOut, double value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getSaturation(rgb, value);
	}
	

}
