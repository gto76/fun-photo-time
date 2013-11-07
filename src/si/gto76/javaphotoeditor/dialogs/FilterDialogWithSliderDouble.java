package si.gto76.javaphotoeditor.dialogs;

import si.gto76.javaphotoeditor.MyInternalFrame;

public abstract class FilterDialogWithSliderDouble extends FilterDialogWithSlider  {
	public FilterDialogWithSliderDouble( MyInternalFrame selectedFrame, String title ) {
		super(selectedFrame, title);
	}
	
	public abstract double getValues();
}
