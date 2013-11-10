package si.gto76.javaphotoeditor.dialogs;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class HelpDialog extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4236082773760097536L;
	protected JPanel p;
	protected JDialog dlg;
	protected JOptionPane op;

    public HelpDialog() throws URISyntaxException {
    	
    	final URI uri = new URI("http://www.yahoo.com");
        class OpenUrlAction implements ActionListener {
          @Override public void actionPerformed(ActionEvent e) {
            open(uri);
          }
        }
        JButton button = new JButton();
        button.setText("<HTML><FONT color=\"#000099\"><U>homepage</U></FONT></HTML>");
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setBackground(Color.WHITE);
        button.setToolTipText(uri.toString());
        button.addActionListener(new OpenUrlAction());
    	
    	//////////////
        
		p = new JPanel();
    	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    	
    	op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.CLOSED_OPTION);
    	dlg = op.createDialog(this, "Photo Fun Time");
    	JLabel lbl;
    	/*
    	lbl = new JLabel("Photo Fun Time");
    	p.add(lbl); */
    	p.add(new JLabel(" "));
    	lbl = new JLabel("Version: 0.9.0");
    	p.add(lbl);
    	p.add(new JLabel(" "));
    	lbl = new JLabel("Author: Jure Šorn");
    	p.add(lbl);
    	p.add(new JLabel(" "));
    	//p.add(button);
    	
    	dlg.setSize(240, 160);
    	dlg.setVisible(true);
    	dlg.dispose();
    	
	}
	  
	private static void open(URI uri) {
	    if (Desktop.isDesktopSupported()) {
	    	try {
	    		Desktop.getDesktop().browse(uri);
	    	} catch (IOException e) {
	    	}
	    }
	}

}
