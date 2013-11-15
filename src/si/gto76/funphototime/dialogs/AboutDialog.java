package si.gto76.funphototime.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import si.gto76.funphototime.Conf;


public class AboutDialog extends JFrame {

	private static final long serialVersionUID = 4236082773760097536L;
	protected JPanel p;
	protected JDialog dlg;
	protected JOptionPane op;

    public AboutDialog() throws URISyntaxException {
    	/* Buton to website
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
    	*/
    	//////////////
        
		p = new JPanel();
    	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    	op = new JOptionPane(p,
    		JOptionPane.PLAIN_MESSAGE,
    		JOptionPane.CLOSED_OPTION);
    	dlg = op.createDialog(this, "About");
    	JLabel lbl;

    	// Name
    	lbl = new JLabel("Fun Photo Time", SwingConstants.CENTER); 
    	lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
    	Font newLabelFont =new Font(lbl.getFont().getName(), Font.BOLD, lbl.getFont().getSize()+3);
    	lbl.setFont(newLabelFont);
    	lbl.setHorizontalAlignment(JLabel.CENTER);
    	p.add(lbl);
    	// Icon
    	URL url = getClass().getResource(Conf.ICON_FILENAME_M);
    	ImageIcon icon = new ImageIcon(url);
	    JLabel label = new JLabel(icon);
	    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    	p.add(label);
    	// Other info
    	addLabelToPanel(" ", p);
    	addLabelToPanel("Version: " + Conf.VERSION, p);
    	addLabelToPanel(" ", p);
    	addLabelToPanel("Author: Jure Šorn", p);
    	addLabelToPanel("<sornjure@hotmail.com>", p);
    	addLabelToPanel(" ", p);
    	//p.add(button);
    	
    	dlg.setSize(240, 105);
    	dlg.pack();
    	dlg.setAlwaysOnTop(true);
    	dlg.setVisible(true);
    	dlg.dispose();
    	
	}
    
    private static void addLabelToPanel(String txt, JPanel pnl) {
    	JLabel lbl = new JLabel(txt);
    	lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
    	pnl.add(lbl);
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
