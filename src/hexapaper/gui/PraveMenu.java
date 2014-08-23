package hexapaper.gui;

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
		showB = new JCheckBox(Strings.ne, false);
		showB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriShowColor();
			}
		});
		showP.add(showL);
		showP.add(showB);

		JPanel showN = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel showLn = new JLabel(Strings.showNPCColor);
		showBn = new JCheckBox(Strings.ne, false);
		showBn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriShowNPC();
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

	public void update() {
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

		if (isPostava && ((Postava) vlastnosti).isPJ()) {
			name = "(NPC) ";
		}
		name = name.concat(vlastnosti.getNick());
		JLabel nameLabel = new JLabel(name);
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

		if (param.size() < 6) {
			sizeParam = 6;
			vert.setEnabled(false);
			druhySc.setVerticalScrollBar(vert);
		} else {
			sizeParam = param.size();
			vert.setEnabled(true);
			druhySc.setVerticalScrollBar(vert);
		}
		JPanel druhyIn = new JPanel(new GridLayout(sizeParam, 1));
		MouseListener m = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (isDel) {
					for (int i = 0; i < param.size(); i++) {
						if (param.get(i).name == e.getComponent().getParent().getName() &&
								e.getComponent().getParent().getName() != Strings.name) {
							param.remove(i);
							updateCreate();
							break;
						}
					}
				}
			}
		};
		for (PropPair prv : param) {
			JPanel druhy = new JPanel(new GridLayout(1, 2, 10, 0));
			druhy.setPreferredSize(new Dimension(0, 30));
			druhy.setName(prv.name);
			EditableJLabel lblName = new EditableJLabel(prv.name);
			lblName.addMouseListener(m);
			JTextField tfldValue = new JTextField(prv.value);
			tfldValue.addMouseListener(m);
			druhy.add(lblName);
			druhy.add(tfldValue);
			druhyIn.add(druhy);
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
		JToggleButton del = new JToggleButton(Strings.delPropBut, isDel);
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isDel = ((JToggleButton) e.getSource()).isSelected();
			}
		});
		// treti.add(add);
		// treti.add(del);
		// gbc.gridx = 0;
		// gbc.gridy = 7;
		// gbc.weighty = 0;
		// gbc.gridheight = 1;
		// gbl.setConstraints(treti, gbc);
		// VP.add(treti);
		//
		// JButton hotovo = new JButton(Strings.vytvorPostavu);
		// hotovo.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent paramActionEvent) {
		// readParam();
		// // System.out.println(param.toString());
		//
		// if (param.get(0).value.trim().isEmpty()) {
		// JOptionPane.showMessageDialog(vpg, Strings.warningNameIsEmpty,
		// Strings.varovani, JOptionPane.WARNING_MESSAGE);
		// return;
		// }
		// Postava man = new Postava(param.remove(0).value, sk.LocDontCare,
		// isNPSCB.isSelected(), param);
		// sk.databazePostav.add(man);
		// updateDatabaze();
		// clearChar();
		// }
		// });
		// gbc.gridx = 0;
		// gbc.gridy = 8;
		// gbl.setConstraints(hotovo, gbc);
		// VP.add(hotovo);
		return VP;
	}

	protected void readParam() {
		for (int i = 0; i < param.size(); i++) {
			String name = param.get(i).name;
			String value = ((JTextField) ((JPanel) ((JPanel) ((JViewport) ((JScrollPane) vpg.getComponent(1)).getComponent(0)).getComponent(0)).getComponent(i)).getComponent(1)).getText();
			param.set(i, new PropPair(name, value));
		}
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
