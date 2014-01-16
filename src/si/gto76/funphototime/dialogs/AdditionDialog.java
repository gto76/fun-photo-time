package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class AdditionDialog extends OperationDialog {
	
	public AdditionDialog( JDesktopPane desktop ) {
		super( desktop, "Addition", " + ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.addition(imgIn1, imgIn2);
	}

}
