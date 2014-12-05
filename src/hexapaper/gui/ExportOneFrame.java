package hexapaper.gui;

import hexapaper.entity.Artefact;
import hexapaper.entity.HPEntity;
import hexapaper.entity.Postava;
import hexapaper.file.SaveFile;
import hexapaper.file.Wrappers;
import hexapaper.file.Wrappers.DatabaseWrapper;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.file.FileHandler;

public class ExportOneFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JPanel spg;
	HPEntity beExported;
	ArrayList<HPEntity> exportList;
	HPSklad sk = HPSklad.getInstance();

	public ExportOneFrame(ArrayList<HPEntity> exportList) {
		frame = new JFrame(sk.str.get("export"));
		frame.setSize(225, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(1, 2, 0, 10));

		this.exportList = exportList;

		spg = databazeArtefactu();

		add(spg);
		frame.add(this);
		frame.setVisible(true);
	}

	private JPanel databazeArtefactu() {
		JPanel SP = new JPanel();
		String title = sk.str.get("export");
		if (exportList.size() != 0) {
			if (exportList.get(0) instanceof Artefact) {
				title = sk.str.get("vytvoreneArtefakty");
			}
			if (exportList.get(0) instanceof Postava) {
				title = sk.str.get("vytvorenePostavy");
			}
		}
		SP.setBorder(new TitledBorder(title));
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		SP.setLayout(gbl);
		JScrollPane datPo = new JScrollPane();
		datPo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JList<Object> list = new JList<>(exportList.toArray());

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList<PropPair> lsm = (JList<PropPair>) e.getSource();
				boolean isAdjusting = e.getValueIsAdjusting();
				if (lsm.isSelectionEmpty()) {
					System.err.println("PostavaAddFrame.list.ListSelectionListener.valueChanged - " +
							"Neco je spatne. Neni vybran zadny prvek");
				} else {
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();
					if (minIndex == maxIndex && lsm.isSelectedIndex(minIndex) && isAdjusting) {
						beExported = exportList.get(minIndex).clone();
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

		JButton del = new JButton(sk.str.get("export"));
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (beExported != null) {
					FileHandler fh=FileHandler.showDialog(sk.str.get("Db_ext"), sk.str.get("Db_text"), true);
					DatabaseWrapper db=sk.wrappers.new DatabaseWrapper();
					db.Version=sk.FILEVERSION;
					db.addEntity(beExported, null);
					try {
						fh.write(db);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					beExported = null;
				}
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

}
