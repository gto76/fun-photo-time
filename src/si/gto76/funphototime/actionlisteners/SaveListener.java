package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import si.gto76.funphototime.ExtensionFileFilter;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.MyInternalFrame;

public class SaveListener implements ActionListener {
	
	FunPhotoTimeFrame frame;
	public SaveListener(FunPhotoTimeFrame frame) {
		this.frame = frame;
	}
	
	// TODO: 10 08_Longyearbyen_prn.JPG -> jpg ni bil odtranjen imenu
	// TODO 15 da tudi zadnji save folder shrani v last folder
    public void actionPerformed(ActionEvent e) {
    	if ( frame.desktop.getSelectedFrame() == null ) 
    		return;
		JFileChooser fc = new JFileChooser();
		String fileName = ((MyInternalFrame)frame.desktop.getSelectedFrame()).getFileName();
		// odstrani koncnico imenu
		int indexOfDot = fileName.indexOf(".");
		if (indexOfDot != -1)
			fileName = (String) fileName.subSequence(0, indexOfDot);
		if (frame.lastPath != null)
			fc.setSelectedFile(new File(frame.lastPath)); // TODO 10 save V tem primeru je treba odstranit ime in dodat ime izbranega frejma
		else
			fc.setSelectedFile(new File(fileName));
		fc.setDialogTitle("Save As");

		for (ExtensionFileFilter filter : ExtensionFileFilter.all) {
			fc.addChoosableFileFilter(filter);
		}
		fc.setFileFilter(ExtensionFileFilter.png);
		
		// Split function here //
		
    	int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	String formatName = "";
        	String errMessage = "";
        	File outputfile = fc.getSelectedFile();
        	final String givenName = outputfile.getName();
        	
        	// No filter chosen
        	if (fc.getFileFilter().getDescription() == "All Files") {
        		System.out.println(" No filter chosen ");
        		ExtensionFileFilter fileFilter = ExtensionFileFilter.getFilter(givenName);
        		// No extension given - ERR
        		if ( givenName.indexOf(".") == -1 ) {
        			errMessage = "No filename extension or file filter selected. Image was not saved.";
            	}
        		// No file filter matches the extension - ERR 
        		else if (fileFilter == null) {
        			errMessage = "Unknown filename extension. Image was not saved.";
        		}
        		// Valid extension - OK
        		else {
        			formatName = fileFilter.getDescription();
        		}
        	}
        	// Filter selected
        	else {
        		System.out.println("Filter selected");
            	ExtensionFileFilter selectedFilter = (ExtensionFileFilter) fc.getFileFilter();
            	// Filter and extension match - OK
            	if ( selectedFilter.accept(givenName) ) {
            		formatName = selectedFilter.getDescription();
            	}
            	// No extension given - OK
            	else if ( givenName.indexOf(".") == -1 ) {
            		//givenName = givenName.concat("." + selectedFilter.getDescription());
            		String newPathName = outputfile.getPath().concat("."+selectedFilter.getDescription() );
            		outputfile = new File(newPathName);
            		formatName = selectedFilter.getDescription();
            	}
            	// Extension does not match selected filter - ERR
            	else {
            		errMessage = "Filename extension and file filter did not match. Image was not saved.";
            	}
        	}
        	
			System.out.println("Ext: " + formatName);
			System.out.println("Name: " + outputfile.getName());
			
			if (formatName != "") {
            	try {
		        	ImageIO.write(frame.getSelectedBufferedImage(), formatName, outputfile);
		    	} catch (IOException f) {
		    		System.out.println("SAVE ERROR!");
		    	}
			} 
			else {
				// TODO 10 error dialog
				System.out.println(errMessage);
			}
	    }
    }
}

