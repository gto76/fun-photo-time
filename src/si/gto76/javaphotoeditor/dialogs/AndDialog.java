package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class AndDialog extends OperationDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4848154008464104710L;

	public AndDialog( JDesktopPane desktop ) {
		super( desktop, "And", " AND ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filtri.and(imgIn1, imgIn2); 
	}

}
