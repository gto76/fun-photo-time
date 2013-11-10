package si.gto76.javaphotoeditor.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class Greyscale1Thread2 extends FilterThread2 {
	
	public Greyscale1Thread2( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.assignLevelToAllColors(Filtri.getGrayLevel1(rgb));
	}
	
}
