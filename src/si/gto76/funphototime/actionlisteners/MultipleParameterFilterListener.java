package si.gto76.funphototime.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.dialogs.FilterDialog;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsDouble;
import si.gto76.funphototime.dialogs.FilterDialogThatReturnsInts;
import si.gto76.funphototime.dialogs.FilterDialogWithSliderDouble;
import si.gto76.funphototime.enums.SingleParameterFilter;
import si.gto76.funphototime.enums.MultipleParameterFilter;

public class MultipleParameterFilterListener extends AbstractParameterFilterListener
												implements ActionListener {
	
	MultipleParameterFilter filter;
	int[] values;
	
	public MultipleParameterFilterListener(MultipleParameterFilter filter, FunPhotoTimeFrame mainFrame) {
		super(mainFrame);
		this.filter = filter;
	}

	@Override
	protected FilterDialog getDialog(MyInternalFrame frameIn) {
		return (FilterDialog) filter.getDialog(frameIn);
	}

	@Override
	protected void storeValues(FilterDialog dialog) {
		values = ((FilterDialogThatReturnsInts)dialog).getInts();
	}

	@Override
	protected Thread createThread(MyInternalFrame frameIn, MyInternalFrame frameOut) {
		return new Thread(new WaitingThread(frameIn, frameOut));
	}

	@Override
	protected void createFilterThread(BufferedImage imgIn, MyInternalFrame frameOut) {
		filter.createThread(imgIn, values , frameOut);
	}
	
}

