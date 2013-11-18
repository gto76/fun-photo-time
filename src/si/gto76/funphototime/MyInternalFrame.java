package si.gto76.funphototime;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


public class MyInternalFrame extends JInternalFrame 
						implements InternalFrameListener /*, MouseMotionListener*/ {
					        
					        
	
	private static final long serialVersionUID = -1665181775406001910L;
	private ImageIcon icon;
	private BufferedImage originalImg; //image that is not resized
	
	//kolk okvirjev je ze bilo odprtih
	private static int openFrameCount = 0;
	private int zoom = 100;
	private String fileName;
	private int fileNameInstanceNo;
	
	Thread thread;
	FunPhotoTimeFrame mainFrame;

	
	/*
	 * CONSTRUCTORS
	 */
	
	// Used when importing from other frame
	public MyInternalFrame(FunPhotoTimeFrame mainFrame, BufferedImage imgIn, MyInternalFrame frameIn) {
		super("", true, true, true, true);
		this.setTitle(fileName);
		//če ni frameIn zoom 100% zaenkrat ostane brez originalne slike
		//(ker jo more en od FilterThreadov, ki teče v ozadju še narest)
	    if ( frameIn.getZoom() == 100 ) {
	    	originalImg = imgIn;
	    }
	    this.zoom = frameIn.zoom;
	    fileName = frameIn.getFileName(); 
	    init(mainFrame, imgIn);
	}
	
	// Used when importing from file
	public MyInternalFrame(FunPhotoTimeFrame mainFrame, BufferedImage imgIn, String title) {
		super("", true, true, true, true);
		originalImg = imgIn;
		this.zoom = 100;
	    this.fileName = title;
	    init(mainFrame, imgIn);
	}
	
	// Initializes the window components
	private void init(FunPhotoTimeFrame mainFrame, BufferedImage imgIn) {
		fileNameInstanceNo = ++openFrameCount;
		refreshTitle();
		this.mainFrame = mainFrame;
		
	    icon = new ImageIcon((Image)imgIn, "description");
	    JLabel label = new JLabel(icon);
	    label.setBackground(Conf.BACKGROUND_COLOR);
	    label.setOpaque(true);
        JScrollPane scrollPane = new JScrollPane(label);

        // TODO 10 kolescek je pocasen ce se lahko premikamo samo horizontalno        
        scrollPane.getVerticalScrollBar().setUnitIncrement(Conf.SCROLL_PANE_SPEED);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    setLocation(Conf.X_OFFSET*(openFrameCount%10), Conf.Y_OFFSET*(openFrameCount%10));
	    
	    pack();
		addInternalFrameListener(this);
	}
	
	
	/*
	 * ROUTINES
	 */
	
	public void anounceThread(Thread thread) {
		this.thread = thread;
	}

	public String getFileName() {
		return fileName;
    }
	
	public BufferedImage getImg() { //zoomed, displayed img
		BufferedImage bf = (BufferedImage) icon.getImage();
    	return bf;
    }
    
    public BufferedImage getOriginalImg() {
		return originalImg;
    }
    
    public void setImg(BufferedImage imgIn) {
    	icon.setImage((Image) imgIn);
    }
    
    public void setOriginalImg(BufferedImage imgIn) {
    	originalImg = imgIn;
    }
    
    public int getZoom() {
    	return zoom;
    }
    
    /// ZOOM /////////////////////////////////////////////////
    public void zoom(int cent) {
    	waitForThread();
    	if ( originalImg != null && cent < 100 && cent > 0 && cent != zoom) {
    		zoom = cent;
    		refreshImage();
	    }
    }
    
    public void zoomOut() { //za 50 posto
    	waitForThread();
    	if ( originalImg != null && (zoom / 2) > 0) {
    		zoom /= 2;
    		refreshImage();
    	}
	}
	
	public void zoomOut(int cent) { //za cent
    	waitForThread();
    	if (cent < 1) return;
    	if ( originalImg != null && zoom - cent > 0 ) {
    		zoom -= cent;
    		refreshImage();
    	}
	}
	
	public void zoomIn() { //za 50 posto
    	waitForThread();
		if ( originalImg != null && zoom * 2 < 100 ) {
	    	zoom *= 2;
	    	refreshImage();
	    	return;
	    }
	    if ( originalImg != null && zoom * 2 == 100) {
	    	actualSize();
	    }
	}
	
	public void zoomIn(int cent) { //za cent
		waitForThread();
		if (cent < 1) return;
		if ( originalImg != null && zoom + cent < 100 ) {
	    	zoom += cent;
	    	refreshImage();
	    	return;
	    }
	    if ( originalImg != null && zoom + cent == 100) {
	    	actualSize();
	    }
	}
	
	public void actualSize() {
    	waitForThread();
		if ( originalImg != null && zoom!=100 ) {
			zoom = 100;
			refreshImage();
		}
	}
	
	/// REFRESHMENTS ////////////////////////////////////////////
	private void refreshImage() {
		boolean isMax = this.isMaximum;
		if (zoom == 100) {
			icon.setImage(originalImg);
		} else {
			icon.setImage(Zoom.out(originalImg, zoom));
		}
		refreshTitle();
    	pack();

    	if (isMax) {
    		try {
				this.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
    	}
	}
	
	private void refreshTitle() {
		String title;
		if (zoom == 100) {
			title = fileName+" #"+fileNameInstanceNo;
		}
		else {
			title = fileName+" #"+fileNameInstanceNo+" / zoom: "+zoom+"%";
		}
		this.setTitle(title);
	}
	/////////////////////////////////////////////////
	
	
	public void waitForThread() {
		if ( thread != null ) {
			//se sprehodimo skoz celo hierarhijo da prodemo do glavnega frejma
			//Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			//JRootPane mainFrame = (JRootPane) this.getParent().getParent().getParent();
			//HourglassThread hourglassThread = new HourglassThread(mainFrame);
			//mainFrame.setCursor(hourglassCursor);
			try {
				thread.join();
			}
			catch ( InterruptedException f ) {}
			//Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			//mainFrame.setCursor(hourglassCursor);
		}
	}
	
	public String toString() {
    	return getTitle();
    }

	/*
	 * INTERNAL FRAME EVENTS
	 */

	public void internalFrameClosing(InternalFrameEvent e) {
		////System.out.println("Internal frame "+this+" is closing");
	}

    public void internalFrameClosed(InternalFrameEvent e) {
		//prekine nit ko se zepre
		if ( thread != null ) {
			thread.interrupt();
			try {
				thread.join();
			}
			catch ( InterruptedException f ) { 
			}
		}
		//odstrani okno iz view menija
		mainFrame.removeInternalFrameReference(this);
	}

    public void internalFrameOpened(InternalFrameEvent e) {
	}

    public void internalFrameIconified(InternalFrameEvent e) {
	}

    public void internalFrameDeiconified(InternalFrameEvent e) {
	}

    public void internalFrameActivated(InternalFrameEvent e) {
	}

    public void internalFrameDeactivated(InternalFrameEvent e) {
	}

    /*
     * MOUSE MOTION LISTENER
     */

    //hand move tool:
    
    /*
	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e){
		if(SwingUtilities.isLeftMouseButton(e)){
			//make sure the point is within bounds of image
			int l_x, l_y;
			Point tempPoint = new Point(e.getPoint());
			l_x = source.getWidth();
			l_y = source.getHeight();
			if( tempPoint.x > l_x)
				tempPoint.x = l_x;
			if( tempPoint.y > l_y)
				tempPoint.y = l_y;
			sel.updateSelectionArea(tempPoint);
			repaint(); 
		}
		else if(SwingUtilities.isRightMouseButton(e)){
			JViewport viewport = parent.getViewport();
			JScrollBar jsbY= parent.getVerticalScrollBar();
			JScrollBar jsbX= parent.getHorizontalScrollBar();
			try{
				Point newPosition = parent.getMousePosition();
				
				int dx, dy;
				dx = panAnchor.x - newPosition.x;
				dy = panAnchor.y - newPosition.y;
				
				// update pan anchor
				panAnchor.x = newPosition.x;
				panAnchor.y = newPosition.y;
				
				int x, y;
				x = viewport.getViewPosition().x + dx;
				y = viewport.getViewPosition().y + dy;
				
				if ( x < 1 || this.getDims().width < parent.getVisibleRect().width + jsbY.getWidth())
					x = 1;
				else if( x > (this.getDims().width - parent.getVisibleRect().width + jsbY.getWidth()))
					x = this.getDims().width - parent.getVisibleRect().width + jsbY.getWidth();
				
				if ( y < 1 || this.getDims().height < parent.getVisibleRect().height + jsbX.getHeight())
					y = 1;
				else if( y > (this.getDims().height - parent.getVisibleRect().height + jsbX.getHeight()))
					y = this.getDims().height - parent.getVisibleRect().height + jsbX.getHeight();
				
				viewport.setViewPosition(new Point(x,y));
			}
			catch(Exception e2){}
			
			repaint();
		}
		
	}
*/

	
}
