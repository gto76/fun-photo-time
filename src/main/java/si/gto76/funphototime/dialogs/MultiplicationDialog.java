package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class MultiplicationDialog extends OperationDialog {
	
	public MultiplicationDialog( JDesktopPane desktop ) {
		super( desktop, "Multiplication", "  x  ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.multiplication(imgIn1, imgIn2);
	}

}
