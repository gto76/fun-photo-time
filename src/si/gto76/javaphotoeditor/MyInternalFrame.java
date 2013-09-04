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
	
	//kolk okvirjev je ze bilo odprtih
	private static int openFrameCount = 0;
	private static final int xOffset = 30, yOffset = 30;
	private ImageIcon icon;
	private BufferedImage originalImg; //image that is not resized
	private int zoom = 100;
	private String fileName;
	private String fileNameInstanceNo;
	Thread thread;
	JFrame mainFrame;
	//boolean hasOriginalImage;

	public MyInternalFrame() {
		super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    
	    //%10 - da se zacnejo spet odpirat levo zgoraj ko se enkrat odpre 11sti
	    setLocation(xOffset*(openFrameCount%10), yOffset*(openFrameCount%10));
	    //hasOriginalImage = false;
		addInternalFrameListener(this);
	}
	
	public MyInternalFrame(BufferedImage imgIn) {
		super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    
	    originalImg = imgIn;
	    //hasOriginalImage = true;
	    icon = new ImageIcon((Image)imgIn, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    
		setLocation(xOffset*(openFrameCount%10), yOffset*(openFrameCount%10));
	    pack();
		addInternalFrameListener(this);
	}
	
	public MyInternalFrame(BufferedImage imgIn, MyInternalFrame frameIn) {
		super(frameIn.getFileName()+" #"+(++openFrameCount)+" / zoom: "+frameIn.zoom+"%",
	          true, true, true, true);
	    
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
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    
	    setLocation(xOffset*(openFrameCount%10), yOffset*(openFrameCount%10));
	    pack();
		addInternalFrameListener(this);
	}
	
	public MyInternalFrame(BufferedImage imgIn, String title) {
		super(title,
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    
		

	    ++openFrameCount;
	    //fileNameInstanceNo = " #" +openFrameCount;
	    this.fileName = title;

	    originalImg = imgIn;
	    icon = new ImageIcon((Image)imgIn, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    
	    setLocation(xOffset*(openFrameCount%10), yOffset*(openFrameCount%10));
	    pack();
		addInternalFrameListener(this);
	}
	
	/*
	METODE
	*/

	public void anounceThread(Thread thread) {
		this.thread = thread;
		//System.out.println("Thread "+thread+" was anounced");
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
    	if ( originalImg != null && cent < 100 && cent > 0) {
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
		if ( originalImg != null ) {
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
				//System.out.println("Waiting for thread: "+thread);
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
		//System.out.println("Internal frame "+this+" was closed");
		if ( thread != null ) {
			thread.interrupt();
			//System.out.println("Thread "+thread+" was interupted");
			try {
				thread.join();
				//System.out.println("Thread "+thread+" was joined");
			}
			catch ( InterruptedException f ) { 
				//System.out.println("With Thread "+thread+" happened "+f);
			}
		}
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
