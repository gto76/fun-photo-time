package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.enums.ZoomOperation;

public class ZoomListener implements ActionListener {
	FunPhotoTimeFrame mainFrame;
	ZoomOperation zo;
	public ZoomListener(ZoomOperation zo, FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.zo = zo;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			MyInternalFrame frame = (MyInternalFrame) mainFrame.desktop.getSelectedFrame();
			zo.apply(frame);
		} catch (OutOfMemoryError g) {
			mainFrame.outOfMemory();
		}
	}
}
