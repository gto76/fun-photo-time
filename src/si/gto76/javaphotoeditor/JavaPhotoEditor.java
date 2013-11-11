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

	public static void main(String[] args) {
		// Try setting native look and feel
		if (Conf.NATIVE_LOOK_AND_FEEL) {
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
        frame.setSize(Conf.MAIN_WINDOW_WIDTH, Conf.MAIN_WINDOW_HEIGHT);
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
