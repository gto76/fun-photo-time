package si.gto76.funphototime.filterthreads;

import java.awt.image.BufferedImage;

import si.gto76.funphototime.MyInternalFrame;


public abstract class FilterThread implements Runnable {
	//za niti, ki se uporabljajo pri sprotnem prikazovanju efekta,
	//pri premikanju sliderja ali kere druge komponente 
	//v efekt dialogu

	public Thread t;
	protected BufferedImage imgIn, imgOut;
	protected MyInternalFrame selectedFrame;
	static int threadCount = 0;
	
	FilterThread( BufferedImage imgIn, BufferedImage imgOut, MyInternalFrame selectedFrame) {
		this.imgIn = imgIn;
		this.imgOut = imgOut;
		this.selectedFrame = selectedFrame;
		t = new Thread(this, "" + ++threadCount);
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
			//ko (ce) se nit izvede do konca, osvezi internal frame
			selectedFrame.repaint(100, 0, 0, 10000, 10000);
		}
		catch ( InterruptedException e ) {
			return;
		}
	}
	
	abstract int filtriraj(int rgb);
}
