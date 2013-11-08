package si.gto76.javaphotoeditor.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class ColorsThread extends FilterThread {
	
	private int[] value;
	
	public ColorsThread( BufferedImage imgIn, BufferedImage imgOut, int[] value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getColors(rgb, value); 
	}
	
}
