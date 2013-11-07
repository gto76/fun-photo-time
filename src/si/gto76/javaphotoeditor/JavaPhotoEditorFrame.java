package si.gto76.javaphotoeditor;

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
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.MenuElement;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import si.gto76.javaphotoeditor.actionlisteners.FilterDialogWithSliderDoubleListener;
import si.gto76.javaphotoeditor.actionlisteners.FilterWithouthDialogListener;
import si.gto76.javaphotoeditor.actionlisteners.OperationDialogListener;
import si.gto76.javaphotoeditor.actionlisteners.ZoomListener;
import si.gto76.javaphotoeditor.dialogs.BitPlaneDialog;
import si.gto76.javaphotoeditor.dialogs.ColorsDialog;
import si.gto76.javaphotoeditor.dialogs.HistogramStretchingDialog;
import si.gto76.javaphotoeditor.dialogs.MyMenuInterface;
import si.gto76.javaphotoeditor.dialogs.ThresholdingDialog;
import si.gto76.javaphotoeditor.enums.Filter;
import si.gto76.javaphotoeditor.enums.NoDialogFilter;
import si.gto76.javaphotoeditor.enums.SpatialFilters;
import si.gto76.javaphotoeditor.enums.ZoomOperation;
import si.gto76.javaphotoeditor.filterthreads2.BitPlaneThread2;
import si.gto76.javaphotoeditor.filterthreads2.ColorsThread2;
import si.gto76.javaphotoeditor.filterthreads2.FilterThread2;
import si.gto76.javaphotoeditor.filterthreads2.HistogramEqualizationThread2;
import si.gto76.javaphotoeditor.filterthreads2.HistogramStretchingThread2;
import si.gto76.javaphotoeditor.filterthreads2.NotThread2;
import si.gto76.javaphotoeditor.filterthreads2.ThresholdingThread2;

/*
 * @(#)Grafika2.java
 *
 * JFC Sample application
 *
 * @author: Jure Sorn 
 * @version 1.00 08/02/21
 */
 
public class JavaPhotoEditorFrame extends JFrame 
							implements ContainerListener, MouseWheelListener {
    
    final private boolean TEST_IMAGE = true; //da avtomatsko odpre testno sliko
	final private String TEST_IMAGE_FILE_NAME = "/home/minerva/131060885.jpg";
    public JDesktopPane desktop;
    private int noOfFrames = 0;
    private Meni meni;
    private String lastPath = "C:\\Documents and Settings\\User\\My Documents\\My Pictures\\Ostalo\\Bergel.jpg";
    
    /**
     * The constructor.
     */  
     
    public JavaPhotoEditorFrame() {
    	super("Java Photo Editor");
        
        /*inicializacije****************************************/
        
        //nastavitve za internal frame
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset + 30, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
        desktop = new JDesktopPane();
        //createFrame();
        setContentPane(desktop);
        
        if (TEST_IMAGE) openTestImage();
        
        //da obvesca o na novo odprtih in zaprtih internal frejmih
        desktop.addContainerListener(this);
        //poslusa kolescek na miski za zoom in, out
        desktop.addMouseWheelListener(this);
        
        //Meniji
        meni = new Meni();
        setJMenuBar(meni.getMenuBar());
        
        
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
                    JavaPhotoEditorFrame.this.windowClosed();
                }
            }
        ); 
        
        //FILE OPEN
        meni.menuFileOpen.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	JFileChooser fc = new JFileChooser();
                	int returnVal = fc.showOpenDialog(JavaPhotoEditorFrame.this);
                	if (returnVal == JFileChooser.APPROVE_OPTION) {
                		try {
                			BufferedImage imgIn = ImageIO.read(fc.getSelectedFile());
							createFrame(imgIn, fc.getSelectedFile().getName());
						} catch (IOException f) {
						}
						
						//TODO fix
						//shrani path za naslednjic ko odpremo open ali save
						//lastPath = fc.getSelectedFile().getAbsolutePath(); 
						//regex ki izloci filename iz patha - not universal
						/*
						Pattern pat = Pattern.compile("^(.+\\\\)[^\\\\]+$");
				        Matcher mat = pat.matcher(lastPath);
				        mat.find();
				        lastPath = mat.group(1);
				        */
				    }
				}
            }
        ); 
        
        //FILE SAVE
        meni.menuFileSaveas.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	if ( desktop.getSelectedFrame() != null ) {
                		JFileChooser fc = new JFileChooser();
                		String fileName = desktop.getSelectedFrame().toString();
                		fc.setSelectedFile(new File(lastPath + fileName));
                		fc.setDialogTitle("Save As");
	                	int returnVal = fc.showSaveDialog(JavaPhotoEditorFrame.this);
			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			                try {
					        File outputfile = fc.getSelectedFile();
					        	ImageIO.write(getSelectedBufferedImage(), "png", outputfile);
					    	} catch (IOException f) {
					    	}
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
        meni.menuFiltersHistogrameq.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

					MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(Filtri.histogramEqualization(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
						double[] histogram = Utility.getHistogram(frameIn.getOriginalImg());
						int[] mappingArray = Filtri.calculateMappingArrayForEqualization(histogram);
						FilterThread2 filterThread = new HistogramEqualizationThread2(frameIn.getOriginalImg(), frameOut, mappingArray);
	                }

                }
            }
        );

		//FILTER SMART BINARIZE
        meni.menuFiltersSmartbin.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	// find the threshold
                	BufferedImage img = SpatialFilters.blur(getSelectedBufferedImage());
                	double[] histogram = Utility.getHistogram(img);
                	int sedlo = Filtri.poisciSedlo(histogram);
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(Filtri.thresholding1(getSelectedBufferedImage(), sedlo), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
                		//System.out.println("Sedlo: " + sedlo);
	                	FilterThread2 filterThread = new ThresholdingThread2(frameIn.getOriginalImg(), sedlo, frameOut);
	                }
                }
                
            }
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
        meni.menuFiltersBitplane.addActionListener
        (
			 new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	BitPlaneDialog dialog = new BitPlaneDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		int values = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new BitPlaneThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                    
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                	
                }
            }
			
        ); 
        
		//FILTER HISTOGRAM STRETCHING
        meni.menuFiltersHistogramst.addActionListener
        (
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
        
        //FILTER Color balance
        meni.menuFiltersColors.addActionListener
        (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	ColorsDialog dialog = new ColorsDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		int values[] = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new ColorsThread2(frameIn.getOriginalImg(), values , frameOut);
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
        meni.menuLogicopNot.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(Filtri.not(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	new NotThread2(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
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
        
        // Add window listener.
        this.addWindowListener
        (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    JavaPhotoEditorFrame.this.windowClosed();
                }
            }
        );  
        
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
    	noOfFrames++;
	}
	
	public void componentRemoved(ContainerEvent e) {
		noOfFrames--;
	}
    
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println("kkjkjsed");
    	int notches = e.getWheelRotation();
    	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
        if (notches < 0) { //Mouse wheel moved UP, zoom in
            frame.zoomIn(10);
        } 
        else {
       		frame.zoomOut(10);
        }
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
    
    public MyInternalFrame createFrame(BufferedImage img) {
		//Create a new internal frame with image.
    	MyInternalFrame frame = new MyInternalFrame(img);
        
        frame.setVisible(true);
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        return frame;
    }
    
    /*
    private MyInternalFrame createZoomedFrame(BufferedImage img, int zoom) {
		//Create a new internal frame with zoomed image. Add original image later.
    	MyInternalFrame frame = new MyInternalFrame(img, zoom);
        
        frame.setVisible(true); 
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        return frame;
    }
    */
    
    public MyInternalFrame createZoomedFrame(BufferedImage img, MyInternalFrame frameIn) {
		//Create a new internal frame with zoomed image. Add original image later.
		//also it inherits the titel
    	MyInternalFrame frameOut = new MyInternalFrame(img, frameIn);
        
        frameOut.setVisible(true); 
        desktop.add(frameOut);
        try {
            frameOut.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        return frameOut;
    }
    
    private MyInternalFrame createFrame(BufferedImage img, String title) {
    	//Create a new internal frame with image and titel.
    	MyInternalFrame frame = new MyInternalFrame(img, title);
        
        frame.setVisible(true); 
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        return frame;
    }
    
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
        fc.setSelectedFile(new File(TEST_IMAGE_FILE_NAME));
    	try {
    		BufferedImage img = ImageIO.read(fc.getSelectedFile());
		    MyInternalFrame frame1 = createFrame(img);
            frame1.setSelected(true);
		} catch (IOException f) {
		}
    	catch (java.beans.PropertyVetoException e) {
    	}
	}
	
    protected void windowClosed() {
    	// TODO: Check if it is safe to close the application
        System.exit(0);
    }
    
}
