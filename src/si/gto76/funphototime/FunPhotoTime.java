package si.gto76.funphototime;
/*
 * @(#)FunPhotoTime.java
 *
 * Aplication for manipulating bitmaps.
 *
 * @author Jure Šorn <sornjure@hotmail.com>
 * @version 0.9.1 13/11/15
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FunPhotoTime {

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
        final FunPhotoTimeFrame frame = new FunPhotoTimeFrame();
        // Show frame.
        frame.setSize(Conf.MAIN_WINDOW_WIDTH, Conf.MAIN_WINDOW_HEIGHT);
        // Set diferent icons, depending on main window focus.
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
              frame.setIconImages(FunPhotoTimeFrame.iconsActive);
            }
            public void windowLostFocus(WindowEvent e) {
              frame.setIconImages(FunPhotoTimeFrame.iconsNotActive);
            }
        });
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
