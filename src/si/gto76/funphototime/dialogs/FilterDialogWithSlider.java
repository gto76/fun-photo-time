package si.gto76.funphototime.dialogs;

import javax.swing.JSlider;

import si.gto76.funphototime.MyInternalFrame;


public abstract class FilterDialogWithSlider extends FilterDialog  {
	
	private static final long serialVersionUID = -7976235939424336985L;
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
	}

}
