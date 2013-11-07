package si.gto76.javaphotoeditor;

import javax.swing.JDesktopPane;

import si.gto76.javaphotoeditor.dialogs.AdditionDialog;
import si.gto76.javaphotoeditor.dialogs.AndDialog;
import si.gto76.javaphotoeditor.dialogs.DivisionDialog;
import si.gto76.javaphotoeditor.dialogs.MultiplicationDialog;
import si.gto76.javaphotoeditor.dialogs.OperationDialog;
import si.gto76.javaphotoeditor.dialogs.OrDialog;
import si.gto76.javaphotoeditor.dialogs.SubtractionDialog;
import si.gto76.javaphotoeditor.dialogs.XorDialog;
import si.gto76.javaphotoeditor.dialogs.MyDialog;

public enum Operation {
	ADDITION,
	SUBTRACTION,
	MULTIPLICATION,
	DIVISION,
	AND,
	OR,
	XOR;

	public OperationDialog getDialog(JDesktopPane desktop) {
		switch (this) {
	    	case ADDITION:
	    		return new AdditionDialog(desktop);
	    	case SUBTRACTION:
	    		return new SubtractionDialog(desktop);
	    	case MULTIPLICATION:
	    		return new MultiplicationDialog(desktop);
	    	case DIVISION:
	    		return new DivisionDialog(desktop);
	    	case AND:
	    		return new AndDialog(desktop);
	    	case OR:
	    		return new OrDialog(desktop);
	    	case XOR:
	    		return new XorDialog(desktop);
	    	default:
	    		throw new ExceptionInInitializerError() {
	    		};
		}
    }

}
