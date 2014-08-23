package hexapaper.gui;


import hexapaper.source.ValueListener;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ValuePanel extends JPanel {
	
	private int pos;
	EditableJLabel Label;
	JTextField Weapon;
	ValueListener list;
	private String name;
	private String value;
	private Properties p;
	final ValuePanel parent=this;
	private MouseListener m=new MouseListener(){

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
			((Component) e.getSource()).getParent().dispatchEvent(e);
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	private MouseListener mL=new MouseListener(){

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
			if(p.delete){
				p.removeParam(parent);
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public ValuePanel(final Properties p, int pos){
		super();
		this.pos=pos;
		this.p=p;
		String name;
		String value;
		if(p.isArtefact){
			list=new ValueListener(p.getArtefact(),pos);
			name=p.getArtefact().getParamName(pos);
			value=p.getArtefact().getParamValue(pos);
		}
		else{
			list=new ValueListener(p.getCharacter(),pos);
			name=p.getCharacter().getParamName(pos);
			value=p.getCharacter().getParamValue(pos);
		}
		this.addMouseListener(mL);
		create(name,value);		
	}
	public void create(String name, String value){		
		super.setLayout(new GridLayout(1, 0, 0, 0));
	
		Label = new EditableJLabel(name);
		Label.getLabel().setHorizontalAlignment(SwingConstants.CENTER);
		Label.addValueChangedListener(list);
		Label.addMouseListener(m);
		Weapon = new JTextField();
		Weapon.setText(value);
		Weapon.setColumns(10);
		Weapon.getDocument().addDocumentListener(list);
		Weapon.addMouseListener(m);
		super.add(Label);
		super.add(Weapon);
	}

}
