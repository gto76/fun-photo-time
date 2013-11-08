package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class OrDialog extends OperationDialog {
	
	public OrDialog( JDesktopPane desktop ) {
		super( desktop, "Or", " OR ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgIn2) {
		return Filtri.logicOperation(imgIn1, imgIn2, 2);
	}

}
