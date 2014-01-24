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
		MyInternalFrame[] frames = mainFrame.getAllFrames();
		int noOfFrames = frames.length;
		Dimension desktopSize = mainFrame.desktop.getSize();
		
		Point[] combos = getAllHorVerCombos(noOfFrames);
		
		Point maxCombo = null;
		int maxComboSurface = 0;
		int maxComboZoom = 100;
		
		for (Point combo : combos) {
			//System.out.println("-------------------");
			//System.out.println("Combo: " + combo);
			Dimension cellSize = getCellSize(combo, desktopSize); 
			int commonZoom = getCommonMaxZoom(frames, cellSize);
			//System.out.println("commonZoom: " + commonZoom);
			int comboSurface = getSurfaceSum(frames, commonZoom);
			//System.out.println("comboSurface: " + comboSurface);
			
			if (comboSurface > maxComboSurface) {
				maxCombo = combo;
				maxComboSurface = comboSurface;
				maxComboZoom = commonZoom;
			}
		}
		/*
		System.out.println("===============");
		System.out.println("maxCombo: " + maxCombo);
		System.out.println("maxComboSurface: " + maxComboSurface);
		System.out.println("maxComboZoom: " + maxComboZoom);
		System.out.println("===============");
		*/	
		tileFrames(mainFrame, maxCombo, maxComboZoom);
	}
	
	////////////////////////

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

	public static Dimension getCellSize(Point combo, Dimension desktopSize) {
		return new Dimension( 
			desktopSize.width / combo.x, 
			desktopSize.height / combo.y 
		);
	}
	
	/////////////////////
	
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
		final int INTERNAL_FRAME_FRAME_WIDTH = 10;
		final int INTERNAL_FRAME_FRAME_HEIGHT = 32;
		
		Dimension imageSize = frame.getOriginalImageSize();
		imageSize.setSize(
			imageSize.width * (zoom / 100.0), 
			imageSize.height * (zoom / 100.0)
		);
		if (imageSize.width + INTERNAL_FRAME_FRAME_WIDTH > cellSize.width)
			return false;
		if (imageSize.height + INTERNAL_FRAME_FRAME_HEIGHT > cellSize.height)
			return false;
		
		return true;
	}

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

	/*
	 * Slots start from (0,0).
	 */
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

}
