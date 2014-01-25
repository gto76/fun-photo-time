package si.gto76.funphototime;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class FunPhotoTime {
	
	static public FunPhotoTimeFrame frame;

	public static void main(String[] args) {
		// Try setting native look and feel if enabled.
		if (Conf.NATIVE_LOOK_AND_FEEL) {
			setNativeLookAndFeel();
		}
		Boolean check = LicenceUtil.check();
		System.out.println("License check: " + check);
		createAndShowGUI();
    }

	public static void createAndShowGUI() {
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	// Create application frame.
    	frame = new FunPhotoTimeFrame();
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
        // What to do on exit.
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
        		onWindowClose();
			}
		});
        // Show frame.
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
		int PromptResult = JOptionPane.showOptionDialog(frame,
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
