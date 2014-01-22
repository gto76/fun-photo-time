package si.gto76.funphototime;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Filters {
	
	///////////////////////////////////
	///////////////////////////////////
	///////////////////////////////////
	
	/*
	 * FUNCTIONS WITH ONE INPUT IMAGE
	 */
	
	interface RGBFunction {
	    int apply(int rgb);
	}
	
	private static BufferedImage processImage(BufferedImage img, RGBFunction f) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = f.apply(rgb);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
	
	//////////////////
	
	/**
	 * NEGATIV
	 */
    public static BufferedImage negativ(BufferedImage img) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
    	        return getNegativ(rgb);
    	    }
    	} );
    }
    public static int getNegativ(int rgb){
    	rgb = ( 0x0000ff - (rgb & 0x0000ff) )
			| ( 0x00ff00 - (rgb & 0x00ff00) )
            | ( 0xff0000 - (rgb & 0xff0000) );
        return rgb;
    }
    
    /**
     * GREY SCALE 1 - Returns grey scale image based on weighted model.
     */
    public static BufferedImage greyScale1(BufferedImage img) {
		return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				int avg = getGrayLevel1(rgb);
				return assignLevelToAllColors(avg);
    	    }
    	} );
    }	

    /**
     * GREY SCALE 2 - Returns grey scale image where all three primary colors contribute equaly to brightnes.
     */
    public static BufferedImage greyScale2(BufferedImage img) {
		return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				int avg = getGrayLevel2(rgb);
				return assignLevelToAllColors(avg);
    	    }
    	} );
    }

    /**
     * CONTRAST 1
     */
    public static BufferedImage contrast1(BufferedImage img, final double pow) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
    	        return getContrast1(rgb, pow);
    	    }
    	} );
    }
    public static int getContrast1(int rgb, double pow) {
    	rgb = (int) ( Math.pow( 
					((double)(rgb & 0x0000ff)/256) , pow ) * 256 ) 
					|
					((int)  ( Math.pow( 
					((double)((rgb & 0x00ff00) >> 8)/256) , pow ) * 256 ) << 8 )
					|
					((int)  ( Math.pow( 
					((double)((rgb & 0xff0000) >> 16)/256) , pow ) * 256 ) << 16 );
    	return rgb;
    }
    
    /**
     * CONTRAST 2 - Strange contrast function.
     */
    public static BufferedImage contrast2(BufferedImage img) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
    	        return getContrast2(rgb);
    	    }
    	} );
    }
    public static int getContrast2(int rgb) {
    	rgb = polovicka(rgb & 0x0000ff)
				|
				(( polovicka((rgb & 0x00ff00) >> 8 ) ) << 8 )
				|
				(( polovicka((rgb & 0xff0000) >> 16 ) ) << 16 );
    	return rgb;
    }
    private static int polovicka(int a) {
    	if (a > 128) { 
    		return 255; 
    	}
    	else { 
    		return 0; 
    	}
    }
    
    /**
     * GAMMA
     */
    public static BufferedImage gamma(BufferedImage img, final double eks/*ponent*/) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
    	        return getGamma(rgb, eks);
    	    }
    	} );
    }
    public static int getGamma(int rgb, double eksponent) {
    	rgb = 	((int) (Math.pow( ((double) (rgb & 0x0000ff)) / 256 , eksponent) * 256))
				|
				(((int) (Math.pow( ((double) ((rgb & 0x00ff00) >> 8 )) / 256 , eksponent) * 256)) << 8)
				|
				(((int) (Math.pow( ((double) ((rgb & 0xff0000) >> 16 )) / 256 , eksponent) * 256)) << 16);
		return rgb;
    }
    
    /**
     * SATURATION
     */
	public static BufferedImage saturation(BufferedImage img, final double saturation) {
		return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getSaturation(rgb, saturation);
    	    }
    	} );
    }   
	public static int getSaturation(int rgb, double saturation) {
    	int bw = getGrayLevel1(rgb);
    	rgb = setBlue(rgb, bw + (int) ((double)(getBlue(rgb) - bw) * saturation ));
		rgb = setGreen(rgb, bw + (int) ((double)(getGreen(rgb) - bw) * saturation ));
		rgb = setRed(rgb, bw + (int) ((double)(getRed(rgb) - bw) * saturation ));
		return rgb;
	}

    /**
     * BRIGHTNESS 1
     */
    public static BufferedImage brightness1(BufferedImage img, final double percent) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getBrightness1(rgb, percent);
			}
    	} );
    }
	public static int getBrightness1(int rgb, double percent) {
		rgb = setBlue(rgb, (int) (getBlue(rgb) * percent));
		rgb = setGreen(rgb, (int) (getGreen(rgb) * percent));
		rgb = setRed(rgb, (int) (getRed(rgb) * percent));
		return rgb;
	}
	
	
    /**
     * BRIGHTNESS 2
     */    
    public static BufferedImage brightness2(BufferedImage img, final int val) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getBrightness2(rgb, val);
			}
    	} );
    }
    public static int getBrightness2(int rgb, int val) {
    	rgb = setBlue(rgb, (int) (getBlue(rgb) + val));
		rgb = setGreen(rgb, (int) (getGreen(rgb) + val));
		rgb = setRed(rgb, (int) (getRed(rgb) + val));	
    	return rgb;
    }

    /**
     * BIT PLANE - Returns BW image, that represents values of n-th bit for every pixel
     */	
	public static BufferedImage bitPlane(BufferedImage img, final int bit) {
		return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getBitPlane(rgb, bit);
    	    }
    	} );
	} 
	public static int getBitPlane(int rgb, int bit) {
		bit = 8 - bit; 
		if ( (((getGrayLevel1(rgb)) >> bit) % 2) == 1 ) {
			rgb = 0xffffff;
		} else {
			rgb = 0x000000;
		}
		return rgb;
	}

    /**
     * THRESHOLDING 1 (Binarization)
     */	
    public static BufferedImage thresholding1(BufferedImage img, final int bitNo) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getThresholding1(rgb, bitNo);
    	    }
    	} );
    }
    public static int getThresholding1(int rgb, int val) {
    	int avg = getGrayLevel1(rgb);
    	if ( ((double)avg / 255 * 100) > val )
    		rgb = assignLevelToAllColors(255);
    	else
    		rgb = assignLevelToAllColors(0);	
    	return rgb;
    }

    /**
     * THRESHOLDING 2 (Binarization)
     */ 
      public static BufferedImage thresholding2(BufferedImage img, final int bitNo) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getThresholding2(rgb, bitNo);
    	    }
    	} );
    }
    /**
     * val: 0 to 100
     */
    public static int getThresholding2(int rgb, int val) {
    	int avg = getGrayLevel2(rgb);
    	if ( ((double)avg / 255 * 100) < (-val+100) )
    		rgb = assignLevelToAllColors(0);
    	else
    		rgb = assignLevelToAllColors(255);	
    	return rgb;
    }
    
    /**
     * Smart binarize tresholding
     */
     public static BufferedImage smartBinarizeThresholding(BufferedImage img, final int bitNo) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getSmartBinarizeThresholding(rgb, bitNo);
    	    }
    	} );
    }
    public static int getSmartBinarizeThresholding(int rgb, int val) {
    	int avg = getGrayLevel1(rgb);
    	if ( ((double)avg) < val )
    		rgb = assignLevelToAllColors(0);
    	else
    		rgb = assignLevelToAllColors(255);	
    	return rgb;
    }

    /**
     * HISTOGRAM STRETCHING (Levels) - ui[0] represents the lower and ui[1] the upper bound
     */    	
    public static BufferedImage histogramStretching(BufferedImage img, int[] ui/*user input*/) {
		//poslje levi in desni rob metodi ki vrne array, ki za vsako mozno svetlost
		//piksla pove v kero naj jo spremeni 
    	int[] mappingArray = calculateMappingArrayForStretching(ui[0], ui[1]);
    	return histogramMapping(img, mappingArray);
	}
    /**
     * Computes such luminosity maping array, that all values less than leftEdge will become 0,
     * and all grater than rightEdge 255. Values in between will be linearly shifted.
     */
    public static int[] calculateMappingArrayForStretching(int leftEdge, int rightEdge) {
		//vrne array, v katerem vsako polje pomeni v kero svetlost naj
		//se preslika piksel s svetlostjo enako oznaki polja
		int[] hm = new int[256]; //Histogram Mapping array
		int w = rightEdge - leftEdge; //width
		double st = 255.0 / w; //step
		double dis = 0; //distance from 0
		int i;
		for (i = 0; i < leftEdge; i++) {
			hm[i] = 0;
		}
		for (; i < rightEdge; i++) {
			hm[i] = (int) dis;
			dis += st;
		}
		for (; i < 256; i++) {
			hm[i] = 255;
		}
		return hm;
	}

    /**
     * HISTOGRAM EQUALIZATION - Tries to make histogram as flat as posible.
     */
	public static BufferedImage histogramEqualization(BufferedImage img) {
		double[] histogram = Utility.getHistogram(img);
		int[] mappingArray = calculateMappingArrayForEqualization(histogram);
		return histogramMapping(img, mappingArray);
	}
	public static int[] calculateMappingArrayForEqualization(double[] histogram) {
		int[] hm = new int[256]; //Histogram Mapping array
		double val;
		
		for (int i = 0; i < 256; i++) {
			val = 0;
			for (int j = 0; j <= i; j++) {
				val += histogram[j];
			}
			hm[i] = (int) (val * 255);
		}
		return hm;
	}
	
	/**
     * HISTOGRAM MAPPING - Filters the image acording to luminosity maping array, 
     * where position of an element represents input luminosity
     * and its value the output luminosity. 
     */
    public static BufferedImage histogramMapping(BufferedImage img, final int[] mappingArray) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
    	        return getHistogramMapping(rgb, mappingArray);
    	    }
    	} );
	}
    public static int getHistogramMapping(int rgb, int[] mappingArray) {
    	int rgbOut = 0;
		rgbOut = mappingArray[getRed(rgb)] << 16;
		rgbOut = rgbOut | mappingArray[getGreen(rgb)] << 8;
		rgbOut = rgbOut | mappingArray[getBlue(rgb)];
		return rgbOut;
    }
    
    /**
     * COLORS - Add val to colors. val[0] is red, 1 green and 2 blue
     */
    public static BufferedImage colors(BufferedImage img, final int[] val) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getColors(rgb, val);
			}
    	} );
    }
    public static int getColors(int rgb, int[] val) {
		rgb = setRed(rgb, (int) (getRed(rgb) + val[0]));
		rgb = setGreen(rgb, (int) (getGreen(rgb) + val[1]));
    	rgb = setBlue(rgb, (int) (getBlue(rgb) + val[2]));	
    	return rgb;
    }
    
    /**
     * SMART BINARIZE - Good for highlighting objects on a monotone surface.
     */
    public static BufferedImage smartBinarize(BufferedImage img) {
    	// find the threshold
    	BufferedImage imgTmp = SpatialFilters.blur(img);
    	double[] histogram = Utility.getHistogram(imgTmp);
    	int sedlo = Filters.findSaddle(histogram);
    	// call thresholding
    	return smartBinarizeThresholding(img, sedlo);
    }
    public static int findSaddle(double[] histogram) {
		double sumL = 0;
		double sumR = 0;
		double min = 1;
		int i, iL, iR, iMin = 0;
		final double PERCENT = 0.2;
		
		for (i = 0; sumL < PERCENT; i++) {
			sumL += histogram[i];
		}
		iL = i;
		//Povecuje i dokler ne pride do svetlosti od katere je 
		//temnejsih PERCENT tock na sliki
		for (i = 255; sumR < PERCENT; i--) {
			sumR += histogram[i];
		}
		iR = i;	
		//isto samo da zacne pri najsvetlejsi vrednosti in jo zmanjsuje
		//dokler ni PERCENT tock svetlejsih
		for (i = iL; i <= iR; i++) {
			if ( histogram[i] < min) {
				iMin = i;
				min = histogram[i];
			}
		}
		//Potem v tem obmocju poisce minimum
		//Se pravi se neko obmocje lahko smatra za ozadje
		//samo ce zavzema vec kot 20% in manj kot 80% povrsine
		return iMin;
	}
    
    /**
     * HISTOGRAM MATCHING - Tries to alter the img in such way that its histogram will resemble
     * the desiredHistogram.
     */
	public static BufferedImage histogramMatching(BufferedImage img, double[] desiredHistogram) {
		double[] histogram = Utility.getHistogram(img);
		int[] mappingArray1 = calculateMappingArrayForEqualization(histogram); //r->s
		int[] mappingArray2 = calculateMappingArrayForEqualization(desiredHistogram); //z->s
		int[] mappingArray3 = calculateMappingArrayForHMatching(mappingArray1, mappingArray2); //r->z
		
		return histogramMatchingIntermediate(img, mappingArray1, mappingArray3);
	}
	
	public static BufferedImage histogramMatchingIntermediate(BufferedImage img,  final int[] mappingArray1, final int[] mappingArray3) {
    	return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return getHistogramMatching(rgb, mappingArray1, mappingArray3);
			}
    	} );
    }
	
    public static int getHistogramMatching(int rgb, int[] mappingArray1, int[] mappingArray3) {
		int rgbOut1 = mappingArray1[getRed(rgb)] << 16;
		rgbOut1 = rgbOut1 | mappingArray1[getGreen(rgb)] << 8;
		rgbOut1 = rgbOut1 | mappingArray1[getBlue(rgb)];
		
		int rgbOut2 = mappingArray3[getRed(rgbOut1)] << 16;
		rgbOut2 = rgbOut2 | mappingArray3[getGreen(rgbOut1)] << 8;
		rgbOut2 = rgbOut2 | mappingArray3[getBlue(rgbOut1)];
		
    	return rgbOut2;
    }
	
	private static int[] calculateMappingArrayForHMatching(int[] ma1, int[] ma2) {
		int[] hm = new int[256]; //Histogram Mapping array
		
		for (int i = 0; i < 256; i++) {
			int z;
			for ( z = 0; ma2[z] - ma1[i] < 0; z++) {
			}
			hm[i] = z;
		}
		return hm;
	}
	
	
	///////////////////////////////////
	///////////////////////////////////
	///////////////////////////////////
	
	/*
	 * FUNCTIONS WITH TWO INPUT IMAGES
	 */
	
	interface RGBFunction2 {
	    int apply(int rgb1, int rgb2);
	}
	
	public static BufferedImage combineImages(BufferedImage img1, BufferedImage img2, RGBFunction2 f) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img1, img2);
		int height = imgOut.getHeight();
		int width = imgOut.getWidth();
		int rgb1, rgb2;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb1 = img1.getRGB(j, i);
				rgb2 = img2.getRGB(j, i);
				int rgbOut = f.apply(rgb1, rgb2);
				imgOut.setRGB( j, i, rgbOut);		
			}
		}
    	return imgOut;
    }
	
	//////////////////
	
	/**
     * NOT
     */
    public static BufferedImage not(BufferedImage img) {
		return processImage(img, new RGBFunction() {
    	    public int apply(int rgb) {
				return ~rgb;
    	    }
    	} );
    }
    
    /**
     * AND
     */
    public static BufferedImage and(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return rgb1 & rgb2;
    	    }
    	} );
    }
    
    /**
     * OR
     */
    public static BufferedImage or(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return rgb1 | rgb2;
    	    }
    	} );
    }
    
    /**
     * XOR
     */
    public static BufferedImage xor(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return rgb1 ^ rgb2;
    	    }
    	} );
    }
    
    /**
     * ADDITION
     */
    public static BufferedImage addition(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return getAddition(rgb1, rgb2);
    	    }
    	} );
    }
	public static int getAddition(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	rgbOut = setRed(rgbOut, getRed(rgb1) + getRed(rgb2));
    	rgbOut = setGreen(rgbOut, getGreen(rgb1) + getGreen(rgb2));
    	rgbOut = setBlue(rgbOut, getBlue(rgb1) + getBlue(rgb2));
    	return rgbOut;
    }
	
	/**
     * SUBTRACTION
     */
    public static BufferedImage subtraction(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return getSubtraction(rgb1, rgb2);
    	    }
    	} );
    }
    public static int getSubtraction(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	rgbOut = setRed(rgbOut, getRed(rgb1) - getRed(rgb2));
    	rgbOut = setGreen(rgbOut, getGreen(rgb1) - getGreen(rgb2));
    	rgbOut = setBlue(rgbOut, getBlue(rgb1) - getBlue(rgb2));
    	return rgbOut;
    }
    
    /**
     * MULTIPLICATION
     */
    public static BufferedImage multiplication(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return getMultiplication(rgb1, rgb2);
    	    }
    	} );
    }
    public static int getMultiplication(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	rgbOut = setRed(rgbOut, getRed(rgb1) * getRed(rgb2));
    	rgbOut = setGreen(rgbOut, getGreen(rgb1) * getGreen(rgb2));
    	rgbOut = setBlue(rgbOut, getBlue(rgb1) * getBlue(rgb2));
    	return rgbOut;
    }
    
    /**
     * DIVISION
     */
    public static BufferedImage division(BufferedImage img1, BufferedImage img2) {
    	return combineImages(img1, img2, new RGBFunction2() {
    	    public int apply(int rgb1, int rgb2) {
    	        return getDivision(rgb1, rgb2);
    	    }
    	} );
    }
    public static int getDivision(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	//da ne pride do deljenja z nic
    	if (getRed(rgb2) == 0)
    		rgbOut = setRed(rgbOut, 255);
    	else
    		rgbOut = setRed(rgbOut, getRed(rgb1) / (getRed(rgb2)));
    	if (getGreen(rgb2) == 0)
    		rgbOut = setGreen(rgbOut, 255);
    	else	
    		rgbOut = setGreen(rgbOut, getGreen(rgb1) / (getGreen(rgb2)));
    	if (getBlue(rgb2) == 0)
    		rgbOut = setBlue(rgbOut, 255);
    	else
    		rgbOut = setBlue(rgbOut, getBlue(rgb1) / (getBlue(rgb2)));
    	return rgbOut;
    }
    
    
	///////////////////////////////////
    ///////////////////////////////////
    ///////////////////////////////////
		
	/*
	 * UTILITY FUNCTIONS
	 */
    
    public static int getGrayLevel1(int rgb) {
    	//vrne povprecno svetlost
    	//gray = 0.3*red + 0.59*green + 0.11*blue
    	int avg;
		avg = (int) ((double) getRed(rgb) * 0.3
			+
			(double) getGreen(rgb) * 0.59
			+
			(double) getBlue(rgb) * 0.11);
		return avg;
    }
    
    public static int getGrayLevel2(int rgb) {
    	//vrne povprecno svetlost
    	int avg;
		avg = ( (rgb & 0x0000ff) 
			+
			((rgb & 0x00ff00) >> 8 ) 
			+
			((rgb & 0xff0000) >> 16 ) ) / 3;
		return avg;
    }
    
    public static int assignLevelToAllColors(int bw) {
    	//vrene sivo barvo z bw svetlostjo
    	int rgb;
    	if ( bw <= 255 ) {
	    	rgb = (bw)
				|
				(bw << 8) 
				|
				(bw << 16);
		}
		else {
			rgb = 0xffffff;
		}
		return rgb;
    }
    
    public static int getRed(int rgb) {
    	return ((rgb & 0xff0000) >> 16 );
    }
    
    public static int getGreen(int rgb) {
    	return ((rgb & 0x00ff00) >> 8 );
    }
    
    public static int getBlue(int rgb) {
    	return (rgb & 0x0000ff);
    }
    
    public static int setRed(int rgb, int red) {
    	if (red <= 0) {
    		return rgb & 0x00ffff;
    	}
    	else if (red >= 255) {
    		return (rgb & 0xffffff) | (rgb | 0xff0000);
    	}
    	else {
    		return (rgb & 0x00ffff) | (red << 16);
    	}
    }
    
    public static int setGreen(int rgb, int green) {
    	if (green <= 0) {
    		return rgb & 0xff00ff;
    	}
    	else if (green >= 255) {
    		return (rgb & 0xffffff) | (rgb | 0x00ff00);
    	}
    	else {
    		return (rgb & 0xff00ff) | (green << 8);
    	}
    }
    
    public static int setBlue(int rgb, int blue) {
    	if (blue <= 0) {
    		return rgb & 0xffff00;
    	}
    	else if (blue >= 255) {
    		return (rgb & 0xffffff) | (rgb | 0x0000ff);
    	}
    	else {
    		return (rgb & 0xffff00) | (blue);
    	}
    }

    public static void printRGB(int rgb) {
    	int red = getRed(rgb);
    	int green = getGreen(rgb);
    	int blue = getBlue(rgb);
    	System.out.println(red + "\t" + green + "\t" + blue);
    }
    
    public static void printRGB(int rgb, String label) {
    	System.out.print(label); 
    	printRGB(rgb);
    }
    
    public static int changeAllColorsByPercent(int rgb, double per) {
		rgb = ( (int) ((double) (rgb & 0x0000ff) * per) + (rgb & 0x0000ff))
			  |
			  (( (int) ((double) ( (rgb & 0x00ff00) >> 8 ) * per) +  ( (rgb & 0x00ff00) >> 8 ) )<< 8)	
			  |
			  (( (int) ((double) ( (rgb & 0xff0000) >> 16 ) * per) +  ( (rgb & 0xff0000) >> 16 ) ) << 16);
		return rgb;
	}
    
} 

