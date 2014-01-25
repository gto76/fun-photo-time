package si.gto76.funphototime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;
import si.gto76.funphototime.actionlisteners.SingleParameterFilterListener;
import si.gto76.funphototime.actionlisteners.MultipleParameterFilterListener;
import si.gto76.funphototime.actionlisteners.ParameterlessFilterListener;
import si.gto76.funphototime.actionlisteners.LoadListener;
import si.gto76.funphototime.actionlisteners.OperationDialogListener;
import si.gto76.funphototime.actionlisteners.SaveListener;
import si.gto76.funphototime.actionlisteners.SpatialFilterWithouthDialogListener;
import si.gto76.funphototime.actionlisteners.TileListener;
import si.gto76.funphototime.actionlisteners.ZoomListener;
import si.gto76.funphototime.dialogs.AboutDialog;
import si.gto76.funphototime.enums.MultipleParameterFilter;
import si.gto76.funphototime.enums.Operation;
import si.gto76.funphototime.enums.ParameterlessFilter;
import si.gto76.funphototime.enums.SingleParameterFilter;
import si.gto76.funphototime.enums.ZoomOperation;

public class ActionListeners {
	public static void set(final FunPhotoTimeFrame frame, Menu meni) {
		
        /*
         * FILE
         */
        //FILE OPEN
        meni.menuFileOpen.addActionListener (
    		new LoadListener(frame)
        ); 
        //FILE SAVE
        meni.menuFileSaveas.addActionListener (
        	new SaveListener(frame)
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
         * EDIT
         */
        // UNDO CLOSE WINDOW
        meni.menuEditUndoCloseWindow.addActionListener(
		new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frame.lastClosedIntrnalFrame != null) {
					frame.internalFrameInit(frame.lastClosedIntrnalFrame);
					frame.lastClosedIntrnalFrame = null;
				}
			}
		});
        
        /*
         * ZOOM
         */
        //ZOOM OUT
        meni.menuZoomOut.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_OUT, frame)
        );
        //ZOOM IN
        meni.menuZoomIn.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_IN, frame)
        );
        //ZOOM 6%
        meni.menuZoomMagnificationSix.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_6, frame)
        );
        //ZOOM 12%
        meni.menuZoomMagnificationTwelve.addActionListener (
        	new ZoomListener(ZoomOperation.ZOOM_12, frame)
        );
        //ZOOM 25%
        meni.menuZoomMagnificationTwentyfive.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_25, frame)
        );
        //ZOOM 50%
        meni.menuZoomMagnificationFifty.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_50, frame)
        );
        //ZOOM 66%
        meni.menuZoomMagnificationSixtysix.addActionListener (
           	new ZoomListener(ZoomOperation.ZOOM_66, frame)
        );
        //ZOOM 100%
        meni.menuZoomMagnificationHoundred.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_ACTUAL, frame)
        );
        //ZOOM ACTUAL SIZE
        meni.menuZoomActualsize.addActionListener (
       		new ZoomListener(ZoomOperation.ZOOM_ACTUAL, frame)
        );

        
        /*
         * FILTERS WITHOUTH PARAMETERS
         */
        //FILTER NEGATIV
        meni.menuFiltersNegativ.addActionListener (
        	new ParameterlessFilterListener(ParameterlessFilter.NEGATIV, frame)
        );
        //FILTER GREYSCALE
        meni.menuFiltersGreyscale.addActionListener (
        	new ParameterlessFilterListener(ParameterlessFilter.GREYSCALE, frame)
        );  
        
        
        /*
         * FILTERS WITHOUTH PARAMETERS, WITH MASKS
         */
		//FILTER BLUR
        meni.menuFiltersBlur.addActionListener (
        	new SpatialFilterWithouthDialogListener(ParameterlessFilter.BLUR, frame)
        );
        //FILTER RELIEF
        meni.menuFiltersRelief.addActionListener (
        	new SpatialFilterWithouthDialogListener(ParameterlessFilter.RELIEF, frame)
        );
        //FILTER SHARPEN
        meni.menuFiltersSharpen.addActionListener (
        	new SpatialFilterWithouthDialogListener(ParameterlessFilter.SHARPEN, frame) 
        );
		//FILTER EDGE DETECTION
        meni.menuFiltersEdgedetection.addActionListener (
        	new SpatialFilterWithouthDialogListener(ParameterlessFilter.EDGE, frame)
        );
        //FILTER MEDIAN
        meni.menuFiltersMedian.addActionListener (
        	new SpatialFilterWithouthDialogListener(ParameterlessFilter.MEDIAN, frame)
        );
        
        
        /*
         * FILTERS WITHOUTH PARAMETERS, SMART
         */
		//FILTER HISTOGRAM EQUALIZATION
        meni.menuFiltersHistogrameq.addActionListener (
        	new ParameterlessFilterListener(ParameterlessFilter.HISTOGRAM_EQ, frame)
        );
		//FILTER SMART BINARIZE
        meni.menuFiltersSmartbin.addActionListener (
        	new ParameterlessFilterListener(ParameterlessFilter.SMART_BIN, frame)
        );
        
        
        /*
         * FILTERS WITH ONE PARAMETER, DOUBLE
         */
        //FILTER CONTRAST
        meni.menuFiltersContrast.addActionListener (
        	new SingleParameterFilterListener(SingleParameterFilter.CONTRAST, frame)
        );
        //FILTER GAMMA
        meni.menuFiltersGamma.addActionListener (
       		new SingleParameterFilterListener(SingleParameterFilter.GAMMA, frame)
        );
        //FILTER SATURATIOM
        meni.menuFiltersSaturation.addActionListener (
       		new SingleParameterFilterListener(SingleParameterFilter.SATURATION, frame)
        );  
        //FILTER BRIGHTNESS
        meni.menuFiltersBrightness1.addActionListener (
       		new SingleParameterFilterListener(SingleParameterFilter.BRIGHTNESS, frame)
        ); 
        //FILTER THRESHOLDING 
        meni.menuFiltersThresholding.addActionListener (
        	new SingleParameterFilterListener(SingleParameterFilter.THRESHOLDING, frame)
        );
        //FILTER BIT-PLANE
        meni.menuFiltersBitplane.addActionListener (
        	new SingleParameterFilterListener(SingleParameterFilter.BIT_PLANE, frame)
        ); 
        
        
        /*
         * FILTERS WITH MULTIPLE PARAMETERS, INT
         */
		//FILTER HISTOGRAM STRETCHING 
        meni.menuFiltersHistogramst.addActionListener (
        	new MultipleParameterFilterListener(MultipleParameterFilter.HISTOGRAM_STRETCHING, frame)	
        ); 
        //FILTER COLOR BALANCE
        meni.menuFiltersColors.addActionListener (
        	new MultipleParameterFilterListener(MultipleParameterFilter.COLOR_BALANCE, frame)	
        );
        
        
        /*
         * LOGIC AND ARITHMETIC OPERATIONS
         */
        //LOGICOP NOT
        meni.menuLogicopNot.addActionListener (
        	new ParameterlessFilterListener(ParameterlessFilter.NEGATIV, frame)
        ); 
        //LOGICOP AND
        meni.menuLogicopAnd.addActionListener (
        	new OperationDialogListener(Operation.AND, frame)
        ); 
        //LOGICOP OR 
        meni.menuLogicopOr.addActionListener (
        	new OperationDialogListener(Operation.OR, frame)
        );
        //LOGICOP XOR
        meni.menuLogicopXor.addActionListener (
        	new OperationDialogListener(Operation.XOR, frame)
        );
        //ARITHMETICOP ADDITION
        meni.menuArithmeticopAddition.addActionListener (      
        	new OperationDialogListener(Operation.ADDITION, frame)
        );
        //ARITHMETICOP SUBTRACTION
        meni.menuArithmeticopSubtraction.addActionListener (
        	new OperationDialogListener(Operation.SUBTRACTION, frame)
        );
        //ARITHMETICOP MULTIPLICATION
        meni.menuArithmeticopMultiplication.addActionListener (
        	new OperationDialogListener(Operation.MULTIPLICATION, frame)
        );
        //ARITHMETICOP DIVISION
        meni.menuArithmeticopDivision.addActionListener (
        	new OperationDialogListener(Operation.DIVISION, frame)
        );
        
        
        /*
         * WINDOW 
         */
        // TILE ALL
        meni.menuWindowTileall.addActionListener (
        	new TileListener(frame)
		);
        
        // CLOSE ALL
        meni.menuWindowCloseall.addActionListener (
			new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String ObjButtons[] = { "Yes", "No" };
            		int PromptResult = JOptionPane.showOptionDialog(frame,
            				"Are you sure you want to close all windows?", "",
            				JOptionPane.DEFAULT_OPTION,
            				JOptionPane.WARNING_MESSAGE, null, ObjButtons,
            				ObjButtons[1]);
            		if (PromptResult == JOptionPane.YES_OPTION) {
            			frame.closeAllFrames();
            			frame.vmu.refreshItems(frame);
            		}
                }
            }
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
    	
	}
}
