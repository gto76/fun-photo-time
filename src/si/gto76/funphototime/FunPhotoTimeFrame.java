package si.gto76.funphototime;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu.Separator;
import javax.swing.MenuElement;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import si.gto76.funphototime.actionlisteners.FilterDialogWithSliderDoubleListener;
import si.gto76.funphototime.actionlisteners.FilterWithouthDialogListener;
import si.gto76.funphototime.actionlisteners.LoadListener;
import si.gto76.funphototime.actionlisteners.OperationDialogListener;
import si.gto76.funphototime.actionlisteners.SaveListener;
import si.gto76.funphototime.actionlisteners.SpatialFilterWithouthDialogListener;
import si.gto76.funphototime.actionlisteners.ZoomListener;
import si.gto76.funphototime.dialogs.AboutDialog;
import si.gto76.funphototime.dialogs.BitPlaneDialog;
import si.gto76.funphototime.dialogs.ColorsDialog;
import si.gto76.funphototime.dialogs.HistogramStretchingDialog;
import si.gto76.funphototime.dialogs.ThresholdingDialog;
import si.gto76.funphototime.enums.Filter;
import si.gto76.funphototime.enums.NoDialogFilter;
import si.gto76.funphototime.enums.ZoomOperation;
import si.gto76.funphototime.filterthreads2.BitPlaneThread2;
import si.gto76.funphototime.filterthreads2.ColorsThread2;
import si.gto76.funphototime.filterthreads2.HistogramStretchingThread2;
import si.gto76.funphototime.filterthreads2.ThresholdingThread2;

 
public class FunPhotoTimeFrame extends JFrame 
							implements ContainerListener, MouseWheelListener {
	
	private static final long serialVersionUID = 5772778382924626863L;
    
	public JDesktopPane desktop;
    private Menu meni;
    public ViewMenuUtil vmu = new ViewMenuUtil();
    
    public File lastPathLoad = null;
    public File lastPathSave = null;
    
    static ArrayList<Image> iconsActive;
    static ArrayList<Image> iconsNotActive;
    static long iconsSize;
    
    /*
     * The constructor.
     */  
    public FunPhotoTimeFrame() {
    	super("Fun Photo Time");
        
        /*Inicializacije****************************************/
        findPicturesDirectory();
    	
        // Internal frame 
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset + 30, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
        desktop = new JDesktopPane();
        desktop.setBackground(Conf.BACKGROUND_COLOR);
        setContentPane(desktop);
        
        //da obvesca o na novo odprtih in zaprtih internal frejmih
        desktop.addContainerListener(this);
        //poslusa kolescek na miski za zoom in, out
        desktop.addMouseWheelListener(this);
        
        // Menus
        meni = new Menu();
        setJMenuBar(meni.getMenuBar());
        
        // Icons
    	final ImageIcon iconImgS = new ImageIcon(getClass().getResource(Conf.ICON_FILENAME_S));
    	final ImageIcon iconImgSBlue = new ImageIcon(getClass().getResource(Conf.ICON_FILENAME_S_BLUE));
    	final ImageIcon iconImgM = new ImageIcon(getClass().getResource(Conf.ICON_FILENAME_M));
    	final ImageIcon iconImgL = new ImageIcon(getClass().getResource(Conf.ICON_FILENAME_L));
    	final ImageIcon iconImgXL = new ImageIcon(getClass().getResource(Conf.ICON_FILENAME_XL));
    	
    	iconsActive = new ArrayList<Image>() {
			private static final long serialVersionUID = 4560955969369357297L;
			{add(iconImgSBlue.getImage()); add(iconImgM.getImage()); add(iconImgL.getImage()); add(iconImgXL.getImage());}
    	};
    	iconsNotActive = new ArrayList<Image>() {
			private static final long serialVersionUID = -337325274310404675L;
			{add(iconImgS.getImage()); add(iconImgM.getImage()); add(iconImgL.getImage()); add(iconImgXL.getImage());}
    	};
        this.setIconImages(iconsActive);
        iconsSize = Utility.getSizeOfImage(iconImgS) + Utility.getSizeOfImage(iconImgSBlue) + 
        		Utility.getSizeOfImage(iconImgM) + Utility.getSizeOfImage(iconImgL) + 
        		Utility.getSizeOfImage(iconImgXL);
        
        /*Action*Listeners***************************************/ 
        
        MenuListener menuListener = new MenuListener() {
        	//ko odpremo meni onemogoci tiste operacije,
        	//ki rabijo sliko, ce ni izbran noben okvir 
            public void menuSelected(MenuEvent e) {
            	disableOrEnableMenuItems();
            }
            public void menuCanceled(MenuEvent e) {
            }
            public void menuDeselected(MenuEvent e) {
            }
        };
        
        //TOP MENU STRUCTURE
        meni.menuFile.addMenuListener(menuListener);
        meni.menuZoom.addMenuListener(menuListener);
        meni.menuFilters.addMenuListener(menuListener);
        meni.menuLogicop.addMenuListener(menuListener);
        meni.menuArithmeticop.addMenuListener(menuListener);
       
        /*
         * FILE
         */
        //FILE OPEN
        meni.menuFileOpen.addActionListener (
    		new LoadListener(this)
        ); 
        //FILE SAVE
        meni.menuFileSaveas.addActionListener (
        	new SaveListener(this)
        );
        //FILE EXIT
        meni.menuFileExit.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FunPhotoTime.onWindowClose(); 
                }
            }
        ); 
        
        /*
         * ZOOM
         */
        //ZOOM OUT
        meni.menuZoomOut.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_OUT, this)
        );
        //ZOOM IN
        meni.menuZoomIn.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_IN, this)
        );
        //ZOOM 6%
        meni.menuZoomMagnificationSix.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_6, this)
        );
        //ZOOM 12%
        meni.menuZoomMagnificationTwelve.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_12, this)
        );
        //ZOOM 25%
        meni.menuZoomMagnificationTwentyfive.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_25, this)
        );
        //ZOOM 50%
        meni.menuZoomMagnificationFifty.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_50, this)
        );
        //ZOOM 66%
        meni.menuZoomMagnificationSixtysix.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_66, this)
        );
        //ZOOM 100%
        meni.menuZoomMagnificationHoundred.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_ACTUAL, this)
        );
        //ZOOM ACTUAL SIZE
        meni.menuZoomActualsize.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_ACTUAL, this)
        );

        /*
         * FILTERS WITHOUTH PARAMETERS
         */
        //FILTER NEGATIV
        meni.menuFiltersNegativ.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.NEGATIV, this)
        );
        //FILTER GREYSCALE
        meni.menuFiltersGreyscale.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.GREYSCALE, this)
        );  
        
        /*
         * FILTERS WITHOUTH PARAMETERS, WITH MASKS
         */
		//FILTER BLUR
        meni.menuFiltersBlur.addActionListener (
        	new SpatialFilterWithouthDialogListener(NoDialogFilter.BLUR, this)
        );
        //FILTER RELIEF
        meni.menuFiltersRelief.addActionListener (
        	new SpatialFilterWithouthDialogListener(NoDialogFilter.RELIEF, this)
        );
        //FILTER SHARPEN
        meni.menuFiltersSharpen.addActionListener (
        	new SpatialFilterWithouthDialogListener(NoDialogFilter.SHARPEN, this) 
        );
		//FILTER EDGE DETECTION
        meni.menuFiltersEdgedetection.addActionListener (
        	new SpatialFilterWithouthDialogListener(NoDialogFilter.EDGE, this)
        );
        //FILTER MEDIAN
        meni.menuFiltersMedian.addActionListener (
        	new SpatialFilterWithouthDialogListener(NoDialogFilter.MEDIAN, this)
        );
        
        /*
         * FILTERS WITHOUTH PARAMETERS, SMART
         */
		//FILTER HISTOGRAM EQUALIZATION
        meni.menuFiltersHistogrameq.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.HISTOGRAM_EQ, this)
        );
		//FILTER SMART BINARIZE
        meni.menuFiltersSmartbin.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.SMART_BIN, this)
        );
        
        /*
         * FILTERS WITH ONE PARAMETER, DOUBLE
         */
        //FILTER CONTRAST
        meni.menuFiltersContrast.addActionListener (
        	new FilterDialogWithSliderDoubleListener(Filter.CONTRAST, this)
        );
        //FILTER GAMMA
        meni.menuFiltersGamma.addActionListener (
       		new FilterDialogWithSliderDoubleListener(Filter.GAMMA, this)
        );
        //FILTER SATURATIOM
        meni.menuFiltersSaturation.addActionListener (
       		new FilterDialogWithSliderDoubleListener(Filter.SATURATION, this)
        );  
        //FILTER BRIGHTNESS
        meni.menuFiltersBrightness1.addActionListener (
       		new FilterDialogWithSliderDoubleListener(Filter.BRIGHTNESS, this)
        ); 
        
        /*
         * FILTERS WITH ONE PARAMETER, INT
         */
        //FILTER THRESHOLDING 
        meni.menuFiltersThresholding.addActionListener
        (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	ThresholdingDialog dialog = new ThresholdingDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		int values = dialog.getValues();
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		new ThresholdingThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                }
            }
        );

        /*
         * FILTERS WITH DIFFERENT INPUT
         */
        //FILTER BIT-PLANE
        meni.menuFiltersBitplane.addActionListener (
			 new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	BitPlaneDialog dialog = new BitPlaneDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		int values = dialog.getValues();
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		new BitPlaneThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                }
            }
        ); 
        
		//FILTER HISTOGRAM STRETCHING 
        meni.menuFiltersHistogramst.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	//naredi sliko tega histograma
                	double[] histogram = Utility.getHistogram(getSelectedBufferedImage());
                	BufferedImage hisImg = Utility.getHistogramImage(histogram);
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	//in jo poslje dialogu v katerem uporabnik doloci levo in desno mejo
                	HistogramStretchingDialog dialog = new HistogramStretchingDialog(frameIn, hisImg);
                	
                	if (!dialog.wasCanceled()) {
                		int values[] = dialog.getValues();
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		new HistogramStretchingThread2(frameIn.getOriginalImg(), values ,frameOut);
                	}
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();                	
                }
            }
        ); 
        
        //FILTER COLOR BALANCE
        meni.menuFiltersColors.addActionListener (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	ColorsDialog dialog = new ColorsDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		int values[] = dialog.getValues();
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		new ColorsThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                }
            }
        );
        
        /*
         * LOGIC AND ARITHMETIC OPERATIONS
         */
        //LOGICOP NOT
        meni.menuLogicopNot.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.NEGATIV, this)
        ); 
        //LOGICOP AND
        meni.menuLogicopAnd.addActionListener (
        		new OperationDialogListener(Operation.AND, this)
        ); 
        //LOGICOP OR 
        meni.menuLogicopOr.addActionListener (
        		new OperationDialogListener(Operation.OR, this)
        );
        //LOGICOP XOR
        meni.menuLogicopXor.addActionListener (
        		new OperationDialogListener(Operation.XOR, this)
        );
        //ARITHMETICOP ADDITION
        meni.menuArithmeticopAddition.addActionListener (      
        	new OperationDialogListener(Operation.ADDITION, this)
        );
        //ARITHMETICOP SUBTRACTION
        meni.menuArithmeticopSubtraction.addActionListener (
        	new OperationDialogListener(Operation.SUBTRACTION, this)
        );
        //ARITHMETICOP MULTIPLICATION
        meni.menuArithmeticopMultiplication.addActionListener (
        		new OperationDialogListener(Operation.MULTIPLICATION, this)
        );
        //ARITHMETICOP DIVISION
        meni.menuArithmeticopDivision.addActionListener (
        		new OperationDialogListener(Operation.DIVISION, this)
        );
        
        /*
         * HELP ABOUT
         */
        meni.menuHelpAbout.addActionListener (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	try {
						new AboutDialog();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
                }
            }
        );
        
        // Add window listener.
        this.addWindowListener (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    FunPhotoTimeFrame.this.windowClosed();
                }
            }
        );  
    	
        if (Conf.TEST_IMAGE) openTestImage();
    }
    
    private void findPicturesDirectory() {
    	String home = System.getProperty("user.home");
        String picturesDir = home + File.separator + "Pictures";
        setLaodAndSavePaths(picturesDir);
        picturesDir = home + File.separator + "My Pictures";
        setLaodAndSavePaths(picturesDir);
	}
    
    private void setLaodAndSavePaths(String picturesDirString) {
    	File picturesDir = new File(picturesDirString);
    	if (picturesDir.exists() && picturesDir.isDirectory()) {
        	lastPathLoad = picturesDir;
            lastPathSave = picturesDir;
        }
    }

	/*
     * CONSTRUCTORS END.
     */
    
    /* ***************************************************************/
    /* ***************************************************************/
    
    /*
     * ROUTINES & FUNCTIONS:
     */

    //metoda, ki se sprozi ko container listener
	//zazna da se je dodal novi internal frame v desktop
    public void componentAdded(ContainerEvent e) {
    	//noOfFrames++;
	}
	
	public void componentRemoved(ContainerEvent e) {
		//noOfFrames--;
	}
    
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		//NE DELA - verjetno tudi ni zazeleno
		/*
		int notches = e.getWheelRotation();
    	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
        if (notches < 0) { //Mouse wheel moved UP, zoom in
            frame.zoomIn(10);
        } 
        else {
       		frame.zoomOut(10);
        }
        */
    }
	
	
    private void disableOrEnableMenuItems() {
		//ce ni nobenega izbranega frejma, onemogoci
		//menije, ki potrebujejo sliko
		
		MenuElement[] menuElement = meni.menuBar.getSubElements();
		Component[] component;
		boolean isThereASelectedFrame =	!( desktop.getSelectedFrame() == null );
		
		for ( int i = 0; i < meni.menuBar.getMenuCount() ; i++ ) {
			component =	((JMenu) menuElement[i]).getMenuComponents();
			for ( int j = 0; j < ((JMenu) menuElement[i]).getItemCount(); j++ ) {
				if (component[j] instanceof Separator)
					continue;
				//ce ni izbrane slike in ce meni ajtem potrebuje eno ali vec slik pol ga disejbla
				if ( !isThereASelectedFrame && (((MyMenuInterface)component[j]).noOfOperands() > 0) ) {
					component[j].setEnabled(false);
				}
				else {
					component[j].setEnabled(true);
				}
			}
		}
	}
    
    /*
     * INTERNAL FRAME CONSTRUCTION
     */
    // Used when importing from other frame
    public MyInternalFrame createZoomedFrame(BufferedImage img, MyInternalFrame frameIn) {
		//Create a new internal frame with zoomed image. Add original image later.
		//also it inherits the titel
    	MyInternalFrame frame = new MyInternalFrame(this, img, frameIn);
    	return internalFrameInit(frame);
    }
	// Used when importing from file
    public MyInternalFrame createFrame(BufferedImage img, String title) {
    	//Create a new internal frame with image and title.
    	MyInternalFrame frame = new MyInternalFrame(this, img, title);
    	setZoomToFitScreen(frame);
    	return internalFrameInit(frame);
    }

	private MyInternalFrame internalFrameInit(MyInternalFrame frame) {
        frame.setVisible(true); 
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        vmu.createViewMenuItem(this, frame);
        return frame;
    }
	
    private void setZoomToFitScreen(MyInternalFrame internalFrame) {
    	BufferedImage img = internalFrame.getOriginalImg();
    	int imgWidth = img.getWidth();
    	int imgHeight = img.getHeight();
    	int mainFrameWidth = this.getWidth();
    	int mainFrameHeight = this.getHeight();
    	double imgRatio = (double)imgWidth / imgHeight;
    	double frameRatio = (double)mainFrameWidth / mainFrameHeight;
    	int zoom = 100;
    	if (imgRatio > frameRatio) {
    		zoom = findZoom(internalFrame.getOriginalImg().getWidth(), mainFrameWidth);
    	} else {
    		zoom = findZoom(internalFrame.getOriginalImg().getHeight(), mainFrameHeight);
    	}
    	internalFrame.zoom(zoom);
	}
    
    private int findZoom(int internalFrameSize, int mainFrameSize) {
		for (int zoom : Zoom.STEPS) {
			if (internalFrameSize*(zoom/100.0) < mainFrameSize) {
				return zoom;
			}
		}
		return Zoom.STEPS[Zoom.STEPS.length-1];
	}
	
    //////////////////////////////////

	public BufferedImage getSelectedBufferedImage() {
    	//Vrne sliko, ki se nahaja v izbranem oknu
    	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
		return frame.getImg();
	}
	
	public BufferedImage getSelectedOriginalBufferedImage() {
    	//Vrne sliko, originalne velikosti  ki se nahaja v izbranem oknu
    	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
    	BufferedImage img = Utility.waitForOriginalImage(frame);
		return img;
	}
    
	private void openTestImage() {
		//Odpre testno sliko
		JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(Conf.TEST_IMAGE_FILE_NAME));
    	try {
    		BufferedImage img = ImageIO.read(fc.getSelectedFile());
    		MyInternalFrame frame1 = createFrame(img, fc.getSelectedFile().getName());
            frame1.setSelected(true); //DOESEN'T WORK
		} catch (IOException f) {
		}
    	catch (java.beans.PropertyVetoException e) {
    	}
	}
	
	public void removeInternalFrameReference(JInternalFrame iFrame) {
		vmu.removeViewMenuItem(this, iFrame);
	}
	
	public Point getFrameCenter() {
    	Point location = this.getLocation();
    	return new Point(
    			location.x + this.getWidth()/2,
    			location.y + this.getHeight()/2
    	);
	}
	
    protected void windowClosed() {
    }

    /*
     * MEMORY
     */
    
	public void outOfMemoryMessage() {
		JOptionPane.showConfirmDialog(this, "Program ran out of memory!\n" +
				"Please close some images, save your work and restart the program.", "", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE); 
	}
	
	public void lowMemoryWarning() {
		JOptionPane.showConfirmDialog(this, "Can not perform operation, running low on memory!\n" +
				"Please close some images.", "", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
	}
	
	public long getMemoryFootprint() {
		long sum = iconsSize + Conf.MEMORY_SAFE_MARGIN;
		for (JInternalFrame frame : desktop.getAllFrames()) {
			MyInternalFrame myFrame = (MyInternalFrame) frame;
			sum += myFrame.getMemoryFootprint();
		}
		return sum;
	}
	
	private static final boolean DEBUG_MEMORY = true;
	
	public boolean isThereEnoughMemoryFor(long newMemorySize) {
		long maxMemory = Runtime.getRuntime().maxMemory();
		long usedMemory = getMemoryFootprint();
		long neededMemory = usedMemory + newMemorySize*2; // *2 !!!
		if (DEBUG_MEMORY) {
			System.out.println("maxMemory: "+maxMemory);
			System.out.println("usedMemory: "+usedMemory);
			System.out.println("newMemorySize: "+newMemorySize);
			System.out.println("neededMemory: "+neededMemory);
			System.out.println();
		}
		if (neededMemory > maxMemory)
			return false;
		else
			return true;
	}
	
	
}
