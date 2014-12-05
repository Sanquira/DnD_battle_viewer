package hexapaper.gui;

import hexapaper.entity.Postava;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;
import hexapaper.source.HPStrings;

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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.EditableJLabel;

public class PostavaAddFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	HPSklad sk = HPSklad.getInstance();
	protected String[] defaultProp = { sk.str.get("name")};
	protected ArrayList<PropPair> param = new ArrayList<PropPair>();
	JPanel vpg;
	JPanel spg;
	// boolean delete = false;
	boolean isNPC = false, isDel = false, isDelD = false;
	JList<Object> list;

	public PostavaAddFrame() {
		frame = new JFrame(sk.str.get("vytvorPostavu"));
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(1, 2, 0, 10));
		for (String str : defaultProp) {
			param.add(new PropPair(str, ""));
		}
		vpg = vytvorPostavu();
		spg = databazePostav();

		add(vpg);
		add(spg);
		frame.add(this);
		frame.setVisible(true);
	}

	private JPanel vytvorPostavu() {
		JPanel VP = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		VP.setLayout(gbl);
		VP.setBorder(new TitledBorder(sk.str.get("vytvorPostavu")));

		JPanel prvni = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel isNPCL = new JLabel(sk.str.get("NPC"));
		final JCheckBox isNPSCB = new JCheckBox();
		isNPSCB.setSelected(isNPC);
		isNPSCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				isNPC = ((JCheckBox) paramActionEvent.getSource()).isSelected();

			}
		});
		prvni.add(isNPCL);
		prvni.add(isNPSCB);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbl.setConstraints(prvni, gbc);
		VP.add(prvni);
		gbc.weightx = 0;

		JScrollPane druhySc = new JScrollPane();
		int sizeParam;
		JScrollBar vert = druhySc.getVerticalScrollBar();
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
						if (param.get(i).name == e.getComponent().getParent().getName() && e.getComponent().getParent().getName() != sk.str.get("name")) {
							readParam();
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
			if (prv.name == param.get(0).name) {
				lblName.setHoverEnable(false);
			}
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
		JToggleButton del = new JToggleButton(sk.str.get("delPropBut"), isDel);
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isDel = ((JToggleButton) e.getSource()).isSelected();
			}
		});
		treti.add(add);
		treti.add(del);
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.weighty = 0;
		gbc.gridheight = 1;
		gbl.setConstraints(treti, gbc);
		VP.add(treti);

		JButton hotovo = new JButton(sk.str.get("vytvorPostavu"));
		hotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				readParam();
				// System.out.println(param.toString());

				if (param.get(0).value.trim().isEmpty()) {
					JOptionPane.showMessageDialog(vpg, sk.str.get("warningNameIsEmpty"), sk.str.get("varovani"), JOptionPane.WARNING_MESSAGE);
					return;
				}
				Postava man = new Postava(param.remove(0).value, sk.LocDontCare, isNPSCB.isSelected(), param);
				sk.databazePostav.add(man.clone());
				updateDatabaze();
				clearChar();
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbl.setConstraints(hotovo, gbc);
		VP.add(hotovo);
		return VP;
	}

	protected void clearChar() {
		param.clear();
		isNPC = false;
		isDel = false;
		for (String str : defaultProp) {
			param.add(new PropPair(str, ""));
		}
		updateCreate();
	}

	protected void updateDatabaze() {
		updateCreate();
		sk.RMenu.updateDatabase();

	}

	private JPanel databazePostav() {
		JPanel SP = new JPanel();
		SP.setBorder(new TitledBorder(sk.str.get("vytvorenePostavy")));
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		SP.setLayout(gbl);
		JScrollPane datPo = new JScrollPane();
		datPo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		list = new JList<>(sk.databazePostav.toArray());

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList<PropPair> lsm = (JList) e.getSource();
				boolean isAdjusting = e.getValueIsAdjusting();
				if (!lsm.isSelectionEmpty()) {
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();
					if (minIndex == maxIndex && lsm.isSelectedIndex(minIndex) && isDelD && isAdjusting) {
						sk.databazePostav.remove(minIndex);
						if (sk.databazePostav.isEmpty()) {
							isDelD = false;
						}
						updateDatabaze();
					}
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		datPo.setViewportView(list);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 8;
		gbl.setConstraints(datPo, gbc);
		SP.add(datPo);

		JToggleButton del = new JToggleButton(sk.str.get("delPropBut"), isDelD);
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.clearSelection();
				isDelD = ((JToggleButton) e.getSource()).isSelected();
			}
		});
		// JSc
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(del, gbc);
		SP.add(del);

		return SP;
	}

	protected void updateCreate() {
		removeAll();
		vpg = vytvorPostavu();
		spg = databazePostav();
		add(vpg);
		add(spg);
		revalidate();
		repaint();
	}

	protected void readParam() {
		// ArrayList<PropPair> tmp = new ArrayList<>();
		for (int i = 0; i < param.size(); i++) {
			String name = ((EditableJLabel) ((JPanel) ((JPanel) ((JViewport) ((JScrollPane) vpg.getComponent(1)).getComponent(0)).getComponent(0)).getComponent(i)).getComponent(0)).getText();
			String value = ((JTextField) ((JPanel) ((JPanel) ((JViewport) ((JScrollPane) vpg.getComponent(1)).getComponent(0)).getComponent(0)).getComponent(i)).getComponent(1)).getText();
			param.set(i, new PropPair(name, value));
		}
		// param = new ArrayList<>(tmp);
	}

}
