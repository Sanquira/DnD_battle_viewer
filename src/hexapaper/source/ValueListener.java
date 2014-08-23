package hexapaper.source;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class ValueListener implements ValueChangedListener, DocumentListener, MouseListener{
	private int pos;
	private Postava man=null;
	private Artefact art=null;
	
	public ValueListener(Postava man,int pos){
		this.pos=pos;
		this.man=man;
	}
	public ValueListener(Artefact man,int pos){
		this.pos=pos;
		this.art=man;
	}
	@Override
	public void valueChanged(String value, JComponent source) {
		if(man!=null){
			man.setParamName(pos,value);
			return;
		}
		art.setParamName(pos,value);
	}	
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		String t = null;
		try {
			t=e.getDocument().getText(0, e.getDocument().getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		//System.out.println(t);
		if(man!=null){
			man.setParamName(pos,t);
			return;
		}
		art.setParamName(pos,t);
		
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
