package si.gto76.funphototime;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import si.gto76.funphototime.mymenu.MyMenu;
import si.gto76.funphototime.mymenu.MyMenuItem;


class Menu {
	
	JMenuBar menuBar = new JMenuBar();

	MyMenu menuFile = new MyMenu(0);
    MyMenuItem menuFileOpen = new MyMenuItem(0);
    MyMenuItem menuFileSaveas = new MyMenuItem(1);
    MyMenuItem menuFileExit = new MyMenuItem(0);
    
    MyMenu menuEdit = new MyMenu(1);
    MyMenuItem menuEditUndoCloseWindow = new MyMenuItem(1);
    
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
	
    MyMenu menuFilters = new MyMenu(1);
    MyMenuItem menuFiltersBrightness1 = new MyMenuItem(1);
    MyMenuItem menuFiltersGamma = new MyMenuItem(1);
    MyMenuItem menuFiltersSaturation = new MyMenuItem(1);
    MyMenuItem menuFiltersGreyscale = new MyMenuItem(1);
    MyMenuItem menuFiltersNegativ = new MyMenuItem(1);
    MyMenuItem menuFiltersHistogrameq = new MyMenuItem(1);
    MyMenuItem menuFiltersHistogramst = new MyMenuItem(1);
    MyMenuItem menuFiltersBitplane = new MyMenuItem(1);
    MyMenuItem menuFiltersThresholding = new MyMenuItem(1);
    MyMenuItem menuFiltersBlur = new MyMenuItem(1);
    MyMenuItem menuFiltersRelief = new MyMenuItem(1);
    MyMenuItem menuFiltersSharpen = new MyMenuItem(1);
    MyMenuItem menuFiltersContrast = new MyMenuItem(1);
    MyMenuItem menuFiltersSmartbin = new MyMenuItem(1);
    MyMenuItem menuFiltersEdgedetection = new MyMenuItem(1);
    MyMenuItem menuFiltersMedian = new MyMenuItem(1);
    MyMenuItem menuFiltersColors = new MyMenuItem(1);
    
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
    
    MyMenu menuWindow = new MyMenu(1);
    MyMenuItem menuWindowTileall = new MyMenuItem(1);
    MyMenuItem menuWindowCloseall = new MyMenuItem(1);
    
    MyMenu menuHelp = new MyMenu(1);
    MyMenuItem menuHelpAbout = new MyMenuItem(0);
    
	public Menu() {
		menuFile.setText("File");
        menuFileOpen.setText("Open...");
        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuFileSaveas.setText("Save As...");
        menuFileSaveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuFileExit.setText("Exit");
        menuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        
        menuEdit.setText("Edit");
        menuEditUndoCloseWindow.setText("Undo Close Window");
        menuEditUndoCloseWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        
        menuZoom.setText("Zoom");
        menuZoomOut.setText("Zoom Out");
        menuZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0));
        menuZoomIn.setText("Zoom In");
        menuZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0));
        menuZoomActualsize.setText("Actual Size");
        menuZoomActualsize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0));
        menuZoomMagnificationSix.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0));
        menuZoomMagnificationTwelve.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0));
        menuZoomMagnificationTwentyfive.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0));
        menuZoomMagnificationFifty.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0));
        menuZoomMagnificationSixtysix.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
        menuZoomMagnificationHoundred.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0));
        
        menuFilters.setText("Filters");
        menuFiltersBrightness1.setText("Brightness...");
        menuFiltersBrightness1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
        menuFiltersGamma.setText("Gamma...");
        menuFiltersGamma.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
        menuFiltersSaturation.setText("Saturation...");
        menuFiltersSaturation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        menuFiltersGreyscale.setText("Grey Scale");
        menuFiltersGreyscale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0));
        menuFiltersNegativ.setText("Negativ");
        menuFiltersNegativ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0));
        menuFiltersHistogrameq.setText("Histogram Equalization");
        menuFiltersHistogrameq.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        menuFiltersHistogramst.setText("Histogram Stretching...");
        menuFiltersHistogramst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
        menuFiltersBitplane.setText("Bit-Plane Slicing...");
        menuFiltersBitplane.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        menuFiltersThresholding.setText("Binarize...");
        menuFiltersThresholding.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0));
        menuFiltersBlur.setText("Blur");
        menuFiltersBlur.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
        menuFiltersRelief.setText("Relief");
        menuFiltersRelief.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        //menuFiltersRelief.setMnemonic('e');
        menuFiltersSharpen.setText("Sharpen");
        menuFiltersSharpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        menuFiltersContrast.setText("Contrast...");
        menuFiltersContrast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
        //menuFiltersSmartbin.setText("Smart Binarize");
        //menuFiltersSmartbin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        menuFiltersEdgedetection.setText("Edge Detection");
        menuFiltersEdgedetection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0));
        menuFiltersMedian.setText("Median Noise Reduction");
        menuFiltersMedian.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
        menuFiltersColors.setText("Color Adjustment...");
        menuFiltersColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        
        menuLogicop.setText("Logic");
        menuLogicopNot.setText("Not");
        menuLogicopNot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0));
        menuLogicopAnd.setText("And...");
        menuLogicopAnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
        menuLogicopOr.setText("Or...");
        menuLogicopOr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0));
        menuLogicopXor.setText("Xor...");
        menuLogicopXor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0));
        
        menuArithmeticop.setText("Arithmetic");
        menuArithmeticopAddition.setText("Add...");
        menuArithmeticopAddition.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
        menuArithmeticopSubtraction.setText("Subtract...");
        menuArithmeticopSubtraction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        menuArithmeticopMultiplication.setText("Multiply...");
        menuArithmeticopMultiplication.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0));
        menuArithmeticopDivision.setText("Divide...");
        menuArithmeticopDivision.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));

        menuWindowTileall.setText("Tile All");
        menuWindowTileall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        menuWindowCloseall.setText("Close All");
        menuWindow.setText("Window");
        
        menuHelp.setText("Help");
        menuHelpAbout.setText("About");
		
		/*****************************************/
		
		menuFile.add(menuFileOpen);
        menuFile.add(menuFileSaveas);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        
        menuEdit.add(menuEditUndoCloseWindow);
        menuBar.add(menuEdit);
        
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
        menuBar.add(menuZoom);
        
        menuFilters.add(menuFiltersBrightness1);
        menuFilters.add(menuFiltersContrast);
        menuFilters.add(menuFiltersGamma);
        menuFilters.add(menuFiltersHistogramst);
        menuFilters.add(menuFiltersHistogrameq);
        menuFilters.addSeparator();
        menuFilters.add(menuFiltersSaturation);
        menuFilters.add(menuFiltersColors);
        //menuFilters.add(menuFiltersGreyscale);
        menuFilters.add(menuFiltersNegativ);
        menuFilters.add(menuFiltersThresholding);
        //menuFilters.add(menuFiltersSmartbin);
        menuFilters.add(menuFiltersBitplane);
        menuFilters.addSeparator();
        menuFilters.add(menuFiltersBlur);
        menuFilters.add(menuFiltersSharpen);
        menuFilters.add(menuFiltersMedian);
        menuFilters.add(menuFiltersRelief);
        menuFilters.add(menuFiltersEdgedetection);
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

        menuWindow.add(menuWindowTileall);
        menuWindow.add(menuWindowCloseall);
        menuWindow.addSeparator();
        menuBar.add(menuWindow);
        
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
}
