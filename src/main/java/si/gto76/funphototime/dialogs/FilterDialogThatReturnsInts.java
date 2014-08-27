package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

public interface FilterDialogThatReturnsInts {
	public int[] getInts();
	public boolean wasCanceled();
	public BufferedImage getProcessedImage();
	public void resetOriginalImage();
}