package si.gto76.funphototime.dialogs;

import si.gto76.funphototime.MyInternalFrame;

public abstract class FilterDialogWithSliderDouble extends FilterDialogWithSlider implements FilterDialogThatReturnsDouble  {
	
	public FilterDialogWithSliderDouble( MyInternalFrame selectedFrame, String title ) {
		super(selectedFrame, title);
	}
	
	//public abstract double getDouble();
}
