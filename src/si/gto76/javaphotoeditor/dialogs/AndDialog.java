package si.gto76.javaphotoeditor.dialogs;

import java.awt.image.BufferedImage;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.Filtri;


public class AndDialog extends OperationDialog {
	
	public AndDialog( JDesktopPane desktop ) {
		super( desktop, "And", " AND ");
	}
	
	protected BufferedImage operation(BufferedImage imgIn1, BufferedImage imgOn2) {
		return Filtri.logicOperation(imgIn1, imgIn2, 1); //TODO: WTF!??????
	}

}
