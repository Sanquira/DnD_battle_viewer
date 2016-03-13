package hexapaper.Listeners;

import hexapaper.entity.EditableEntity;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import core.ValueChangedListener;

public class ValueListener implements ValueChangedListener, DocumentListener {
	private int pos;
	private EditableEntity ent;

	public ValueListener(EditableEntity ent, int pos) {
		this.pos = pos;
		this.ent = ent;
	}

	@Override
	public void valueChanged(String value, JComponent source) {
		ent.setParamName(pos, value);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		String t = null;
		try {
			t = e.getDocument().getText(0, e.getDocument().getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		// System.out.println(t);
		ent.setParamValue(pos, t);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {

	}

}
