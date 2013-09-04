package si.gto76.javaphotoeditor.filterthreads2;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class Brightness1Thread2 extends FilterThread2 {
	
	private double value;
	
	public Brightness1Thread2( BufferedImage imgIn, double value, MyInternalFrame selectedFrame ) {
		super(imgIn, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getBrightness1(rgb, value);
	}
	

}
