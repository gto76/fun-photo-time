package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Operation;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.dialogs.OperationDialog;

public class OperationDialogListener implements ActionListener {
	FunPhotoTimeFrame mainFrame;
	Operation op;
	
	public OperationDialogListener(Operation op, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.op = op;
	}
	
	public void actionPerformed(ActionEvent e) {
    	OperationDialog dialog = (OperationDialog) op.getDialog(mainFrame.desktop);
    	if (!dialog.wasCanceled()) {
    		MyInternalFrame frame = mainFrame.createFrame(dialog.getProcessedOrigImage(), dialog.getName());
    		frame.zoom(dialog.getZoom());
        }
    }
}
