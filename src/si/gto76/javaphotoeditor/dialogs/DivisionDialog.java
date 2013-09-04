package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class DivisionDialog extends OperationDialog {
	
	public DivisionDialog( JDesktopPane desktop ) {
		super( desktop, "Division", " / ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgOn2) {
		return Filtri.arithmeticOperation(imgIn1, imgIn2, 4);
	}

}
