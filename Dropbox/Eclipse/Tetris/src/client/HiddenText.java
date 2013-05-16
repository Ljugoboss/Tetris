package client;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
/**
 * A JTextField with the ability to have a hint when it doesn't have any other text in it or
 * is marked.
 * @author Hugo Nissar
 * @author Jonas Sjöberg
 * @version 1.0
 */
public class HiddenText extends JTextField implements FocusListener {
	private static final long serialVersionUID = 5318231936434728862L;
	private String hint;
	
	/**
	 * Creates a new JTextField with a hint.
	 * @param hint
	 */
	public HiddenText(String hint) {
		super(hint);
		this.hint = hint;
		super.addFocusListener(this);
	}
	
	/**
	 * If the field gets focused/clicked on, remove the hint.
	 */
	@Override
	public void focusGained(FocusEvent arg0) {
		if (this.getText().equals("")) {
			this.setText("");
		}
	}
	
	/**
	 * If the field isn't in focus anymore and the field is empty, add the hint again.
	 */
	@Override
	public void focusLost(FocusEvent arg0) {
		if (this.getText().isEmpty()) {
			super.setText(hint);
		}
	}
	
	/**
	 * @return An empty String if the field is empty or the hint still is in it,
	 * 			else it returns the text in it.
	 */
	public String getText() {
		String typed = super.getText();
		if (typed.equals(hint)) {
			return "";
		} else {
			return typed;
		}
	}
}
