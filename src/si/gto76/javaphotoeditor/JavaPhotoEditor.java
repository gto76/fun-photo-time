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


public class JavaPhotoEditor {
	
	public static void main(String[] args) {
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
        frame.setSize(800, 550);
        frame.setVisible(true);
    }
    
}
