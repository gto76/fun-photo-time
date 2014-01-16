package si.gto76.funphototime.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.filterthreads.ColorsThread;

public class ColorsDialog extends FilterDialog {
	// R, G and B sliders
	protected JSlider[] sld = new JSlider[3];
	
	public ColorsDialog( MyInternalFrame selectedFrame) {
		super(selectedFrame, "RGB", BoxLayout.Y_AXIS, 300, 205);
		
		for (int i = 0; i < 3; i++) {
			sld[i] = new JSlider(JSlider.HORIZONTAL,
	        						-100, 100, 0);
	        sld[i].setMajorTickSpacing(100);
			sld[i].setMinorTickSpacing(10);
			sld[i].setPaintTicks(true);
			sld[i].setPaintLabels(true);
			
			sld[i].addChangeListener(this);
			//super.addComponent(sld[i]);
			//sld[i].fireStateChanged();
		}
		super.addComponents(sld);
	}

	public void stateChanged(ChangeEvent e)  {
		actionWhenStateChanged();
	}

	protected void actionWhenStateChanged() {
		stopActiveThread();
		filterThread = new ColorsThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public int[] getValues() {
    	int[] values = new int[3];
    	for (int i = 0; i < 3; i++) {
    		values[i] = sld[i].getValue(); 
    	}
    	return values;
	}
    
}

