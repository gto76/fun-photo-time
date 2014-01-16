package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class DivisionDialog extends OperationDialog {
	
	public DivisionDialog( JDesktopPane desktop ) {
		super( desktop, "Division", "  /  ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.division(imgIn1, imgIn2);
	}

}
