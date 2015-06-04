package addons.dice;

import hexapaper.source.HPSklad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class LogWindow extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2137955313960178663L;
	HPSklad sk = HPSklad.getInstance();
	DefaultStyledDocument document = new DefaultStyledDocument();
	JTextPane textPane = new JTextPane(document);
	StyleContext context = new StyleContext();
	Style style = context.addStyle("test", null);
	DateFormat dateFormat = new SimpleDateFormat("HH:mm");

	public LogWindow() throws HeadlessException {
		setPreferredSize(new Dimension(400, 300));
		textPane.setEditable(false);
		add(textPane);
		setViewportView(textPane);
	}

	public void addMessage(String text, Color color) {
		try {
			StyleConstants.setForeground(style, color);
			//StyleConstants.setBold(style, true);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
		try {
			document.insertString(document.getLength(), text + '\n', style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getMaximum());
	}
	public void addMessage(String text){
		addMessage(text,Color.BLACK);
	}
	public void insertMessage(String text) throws BadLocationException{
		document.insertString(document.getLength(), text, style);
		this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getMaximum());
	}

	// JTextPane textpane = new JTextPane(document);
	//
	// // build a style
	// Style style = context.addStyle("test", null);
	// // set some style properties
	// StyleConstants.setForeground(style, Color.BLUE);
	// // add some data to the document
	// document.insertString(0, "", style);

}
