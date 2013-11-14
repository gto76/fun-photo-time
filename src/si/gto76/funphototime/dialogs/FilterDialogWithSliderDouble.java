package si.gto76.funphototime.dialogs;

import si.gto76.funphototime.MyInternalFrame;

public abstract class FilterDialogWithSliderDouble extends FilterDialogWithSlider  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2460984653113678856L;

	public FilterDialogWithSliderDouble( MyInternalFrame selectedFrame, String title ) {
		super(selectedFrame, title);
	}
	
	public abstract double getValues();
}
