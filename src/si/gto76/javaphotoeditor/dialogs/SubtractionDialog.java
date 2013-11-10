package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class SubtractionDialog extends OperationDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5494634537687950430L;

	public SubtractionDialog( JDesktopPane desktop ) {
		super( desktop, "Subtraction", " - ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filtri.subtraction(imgIn1, imgIn2);
	}

}
