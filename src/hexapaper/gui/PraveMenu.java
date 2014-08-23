package hexapaper.gui;

import hexapaper.Listeners.ValueListener;
import hexapaper.entity.Artefact;
import hexapaper.entity.Entity;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.source.Sklad;
import hexapaper.source.Sklad.PropPair;
import hexapaper.source.Sklad.prvekkNN;
import hexapaper.source.Strings;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.border.TitledBorder;

public class PraveMenu extends JPanel {

	Sklad sk = Sklad.getInstance();

	JPanel prop, ovladani;
	JCheckBox showB, showBn;
	JComboBox<Object> addAC;
	JComboBox<Object> addPC;
	boolean isAddingWall = false, isAddingFreeSpace = false;
	JToggleButton freespace, wall;
	Entity vlastnosti = null;
	boolean isPostava = false, isDel = false;
	ArrayList<PropPair> param = new ArrayList<PropPair>();

	public PraveMenu() {
		praveMenu();
	}

	public JPanel praveMenu() {
		setPreferredSize(new Dimension(250, 100));
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		// gbc.weighty = 2;
		gbc.gridheight = 2;
		ovladani = ovladani();
		gbl.setConstraints(ovladani, gbc);
		add(ovladani);

		prop = vlastnosti();
		// JScrollPane prop = vlastnosti();
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridheight = 3;
		gbl.setConstraints(prop, gbc);
		add(prop);

		setBackground(Color.yellow);

		return this;
	}

	private JPanel ovladani() {
		JPanel prvni = new JPanel();
		prvni.setPreferredSize(new Dimension(-1, 180));
		prvni.setBorder(new TitledBorder(Strings.ovladaniBitvy));
		prvni.setLayout(new GridLayout(5, 1, 0, 10));

		JPanel showP = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel showL = new JLabel(Strings.showPlayerColor);
		showB = new JCheckBox();
		showB.setSelected(sk.hidePlayerColor);
		osetriShowColor();
		showB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriShowColor();
				updateCreate();
			}
		});
		showP.add(showL);
		showP.add(showB);

		JPanel showN = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel showLn = new JLabel(Strings.showNPCColor);
		showBn = new JCheckBox();
		showBn.setSelected(sk.hideNPCColor);
		osetriShowNPC();
		showBn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriShowNPC();
				updateCreate();
			}
		});
		showN.add(showLn);
		showN.add(showBn);

		JPanel wallFreeSpace = new JPanel(new GridLayout(1, 2, 10, 0));

		wall = new JToggleButton(Strings.addWall);
		wall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriWall(((JToggleButton) e.getSource()).isSelected());
			}
		});

		freespace = new JToggleButton(Strings.addFreeSpace);
		freespace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriFreeSpace(((JToggleButton) e.getSource()).isSelected());
			}
		});

		wallFreeSpace.add(wall);
		wallFreeSpace.add(freespace);

		JPanel addA = new JPanel(new BorderLayout(10, 10));

		// addA.setBorder(new TitledBorder(Strings.addArt));
		Object[] artlist = sk.databazeArtefaktu.toArray();
		addAC = new JComboBox<>();
		addAC.setModel(new DefaultComboBoxModel<>(artlist));

		JButton addAB = new JButton("+");
		// addAB.setPreferredSize(new Dimension(45, 45));
		addAB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				osetriAddAB();
			}
		});

		addA.add(addAC, BorderLayout.CENTER);
		addA.add(addAB, BorderLayout.EAST);

		JPanel addP = new JPanel(new BorderLayout(10, 10));
		Object[] postlist = sk.databazePostav.toArray();
		addPC = new JComboBox<>(postlist);

		JButton addPB = new JButton("+");
		// addPB.setPreferredSize(new Dimension(45, 45));
		addPB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				osetriAddPB();
			}
		});
		addP.add(addPC, BorderLayout.CENTER);
		addP.add(addPB, BorderLayout.EAST);

		prvni.add(showP);
		prvni.add(showN);
		prvni.add(wallFreeSpace);
		prvni.add(addA);
		prvni.add(addP);
		return prvni;
	}

	public void updateDatabase() {
		Object[] artlist = sk.databazeArtefaktu.toArray();
		addAC.setModel(new DefaultComboBoxModel<>(artlist));
		Object[] postlist = sk.databazePostav.toArray();
		addPC.setModel(new DefaultComboBoxModel<>(postlist));
	}

	private void osetriShowColor() {
		sk.hidePlayerColor = showB.isSelected();
		if (showB.isSelected()) {
			showB.setText(Strings.ano);
		} else {
			showB.setText(Strings.ne);
		}
	}

	private void osetriShowNPC() {
		sk.hideNPCColor = showBn.isSelected();
		if (showBn.isSelected()) {
			showBn.setText(Strings.ano);
		} else {
			showBn.setText(Strings.ne);
		}
	}

	private void osetriWall(boolean isActive) {
		if (isActive) {
			freespace.setSelected(false);
			osetriFreeSpace(false);
			sk.setupInserting(new Wall(sk.LocDontCare), true);
		} else {
			sk.setupInserting(null, false);
		}
	}

	private void osetriFreeSpace(boolean isActive) {
		if (isActive) {
			wall.setSelected(false);
			osetriWall(false);
			sk.setupInserting(new FreeSpace(sk.LocDontCare), true);
		} else {
			sk.setupInserting(null, false);
		}
	}

	private void osetriAddAB() {
		wall.setSelected(false);
		freespace.setSelected(false);
		sk.setupInserting(((Artefact) addAC.getSelectedItem()).clone(), false);
	}

	private void osetriAddPB() {
		wall.setSelected(false);
		freespace.setSelected(false);
		sk.setupInserting(((Postava) addPC.getSelectedItem()).clone(), false);
	}

	private JPanel vlastnosti() {
		JPanel VP = new JPanel();
		VP.setBorder(new TitledBorder(Strings.vlastnostiObj));
		if (vlastnosti == null) {
			return VP;
		}
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		VP.setLayout(gbl);
		VP.setBorder(new TitledBorder(Strings.vytvorPostavu));

		JPanel prvni = new JPanel(new GridLayout(1, 1, 10, 0));
		String name = "";
		if (isPostava) {
			if (((Postava) vlastnosti).isPJ()) {
				name = "(" + Strings.NPC + ") ";
			} else {
				name = "(" + Strings.player + ") ";
			}
		} else {
			name = "(" + Strings.artefakt + ") ";
		}
		name = name.concat(vlastnosti.getNick());
		JLabel nameLabel = new JLabel();
		nameLabel.setPreferredSize(new Dimension(-1, 35));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setFont(nameLabel.getFont().deriveFont(15F));
		nameLabel.setText(name);
		prvni.add(nameLabel);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbl.setConstraints(prvni, gbc);
		VP.add(prvni);
		gbc.weightx = 0;

		JScrollPane druhySc = new JScrollPane();
		int sizeParam;
		JScrollBar vert = druhySc.getVerticalScrollBar();
		if (isPostava) {
			param = ((Postava) vlastnosti).getParam();
		} else {
			param = ((Artefact) vlastnosti).getParam();
		}

		if (param.size() < 8) {
			sizeParam = 8;
			vert.setEnabled(false);
			druhySc.setVerticalScrollBar(vert);
		} else {
			sizeParam = param.size();
			vert.setEnabled(true);
			druhySc.setVerticalScrollBar(vert);
		}
		JPanel druhyIn = new JPanel(new GridLayout(sizeParam, 1));
		// druhyIn.add
		MouseListener m = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (isDel) {
					for (int i = 0; i < param.size(); i++) {
						if (param.get(i).name == e.getComponent().getParent().getName()) {
							param.remove(i);
							if (param.size() == 0) {
								isDel = false;
							}
							updateCreate();
							break;
						}
					}

				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				readParam();
			}
		};
		boolean skryj = false;
		if (isPostava) {
			if (((Postava) vlastnosti).isPJ() && sk.hideNPCColor) {
				skryj = true;
			}
			if (!((Postava) vlastnosti).isPJ() && sk.hidePlayerColor) {
				skryj = true;
			}
		}
		if (!skryj) {
			int i = 0;
			for (PropPair prv : param) {
				ValueListener list;
				if (isPostava) {
					list = new ValueListener((Postava) vlastnosti, i);
				} else {
					list = new ValueListener((Artefact) vlastnosti, i);
				}
				JPanel druhy = new JPanel(new GridLayout(1, 2, 10, 0));
				druhy.setPreferredSize(new Dimension(0, 30));
				druhy.setName(prv.name);
				EditableJLabel lblName = new EditableJLabel(prv.name);
				lblName.addMouseListener(m);
				lblName.addValueChangedListener(list);
				JTextField tfldValue = new JTextField(prv.value);
				tfldValue.addMouseListener(m);
				tfldValue.getDocument().addDocumentListener(list);
				druhy.add(lblName);
				druhy.add(tfldValue);
				druhyIn.add(druhy);
			}
		}
		druhySc.setViewportView(druhyIn);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridheight = 5;
		gbl.setConstraints(druhySc, gbc);
		VP.add(druhySc);

		JPanel treti = new JPanel(new GridLayout(1, 2, 10, 0));
		JButton add = new JButton(Strings.addPropBut);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				readParam();
				param.add(new PropPair("<>", ""));
				isDel = false;
				updateCreate();
			}
		});
		add.setEnabled(!skryj);
		JToggleButton del = new JToggleButton(Strings.delPropBut, isDel);
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isDel = ((JToggleButton) e.getSource()).isSelected();
				if (param.size() == 0) {
					isDel = false;
					((JToggleButton) e.getSource()).setSelected(isDel);
				}
			}
		});
		del.setEnabled(!skryj);
		treti.add(add);
		treti.add(del);
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.weighty = 0;
		gbc.gridheight = 1;
		gbl.setConstraints(treti, gbc);
		VP.add(treti);
		return VP;
	}

	protected void readParam() {
		for (int i = 0; i < param.size(); i++) {
			String name = ((EditableJLabel) ((JPanel) ((JPanel) ((JViewport) ((JScrollPane) prop.getComponent(1)).getComponent(0)).getComponent(0)).getComponent(i)).getComponent(0)).getText();
			String value = ((JTextField) ((JPanel) ((JPanel) ((JViewport) ((JScrollPane) prop.getComponent(1)).getComponent(0)).getComponent(0)).getComponent(i)).getComponent(1)).getText();
			param.set(i, new PropPair(name, value));
		}
	}

	protected void updateCreate() {
		removeAll();
		praveMenu();
		revalidate();
		repaint();
	}

	public void redrawProperities(prvekkNN prvekkNN) {
		Entity ent = sk.souradky.get(prvekkNN.getIdx());
		if (ent.getClass() == hexapaper.entity.Postava.class ||
				ent.getClass() == hexapaper.entity.Artefact.class) {
			vlastnosti = sk.souradky.get(prvekkNN.getIdx());
			if (vlastnosti.getClass() == hexapaper.entity.Postava.class) {
				isPostava = true;
			} else {
				isPostava = false;
			}
			removeAll();
			praveMenu();
			revalidate();
			repaint();
		}
	}
}
