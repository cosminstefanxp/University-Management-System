package aii.gui.tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The Class FixedSizeDocument that is used to set a maximum number of characters in a text component. 
 * 
 * Example Use: field.setDocument(new FixedSizeDocument(5));
 */
public class FixedSizeDocument extends PlainDocument {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7241267332280454974L;

	/** The max. */
	private int max = 10;

	/**
	 * Instantiates a new fixed size document.
	 *
	 * @param max the max
	 */
	public FixedSizeDocument(int max) {
		this.max = max;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// check string being inserted does not exceed max length

		if (getLength() + str.length() > max) {
			// If it does, then truncate it

			str = str.substring(0, max - getLength());
		}
		super.insertString(offs, str, a);
	}
}
