package si.gto76.javaphotoeditor;

import javax.swing.JMenuBar;


class Meni {
	
	JMenuBar menuBar = new JMenuBar();
	
	
	
	MyMenu menuFile = new MyMenu(0);
    MyMenuItem menuFileOpen = new MyMenuItem(0);
    MyMenuItem menuFileSaveas = new MyMenuItem(1);
    MyMenuItem menuFileExit = new MyMenuItem(0);
    //MyMenuItem menuFile = new MyMenuItem(1);
    MyMenu menuZoom = new MyMenu(1);
    MyMenuItem menuZoomOut = new MyMenuItem(1);
    MyMenuItem menuZoomIn = new MyMenuItem(1);
    MyMenuItem menuZoomActualsize = new MyMenuItem(1);
    MyMenu menuZoomMagnification = new MyMenu("Magnification",1);
    MyMenuItem menuZoomMagnificationSix = new MyMenuItem("6%",1);
    MyMenuItem menuZoomMagnificationTwelve = new MyMenuItem("12%",1);
    MyMenuItem menuZoomMagnificationTwentyfive = new MyMenuItem("25%",1);
    MyMenuItem menuZoomMagnificationFifty = new MyMenuItem("50%",1);
    MyMenuItem menuZoomMagnificationSixtysix = new MyMenuItem("66%",1);
    MyMenuItem menuZoomMagnificationHoundred = new MyMenuItem("100%",1);
    //MyMenuItem menuZoom = new MyMenuItem(1);
	MyMenu menuFilters = new MyMenu(1);
    MyMenuItem menuFiltersBrightness1 = new MyMenuItem(1);
    //MyMenuItem menuFiltersBrightness2 = new MyMenuItem(1);
    MyMenuItem menuFiltersGamma = new MyMenuItem(1);
    MyMenuItem menuFiltersSaturation = new MyMenuItem(1);
    MyMenuItem menuFiltersGreyscale = new MyMenuItem(1);
    //MyMenuItem menuFiltersGreyscale2 = new MyMenuItem(1);
    MyMenuItem menuFiltersNegativ = new MyMenuItem(1);
    MyMenuItem menuFiltersHistogrameq = new MyMenuItem(1);
    MyMenuItem menuFiltersHistogramst = new MyMenuItem(1);
    //MyMenuItem menuFiltersHistogramma = new MyMenuItem(1);
    MyMenuItem menuFiltersBitplane = new MyMenuItem(1);
    MyMenuItem menuFiltersThresholding = new MyMenuItem(1);
    MyMenuItem menuFiltersBlur = new MyMenuItem(1);
    MyMenuItem menuFiltersRelief = new MyMenuItem(1);
    MyMenuItem menuFiltersSharpen = new MyMenuItem(1);
    MyMenuItem menuFiltersContrast = new MyMenuItem(1);
    MyMenuItem menuFiltersSmartbin = new MyMenuItem(1);
    MyMenuItem menuFiltersEdgedetection = new MyMenuItem(1);
    MyMenuItem menuFiltersMedian = new MyMenuItem(1);
    MyMenuItem menuFiltersColors = new MyMenuItem("Color Adjustment",1);
    //MyMenuItem menuFilters = new MyMenuItem(1);
    MyMenu menuLogicop = new MyMenu(1);
    MyMenuItem menuLogicopNot = new MyMenuItem(1);
    MyMenuItem menuLogicopAnd = new MyMenuItem(1);
    MyMenuItem menuLogicopOr = new MyMenuItem(1);
    MyMenuItem menuLogicopXor = new MyMenuItem(1);
    MyMenu menuArithmeticop = new MyMenu(1);
    MyMenuItem menuArithmeticopAddition = new MyMenuItem(1);
    MyMenuItem menuArithmeticopSubtraction = new MyMenuItem(1);
    MyMenuItem menuArithmeticopMultiplication = new MyMenuItem(1);
    MyMenuItem menuArithmeticopDivision = new MyMenuItem(1);
	
	
	public Meni() {
		
		menuFile.setText("File");
        menuFileOpen.setText("Open...");
        menuFileSaveas.setText("Save As...");
        menuFileExit.setText("Exit");
        menuZoom.setText("Zoom");
        menuZoomOut.setText("Zoom Out");
        menuZoomIn.setText("Zoom In");
        menuZoomActualsize.setText("Actual Size");
        //menuZoom.setText("");
        menuFilters.setText("Filters");
        menuFiltersBrightness1.setText("Brightness...");
        //menuFiltersBrightness2.setText("Brightness2...");
        menuFiltersGamma.setText("Gamma...");
        menuFiltersSaturation.setText("Saturation...");
        menuFiltersGreyscale.setText("Grey Scale");
        //menuFiltersGreyscale2.setText("Grey Scale 2");
        menuFiltersNegativ.setText("Negativ");
        menuFiltersHistogrameq.setText("Histogram Equalization");
        menuFiltersHistogramst.setText("Histogram Stretching...");
        //menuFiltersHistogramma.setText("Histogram Matching...");
        menuFiltersBitplane.setText("Bit-Plane Slicing...");
        menuFiltersThresholding.setText("Binarize...");
        menuFiltersBlur.setText("Blur");
        menuFiltersRelief.setText("Relief");
        menuFiltersSharpen.setText("Sharpen");
        menuFiltersContrast.setText("Contrast...");
        menuFiltersSmartbin.setText("Smart Binarize");
        menuFiltersEdgedetection.setText("Edge Detection");
        menuFiltersMedian.setText("Median Noise Reduction");
        //menuFilters.setText("");
        menuLogicop.setText("Logic");
        menuLogicopNot.setText("NOT");
        menuLogicopAnd.setText("AND");
        menuLogicopOr.setText("OR");
        menuLogicopXor.setText("XOR");
        menuArithmeticop.setText("Arithmetic");
        menuArithmeticopAddition.setText("+");
        menuArithmeticopSubtraction.setText("-");
        menuArithmeticopMultiplication.setText("x");
        menuArithmeticopDivision.setText("/");
		
		/*****************************************/
		
		menuFile.add(menuFileOpen);
        menuFile.add(menuFileSaveas);
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        
        
        menuZoomMagnification.add(menuZoomMagnificationSix);
	    menuZoomMagnification.add(menuZoomMagnificationTwelve);
	    menuZoomMagnification.add(menuZoomMagnificationTwentyfive);
	    menuZoomMagnification.add(menuZoomMagnificationFifty);
	    menuZoomMagnification.add(menuZoomMagnificationSixtysix);
	    menuZoomMagnification.add(menuZoomMagnificationHoundred);
        
        menuZoom.add(menuZoomOut);
        menuZoom.add(menuZoomIn);
        menuZoom.add(menuZoomActualsize);
        menuZoom.add(menuZoomMagnification);
        //menuZoom.add(menuZoom);
        menuBar.add(menuZoom);
        
        menuFilters.add(menuFiltersBrightness1);
        //menuFilters.add(menuFiltersBrightness2);
        menuFilters.add(menuFiltersContrast);
        menuFilters.add(menuFiltersGamma);
        menuFilters.add(menuFiltersHistogrameq);
        menuFilters.add(menuFiltersHistogramst);
        //menuFilters.add(menuFiltersHistogramma);
        menuFilters.add(menuFiltersSaturation);
        menuFilters.add(menuFiltersGreyscale);
        //menuFilters.add(menuFiltersGreyscale2);
        menuFilters.add(menuFiltersNegativ);
        menuFilters.add(menuFiltersThresholding);
        menuFilters.add(menuFiltersSmartbin);
        menuFilters.add(menuFiltersBitplane);
        menuFilters.add(menuFiltersBlur);
        menuFilters.add(menuFiltersSharpen);
        menuFilters.add(menuFiltersMedian);
        menuFilters.add(menuFiltersRelief);
        menuFilters.add(menuFiltersEdgedetection);
        menuFilters.add(menuFiltersColors);
        //menuFilters.add(menuFilters);
        menuBar.add(menuFilters);
        
        menuLogicop.add(menuLogicopNot);
        menuLogicop.add(menuLogicopAnd);
        menuLogicop.add(menuLogicopOr);
        menuLogicop.add(menuLogicopXor);
        menuBar.add(menuLogicop);
        
        menuArithmeticop.add(menuArithmeticopAddition);
        menuArithmeticop.add(menuArithmeticopSubtraction);
        menuArithmeticop.add(menuArithmeticopMultiplication);
        menuArithmeticop.add(menuArithmeticopDivision);
        menuBar.add(menuArithmeticop);
        
        
        //setJMenuBar(menuBar);
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
