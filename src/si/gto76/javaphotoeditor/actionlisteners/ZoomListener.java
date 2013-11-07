package si.gto76.javaphotoeditor.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.enums.ZoomOperation;

public class ZoomListener implements ActionListener {
	JDesktopPane desktop;
	ZoomOperation zo;
	public ZoomListener(ZoomOperation zo, JDesktopPane desktop) {
		this.desktop = desktop;
		this.zo = zo;
	}

	public void actionPerformed(ActionEvent e) {
		MyInternalFrame frame = (MyInternalFrame) desktop.getSelectedFrame();
		zo.apply(frame);
	}
}
