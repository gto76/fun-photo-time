package si.gto76.javaphotoeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import si.gto76.javaphotoeditor.dialogs.OperationDialog;

public class OperationDialogListener implements ActionListener {
	JavaPhotoEditorFrame mainFrame;
	Operation op;
	
	public OperationDialogListener(Operation op, JavaPhotoEditorFrame mainFrame) {//, JDesktopPane desktop) { //, si.gto76.javaphotoeditor.OperationDialogFactory) {
		this.mainFrame = mainFrame;
		this.op = op;
	}
	
	public void actionPerformed(ActionEvent e) {
    	OperationDialog dialog = (OperationDialog) op.getDialog(mainFrame.desktop);//new AdditionDialog(desktop);
    	if (!dialog.wasCanceled()) {
    		MyInternalFrame frame = mainFrame.createFrame(dialog.getProcessedOrigImage());
    		frame.zoom(dialog.getZoom());
        }
    }
}
