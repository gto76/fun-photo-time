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
import si.gto76.funphototime.ImagePreviewPanel;

public class LoadListener implements ActionListener {
	
	FunPhotoTimeFrame frame;
	public LoadListener(FunPhotoTimeFrame frame) {
		this.frame = frame;
	}
	
    public void actionPerformed(ActionEvent e) {
    	try {
	    	JFileChooser chooser = new JFileChooser();
	    	
	    	ImagePreviewPanel preview = new ImagePreviewPanel();
	    	chooser.setAccessory(preview);
	    	chooser.addPropertyChangeListener(preview);
	    	
	    	chooser.setMultiSelectionEnabled(true);
	    	// adds file filters
	    	for (ExtensionFileFilter filter : ExtensionFileFilter.all) {
				chooser.addChoosableFileFilter(filter);
			}
	    	if (frame.lastPathLoad != null) {
	    		chooser.setCurrentDirectory(frame.lastPathLoad);
	    	}
	    	int returnVal = chooser.showOpenDialog(frame);
	    	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    		try {
	    			for (File fIn : chooser.getSelectedFiles()) {
	    				// TODO memory footprint check
	    				BufferedImage imgIn = ImageIO.read(fIn);
						frame.createFrame(imgIn, fIn.getName());
	    			}
				} catch (IOException f) {}
				frame.lastPathLoad = chooser.getSelectedFile();
		    }
    	} catch (OutOfMemoryError g) {
    		frame.outOfMemoryMessage();
    	}
	}

}
