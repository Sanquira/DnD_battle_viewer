package hexapaper.gui;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;
import hexapaper.source.ValueChangedListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.JButton;

public class Properties extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Character man=new Character(this,"Pole",new Location(5,4, 0), true,null);
	private Postava man;
	private Artefact art;
	private EditableJLabel nick;
	private JLabel PNadpis;
	static JPanel panel=new JPanel();
	private ValuePanel v;
	public boolean delete=false;
	public boolean isArtefact=false;
	private boolean hideNPC=false;
	private boolean hidePlayer=false;
	private List<ValuePanel> Panels=new ArrayList<ValuePanel>();
	private JButton New;
	private JToggleButton Remove;
	GridBagConstraints gbc ;
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
			AbstractButton but=(AbstractButton) e.getSource();
			triggerButtons(but,e);
			
		}

		private void triggerButtons(AbstractButton but, MouseEvent e) {
			if(but.getText()=="Pøidat"){
				if(isArtefact){
					art.addParam("<>","");
					update(art);
					return;
				}
				man.addParam("<>","");
				update(man);
			}
			else{
				if(delete){
					delete=false;
				}
				else{
					delete=true;
				}
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public Properties(){
		//JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		super(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		//super.setBorder(new TitledBorder("Properties"));
		panel.setPreferredSize(new Dimension(super.getSize().width,0));
		//panel.getPreferredSize().height=0;
		GridBagLayout gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.setLayout(gbl);
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		/**
		PNadpis = new JLabel();		
		PNadpis.setPreferredSize(new Dimension(getWidth(), 14));
		PNadpis.setFont(new Font("Times New Roman", Font.BOLD, 13));
		PNadpis.setHorizontalAlignment(SwingConstants.CENTER);
		PNadpis.setSize(panel.getWidth(), 50);
		panel_2.add(PNadpis);
		panel.add(panel_2);
		*/
		
		//JPanel panel_1 = new JPanel();
		//panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		nick = new EditableJLabel("");
		//nick.getLabel().setPreferredSize(new Dimension(getWidth(), 14));
		nick.getLabel().setHorizontalAlignment(SwingConstants.CENTER);
		nick.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//nick.setSize(panel.getWidth(), 50);
		nick.addValueChangedListener(new ValueChangedListener(){

			@Override
			public void valueChanged(String value, JComponent source) {
				if(isArtefact){
					art.setNick(value);
					return;
				}
				man.setNick(value);
				
			}
			
		});
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty =0.2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		panel.add(nick,gbc);
		
		New = new JButton("Pøidat");
		New.setPreferredSize(new Dimension(80, 20));
		New.addMouseListener(m);
		Remove = new JToggleButton("Odebrat");
		Remove.setPreferredSize(new Dimension(80, 20));
		Remove.addMouseListener(m);
		//update(man);
		

				
		
	}
	public void update(Postava man){
		//System.out.print(this.man.getParam().get(0)[1]);		
		removePanels();
		this.man=man;
		isArtefact=false;
		Panels=new ArrayList<ValuePanel>();
		String Ptext="(Hráè)";
		if(man.isPJ()){
			Ptext="(NPC)";
		}
		nick.setText(Ptext+" "+man.getNick());
		if((man.isPJ()&&!hideNPC)||(!man.isPJ()&&!hidePlayer)){
			setupPanels(man);
		}
		
		

	}
	public void update(Artefact man){
		//System.out.print(this.man.getParam().get(0)[1]);		
		this.art=man;
		isArtefact=true;
		removePanels();
		Panels=new ArrayList<ValuePanel>();
		String Ptext="(Art)";
		nick.setText(Ptext+" "+man.getNick());
		setupPanels(man);
	}
	public void setupPanels(Postava man){
		gbc.gridx =0;
		gbc.gridwidth=2;
		ValuePanel x;
		for(int i=0;i<man.getParam().size();i++){
			x=new ValuePanel(this,i);
			gbc.gridy +=1;			
			panel.add(x,gbc);
			//x.setPreferredSize(new Dimension(panel.getWidth(),20));
			x.setMinimumSize(new Dimension(0,20));
			Panels.add(x);
		}
		gbc.gridwidth=1;
		gbc.gridy +=1;
		panel.add(New,gbc);
		gbc.gridx =1;		
		panel.add(Remove,gbc);
		
	}
	public void setupPanels(Artefact man){
		gbc.gridx =0;
		gbc.gridwidth=2;
		ValuePanel x;
		for(int i=0;i<man.getParam().size();i++){
			x=new ValuePanel(this,i);
			gbc.gridy +=1;			
			panel.add(x,gbc);
			//x.setPreferredSize(new Dimension(panel.getWidth(),20));
			x.setMinimumSize(new Dimension(0,20));
			Panels.add(x);
		}
		gbc.gridwidth=1;
		gbc.gridy +=1;
		panel.add(New,gbc);
		gbc.gridx =1;		
		panel.add(Remove,gbc);
		
	}
	public void removePanels(){
		for(int i=0;i<Panels.size();i++){
			ValuePanel t=Panels.get(i);
			t.removeAll();
			panel.remove(Panels.get(i));			
		}
		panel.remove(Remove);
		panel.remove(New);
		panel.revalidate();
		panel.repaint();
		gbc.gridy =0;
		gbc.gridx=0;
	}	
	public void removeParam(ValuePanel comp) {
		comp.removeAll();
		panel.remove(comp);
		if(!isArtefact){
			man.removeParam(Panels.indexOf(comp));		
			update(man);
		}
		else{
			art.removeParam(Panels.indexOf(comp));		
			update(art);
		}
		Panels.remove(comp);
		
	}
	public Postava getCharacter(){
		return man;
	}
	public void hideNPC(boolean t){
		if(t){
			hideNPC=true;
			update(man);
			return;
		}
		hideNPC=false;
		update(man);		
	}
	public void hidePlayer(boolean t){
		if(t){
			hideNPC=true;
			update(man);
			return;
		}
		hideNPC=false;
		update(man);	
	}
	public Artefact getArtefact() {
		return art;
	}

}
