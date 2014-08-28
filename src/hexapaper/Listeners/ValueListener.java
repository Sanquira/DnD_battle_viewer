package hexapaper.Listeners;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class ValueListener implements ValueChangedListener, DocumentListener {
	private int pos;
	private Postava man = null;
	private Artefact art = null;

	public ValueListener(Postava man, int pos) {
		this.pos = pos;
		this.man = man;
	}

	public ValueListener(Artefact man, int pos) {
		this.pos = pos;
		this.art = man;
	}

	@Override
	public void valueChanged(String value, JComponent source) {
		if (man != null) {
			man.setParamName(pos, value);
			return;
		}
		art.setParamName(pos, value);
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
		if (man != null) {
			man.setParamValue(pos, t);
			return;
		}
		art.setParamValue(pos, t);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {

	}

}
