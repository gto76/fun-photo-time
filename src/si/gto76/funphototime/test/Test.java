package si.gto76.funphototime.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import si.gto76.funphototime.SpatialFilters;

public class Test {
 	static BufferedImage img;
	
 	public static void main(String[] args) {
		openTestImage();
		long startTime = System.nanoTime();
		SpatialFilters.blur(img);
		//SpatialFiltersNEW.blur(img);
		long stopTime = System.nanoTime();
		System.out.println((stopTime - startTime)/1000000000.0);
	}
	
	private static void openTestImage() {
		//Odpre testno sliko
		JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("/home/minerva/Pictures/foto/DSC_0008.JPG"));
    	try {
    		img = ImageIO.read(fc.getSelectedFile());
		} catch (IOException f) {
		}
	}
}
