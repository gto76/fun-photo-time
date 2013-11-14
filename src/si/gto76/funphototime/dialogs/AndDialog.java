package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class AndDialog extends OperationDialog {
	
	private static final long serialVersionUID = -4848154008464104710L;

	public AndDialog( JDesktopPane desktop ) {
		super( desktop, "And", " AND ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.and(imgIn1, imgIn2); 
	}

}
