package si.gto76.javaphotoeditor;
/*
 * @(#)JavaPhotoEditor.java
 *
 * Aplication for manipulating bitmaps.
 *
 * @author Jure Sorn
 * @version 1.00 08/02/21
 */

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JavaPhotoEditor {

	private static final boolean NATIVE_LOOK_AND_FEEL = false;
	private static final int MAIN_WINDOW_WIDTH = 800;
	private static final int MAIN_WINDOW_HEIGHT = 550;

	public static void main(String[] args) {
		// Try setting native look and feel
		if (NATIVE_LOOK_AND_FEEL) {
			setNativeLookAndFeel();
		}
    	//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
    	// Create application frame.
    	JFrame.setDefaultLookAndFeelDecorated(true);
        JavaPhotoEditorFrame frame = new JavaPhotoEditorFrame();
        // Show frame.
        frame.setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        //frame.setBackground(Color.BLACK);
        //frame.set
        frame.setVisible(true);
    }
    
    private static void setNativeLookAndFeel() {
    	try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    }
	    catch (ClassNotFoundException e) {
	    }
	    catch (InstantiationException e) {
	    }
	    catch (IllegalAccessException e) {
	    }
    }
    
}
