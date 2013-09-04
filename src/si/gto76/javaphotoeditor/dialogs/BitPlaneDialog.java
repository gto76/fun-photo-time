package si.gto76.javaphotoeditor.dialogs;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import si.gto76.javaphotoeditor.MyInternalFrame;
import si.gto76.javaphotoeditor.filterthreads.BitPlaneThread;


public class BitPlaneDialog extends FilterDialog  {
	
	private JSpinner spinner;
	
	public BitPlaneDialog( MyInternalFrame selectedFrame ) {
		super(selectedFrame, "Bit Plane");
		
		SpinnerModel model =
        new SpinnerNumberModel(1, 1, 8, 1);
		spinner = new JSpinner(model);
		
		spinner.addChangeListener(this);
		addComponent(spinner);
	}
	
	public void stateChanged(ChangeEvent e)  {
		//ko se slider premakne prvo pogleda ce ze obstaja
		//kaksna nit in jo prekine
		if ( filterThread != null ) {
			filterThread.t.interrupt();
		}
		//nato naredi novo nit
		filterThread = new BitPlaneThread(imgIn, imgOut, getValues(), selectedFrame);
	}
    
    public int getValues() {
    	return (Integer) spinner.getValue();
    }

}

/*
//Tko je zgledal ko je biu dialog se metoda in je bil del grafika2frama

protected int BitPlaneDialog() throws CancelException{
    	JPanel p = new JPanel();
    	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
    	SpinnerModel model =
        new SpinnerNumberModel(1, 1, 8, 1);
    	JSpinner spinner = new JSpinner(model);
    	p.add(spinner);
    	
    	final JOptionPane op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.OK_CANCEL_OPTION);
    	JDialog dlg = op.createDialog(this, "Bit-Plane Slicing");
    	dlg.setVisible(true);
    	dlg.dispose();
    	
    	//pogleda s kerim gumbom je uporabnik zapri dialog
    	//in ce je bil to cancel vrne CancelException
    	int option = ((Integer)op.getValue()).intValue();
		if (option == JOptionPane.CANCEL_OPTION) {
		    throw new CancelException();
		} 
    	
    	Integer value = (Integer) spinner.getValue();
    	//System.out.println(value);
    	return ((int) value);
    }
*/
