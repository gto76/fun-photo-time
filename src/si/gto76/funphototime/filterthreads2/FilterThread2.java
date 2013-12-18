package si.gto76.funphototime.filterthreads2;

import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Utility;


public abstract class FilterThread2 implements Runnable {
	//se uporablja ko se ustvari nov frejm, ki je se brez originalImg	
	Thread t;
	protected BufferedImage imgIn, imgOut;
	protected MyInternalFrame selectedFrame;
	static int threadCount = 0;
	
	
	FilterThread2( BufferedImage imgIn, MyInternalFrame selectedFrame) {
		this.imgIn = imgIn;
		this.imgOut = Utility.declareNewBufferedImage(imgIn);
		this.selectedFrame = selectedFrame;
		t = new Thread(this, "" + ++threadCount);
		selectedFrame.anounceThread(t);
		t.start();
	}
	
	public void run() {
		int height = imgIn.getHeight();
		int width = imgIn.getWidth();
		int rgb;
		
		try {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					rgb = imgIn.getRGB(j, i);
					rgb = filtriraj(rgb);
					imgOut.setRGB(j, i, rgb);
					
					if (Thread.interrupted()) {
						throw new InterruptedException();
					}
				}
			}
			selectedFrame.setOriginalImg(imgOut);
		}
		catch ( InterruptedException e ) {
			return;
		}
	}
	
	abstract int filtriraj(int rgb);
	
}
