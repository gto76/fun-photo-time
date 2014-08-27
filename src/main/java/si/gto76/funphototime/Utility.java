package si.gto76.funphototime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageTypeSpecifier;
import javax.swing.ImageIcon;


public class Utility {
	
	public static BufferedImage getHistogramImage (double[] histogram) {
        //naredi sliko histograma
		final int height = 150;
    	final  Color red = Color.red;
    	final  Color white = Color.white;

    	BufferedImage img = 
			new BufferedImage(256, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();
    	
    	int height2;
    	g2.setPaint(white);
    	g2.fill (new Rectangle2D.Double(0, 0, 256, height));
    	g2.setPaint(red);
    	//Gre cez vseh 256 polj od histograma[] in narise navpicno crto
    	for (int i = 0; i < 256; i++) {
    		height2 = height - (int)(histogram[i] * height * Conf.HISTOGRAM_VERTICAL_ZOOM);
    		g2.draw(new Line2D.Double(i, height, i, height2));
		}
		return img;
	}
	
	public static double[] getHistogram(BufferedImage img) {
    	//Vrne array z vrednostmi v procentih (0 - 1, koliko je vsake svetlosti
    	//na sliki, zacensi s cisto crno (0) in naprej do ciste bele (255)
    	int height = img.getHeight();
		int width = img.getWidth();
		int rgb;
		double pixelWeight = 1.0 / (height * width);
		double[] histogram = new double[256];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = img.getRGB(j, i);
				int bw = Filters.getGrayLevel2(rgb);
				histogram[bw]+= pixelWeight;
			}
		}
		return histogram;	
	} 
	
	public static BufferedImage declareNewBufferedImage(int x, int y) {
    	//naredi novo prazno sliko zeljene velikosti
		BufferedImage imgOut = new BufferedImage(x, 
								y, 
								BufferedImage.TYPE_INT_RGB );
		return imgOut;
    }
	
	public static BufferedImage declareNewBufferedImage(BufferedImage img) {
    	//naredi novo prazno sliko enake velikosti 
    	//kot jo ima tista, ki jo podamo kot argument
    	ImageTypeSpecifier its = new ImageTypeSpecifier(img);
		BufferedImage imgOut = new BufferedImage(img.getWidth(), 
								img.getHeight(), 
								its.getBufferedImageType() );
		return imgOut;
    }
    
    public static BufferedImage declareNewBufferedImage(BufferedImage img1, BufferedImage img2) {
    	int h1 = img1.getHeight();
		int w1 = img1.getWidth();
		int h2 = img2.getHeight();
		int w2 = img2.getWidth();
		int height = (h1 < h2) ? h1 : h2;
		int width = (w1 < w2) ? w1 : w2;
    	//uzame sirino od ozje slike in visino od nizje
		//taksnih dimenzij je potem izhodna slika
    	ImageTypeSpecifier its = new ImageTypeSpecifier(img1);
		BufferedImage imgOut = new BufferedImage(width, height, 
								its.getBufferedImageType() );
		return imgOut;
    }
    
    public static void copyBufferedImage(BufferedImage imgOrig, BufferedImage imgCopy) {
    	int height = imgCopy.getHeight();
		int width = imgCopy.getWidth();
		int rgb;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb = imgOrig.getRGB(j, i);
				imgCopy.setRGB(j, i, rgb);
			}
		}
    }
    
    public static BufferedImage declareNewBufferedImageAndCopy(BufferedImage img) {
    	BufferedImage imgOut = declareNewBufferedImage(img);
    	copyBufferedImage(img, imgOut);
    	return imgOut;
    }
    
    public static String histogramToString(double[] histogram) {
		StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < histogram.length; i++) {
    		sb.append(i + " " + histogram[i] + "\t");
		}
    	return sb.toString();
	}
    
    public static void initializeArray(int[] arr) {
    	for (int i = 0; i < arr.length; i++) {
    		arr[i] = 0;
    	}
    }
    
    public static void initializeArray(int[][] arr) {
    	for (int i = 0; i < arr.length; i++) {
    		for (int j = 0; j < arr[0].length; j++) {
    			arr[i][j] = 0;
    		}
    	}
    }
    
    public static BufferedImage waitForOriginalImage(MyInternalFrame frame) {
		while (frame.getOriginalImg() == null) {
			try {
				TimeUnit.MILLISECONDS.sleep(Conf.ORIGINAL_IMAGE_WAITING_INTERVAL_MSEC);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
        return frame.getOriginalImg();
    }
    
    public static Point centerTheCenter(Point center, int width, int Height) {
    	return new Point(
    		center.x - width/2,
    		center.y - width/2
    	);    	
    }

	public static long getSizeOfImage(BufferedImage imgIn) {
		return (long) ( imgIn.getHeight() * imgIn.getWidth() * 3 );
	}
	
	public static long getSizeOfImage(ImageIcon imgIn) {
		return (long) ( imgIn.getIconHeight() * imgIn.getIconWidth() * 3 );
	}
	
	public static double getSurfaceAreaFactorForZoom(int zoom) {
		return Math.pow(1.0/(zoom/100.0), 2);
	}
	
}
