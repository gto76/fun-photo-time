package si.gto76.funphototime.actionlisteners;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;

import si.gto76.funphototime.FunPhotoTimeFrame;
import si.gto76.funphototime.MyInternalFrame;
import si.gto76.funphototime.Utility;
import si.gto76.funphototime.Zoom;

public class TileListener implements ActionListener {
	
	FunPhotoTimeFrame mainFrame;
	public TileListener(FunPhotoTimeFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void actionPerformed(ActionEvent arg0) {
		HorVerCombo maxCombo = getMaxCombo(mainFrame);
		tileFrames(mainFrame, maxCombo.combo, maxCombo.zoom);
	}
	
	////////////////////////

	/*
	 * GET BEST HORIZONTAL/VERTICAL COMBINATION
	 */
	public static HorVerCombo getMaxCombo(FunPhotoTimeFrame mainFrame) {
		MyInternalFrame[] frames = mainFrame.getAllFrames();
		Dimension desktopSize = mainFrame.desktop.getSize();
		Point[] combos = getAllHorVerCombos(frames.length);
		HorVerCombo maxCombo = new HorVerCombo(null, 0, 100);

		for (Point combo : combos) {
			Dimension cellSize = getCellSize(combo, desktopSize); 
			int commonZoom = getCommonMaxZoom(frames, cellSize);
			int comboSurface = getSurfaceSum(frames, commonZoom);
			
			if (comboSurface > maxCombo.surface) {
				maxCombo = new HorVerCombo(combo, comboSurface, commonZoom);
			}
		}
		return maxCombo;
	}
	
	////////////////////////

	/*
	 * GET ALL HORIZONTAL/VERTICAL COMBINATIONS
	 * 
	 * Returns all combinations of columns/rows, that could potentialy maximize
	 * sum of surfacess. For noOfFrames = 9 it returns: (1,9), (2,5), (3,3), (5,2), (9,1)  
	 */
	public static Point[] getAllHorVerCombos(int noOfFrames) {
		List<Point> combos = new ArrayList<Point>();
		int lastY = 0;
		for (int x = 1; x <= noOfFrames; x++) {
			int y = noOfFrames / x;
			int reminder = noOfFrames % x;
			if (reminder > 0)
				y += 1;
			if (lastY == y)
				continue;
			lastY = y;
			combos.add(new Point(x, y));
		}
		return combos.toArray(new Point[0]);
	}

	/////////////////////
	
	/*
	 * GET COMMON MAXIMUM ZOOM
	 * 
	 * Returns the zoom that maximizes the sum of surfaces of internal frames.
	 * Zoom is restricted to values of Zoom.STEPS array. 
	 */
	public static int getCommonMaxZoom(MyInternalFrame[] frames, Dimension cellSize) {
		for (int zoom : Zoom.STEPS) {
			if (allFitInCell(frames, cellSize, zoom)) 
				return zoom;
		}
		return 0;
	}
	
	public static boolean allFitInCell(MyInternalFrame[] frames, Dimension cellSize, int zoom) {
		for (MyInternalFrame frame : frames) {
			if (!fitsInCell(frame, cellSize, zoom)) 
				return false;
		}
		return true;
	}

	public static boolean fitsInCell(MyInternalFrame frame, Dimension cellSize, int zoom) {
		Dimension imageSize = frame.getOriginalImageSize();
		imageSize.setSize(
			imageSize.width * (zoom / 100.0), 
			imageSize.height * (zoom / 100.0)
		);
		
		if (imageSize.width + MyInternalFrame.BORDER_WIDTH > cellSize.width)
			return false;
		if (imageSize.height + MyInternalFrame.BORDER_HEIGHT > cellSize.height)
			return false;
		
		return true;
	}
	
	/////////////////////

	/*
	 * GET SUM OF SURFACES
	 */
	public static int getSurfaceSum(MyInternalFrame[] frames, int zoom) {
		int sum = 0;
		for (MyInternalFrame frame : frames) {
			sum += getSurface(frame, zoom);
		}
		return sum;
	}
	
	public static int getSurface(MyInternalFrame frame, int zoom) {
		return (int) ((frame.getOriginalImageMemoryFootprint() / 3) 
				/ Utility.getSurfaceAreaFactorForZoom(zoom));
	}

	////////////////////////
	////////////////////////

	/*
	 * TILE FRAMES
	 */
	private static void tileFrames(FunPhotoTimeFrame mainFrame, Point combo, int zoom) {
		Dimension desktopSize = mainFrame.desktop.getSize();
		int slotNo = 0;
		for (MyInternalFrame frame : mainFrame.getAllFrames()) {
			frame.setZoom(zoom);
			Point slot = getSlot(combo.x, slotNo);
			Point location = getLocation(desktopSize, frame.getSize(), combo, slot);
			frame.setLocation(location);
			slotNo++;
		}
	}

	// Slots start from (0,0).
	private static Point getSlot(int width, int n) {
		return new Point(n % width, n / width);
	}

	private static Point getLocation(Dimension desktopSize, Dimension frameSize, Point combo,	Point slot) {
		Dimension cellSize = getCellSize(combo, desktopSize);
		Point upperLeftCornerOfCell = getUpperLeftCornerOfCell(cellSize, slot);
		Point locationWithinCell = getLocationWithinCell(frameSize, cellSize);		
		return new Point(
			upperLeftCornerOfCell.x + locationWithinCell.x,
			upperLeftCornerOfCell.y + locationWithinCell.y
		);
	}

	private static Point getUpperLeftCornerOfCell(Dimension cellSize, Point slot) {
		return new Point(
			slot.x * cellSize.width,
			slot.y * cellSize.height
		);
	}

	private static Point getLocationWithinCell(Dimension frameSize,
			Dimension cellSize) {
		return new Point(
			(cellSize.width - frameSize.width) / 2,
			(cellSize.height - frameSize.height) / 2
		);
	}

	////////////////////////
	////////////////////////
	
	/*
	 * COMMON
	 */
	
	public static Dimension getCellSize(Point combo, Dimension desktopSize) {
		return new Dimension( 
			desktopSize.width / combo.x, 
			desktopSize.height / combo.y 
		);
	}

}

class HorVerCombo {
	public final Point combo;
	public final int surface, zoom;
	public HorVerCombo(Point combo, int surface, int zoom) {
		this.combo = combo;
		this.surface = surface;
		this.zoom = zoom;
	}
}