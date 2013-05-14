package client;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class HiddenText extends JTextField implements FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5318231936434728862L;
	private String hint;
	
	public HiddenText(String hint) {
		super(hint);
		this.hint = hint;
		super.addFocusListener(this);
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		if (this.getText().equals("")) {
			this.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (this.getText().isEmpty()) {
			super.setText(hint);
		}
	}
	
	public String getText() {
		String typed = super.getText();
		if (typed.equals(hint)) {
			return "";
		} else {
			return typed;
		}
	}
}
