package si.gto76.javaphotoeditor.enums;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.dialogs.Brightness1Dialog;
import si.gto76.javaphotoeditor.dialogs.ContrastDialog;
import si.gto76.javaphotoeditor.dialogs.FilterDialogWithSliderDouble;
import si.gto76.javaphotoeditor.dialogs.GammaDialog;
import si.gto76.javaphotoeditor.dialogs.SaturationDialog;

public enum ZoomOperation {
	ZOOM_OUT,
	ZOOM_IN,
	ZOOM_6,
	ZOOM_12,
	ZOOM_25,
	ZOOM_50,
	ZOOM_66,
	ZOOM_ACTUAL;

	public void apply(MyInternalFrame frame) {
		switch (this) {
	    	case ZOOM_OUT:
	    		frame.zoomOut(10);
	    		break;
	    	case ZOOM_IN:
	    		frame.zoomIn(10);
	    		break;
	    	case ZOOM_6:
	    		frame.zoom(6);
	    		break;
	    	case ZOOM_12:
	    		frame.zoom(12);
	    		break;
	    	case ZOOM_25:
	    		frame.zoom(25);
	    		break;
	    	case ZOOM_50:
	    		frame.zoom(50);
	    		break;
	    	case ZOOM_66:
	    		frame.zoom(66);
	    		break;
	    	case ZOOM_ACTUAL:
	    		frame.actualSize();
	    		break;
	    	default:
	    		throw new ExceptionInInitializerError() {
	    		};
		}
    }
}