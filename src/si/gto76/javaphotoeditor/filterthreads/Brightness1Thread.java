package si.gto76.javaphotoeditor.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.MyInternalFrame;


public class Brightness1Thread extends FilterThread {
	
	private double value;
	
	public Brightness1Thread( BufferedImage imgIn, BufferedImage imgOut, double value, MyInternalFrame selectedFrame ) {
		super(imgIn, imgOut, selectedFrame);
		this.value = value;
	}
		
	protected int filtriraj(int rgb) {
		return Filtri.getBrightness1(rgb, value);
	}
	

}
