package si.gto76.funphototime.dialogs;

import java.awt.image.BufferedImage;

public interface FilterDialogThatReturnsDouble {
	public double getDouble();
	public boolean wasCanceled();
	public BufferedImage getProcessedImage();
	public void resetOriginalImage();
}
