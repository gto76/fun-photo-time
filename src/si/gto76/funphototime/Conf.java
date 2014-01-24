package si.gto76.funphototime;

import java.awt.Color;

public class Conf {
	public static final String VERSION = "0.9.3";
	// Main frame
	public static final boolean NATIVE_LOOK_AND_FEEL = false;
	public static final int MAIN_WINDOW_WIDTH = 800;
	public static final int MAIN_WINDOW_HEIGHT = 550;
	public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
	// Icons
	public static final String ICON_FILENAME_S = "/resources/icon-trans-16x16.png"; 
	public static final String ICON_FILENAME_S_BLUE = "/resources/icon-blue-16x16.png"; // b8cfe5 is color of background
	public static final String ICON_FILENAME_M = "/resources/icon-trans-32x32.png"; 
	public static final String ICON_FILENAME_L = "/resources/icon-trans-64x64.png"; 
	public static final String ICON_FILENAME_XL = "/resources/icon-trans-128x128.png"; 
	// Test image
	public static final boolean TEST_IMAGE = true;
	public static final String TEST_IMAGE_FILE_NAME = "/home/minerva/Pictures/Foto/Kuhna/DSC_0020.JPG";
	// Internal frame
	public static final int SCROLL_PANE_SPEED = 14;
	public static final int X_OFFSET = 30;
	public static final int Y_OFFSET = 30;
	// Histogram
	public static final int HISTOGRAM_VERTICAL_ZOOM = 60;
	// Dialogs
	//public static final int DIALOG_LOCATION_X = 200;
	//public static final int DIALOG_LOCATION_Y = 200;
	
	public static final long ORIGINAL_IMAGE_WAITING_INTERVAL_MSEC = 100;
	public static final long MEMORY_SAFE_MARGIN = 5000000;
	
	public static final boolean DEBUG_MEMORY = true;
}
