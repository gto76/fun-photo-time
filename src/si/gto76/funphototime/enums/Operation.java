package si.gto76.funphototime.enums;

import javax.swing.JDesktopPane;

import si.gto76.funphototime.dialogs.AdditionDialog;
import si.gto76.funphototime.dialogs.AndDialog;
import si.gto76.funphototime.dialogs.DivisionDialog;
import si.gto76.funphototime.dialogs.MultiplicationDialog;
import si.gto76.funphototime.dialogs.OperationDialog;
import si.gto76.funphototime.dialogs.OrDialog;
import si.gto76.funphototime.dialogs.SubtractionDialog;
import si.gto76.funphototime.dialogs.XorDialog;

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
					private static final long serialVersionUID = -3603518262645278157L;
	    		};
		}
    }

}
