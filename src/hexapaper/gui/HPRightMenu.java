package hexapaper.gui;

import hexapaper.hexapaper;
import hexapaper.Listeners.ValueListener;
import hexapaper.entity.Artefact;
import hexapaper.entity.HPEntity;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;
import hexapaper.source.HPSklad.prvekkNN;
import hexapaper.source.HPStrings;

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
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

import core.DocumentAdapter;
import core.EditableJLabel;

public class HPRightMenu extends JPanel {

	HPSklad sk = HPSklad.getInstance();

	JPanel prop;
	JCheckBox showB, showBn;
	JComboBox<Object> addAC;
	JComboBox<Object> addPC;
	boolean isAddingWall = false, isAddingFreeSpace = false;
	JToggleButton freespace, wall;
	HPEntity vlastnosti = null;
	boolean isPostava = false, isDel = false;
	ArrayList<PropPair> param = new ArrayList<PropPair>();

	public HPRightMenu() {
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
		JPanel ovladani = ovladani();
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
		prvni.setBorder(new TitledBorder(sk.str.get("ovladaniBitvy")));
		prvni.setLayout(new GridLayout(5, 1, 0, 10));

		JPanel showP = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel showL = new JLabel(sk.str.get("showPlayerColor"));
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
		JLabel showLn = new JLabel(sk.str.get("showNPCColor"));
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

		wall = new JToggleButton(sk.str.get("addWall"));
		wall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				osetriWall(((JToggleButton) e.getSource()).isSelected());
			}
		});

		freespace = new JToggleButton(sk.str.get("addFreeSpace"));
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
			showB.setText(sk.str.get("ano"));
		} else {
			showB.setText(sk.str.get("ne"));
		}
	}

	private void osetriShowNPC() {
		sk.hideNPCColor = showBn.isSelected();
		if (showBn.isSelected()) {
			showBn.setText(sk.str.get("ano"));
		} else {
			showBn.setText(sk.str.get("ne"));
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
		if (addAC.getSelectedItem() != null) {
			sk.setupInserting(((Artefact) addAC.getSelectedItem()).clone(), false);
		}
		;
	}

	private void osetriAddPB() {
		wall.setSelected(false);
		freespace.setSelected(false);
		sk.setupInserting(((Postava) addPC.getSelectedItem()).clone(), false);
	}

	private JPanel vlastnosti() {
		JPanel VP = new JPanel();
		VP.setBorder(new TitledBorder(sk.str.get("vlastnostiObj")));
		if (vlastnosti == null) {
			return VP;
		}
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		VP.setLayout(gbl);
		VP.setBorder(new TitledBorder(sk.str.get("vytvorPostavu")));

		JPanel prvni = new JPanel(new BorderLayout(10, 10));

		String name = "";
		if (isPostava) {
			if (((Postava) vlastnosti).isPJ()) {
				name = sk.str.get("NPC");
			} else {
				name = sk.str.get("player");
			}
		} else {
			name = sk.str.get("artefakt");
		}
		JLabel nameLabel = new JLabel(name);
		JLabel tag = new JLabel(sk.str.get("Tag"));
		tag.setPreferredSize(new Dimension(35, -1));
		prvni.add(nameLabel, BorderLayout.CENTER);
		prvni.add(tag, BorderLayout.EAST);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbl.setConstraints(prvni, gbc);
		VP.add(prvni);
		gbc.weightx = 0;

		name = vlastnosti.getNick();
		JPanel nameatag = new JPanel(new BorderLayout(10, 10));

		EditableJLabel nameL = new EditableJLabel(vlastnosti.getNick());
		nameL.setPreferredSize(new Dimension(-1, 35));
		nameL.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				String chtag = "";
				try {
					chtag = e.getDocument().getText(0, e.getDocument().getLength());
				} catch (BadLocationException er) {
					er.printStackTrace();
				}
				vlastnosti.setNick(chtag);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				insertUpdate(e);
			}
		});

		EditableJLabel tagL = new EditableJLabel(vlastnosti.tag);
		tagL.setPreferredSize(new Dimension(35, 35));
		tagL.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				String chtag = vlastnosti.tag;
				try {
					if (arg0.getDocument().getLength() <= 2) {
						chtag = arg0.getDocument().getText(0, arg0.getDocument().getLength());

					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				vlastnosti.setTag(chtag);
				hexapaper.HPfrm.repaint();

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				insertUpdate(e);
			}
		});

		nameatag.add(nameL, BorderLayout.CENTER);
		nameatag.add(tagL, BorderLayout.EAST);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbl.setConstraints(nameatag, gbc);
		VP.add(nameatag);

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
				i += 1;
			}
		}
		druhySc.setViewportView(druhyIn);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridheight = 5;
		gbl.setConstraints(druhySc, gbc);
		VP.add(druhySc);

		JPanel treti = new JPanel(new GridLayout(1, 2, 10, 0));
		JButton add = new JButton(sk.str.get("addPropBut"));
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
		JToggleButton del = new JToggleButton(sk.str.get("delPropBut"), isDel);
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
		HPEntity ent = sk.souradky.get(prvekkNN.getIdx());
		if (ent instanceof Postava ||
				ent instanceof Artefact) {
			vlastnosti = sk.souradky.get(prvekkNN.getIdx());
			if (vlastnosti instanceof Postava) {
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
