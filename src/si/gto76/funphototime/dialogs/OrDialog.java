package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class OrDialog extends OperationDialog {
	
	private static final long serialVersionUID = 6056902344950844669L;

	public OrDialog( JDesktopPane desktop ) {
		super( desktop, "Or", " OR ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.or(imgIn1, imgIn2);
	}

}
