package si.gto76.javaphotoeditor;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


public class MyInternalFrame extends JInternalFrame 
						implements InternalFrameListener {
	private static final int SCROLL_PANE_SPEED = 14;
	private static final int X_OFFSET = 30, Y_OFFSET = 30;
	//kolk okvirjev je ze bilo odprtih
	private static int openFrameCount = 0;
	private ImageIcon icon;
	private BufferedImage originalImg; //image that is not resized
	private int zoom = 100;
	private String fileName;
	private String fileNameInstanceNo;
	Thread thread;
	JavaPhotoEditorFrame mainFrame;
	//boolean hasOriginalImage;

	public MyInternalFrame(JavaPhotoEditorFrame mainFrame) {
		super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    this.mainFrame = mainFrame;
	    //%10 - da se zacnejo spet odpirat levo zgoraj ko se enkrat odpre 11sti
	    setLocation(X_OFFSET*(openFrameCount%10), Y_OFFSET*(openFrameCount%10));
	    //hasOriginalImage = false;
		addInternalFrameListener(this);
	}
	
	public MyInternalFrame(BufferedImage imgIn,JavaPhotoEditorFrame mainFrame) {
		super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
		this.mainFrame = mainFrame;
	    originalImg = imgIn;
	    //hasOriginalImage = true;
	    icon = new ImageIcon((Image)imgIn, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_PANE_SPEED);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    
		setLocation(X_OFFSET*(openFrameCount%10), Y_OFFSET*(openFrameCount%10));
	    pack();
		addInternalFrameListener(this);
	}
	
	public MyInternalFrame(BufferedImage imgIn, MyInternalFrame frameIn,JavaPhotoEditorFrame mainFrame) {
		super(frameIn.getFileName()+" #"+(++openFrameCount)+" / zoom: "+frameIn.zoom+"%",
	          true, true, true, true);
		this.mainFrame = mainFrame;
	    if ( frameIn.getZoom() == 100 ) {
	    	originalImg = imgIn;
	    	//hasOriginalImage = true;
	    }
	    else {
			//zaenkrat ostane brez originalne slike
	    	//hasOriginalImage = false;
	    }
	    
	    this.zoom = frameIn.zoom;
	    fileName = frameIn.getFileName();
	    fileNameInstanceNo = " #" +openFrameCount;
	    
	    icon = new ImageIcon((Image)imgIn, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_PANE_SPEED);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    
	    setLocation(X_OFFSET*(openFrameCount%10), Y_OFFSET*(openFrameCount%10));
	    pack();
		addInternalFrameListener(this);
	}
	
	public MyInternalFrame(BufferedImage imgIn, String title, JavaPhotoEditorFrame mainFrame) {
		super(title,
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
		this.mainFrame = mainFrame;
	    ++openFrameCount;
	    //fileNameInstanceNo = " #" +openFrameCount;
	    this.fileName = title;

	    originalImg = imgIn;
	    icon = new ImageIcon((Image)imgIn, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_PANE_SPEED);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    
	    setLocation(X_OFFSET*(openFrameCount%10), Y_OFFSET*(openFrameCount%10));
	    pack();
		addInternalFrameListener(this);
	}
	
	/*
	METODE
	*/

	public void anounceThread(Thread thread) {
		this.thread = thread;
	}

	public String getFileName() { //zoomed, displayed img
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
    
    public void zoom(int cent) {
    	waitForThread();
    	if ( originalImg != null && cent < 100 && cent > 0 && cent != zoom) {
    		zoom = cent;
	    	icon.setImage(Zoom.out(originalImg, cent));
	    	super.setTitle(fileName + fileNameInstanceNo +" / zoom: " +zoom+ "%");
	    	pack();
	    }
    }
    
    public void zoomOut() { //za 50 posto
    	waitForThread();
    	if ( originalImg != null && (zoom / 2) > 0) {
    		zoom /= 2;
	    	icon.setImage(Zoom.out(originalImg, zoom));
	    	super.setTitle(fileName + fileNameInstanceNo + " / zoom: " +zoom+ "%");
	    	pack();
    	}
	}
	
	public void zoomOut(int cent) { //za cent
    	waitForThread();
    	if ( originalImg != null && zoom - cent > 0 ) {
    		zoom -= cent;
	    	icon.setImage(Zoom.out(originalImg, zoom));
	    	super.setTitle(fileName + fileNameInstanceNo + " / zoom: " +zoom+ "%");
	    	pack();
    	}
	}
	
	public void zoomIn() { //za 50 posto
    	waitForThread();
		if ( originalImg != null && zoom * 2 < 100 ) {
	    	zoom *= 2;
	    	icon.setImage(Zoom.out(originalImg, zoom));
	    	super.setTitle(fileName + fileNameInstanceNo + " / zoom: " +zoom+ "%");
	    	pack();
	    	return;
	    }
	    if ( originalImg != null && zoom * 2 == 100) {
	    	actualSize();
	    }
	}
	
	public void zoomIn(int cent) { //za cent
    	waitForThread();
		if ( originalImg != null && zoom + cent < 100 ) {
	    	zoom += cent;
	    	icon.setImage(Zoom.out(originalImg, zoom));
	    	super.setTitle(fileName + fileNameInstanceNo + " / zoom: " +zoom+ "%");
	    	pack();
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
			icon.setImage(originalImg);
			super.setTitle(fileName + fileNameInstanceNo);
		    pack();
		}
	}
	
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
	INTERNAL FRAME EVENTS
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
	
}
