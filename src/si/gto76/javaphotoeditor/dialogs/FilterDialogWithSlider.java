package si.gto76.javaphotoeditor.dialogs;

import javax.swing.JSlider;

import si.gto76.javaphotoeditor.MyInternalFrame;


abstract class FilterDialogWithSlider extends FilterDialog  {
	
	protected JSlider sld;
	
	public FilterDialogWithSlider( MyInternalFrame selectedFrame, String title ) {
		super(selectedFrame, title);
		
		sld = new JSlider(JSlider.HORIZONTAL,
        						-100, 100, 0);
        sld.setMajorTickSpacing(100);
		sld.setMinorTickSpacing(10);
		sld.setPaintTicks(true);
		sld.setPaintLabels(true);
		
		sld.addChangeListener(this);
		super.addComponent(sld);
		//sld.fireStateChanged();
	}

}
