package si.gto76.funphototime.enums;

import si.gto76.funphototime.MyInternalFrame;

public enum ZoomOperation {
	ZOOM_OUT,
	ZOOM_IN,
	ZOOM_6,
	ZOOM_12,
	ZOOM_25,
	ZOOM_50,
	ZOOM_66,
	ZOOM_ACTUAL;
	
	public int newZoom(MyInternalFrame frame) {
		switch (this) {
	    	case ZOOM_OUT:
	    		int newZoomOut = frame.getZoom() + 10;
	    		if (newZoomOut >= 100)
	    			return 100;
	    		else 
	    			return newZoomOut;
	    	case ZOOM_IN:
	    		int newZoomIn = frame.getZoom() - 10;
	    		if (newZoomIn < 1)
	    			return 1;
	    		else 
	    			return newZoomIn;
	    	case ZOOM_6:
	    		return 6;
	    	case ZOOM_12:
	    		return 12;
	    	case ZOOM_25:
	    		return 25;
	    	case ZOOM_50:
	    		return 50;
	    	case ZOOM_66:
	    		return 66;
	    	case ZOOM_ACTUAL:
	    		return 100;
	    	default:
	    		throw new ExceptionInInitializerError() {
					private static final long serialVersionUID = 4033579193896323312L;
	    		};
		}
	}

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
					private static final long serialVersionUID = 4033579193896323312L;
	    		};
		}
    }
}
