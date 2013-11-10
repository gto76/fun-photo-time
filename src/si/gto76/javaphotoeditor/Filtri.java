package si.gto76.javaphotoeditor;

import java.awt.image.BufferedImage;


// TODO remove duplication filtri
public class Filtri {
	
	/*
	public static BufferedImage template(BufferedImage img) {
    	BufferedImage imgOut = declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				
				rgb = ;
				
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
    */
    
    public static BufferedImage negativ(BufferedImage img) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getNegativ(rgb);
				imgOut.setRGB(j, i, rgb);
			
			}
		}
    	return imgOut;
    }
    public static int getNegativ(int rgb){
    	rgb = ( 0x0000ff - (rgb & 0x0000ff) )
			| ( 0x00ff00 - (rgb & 0x00ff00) )
            | ( 0xff0000 - (rgb & 0xff0000) );
        return rgb;
    }
    
    public static BufferedImage contrast(BufferedImage img, double pow) {
    	//Pomajnsa kontrast s kvadratno transformacijo
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getContrast(rgb, pow);
				imgOut.setRGB(j, i, rgb);
			
			}
		}
    	return imgOut;
    }
    public static int getContrast(int rgb, double pow) {
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
    
    
    
    public static BufferedImage contrast2(BufferedImage img) {
    	//Poveca kontrast do maksimuma (BW) za vsako barvo posebej
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				
				rgb = polovicka(rgb & 0x0000ff)
					|
					(( polovicka((rgb & 0x00ff00) >> 8 ) ) << 8 )
					|
					(( polovicka((rgb & 0xff0000) >> 16 ) ) << 16 );
					
				imgOut.setRGB(j, i, rgb);
			
			}
		}
    	return imgOut;
    }
    private static int polovicka(int a) {
    	if (a > 128) { 
    		return 255; 
    	}
    	else { 
    		return 0; 
    	}
    }

    public static BufferedImage greyScale(BufferedImage img) {
    	//barvno sliko spemeni v sivo
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb, avg;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				//izracuna povprecno svetlost barv
				avg = getGrayLevel(rgb);
				//ki jo dodeli vsem barvam	
				rgb = assignLevelToAllColors(avg);
				
				imgOut.setRGB(j, i, rgb);
			
			}
		}
    	return imgOut;
    }
    
    public static BufferedImage greyScale2(BufferedImage img) {
    	//barvno sliko z uporabo utezi spemeni v sivo
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb, avg;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				//izracuna povprecno svetlost barv
				avg = getGrayLevel2(rgb);
				//ki jo dodeli vsem barvam	
				rgb = assignLevelToAllColors(avg);
				
				imgOut.setRGB(j, i, rgb);
			
			}
		}
    	return imgOut;
    }
    
    public static BufferedImage gamma(BufferedImage img, double eks/*ponent*/) {
    	//priredi sliki gammo
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getGamma(rgb, eks);
				imgOut.setRGB(j, i, rgb);
			
			}
		}
    	return imgOut;
    }
    public static int getGamma(int rgb, double eksponent) {
    	rgb = 	((int) (Math.pow( ((double) (rgb & 0x0000ff)) / 256 , eksponent) * 256))
				|
				(((int) (Math.pow( ((double) ((rgb & 0x00ff00) >> 8 )) / 256 , eksponent) * 256)) << 8)
				|
				(((int) (Math.pow( ((double) ((rgb & 0xff0000) >> 16 )) / 256 , eksponent) * 256)) << 16);
		return rgb;
    }
    
	public static BufferedImage histogramStretching(BufferedImage img, int[] ui/*user input*/) {
		BufferedImage imgOut = Utility.declareNewBufferedImage(img);
		int height = img.getHeight();
		int width = img.getWidth();
		int rgb, rgbOut;
		int[] mappingArray = calculateMappingArrayForStretching(ui[0], ui[1]);
		//poslje levi in desni rob metodi ki vrne array, ki za vsako mozno svetlost
		//piksla pove v kero naj jo spremeni 
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				//OLD zdaj premaknjeno spodaj
				/*
				rgbOut = 0;
				rgbOut = mappingArray[getRed(rgb)] << 16;
				rgbOut = rgbOut | mappingArray[getGreen(rgb)] << 8;
				rgbOut = rgbOut | mappingArray[getBlue(rgb)];
				*/
				//NEW:
				rgbOut = getHistogramStretching(rgb, mappingArray);
				
				imgOut.setRGB(j, i, rgbOut);
			
			}
		}
		return imgOut;	
	}
    public static int getHistogramStretching(int rgb, int[] mappingArray) {
    	int rgbOut = 0;
		rgbOut = mappingArray[getRed(rgb)] << 16;
		rgbOut = rgbOut | mappingArray[getGreen(rgb)] << 8;
		rgbOut = rgbOut | mappingArray[getBlue(rgb)];
		return rgbOut;
    }	
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
	
	public static BufferedImage histogramEqualization(BufferedImage img) {
		BufferedImage imgOut = Utility.declareNewBufferedImage(img);
		int height = img.getHeight();
		int width = img.getWidth();
		int rgb, rgbOut;
		double[] histogram = Utility.getHistogram(img);
		int[] mappingArray = calculateMappingArrayForEqualization(histogram);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgbOut = 0;
				
				rgbOut = mappingArray[getRed(rgb)] << 16;
				rgbOut = rgbOut | mappingArray[getGreen(rgb)] << 8;
				rgbOut = rgbOut | mappingArray[getBlue(rgb)];
				
				imgOut.setRGB(j, i, rgbOut);
			
			}
		}
		return imgOut;
	}
	public static int getHistogramEqualization(int rgb, int[]mappingArray) {
		int rgbOut = 0;
		
		rgbOut = mappingArray[getRed(rgb)] << 16;
		rgbOut = rgbOut | mappingArray[getGreen(rgb)] << 8;
		rgbOut = rgbOut | mappingArray[getBlue(rgb)];

		return rgbOut;
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
			//System.out.println(i + "\t" + hm[i]);
		}
		return hm;
	}
	
	
	public static BufferedImage histogramMatching(BufferedImage img, double[] desiredHistogram) {
		BufferedImage imgOut = Utility.declareNewBufferedImage(img);
		int height = img.getHeight();
		int width = img.getWidth();
		int rgb, rgbOut2, rgbOut1;
		double[] histogram = Utility.getHistogram(img);
		int[] mappingArray1 = calculateMappingArrayForEqualization(histogram); //r->s
		int[] mappingArray2 = calculateMappingArrayForEqualization(desiredHistogram); //z->s
		int[] mappingArray3 = calculateMappingArrayForHMatching(mappingArray1, mappingArray2); //r->z
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgbOut1 = 0;
				
				rgbOut1 = mappingArray1[getRed(rgb)] << 16;
				rgbOut1 = rgbOut1 | mappingArray1[getGreen(rgb)] << 8;
				rgbOut1 = rgbOut1 | mappingArray1[getBlue(rgb)];
				
				rgbOut2 = mappingArray3[getRed(rgbOut1)] << 16;
				rgbOut2 = rgbOut2 | mappingArray3[getGreen(rgbOut1)] << 8;
				rgbOut2 = rgbOut2 | mappingArray3[getBlue(rgbOut1)];
				
				imgOut.setRGB(j, i, rgbOut2);
			
			}
		}
		return imgOut;
	}
	private static int[] calculateMappingArrayForHMatching(int[] ma1, int[] ma2) {
		int[] hm = new int[256]; //Histogram Mapping array
		//double val;
		
		for (int i = 0; i < 256; i++) {
			int z;
			for ( z = 0; ma2[z] - ma1[i] < 0; z++) {
			}
			hm[i] = z;
			//System.out.println(i + "\t" + hm[i]);
		}
		return hm;
	}
				
	public static BufferedImage bitPlane(BufferedImage img, int bit) {
		//Vrne binarno sliko, ki predstavlja vrednost izbranega
		//bita (crna - 0, bela - 1) za vsak piksel
		//se cudno obnasa, za vecje bite vraca skor cist crno sliko
		BufferedImage imgOut = Utility.declareNewBufferedImage(img);
		int height = img.getHeight();
		int width = img.getWidth();
		int rgb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getBitPlane(rgb, bit);
				imgOut.setRGB(j, i, rgb);
			}
		}
		return imgOut;	
	} 
	public static int getBitPlane(int rgb, int bit) {
		bit = 8 - bit; 
		if ( (((getGrayLevel(rgb)) >> bit) % 2) == 1 ) {
			rgb = 0xffffff;
		} else {
			rgb = 0x000000;
		}
		return rgb;
	}
	
	public static BufferedImage saturation(BufferedImage img, double saturation) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getSaturation(rgb, saturation);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
    public static int getSaturation(int rgb, double saturation) {
    	int bw = getGrayLevel(rgb);
    	rgb = setBlue(rgb, bw + (int) ((double)(getBlue(rgb) - bw) * saturation ));
		rgb = setGreen(rgb, bw + (int) ((double)(getGreen(rgb) - bw) * saturation ));
		rgb = setRed(rgb, bw + (int) ((double)(getRed(rgb) - bw) * saturation ));
		return rgb;
	}
    
    public static BufferedImage brightness1(BufferedImage img, double percent) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getBrightness1(rgb, percent);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
	public static int getBrightness1(int rgb, double percent) {
		rgb = setBlue(rgb, (int) (getBlue(rgb) * percent));
		rgb = setGreen(rgb, (int) (getGreen(rgb) * percent));
		rgb = setRed(rgb, (int) (getRed(rgb) * percent));
		return rgb;
	}
	

	public static BufferedImage brightness2(BufferedImage img, int val) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getBrightness2(rgb, val);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
    public static int getBrightness2(int rgb, int val) {
    	rgb = setBlue(rgb, (int) (getBlue(rgb) + val));
		rgb = setGreen(rgb, (int) (getGreen(rgb) + val));
		rgb = setRed(rgb, (int) (getRed(rgb) + val));	
    	return rgb;
    }
    
    public static BufferedImage smartBinarize(BufferedImage img) {
    	// find the threshold
    	BufferedImage imgTmp = SpatialFilters.blur(img);
    	double[] histogram = Utility.getHistogram(imgTmp);
    	int sedlo = Filtri.poisciSedlo(histogram);
    	// call thresholding
    	return thresholding1(img, sedlo);
    }
    
    public static BufferedImage thresholding(BufferedImage img, int val) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getThresholding(rgb, val);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
    public static int getThresholding(int rgb, int val) {
    	int avg = getGrayLevel(rgb);
    	if ( ((double)avg / 255 * 100) > val )
    		rgb = assignLevelToAllColors(255);
    	else
    		rgb = assignLevelToAllColors(0);	
    	return rgb;
    }
    
    public static BufferedImage thresholding1(BufferedImage img, int val) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getThresholding1(rgb, val);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
    public static int getThresholding1(int rgb, int val) {
    	int avg = getGrayLevel(rgb);
    	if ( ((double)avg) < val )
    		rgb = assignLevelToAllColors(0);
    	else
    		rgb = assignLevelToAllColors(255);	
    	return rgb;
    }
    
    public static int poisciSedlo(double[] histogram) {
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
		
		//System.out.println(iMin);
		return iMin;
	}

    public static BufferedImage colors(BufferedImage img, int[] val) {
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int width = img.getWidth();
    	int height = img.getHeight();
		int rgb;
    	
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				rgb = getColors(rgb, val);
				imgOut.setRGB(j, i, rgb);
			}
		}
    	return imgOut;
    }
    public static int getColors(int rgb, int[] val) {
		rgb = setRed(rgb, (int) (getRed(rgb) + val[0]));
		rgb = setGreen(rgb, (int) (getGreen(rgb) + val[1]));
    	rgb = setBlue(rgb, (int) (getBlue(rgb) + val[2]));	
    	return rgb;
    }
    
	/**
     * Logicne Funkcije 
     */
	
	public static BufferedImage not(BufferedImage img) {
    	//vse bite zamenja z obratno vrednostjo
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img);
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				imgOut.setRGB(j, i, ~rgb);
			}
		}
    	return imgOut;
    }
	public static int getNot(int rgb) {
		return ~rgb;
	}
	
	public static BufferedImage logicOperation(BufferedImage img1, BufferedImage img2, int operation) {
    	//nad vsakim parom bitov se izvede: ce je operation 1 - AND, 2 - OR, 3 - XOR
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img1, img2);
		int height = imgOut.getHeight();
		int width = imgOut.getWidth();
		int rgb1, rgb2;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb1 = img1.getRGB(j, i);
				rgb2 = img2.getRGB(j, i);
				
				switch (operation) {
					case 1:
						imgOut.setRGB(j, i, rgb1 & rgb2); break;
					case 2:
						imgOut.setRGB(j, i, rgb1 | rgb2); break;
					case 3:
						imgOut.setRGB( j, i, rgb1 ^ rgb2 ); break;
				}				
			}
		}
    	return imgOut;
    }
    
    /**
     * Aritmeticne Funkcije 
     */
    
    public static BufferedImage arithmeticOperation(BufferedImage img1, BufferedImage img2, int operation) {
    	//
    	BufferedImage imgOut = Utility.declareNewBufferedImage(img1, img2);
    	int height = imgOut.getHeight();
		int width = imgOut.getWidth();
		int rgb1, rgb2;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb1 = img1.getRGB(j, i);
				rgb2 = img2.getRGB(j, i);
				switch (operation) {
					case 1:
						imgOut.setRGB(j, i, add(rgb1, rgb2)); break;
					case 2:
						imgOut.setRGB(j, i, sub(rgb1, rgb2)); break;
					case 3:
						imgOut.setRGB( j, i, mul(rgb1, rgb2)); break;
					case 4:
						imgOut.setRGB( j, i, div(rgb1, rgb2)); break;
				}
			}
		}
    	return imgOut;
    }
    
	public static int add(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	rgbOut = setRed(rgbOut, getRed(rgb1) + getRed(rgb2));
    	rgbOut = setGreen(rgbOut, getGreen(rgb1) + getGreen(rgb2));
    	rgbOut = setBlue(rgbOut, getBlue(rgb1) + getBlue(rgb2));
    	return rgbOut;
    }
    
    public static int sub(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	rgbOut = setRed(rgbOut, getRed(rgb1) - getRed(rgb2));
    	rgbOut = setGreen(rgbOut, getGreen(rgb1) - getGreen(rgb2));
    	rgbOut = setBlue(rgbOut, getBlue(rgb1) - getBlue(rgb2));
    	return rgbOut;
    }
    
    public static int mul(int rgb1, int rgb2) {
    	int rgbOut = 0;
    	rgbOut = setRed(rgbOut, getRed(rgb1) * getRed(rgb2));
    	rgbOut = setGreen(rgbOut, getGreen(rgb1) * getGreen(rgb2));
    	rgbOut = setBlue(rgbOut, getBlue(rgb1) * getBlue(rgb2));
    	return rgbOut;
    }
    
    public static int div(int rgb1, int rgb2) {
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
		
		
	/**
     * Pomozne Metode
     */
     
    
    public static int getGrayLevel(int rgb) {
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
