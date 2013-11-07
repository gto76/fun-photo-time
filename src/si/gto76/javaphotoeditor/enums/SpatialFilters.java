package si.gto76.javaphotoeditor.enums;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import si.gto76.javaphotoeditor.Filtri;
import si.gto76.javaphotoeditor.Utility;

public class SpatialFilters {
	

	private static int[][] blurMask = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
	private static int[][] reliefMask = {{20, 0, 0}, {0, 0, 0}, {0, 0, -20}};
	private static int[][] sharpenMask = {{-1, -1, -1}, {-1, 20, -1}, {-1, -1, -1}};
	private static int[][] edgeDetectionMaskV = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
	private static int[][] edgeDetectionMaskV1 = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
	private static int[][] edgeDetectionMaskH = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
	private static int[][] edgeDetectionMaskH1 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};


	public static BufferedImage blur(BufferedImage img) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = getSum(img, j, i, blurMask, 9);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
	}
	
	public static int getBlur(BufferedImage img, int x, int y) {
		return getSum(img, x, y, blurMask, 9);
	}


	public static BufferedImage relief(BufferedImage img) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = getSum(img, j, i, reliefMask, 1);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
	}

	public static int getRelief(BufferedImage img, int x, int y) {
		return getSum(img, x, y, reliefMask, 1);
	}
	

	public static BufferedImage sharpen(BufferedImage img) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = getSum(img, j, i, sharpenMask, 12);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
	}

	public static int getSharpen(BufferedImage img, int x, int y) {
		return getSum(img, x, y, sharpenMask, 12);
	}
	

	public static BufferedImage edgeDetection(BufferedImage img) {
		
		int mSize = 3; //-> mask size
		int edge = mSize / 2;
		
		BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb, rgb1;
		
		for (int i = edge; i < height - edge; i++) {
			for (int j = edge; j < width - edge; j++) {
				rgb = getEdge(img, j, i);
				imgOut.setRGB(j, i, rgb);
			}
		}
		
    	return imgOut;
	}

	public static int getEdge(BufferedImage img, int x, int y) {
		int rgb;
		rgb = getSum(img, x, y, edgeDetectionMaskH, 4);
		rgb += getSum(img, x, y, edgeDetectionMaskH1, 4);
		rgb += getSum(img, x, y, edgeDetectionMaskV, 4);
		rgb += getSum(img, x, y, edgeDetectionMaskV1, 4);
		return rgb;
	}


	public static BufferedImage median(BufferedImage img) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
		
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = getMedian(img, j, i);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
	}
	public static int getMedian(BufferedImage img, int x, int y) {
		int width = img.getWidth();
    	int height = img.getHeight();
    	int xx, yy;
    	//int rgb = 0;
		int size = 3;
    	int[] rgb = new int[size*size];
    	int[] bw = new int[size*size];
    	
	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				xx = x-(size/2) + j;
				yy = y-(size/2) + i;
				//pogleda ce je koordinata izven slike in ce je vrne 0 (crno)
				if ( (xx < 0) || (yy < 0) || (xx >= width) || (yy >= height) )
					rgb[i*3 + j] = 0;
				else
					rgb[i*3 + j] = img.getRGB( x-(size/2) + j , y-(size/2) + i);
					
			}
		}
		
		Arrays.sort(rgb); //NEPRAVILNO!!!
		return rgb[size*size / 2];
	}
	/*
	private static int getMedian2(BufferedImage img, int x, int y) {
		int width = img.getWidth();
    	int height = img.getHeight();
    	int xx, yy;
    	//int rgb = 0;
		int size = 3;
    	int[] rgb = new int[size*size];
    	int[] bw = new int[size*size];
    	
	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				xx = x-(size/2) + j;
				yy = y-(size/2) + i;
				//pogleda ce je koordinata izven slike in ce je vrne 0 (crno)
				if ( (xx < 0) || (yy < 0) || (xx >= width) || (yy >= height) )
					rgb[i*3 + j] = 0;
				else
					rgb[i*3 + j] = img.getRGB( x-(size/2) + j , y-(size/2) + i);
					
			}
		}
		
		Arrays.sort(rgb); //NEPRAVILNO!!!
		return rgb[size*size / 2];
	}
	*/

	/************************************************/
	

	private static int getSum(BufferedImage img, int x, int y, int[][] maska, int delitelj) {
		int avgRed = 0;
		int avgGreen = 0;
		int avgBlue = 0;
		int rgb = 0;
		int width = img.getWidth();
    	int height = img.getHeight();
    	int xx, yy;
		int size = maska.length;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				xx = x-(size/2) + j;
				yy = y-(size/2) + i;
				//pogleda ce je koordinata izven slike in ce je vrne 0 (crno)
				if ( (xx < 0) || (yy < 0) || (xx >= width) || (yy >= height) )
					rgb = 0;
				else
					rgb = img.getRGB( x-(size/2) + j , y-(size/2) + i);
				
				avgRed += maska[j][i] * Filtri.getRed(rgb);
				avgGreen += maska[j][i] * Filtri.getGreen(rgb);
				avgBlue += maska[j][i] * Filtri.getBlue(rgb);
				
			}
		}
		rgb = Filtri.setRed(rgb, avgRed / delitelj);
		rgb = Filtri.setGreen(rgb, avgGreen / delitelj);
		rgb = Filtri.setBlue(rgb, avgBlue / delitelj);
		return rgb;
	}
	

	
	/*
	public void nacin(String nacin){
		if ( nacin.equals("glajenje") ){
			
			maska[0][0] = 0.5;
			maska[0][1] = 1;
			maska[0][2] = 0.5;
			
			maska[1][0] = 1;
			maska[1][1] = 2;
			maska[1][2] = 1;
			
			maska[2][0] = 0.5;
			maska[2][1] = 1;
			maska[2][2] = 0.5;
		}
	*/
}
