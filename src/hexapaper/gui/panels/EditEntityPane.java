package hexapaper.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import core.EditableJLabel;
import core.ValueChangedListener;
import hexapaper.Listeners.ValueListener;
import hexapaper.entity.EditableEntity;
import hexapaper.entity.Postava;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;
import net.miginfocom.swing.MigLayout;

public class EditEntityPane<T extends EditableEntity> extends JPanel {
	
	private T type;
	private String btnText = "Save";
	private EditableJLabel tagL;
	private EditableJLabel nameL;
	private JPanel properties, customPanel;
	private JLabel nameLabel;
	private T entity;
	private HPSklad sk = HPSklad.getInstance();
	private boolean isDel;
	private MouseAdapter removeListener = new MouseAdapter(){

		@Override
		public void mouseClicked(MouseEvent e) {
			if (isDel) {
				ArrayList<PropPair> param = entity.getParam();
				for (int i = 0; i < param.size(); i++) {
					if (param.get(i).name == e.getComponent().getParent().getName()) {
						param.remove(i);
						if (param.size() == 0) {
							isDel = false;
						}
						updateProperties();
						break;
					}
				}

			}			
		}		
	};
	private JButton customBtn;
	
	public EditEntityPane(String btnText){
		this();
		customBtn.setText(btnText);
	}
	public EditEntityPane(){
		createFrame();
	}
	public EditEntityPane(JPanel custom){
		this.customPanel = custom;
		createFrame();
	}
	public EditEntityPane(JPanel custom, String btnText, String nameText){
		this(custom);
		customBtn.setText(btnText);
		setNameText(nameText);
	}
	
	public static void main(String[] args){
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(new EditEntityPane<EditableEntity>("test"), BorderLayout.CENTER);
		f.show();
	}
	private void createFrame() {
		setLayout(new MigLayout("nogrid,fillx", "[]", "[][][][grow 200,fill][]"));
		//setBorder(new TitledBorder(sk.str.CreateCharacter));

		JPanel prvni = new JPanel(new BorderLayout(10, 10));

		nameLabel = new JLabel("NPC");
		JLabel tag = new JLabel("Tag");
		//JLabel nameLabel = new JLabel(entity instanceof Postava? sk.str.player : sk.str.Artefact);
		//JLabel tag = new JLabel(sk.str.Tag);
		tag.setPreferredSize(new Dimension(35, -1));
		prvni.add(nameLabel, BorderLayout.CENTER);
		prvni.add(tag, BorderLayout.EAST);
		//gbl.setConstraints(prvni, gbc);
		add(prvni, "growx,wrap");
		add(new JSeparator(JSeparator.HORIZONTAL),"growx,wrap");
		JPanel nameatag = new JPanel();

		nameL = new EditableJLabel("Jméno");
		nameL.setPreferredSize(new Dimension(-1, 35));
		nameL.addValueChangedListener(new ValueChangedListener(){

			@Override
			public void valueChanged(String value, JComponent source) {
				if (sk.isConnected && !sk.isPJ) {
					return;
				}
				entity.setName(value);
				//nameL.setText(value);
			}
			
		});

		tagL = new EditableJLabel("");
		tagL.setPreferredSize(new Dimension(35, 35));
		tagL.addValueChangedListener(new ValueChangedListener(){

			@Override
			public void valueChanged(String value, JComponent source) {
				if (sk.isConnected && !sk.isPJ) {
					return;
				}
				entity.setTag(value);;
				sk.hraciPlocha.repaint();
				//String tag = entity.getTag();
				//tagL.setText(tag);
				
			}
		});
		nameatag.setLayout(new BorderLayout(0, 0));

		nameatag.add(nameL, BorderLayout.CENTER);
		nameatag.add(tagL, BorderLayout.EAST);
		add(nameatag, "growx,wrap");
		if(customPanel != null){
			add(customPanel, "growx,wrap");
		}
		JScrollPane propertiesPane = new JScrollPane(); 
		properties = new JPanel();
		properties.setLayout(new MigLayout("nogrid,fillx"));
		propertiesPane.setBorder(new TitledBorder("Vlastnosti"));
		propertiesPane.setViewportView(properties);
		add(propertiesPane, "grow,wrap");
		add(generateControlPanel(), "growx");
		//properties.setBorder(new TitledBorder(sk.str.Objproperties));
		//gbl.setConstraints(nameatag, gbc);
	}
	
	public void updateProperties(){
		properties.removeAll();
		if(entity != null){
			int i = 0;
			for(PropPair pair: entity.getParam()){
				ValueListener list = new ValueListener(entity,i); 
				JPanel prop = new JPanel(new GridLayout(1, 2, 10, 0));
				//prop.setPreferredSize(new Dimension(0, 30));
				prop.setName(pair.name);
				EditableJLabel name = new EditableJLabel(pair.name);
				name.addMouseListener(removeListener);
				name.addValueChangedListener(list);
				JTextField value = new JTextField(pair.value);
				value.addMouseListener(removeListener);
				value.getDocument().addDocumentListener(list);
				prop.add(name);
				prop.add(value);
				properties.add(prop,"growx, wrap");
				i++;
			}
		}
		properties.revalidate();
		properties.repaint();
	}
	protected JPanel generateControlPanel(){
		JPanel control = new JPanel();
		control.setLayout(new MigLayout("gapx 10px,fill"));
		JButton add = new JButton("Přidat");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				entity.getParam().add(new PropPair("<>", ""));
				//isDel = false;
				updateProperties();
			}
		});
		JToggleButton rm = new JToggleButton("Odebrat");
		rm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isDel = ((JToggleButton) e.getSource()).isSelected();
				if (entity.getParam().size() == 0) {
					isDel = false;
					((JToggleButton) e.getSource()).setSelected(isDel);
				}
			}
		});
		customBtn = new JButton(this.btnText);
		customBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		control.add(add, "cell 0 0,grow");
		control.add(rm, "cell 1 0,grow");
		control.add(customBtn, "cell 0 1 2 1,grow");
		return control;
	}
	public JButton getCustomButton() {
		return customBtn;
	}
	public void setNameText(String nameText) {
		this.nameLabel.setText(nameText);
		this.nameLabel.repaint();
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
		setNameText( entity instanceof Postava? sk.str.player : sk.str.Artefact);
		tagL.setText(entity.getTag());
		nameL.setText(entity.getName());
		updateProperties();
	}
	public JPanel getCustomPanel() {
		return customPanel;
	}	
}
