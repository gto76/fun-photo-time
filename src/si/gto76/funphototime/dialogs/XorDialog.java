package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.Filters;


public class XorDialog extends OperationDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3881568387038110320L;

	public XorDialog( JDesktopPane desktop ) {
		super( desktop, "Xor", " XOR ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filters.xor(imgIn1, imgIn2);
	}

}
