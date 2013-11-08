package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class XorDialog extends OperationDialog {
	
	public XorDialog( JDesktopPane desktop ) {
		super( desktop, "Xor", " XOR ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filtri.logicOperation(imgIn1, imgIn2, 3);
	}

}
