package si.gto76.funphototime;

import java.awt.image.BufferedImage;


public class Zoom {
	public static final int[] STEPS = {100, 66, 50, 25, 12, 6, 3, 1};
	
	public static BufferedImage out(BufferedImage img, int cent) {
		int width = img.getWidth();
    	int height = img.getHeight();
    	int newWidth = (int)(width * (double)cent / 100.0);
    	int newHeight = (int)(height * (double)cent / 100.0);
    	BufferedImage imgOut = 
    		Utility.declareNewBufferedImage(newWidth, newHeight);
    	int rgb;
    	double ratio = 100.0 / (double)cent;
    	
		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				//nearest neighbor
				rgb = img.getRGB((int)Math.round(j * ratio), (int)Math.round(i * ratio));
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
	}
}
