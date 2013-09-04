package si.gto76.javaphotoeditor.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class NegativeThread2 extends FilterThread2 {
	
	public NegativeThread2( BufferedImage imgIn, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getNegativ(rgb);
	}
	
}
