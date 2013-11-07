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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.MenuElement;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import si.gto76.javaphotoeditor.dialogs.AdditionDialog;
import si.gto76.javaphotoeditor.dialogs.AndDialog;
import si.gto76.javaphotoeditor.dialogs.BitPlaneDialog;
import si.gto76.javaphotoeditor.dialogs.Brightness1Dialog;
import si.gto76.javaphotoeditor.dialogs.ContrastDialog;
import si.gto76.javaphotoeditor.dialogs.DivisionDialog;
import si.gto76.javaphotoeditor.dialogs.GammaDialog;
import si.gto76.javaphotoeditor.dialogs.HistogramStretchingDialog;
import si.gto76.javaphotoeditor.dialogs.MultiplicationDialog;
import si.gto76.javaphotoeditor.dialogs.MyMenuInterface;
import si.gto76.javaphotoeditor.dialogs.OperationDialog;
//import si.gto76.javaphotoeditor.dialogs.OperationDialog;
import si.gto76.javaphotoeditor.dialogs.OrDialog;
import si.gto76.javaphotoeditor.dialogs.SaturationDialog;
import si.gto76.javaphotoeditor.dialogs.SubtractionDialog;
import si.gto76.javaphotoeditor.dialogs.ThresholdingDialog;
import si.gto76.javaphotoeditor.dialogs.XorDialog;
import si.gto76.javaphotoeditor.filterthreads2.BitPlaneThread2;
import si.gto76.javaphotoeditor.filterthreads2.Brightness1Thread2;
import si.gto76.javaphotoeditor.filterthreads2.ContrastThread2;
import si.gto76.javaphotoeditor.filterthreads2.FilterThread2;
import si.gto76.javaphotoeditor.filterthreads2.GammaThread2;
import si.gto76.javaphotoeditor.filterthreads2.Greyscale1Thread2;
import si.gto76.javaphotoeditor.filterthreads2.Greyscale2Thread2;
import si.gto76.javaphotoeditor.filterthreads2.HistogramEqualizationThread2;
import si.gto76.javaphotoeditor.filterthreads2.HistogramStretchingThread2;
import si.gto76.javaphotoeditor.filterthreads2.NegativeThread2;
import si.gto76.javaphotoeditor.filterthreads2.NotThread2;
import si.gto76.javaphotoeditor.filterthreads2.SaturationThread2;
import si.gto76.javaphotoeditor.filterthreads2.ThresholdingThread2;
import si.gto76.javaphotoeditor.filterthreads3.BlurThread3;
import si.gto76.javaphotoeditor.filterthreads3.EdgeDetectionThread3;
import si.gto76.javaphotoeditor.filterthreads3.FilterThread3;
import si.gto76.javaphotoeditor.filterthreads3.MedianThread3;
import si.gto76.javaphotoeditor.filterthreads3.ReliefThread3;
import si.gto76.javaphotoeditor.filterthreads3.SharpenThread3;

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
    
    final private boolean TEST_IMAGE = false; //da avtomatsko odpre testno sliko
	private ArrayList list = new ArrayList();
    JDesktopPane desktop;
    private int noOfFrames = 0;
    private Meni meni;
    private String lastPath = "C:\\Documents and Settings\\User\\My Documents\\My Pictures\\Ostalo\\Bergel.jpg";
    
    
    /**
     * The constructor.
     */  
     
    public JavaPhotoEditorFrame() {
    	super("Glavno okno grafike");
        
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
        
        //FILE
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
                	//fc.setSelectedFile(new File("C:\\Documents and Settings\\User\\My Documents\\My Pictures\\Ostalo\\color_spectrum.jpg"));
                	int returnVal = fc.showOpenDialog(JavaPhotoEditorFrame.this);
                	if (returnVal == JFileChooser.APPROVE_OPTION) {
            			try {
						    list.add( ImageIO.read(fc.getSelectedFile() ) );
						} catch (IOException f) {
						}
						createFrame((BufferedImage) list.get(list.size()-1), 
													fc.getSelectedFile().getName());
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
        
        //ZOOM OUT
        meni.menuZoomOut.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoomOut(10);
                }
            }
        );
        
        //ZOOM IN
        meni.menuZoomIn.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoomIn(10);
                }
            }
        );
        
        //ZOOM
        meni.menuZoomMagnificationSix.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoom(6);
                }
            }
        );
        
        //ZOOM
        meni.menuZoomMagnificationTwelve.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoom(12);
                }
            }
        );
        
        //ZOOM
        meni.menuZoomMagnificationTwentyfive.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoom(25);
                }
            }
        );
        
        //ZOOM
        meni.menuZoomMagnificationFifty.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoom(50);
                }
            }
        );
        
        //ZOOM
        meni.menuZoomMagnificationSixtysix.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.zoom(66);
                }
            }
        );
        
        //ZOOM
        meni.menuZoomMagnificationHoundred.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.actualSize();
                }
            }
        );
        
        //ZOOM ACTUAL SIZE
        meni.menuZoomActualsize.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
					frame.actualSize();
                }
            }
        );
        
        
		/*
		FILTRI BREZ DIALOGA
		*/


        //FILTER NEGATIV
        meni.menuFiltersNegativ.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	//naredi nov frame ki zaenkrat vsebuje samo zumirano obdelano sliko brez originala
                	MyInternalFrame frameOut = createZoomedFrame(Filtri.negativ(getSelectedBufferedImage()), frameIn);
                	
                	if ( frameIn.getZoom() != 100 ) {
	                	//novemu frejmu doloci originalno sliko, ki je za enkrat prazna in istih dimenzij
	                	//kot originalna slika pri prejsnjem frejmu
	                	
						//frameOut.setOriginalImg(Utility.declareNewBufferedImage(frameIn.getOriginalImg()));
	                	//naredi nit, ki bo filtrirala originalmno sliko
	                	FilterThread2 filterThread = new NegativeThread2(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );
        
        //FILTER GREYSCALE
        meni.menuFiltersGreyscale.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(Filtri.greyScale(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread2 filterThread = new Greyscale1Thread2(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );  
        
        //FILTER GREYSCALE 2
        /*
        meni.menuFiltersGreyscale2.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(Filtri.greyScale(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread2 filterThread = new Greyscale2Thread2(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );
		*/

		//FILTER BLUR
        meni.menuFiltersBlur.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(SpatialFilters.blur(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread3 filterThread = new BlurThread3(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );
        
        //FILTER RELIEF
        meni.menuFiltersRelief.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(SpatialFilters.relief(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread3 filterThread = new ReliefThread3(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );
        
        //FILTER SHARPEN
        meni.menuFiltersSharpen.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(SpatialFilters.sharpen(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread3 filterThread = new SharpenThread3(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );

		//FILTER EDGE DETECTION
        meni.menuFiltersEdgedetection.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(SpatialFilters.edgeDetection(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread3 filterThread = new EdgeDetectionThread3(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );
        
        //FILTER MEDIAN
        meni.menuFiltersMedian.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	MyInternalFrame frameOut = createZoomedFrame(SpatialFilters.median(getSelectedBufferedImage()), frameIn);
                	if ( frameIn.getZoom() != 100 ) {
	                	FilterThread3 filterThread = new MedianThread3(frameIn.getOriginalImg(), frameOut);
	                }
                }
            }
        );

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

		//FILTER Smart Binarize
        // TODO adopt to new way	
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
		FILTRI Z DIALOGOM
		*/

        
        //FILTER CONTRAST
        meni.menuFiltersContrast.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	ContrastDialog dialog = new ContrastDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		double values = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new ContrastThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                    
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                	
                }
            }
        );
        
        
        //FILTER GAMMA
        meni.menuFiltersGamma.addActionListener
        (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	GammaDialog dialog = new GammaDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		double values = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new GammaThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                    
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                	
                }
            }
        );
        
        
        //FILTER HISTOGRAM MATCHING
/*
        meni.menuFiltersHistogramma.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	HistogramMatchingDialog dialog = new HistogramMatchingDialog(desktop, (MyInternalFrame)desktop.getSelectedFrame());
                	if (!dialog.wasCanceled()) {
                		list.add(dialog.getProcessedImage());
	                    createFrame((BufferedImage) list.get(list.size()-1));
                    }
                }
            }
        );
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
        
        //FILTER Saturation
        meni.menuFiltersSaturation.addActionListener
        (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	SaturationDialog dialog = new SaturationDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		double values = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new SaturationThread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                    
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                	
                }
            }
        );  
        
        //FILTER Brightness1
        meni.menuFiltersBrightness1.addActionListener
        (
          

			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	Brightness1Dialog dialog = new Brightness1Dialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		double values = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new Brightness1Thread2(frameIn.getOriginalImg(), values , frameOut);
                	}
                    
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();
                	
                }
            }
        ); 
        
        //FILTER Brightness2
/*
        meni.menuFiltersBrightness2.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	Brightness2Dialog dialog = new Brightness2Dialog((MyInternalFrame)desktop.getSelectedFrame());
                	if (!dialog.wasCanceled()) {
                		list.add(dialog.getProcessedImage());
	                    createFrame((BufferedImage) list.get(list.size()-1));
                    }
                    dialog.resetOriginalImage();
                    
                }
            }
        );
*/        
        //FILTER Thresholding
        meni.menuFiltersThresholding.addActionListener
        (

			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	ThresholdingDialog dialog = new ThresholdingDialog(frameIn);
                	
                	if (!dialog.wasCanceled()) {
                		int values = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new ThresholdingThread2(frameIn.getOriginalImg(), values , frameOut);
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
                	//dobi array, ki predstavlja histogram
                	double[] histogram = Utility.getHistogram(getSelectedOriginalBufferedImage());
                	//naredi sliko tega histograma
                	BufferedImage hisImg = Utility.getHistogramImage(histogram);
                	
                	MyInternalFrame frameIn = (MyInternalFrame) desktop.getSelectedFrame();
                	//in jo poslje dialogu v katerem uporabnik doloci levo in desno mejo
                	HistogramStretchingDialog dialog = new HistogramStretchingDialog(frameIn, hisImg);
                	
                	if (!dialog.wasCanceled()) {
                		int values[] = dialog.getValues();
                		//System.out.println(values);
                		MyInternalFrame frameOut = createZoomedFrame(dialog.getProcessedImage(), frameIn);
                		FilterThread2 filterThread = new HistogramStretchingThread2(frameIn.getOriginalImg(), values ,frameOut);
                	}
                	//da prekopira nazaj prvotno sliko v izbrani frame
                	dialog.resetOriginalImage();                	

                }
            }
        ); 
        
        
		/*
		LOGICNI OPERATORJI
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
        meni.menuLogicopAnd.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new AndDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
        ); 
        
        //LOGICOP OR TODO od tle naprej...
        meni.menuLogicopOr.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new OrDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
        );
        
        //LOGICOP XOR
        meni.menuLogicopXor.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new XorDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
        );

		
		/*
		ARITMETICNI OPERATORJI
		*/

        
        //ARITHMETICOP ADDITION
        meni.menuArithmeticopAddition.addActionListener
        (
    		new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new AdditionDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
        );
        
        //ARITHMETICOP SUBTRACTION
        meni.menuArithmeticopSubtraction.addActionListener
        (
    		new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new AdditionDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
        );
        
        //ARITHMETICOP MULTIPLICATION
        meni.menuArithmeticopMultiplication.addActionListener
        (
    		new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new MultiplicationDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
        );
        
        //ARITHMETICOP DIVISION
        meni.menuArithmeticopDivision.addActionListener
        (
    		new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	OperationDialog dialog = new DivisionDialog(desktop);
                	if (!dialog.wasCanceled()) {
                		MyInternalFrame frame = createFrame(dialog.getProcessedOrigImage());
                		frame.zoom(dialog.getZoom());
	                }
                }
            }
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
    
    /**
     * The constructor.
     */ 
    
    
    
    /****************************************************************/
    /****************************************************************/
    /****************************************************************/
    
    
    
    /**
     * Metode in Funkcije
     */
     
    public void componentAdded(ContainerEvent e) {
    	//metoda, ki se sprozi ko container listener
    	//zazna da se je dodal novi internal frame v desktop
    	noOfFrames++;
    	//MyInternalFrame myInternalFrame = (MyInternalFrame) e.getChild();
    	//myInternalFrame.addMouseWheelListener(this);
	}
	
	public void componentRemoved(ContainerEvent e) {
		noOfFrames--;
		
		//Da zbrise iz lista sliko, ki jo ne rabimo vec
		MyInternalFrame myInternalFrame = (MyInternalFrame) e.getChild();
		BufferedImage img = myInternalFrame.getImg();
		boolean isRemoved = list.remove(img);
	}
	//namesto tega bi seveda lahko uporabu getAllFrames()
	
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
    
    MyInternalFrame createZoomedFrame(BufferedImage img, MyInternalFrame frameIn) {
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
    
    private BufferedImage getSelectedBufferedImage() {
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
        fc.setSelectedFile(new File(
        	"C:\\Documents and Settings\\User\\My Documents\\My Pictures\\Ostalo\\Bergell.jpg"));
    	try {
		    list.add(ImageIO.read(fc.getSelectedFile()  ));
		    createFrame((BufferedImage) list.get(list.size()-1));
		} catch (IOException f) {
		}
	}
	
    protected void windowClosed() {
    	// TODO: Check if it is safe to close the application
    	
        // Exit application.
        System.exit(0);
    }
    
}
