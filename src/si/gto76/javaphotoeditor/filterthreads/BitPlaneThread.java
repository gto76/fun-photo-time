package si.gto76.javaphotoeditor.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class BitPlaneThread extends FilterThread {
	
	private int value;
	
	public BitPlaneThread( BufferedImage imgIn, BufferedImage imgOut, int value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getBitPlane(rgb, value);
	}
	

}
