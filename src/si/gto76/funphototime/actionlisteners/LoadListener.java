package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import si.gto76.funphototime.ExtensionFileFilter;
import si.gto76.funphototime.FunPhotoTimeFrame;

public class LoadListener implements ActionListener {
	
	FunPhotoTimeFrame frame;
	public LoadListener(FunPhotoTimeFrame frame) {
		this.frame = frame;
	}
	
    public void actionPerformed(ActionEvent e) {
    	JFileChooser fc = new JFileChooser();
    	fc.setMultiSelectionEnabled(true);
    	// adds file filters
    	for (ExtensionFileFilter filter : ExtensionFileFilter.all) {
			fc.addChoosableFileFilter(filter);
		}
    	if (frame.lastPath != null) {
    		fc.setCurrentDirectory(frame.lastPath);
    	}
    	int returnVal = fc.showOpenDialog(frame);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		try {
    			for (File fIn : fc.getSelectedFiles()) {
    				BufferedImage imgIn = ImageIO.read(fIn);
					frame.createFrame(imgIn, fIn.getName());
    			}
			} catch (IOException f) {
			}
			frame.lastPath = fc.getSelectedFile();
	    }
	}

}
