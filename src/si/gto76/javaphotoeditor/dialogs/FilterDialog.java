package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;
import javax.swing.event.ChangeListener;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.Utility;
import si.gto76.javaphotoeditor.filterthreads.FilterThread;

abstract class FilterDialog extends MyDialog
								implements ChangeListener {
	
	protected BufferedImage imgIn, imgOut;
	protected MyInternalFrame selectedFrame;
	protected FilterThread filterThread;
	
	public FilterDialog( MyInternalFrame selectedFrame, String title ) {
		super(title);
		init(selectedFrame);
	}
	public FilterDialog( MyInternalFrame selectedFrame, String title, int orientation ) {
		super(title, orientation);
		init(selectedFrame);
	}
	// constructor with size
	public FilterDialog( MyInternalFrame selectedFrame, String title, int orientation, int x, int y) {
		super(title, orientation, x, y);
		init(selectedFrame);
	}
	private void init(MyInternalFrame selectedFrame) {
		this.selectedFrame = selectedFrame;
		imgIn = selectedFrame.getImg();
		imgOut = Utility.declareNewBufferedImageAndCopy(imgIn); //naredi kopijo
		selectedFrame.setImg(imgOut); //na kero usmeri kazalec od frejma
	}
    
    public void resetOriginalImage() {
    	//spremeni sliko iz izbranega okvirja v prvotno stanje
    	//ce sploh obstaja kaksna nit jo ustavi ali pocaka
    	if (filterThread != null) {
    		try {
	    		//ce je bil izbran cancel avtomatsko prekine zadnjo nit,
	    		//drugace (ce je bil OK) jo pocaka da konca svoje delo
	    		//to verjetno niti ni nujno
	    		if ( wasCanceled() ) 
					filterThread.t.interrupt();
				filterThread.t.join();
			} 
			catch ( InterruptedException e ) {}
		}
		selectedFrame.setImg(imgIn);
    }
	
	public BufferedImage getProcessedImage() {
		try {
			filterThread.t.join();
		} 
		catch ( InterruptedException e ) {}
		return imgOut;
    }

}
