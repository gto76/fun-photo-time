package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.dialogs.FilterDialogWithSliderDouble;
import si.gto76.funphototime.enums.Filter;

public class FilterDialogWithSliderDoubleListener implements ActionListener{
	FunPhotoTimeFrame mainFrame;
	Filter fi;

	public FilterDialogWithSliderDoubleListener(Filter fi, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.fi = fi;
    }
	
	public void actionPerformed(ActionEvent e) {
    	MyInternalFrame frameIn = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
    	FilterDialogWithSliderDouble dialog = fi.getDialog(frameIn); //right CAST
    	
    	if (!dialog.wasCanceled()) {
    		double values = dialog.getValues(); //right VALUE TYPE
    		MyInternalFrame frameOut = mainFrame.createZoomedFrame(dialog.getProcessedImage(), frameIn);
    		fi.createThread(frameIn.getOriginalImg(), values , frameOut); //right THREAD
    	}
    	dialog.resetOriginalImage();
    }
	
}
