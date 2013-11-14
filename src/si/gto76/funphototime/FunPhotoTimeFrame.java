package si.gto76.funphototime;

import java.awt.Component;
import java.awt.Dimension;
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
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.MenuElement;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import si.gto76.funphototime.actionlisteners.FilterDialogWithSliderDoubleListener;
import si.gto76.funphototime.actionlisteners.FilterWithouthDialogListener;
import si.gto76.funphototime.actionlisteners.OperationDialogListener;
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

// TODO 2 preimenovat projekt

/*
 * @author: Jure Sorn 
 * @version 1.00 08/02/21
 */
 
public class FunPhotoTimeFrame extends JFrame 
							implements ContainerListener, MouseWheelListener {
	
	private static final long serialVersionUID = 5772778382924626863L;
    public JDesktopPane desktop;
    private Menu meni;
    private String lastPath = null;
    public ViewMenuUtil vmu = new ViewMenuUtil();
    
    static ImageIcon iconImg1;
    static ImageIcon iconImg2;
    
    /*
     * The constructor.
     */  
    //TODO 2 dodat ikonco
    //TODO 3 shortcuts
    public FunPhotoTimeFrame() {
    	super("Photo Fun Time"); // super("Java Photo Editor");
        
        /*Inicializacije****************************************/
        
        //nastavitve za internal frame
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset + 30, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
        desktop = new JDesktopPane();
        desktop.setBackground(Conf.BACKGROUND_COLOR);
        //createFrame();
        setContentPane(desktop);
        
        //da obvesca o na novo odprtih in zaprtih internal frejmih
        desktop.addContainerListener(this);
        //poslusa kolescek na miski za zoom in, out
        desktop.addMouseWheelListener(this);
        
        //Meniji
        meni = new Menu();
        setJMenuBar(meni.getMenuBar());
        
      //Ikonca
        URL url = getClass().getResource(Conf.ICON_FILENAME_1);
        iconImg1 = new ImageIcon(url);

        url = getClass().getResource(Conf.ICON_FILENAME_2);
        iconImg2 = new ImageIcon(url);
        
        this.setIconImage(iconImg1.getImage());
        
        
        
        //new ImageIcon(new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));
        

        
        
        
        /*Action*Listenerji***************************************/ 
        
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
        
        //FILE EXIT
        meni.menuFileExit.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FunPhotoTimeFrame.this.windowClosed();
                }
            }
        ); 
        
        //FILE OPEN
        meni.menuFileOpen.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	JFileChooser fc = new JFileChooser();
                	fc. setMultiSelectionEnabled(true);
                	if (lastPath != null)
                		fc.setCurrentDirectory(new File(lastPath));
                	int returnVal = fc.showOpenDialog(FunPhotoTimeFrame.this);
                	if (returnVal == JFileChooser.APPROVE_OPTION) {
                		try {
                			for (File fIn : fc.getSelectedFiles()) {
                				BufferedImage imgIn = ImageIO.read(fIn);
    							createFrame(imgIn, fc.getSelectedFile().getName());
                			}
						} catch (IOException f) {
						}
						lastPath = fc.getSelectedFile().getPath();
				    }
				}
            }
        ); 

        final String[] pngExtensions = {".png"};
        final ExtensionFileFilter pngFileFilter = new ExtensionFileFilter("*.png,*.PNG", pngExtensions);
        final String[] gifExtensions = {".gif"};
        final ExtensionFileFilter gifFileFilter = new ExtensionFileFilter("*.gif,*.GIF", gifExtensions);
        final String[] bmpExtensions = {".bmp"};
        final ExtensionFileFilter bmpFileFilter = new ExtensionFileFilter("*.bmp,*.BMP", bmpExtensions);
        final String[] jpgExtensions = {".jpg", ".jpeg"};
        final ExtensionFileFilter jpgFileFilter = new ExtensionFileFilter("*.jpg,*.JPG", jpgExtensions);
		
        //FILE SAVE
        meni.menuFileSaveas.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	if ( desktop.getSelectedFrame() == null ) 
                		return;
            		JFileChooser fc = new JFileChooser();
            		String fileName = ((MyInternalFrame)desktop.getSelectedFrame()).getFileName();
            		// odstrani koncnico imenu
            		int indexOfDot = fileName.indexOf(".");
            		if (indexOfDot != -1)
            			fileName = (String) fileName.subSequence(0, indexOfDot);
            		if (lastPath != null)
            			fc.setSelectedFile(new File(lastPath)); // TODO 1 save V tem primeru je treba odstranit ime in dodat ime izbranega frejma
            		else
            			fc.setSelectedFile(new File(fileName));
            		fc.setDialogTitle("Save As");

            		fc.addChoosableFileFilter(pngFileFilter);
            		fc.addChoosableFileFilter(jpgFileFilter);
            		fc.addChoosableFileFilter(bmpFileFilter);
            		fc.addChoosableFileFilter(gifFileFilter);
            		fc.setFileFilter(pngFileFilter);
            		
                	int returnVal = fc.showSaveDialog(FunPhotoTimeFrame.this);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		            	// TODO 1 save in da sejva original image in ne zooma
		            	// if filter png, koncnica png -> save as png
		            	// if filter png, koncnica "" -> add koncnica, save as png
		            	// if filter png, koncnica ~png (ali vec koncnic) -> FAIL
		            	
		            	// if filter jpg, koncnica jpg,jpeg -> save as koncnica
		            	// if filter jpg, koncnica "" -> add koncnica, save as koncnica
		            	// if filter jpg, koncnica ~jpg (ali vec koncnic) -> FAIL
		            	
		            	// if filter all, koncnica png -> save as png
		            	// if filter all, koncnica jpg,jpeg -> save as koncnica
		            	// if filter all, koncnica "" -> save as png
		            	// if filter all, koncnica ~png, ~jpg (ali vec koncnic) -> FAIL
		            	
		            	String ext = "";
		            	
		            	File outputfile = fc.getSelectedFile();
		            	String givenName = outputfile.getName();
		            	
		            	if (fc.getFileFilter().equals(pngFileFilter)) { 
		            		ext = "png";
		            	}
		            	else if (fc.getFileFilter().equals(jpgFileFilter)) {
							ext = "jpg";
						} else { // all
							
						}
						
						//System.out.println("Ext: "+ext);
		                
						
						try {
		                	
				        	ImageIO.write(getSelectedBufferedImage(), ext, outputfile);
				    	} catch (IOException f) {
				    		System.out.println("SAVE ERROR!");
				    	}
				    }
		            
		            
		        }
            }
        );

        
        /*
         * ZOOM
         */
        //ZOOM OUT
        meni.menuZoomOut.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_OUT, desktop)
        );
        //ZOOM IN
        meni.menuZoomIn.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_IN, desktop)
        );
        //ZOOM 6%
        meni.menuZoomMagnificationSix.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_6, desktop)
        );
        //ZOOM 12%
        meni.menuZoomMagnificationTwelve.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_12, desktop)
        );
        //ZOOM 25%
        meni.menuZoomMagnificationTwentyfive.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_25, desktop)
        );
        //ZOOM 50%
        meni.menuZoomMagnificationFifty.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_50, desktop)
        );
        //ZOOM 66%
        meni.menuZoomMagnificationSixtysix.addActionListener (
           		new ZoomListener(ZoomOperation.ZOOM_66, desktop)
        );
        //ZOOM 100%
        meni.menuZoomMagnificationHoundred.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_ACTUAL, desktop)
        );
        //ZOOM ACTUAL SIZE
        meni.menuZoomActualsize.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_ACTUAL, desktop)
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
        	new FilterWithouthDialogListener(NoDialogFilter.BLUR, this)
        );
        //FILTER RELIEF
        meni.menuFiltersRelief.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.RELIEF, this)
        );
        //FILTER SHARPEN
        meni.menuFiltersSharpen.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.SHARPEN, this) 
        );
		//FILTER EDGE DETECTION
        meni.menuFiltersEdgedetection.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.EDGE, this)
        );
        //FILTER MEDIAN
        meni.menuFiltersMedian.addActionListener (
        	new FilterWithouthDialogListener(NoDialogFilter.MEDIAN, this)
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
                	double[] histogram = Utility.getHistogram(getSelectedOriginalBufferedImage());
                	BufferedImage hisImg = Utility.getHistogramImage(histogram);
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	//in jo poslje dialogu v katerem uporabnik doloci levo in desno mejo
                	HistogramStretchingDialog dialog = new HistogramStretchingDialog(frameIn, hisImg);
                	
                	if (!dialog.wasCanceled()) {
                		int values[] = dialog.getValues();
                		//System.out.println(values);
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
                		//System.out.println(values);
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
        //FILTER THRESHOLDING 
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
				//try {
					//ce ni izbrane slike in ce meni ajtem potrebuje eno ali vec slik pol ga disejbla
					if ( !isThereASelectedFrame && (((MyMenuInterface)component[j]).noOfOperands() > 0) ) {
						component[j].setEnabled(false);
					}
					else {
						component[j].setEnabled(true);
					}
				//} catch (java.lang.ClassCastException e) {}
				//gleda za izjeme, ce je slucajno kaksen menu med meniji...
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
    	//Create a new internal frame with image and titel.
    	MyInternalFrame frame = new MyInternalFrame(this, img, title);
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
    //////////////////////////////////
    
    public BufferedImage getSelectedBufferedImage() {
    	//Vrne sliko, ki se nahaja v izbranem oknu
    	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
		return frame.getImg();
	}
	
	private BufferedImage getSelectedOriginalBufferedImage() {
    	//Vrne sliko, originalne velikosti  ki se nahaja v izbranem oknu
    	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
		return frame.getOriginalImg();
	}
    
	private void openTestImage() {
		//Odpre testno sliko
		JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(Conf.TEST_IMAGE_FILE_NAME));
    	try {
    		BufferedImage img = ImageIO.read(fc.getSelectedFile());
    		MyInternalFrame frame1 =createFrame(img, fc.getSelectedFile().getName());
            frame1.setSelected(true); //DOESENT WORK
		} catch (IOException f) {
		}
    	catch (java.beans.PropertyVetoException e) {
    	}
	}
	
	public void removeInternalFrameReference(JInternalFrame iFrame) {
		vmu.removeViewMenuItem(this, iFrame);
	}
	
    protected void windowClosed() {
        System.exit(0);
    }
    
}
