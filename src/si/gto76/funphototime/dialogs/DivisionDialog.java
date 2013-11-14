package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class DivisionDialog extends OperationDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1521758606230922389L;

	public DivisionDialog( JDesktopPane desktop ) {
		super( desktop, "Division", "  /  ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.division(imgIn1, imgIn2);
	}

}
