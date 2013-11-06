package si.gto76.javaphotoeditor.dialogs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.Utility;
import si.gto76.javaphotoeditor.filterthreads.FilterThread;
import si.gto76.javaphotoeditor.filterthreads.HistogramStretchingThread;
import si.gto76.javaphotoeditor.filterthreads.ThresholdingThread;


public class HistogramStretchingDialog extends JFrame 
								implements ChangeListener  {
     
 	 protected BufferedImage imgIn, imgOut;
 	 protected MyInternalFrame selectedFrame;
 	 protected FilterThread filterThread;
     
     private BufferedImage histogramImg1, histogramImg2;
     private JPanel p;
     private ImageIcon icon;
     private JLabel label;
     private JSlider sld1, sld2;
     private JOptionPane op;
     private JDialog dlg;
     
     public HistogramStretchingDialog( MyInternalFrame selectedFrame, BufferedImage histogramImg ) {
     	
 		this.selectedFrame = selectedFrame;
 		imgIn = selectedFrame.getImg();
 		imgOut = Utility.declareNewBufferedImageAndCopy(imgIn); //naredi kopijo
 		selectedFrame.setImg(imgOut); //na kero usmeri kazalec od frejma
    	 
     	histogramImg1 = histogramImg;
        histogramImg2 = Utility.declareNewBufferedImageAndCopy(histogramImg1);
    	
    	p = new JPanel();
    	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    	
        icon = new ImageIcon((Image)histogramImg2, "description");
        
        label = new JLabel(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
		p.add(label);
         
        sld1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        sld1.addChangeListener(this);
        p.add(sld1);
        
        sld2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        sld2.addChangeListener(this);
        p.add(sld2);
        
        op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.OK_CANCEL_OPTION);
    	
    	dlg = op.createDialog(this, "Histogram");
    	dlg.setSize(300,500);
    	dlg.setVisible(true);
    	dlg.dispose();
    	
    }
    
    public int[] getValues() {
    	//Vrne vrednosti slajderjev
    	int[] value = new int[2];
    	value[0] = sld1.getValue();
    	value[1] = sld2.getValue();
    	return value;
    }
    
    public boolean wasCanceled() {
    	//pogleda ce je uporabnik zaprl dialog z cancel gumbom
    	int option = ((Integer)op.getValue()).intValue();
		if (option == JOptionPane.CANCEL_OPTION) return true;
		else return false;
    }
    
    public void stateChanged(ChangeEvent e) {
    	processPicture(); 	
    	// Poslusa slajderja in sproti izrisuje pomozne crte na histogram
    	//Kako zdej od tle spreminjat ikono v labelu v dialogu?
        histogramImg2 = Utility.declareNewBufferedImageAndCopy(histogramImg1);
        
        int val1 = sld1.getValue();
        int val2 = sld2.getValue();
        
        //Da se ne morta prekrizat
        if ( val1 >= val2 ) {
        	sld1.setValue(val2);
        }
        if ( val2 <= val1 ) {
        	sld2.setValue(val1);
        }
        
		Graphics2D g2 = histogramImg2.createGraphics();
    	final Color black = Color.black;
    	final int height = 400;
    	int height2 = 0;
    	
    	g2.setPaint(black);
    	
    	//da rise crtkani crti
    	final float dash1[] = {10.0f};
	    final BasicStroke dashed = new BasicStroke(1.0f, 
	                                          BasicStroke.CAP_BUTT, 
	                                          BasicStroke.JOIN_MITER, 
	                                          10.0f, dash1, 0.0f);
		g2.setStroke(dashed);
		
    	g2.draw(new Line2D.Double(val1, height, val1, height2));
    	g2.draw(new Line2D.Double(val2, height, val2, height2));
		
		icon.setImage((Image) histogramImg2);
        
        dlg.repaint(100, 0, 0, 1000, 1000);
    
    } 
    
	protected void processPicture() {
		//ko se slider premakne prvo pogleda ce ze obstaja
		//kaksna nit in jo prekine
		//System.out.println("1");
		if ( filterThread != null ) {
			filterThread.t.interrupt();
			try {
				filterThread.t.join();
			}
			catch ( InterruptedException f ) {}
		}
		//System.out.println("2");
		//nato naredi novo nit
		filterThread = new HistogramStretchingThread(imgIn, imgOut, getValues(), selectedFrame);
		//System.out.println("3");	
	}
	
	public void resetOriginalImage() {
	    	//spremeni sliko iz izbranega okvirja v prvotno stanje
	    	//ce sploh obstaja kaksna nit jo ustavi ali pocaka
	    	if (filterThread != null) {
	    		try {
		    		//ce je bil izbran cancel avtomatsko prekine zadnjo nit,
		    		//drugace (ce je bil OK) jo pocaka da konca svoje delo
		    		//to verjetno niti ni nujno
		    		if ( wasCanceled() ) 
						filterThread.t.interrupt();
					filterThread.t.join();
				
				} 
				catch ( InterruptedException e ) {}
			}
			selectedFrame.setImg(imgIn);
	    }
		
	public BufferedImage getProcessedImage() {
			try {
				filterThread.t.join();
			} 
			catch ( InterruptedException e ) {}
			return imgOut;
	}
    
}
                                   		

