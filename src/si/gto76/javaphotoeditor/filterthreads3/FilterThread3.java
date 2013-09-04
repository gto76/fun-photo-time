package si.gto76.javaphotoeditor.filterthreads3;

import java.awt.image.BufferedImage;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.Utility;


public abstract class FilterThread3 implements Runnable {
	//se uporablja ko se ustvari nov frejm, ki je se brez originalImg
	//in uporabljamo spatial filtre
	Thread t;
	protected BufferedImage imgIn, imgOut;
	protected MyInternalFrame selectedFrame;
	static int threadCount = 0;
	
	
	FilterThread3( BufferedImage imgIn, MyInternalFrame selectedFrame) {
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
					rgb = filtriraj(j, i);
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
	
	abstract int filtriraj(int x, int y);

}
