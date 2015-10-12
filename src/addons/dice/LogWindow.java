package addons.dice;

import hexapaper.source.HPSklad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		//textPane.setContentType("HTML/plain");
		add(textPane);
		setViewportView(textPane);
	}

	public void addMessage(String text, Color color,boolean prefix) {
		String added;
		if(prefix){
			added = getPrefix()+text;			
		}
		else{
			added = text;
		}
		try {
			StyleConstants.setForeground(style, color);
			//StyleConstants.setBold(style, true);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return;
		}
		try {
			document.insertString(document.getLength(), added + '\n', style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getMaximum());
	}
	private String getPrefix() {
		return "["+dateFormat.format(new Date())+"]: ";
	}
	public void addMessage(String text){
		addMessage(text,Color.BLACK,true);
	}
	public void insertMessage(String text){
		insertMessage(text,Color.BLACK);
	}
	public void insertMessage(String text,Color clr){
		StyleConstants.setForeground(style, clr);
		try {
			document.insertString(document.getLength(), text, style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getVerticalScrollBar().setValue(this.getVerticalScrollBar().getMaximum());
	}


//	public void addMessage(String txt){
//		insertMessage(txt);
//	}

	// JTextPane textpane = new JTextPane(document);
	//
	// // build a style
	// Style style = context.addStyle("test", null);
	// // set some style properties
	// StyleConstants.setForeground(style, Color.BLUE);
	// // add some data to the document
	// document.insertString(0, "", style);

}
