package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class MultiplicationDialog extends OperationDialog {
	
	public MultiplicationDialog( JDesktopPane desktop ) {
		super( desktop, "Multiplication", " x ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filtri.arithmeticOperation(imgIn1, imgIn2, 3);
	}

}
