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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class FunPhotoTime {
	static public FunPhotoTimeFrame frame;

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
    
    public static void createAndShowGUI() {
    	// Create application frame.
    	JFrame.setDefaultLookAndFeelDecorated(true);
        /*final FunPhotoTimeFrame*/ frame = new FunPhotoTimeFrame();
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
        
        // Exit dialog
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
        	@Override
			public void windowClosing(WindowEvent we) {
        		onWindowClose();
			}
		});
        
        frame.setVisible(true);
    }
    
    public static void onWindowClose() {
        int noOfFrames = frame.desktop.getComponentCount();
    	if (noOfFrames > 0)
    		confirmExit();
    	else
    		System.exit(0);
    }
    
    private static void confirmExit() {
    	String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null,
				"Are you sure you want to exit?", "",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null, ObjButtons,
				ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
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
