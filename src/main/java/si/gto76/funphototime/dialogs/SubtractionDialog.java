package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class SubtractionDialog extends OperationDialog {
	
	public SubtractionDialog( JDesktopPane desktop ) {
		super( desktop, "Subtraction", "  -  ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.subtraction(imgIn1, imgIn2);
	}

}
