package dungeonmapper.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import core.DeselectButtonGroup;
import dungeonmapper.source.DMSklad;

public class DMRightMenu extends JPanel {

	DMSklad sk = DMSklad.getInstance();
	HashMap<String, Icon> icons = new HashMap<>();

	public DMRightMenu() {
		loadIcon();
		setBackground(Color.yellow);
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		// gbc.weighty = 2;
		gbc.gridheight = 1;
		JPanel panelDrawOrder = panelDrawOrder();
		gbl.setConstraints(panelDrawOrder, gbc);
		add(panelDrawOrder);

		gbc.gridx = 0;
		gbc.gridy = 1;
		// gbc.weightx = 1;
		// gbc.weighty = 2;
		// gbc.gridheight = 1;
		JPanel panelDrawShape = panelDrawShape();
		gbl.setConstraints(panelDrawShape, gbc);
		add(panelDrawShape);

	}

	private JPanel panelDrawOrder() {
		JPanel PDO = new JPanel();
		PDO.setLayout(new FlowLayout(FlowLayout.LEFT));
		PDO.setBorder(new TitledBorder(sk.str.get("drawOrder")));

		ButtonGroup grpOrder = new ButtonGroup();

		for (String string : sk.drawOrders) {
			JComponent tmp = makeToggleButton(string);
			PDO.add(tmp);
			grpOrder.add((AbstractButton) tmp);
		}

		grpOrder.setSelected(grpOrder.getElements().nextElement().getModel(), true);

		return PDO;
	}

	private JPanel panelDrawShape() {
		JPanel PDS = new JPanel();
		PDS.setBorder(new TitledBorder(sk.str.get("drawShape")));
		PDS.setLayout(new FlowLayout(FlowLayout.LEFT));

		DeselectButtonGroup grpOrder = new DeselectButtonGroup();

		for (String string : sk.drawShapes) {
			JComponent tmp = makeToggleButton(string);
			PDS.add(tmp);
			grpOrder.add((AbstractButton) tmp);
		}

		return PDS;
	}

	private JComponent makeToggleButton(String name) {
		JToggleButton tmp = new JToggleButton(icons.get("icon" + name));
		tmp.setToolTipText(sk.str.get(name + "Tooltip"));
		tmp.setBackground(Color.gray);
		tmp.setPreferredSize(new Dimension(icons.get("icon" + name).getIconHeight(),
				icons.get("icon" + name).getIconWidth()));
		tmp.setUI(new MyUI(Color.white));
		return tmp;
	}

	private void loadIcon() {
		try {
			icons.put("icondraw", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/draw.png"))));
			icons.put("iconerase", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/erase.png"))));
			icons.put("iconnegate", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/negate.png"))));
			icons.put("iconrect", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/rect.png"))));
			icons.put("iconcirc", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/circ.png"))));
			icons.put("iconpen", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/pen.png"))));
			icons.put("iconSU", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/stairUP.png"))));
			icons.put("iconSD", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/stairDown.png"))));
			icons.put("iconhole", new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream("/images/hole.png"))));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class MyUI extends javax.swing.plaf.metal.MetalToggleButtonUI
	{
		Color color;

		public MyUI(Color c) {
			color = c;
		}

		public Color getSelectColor() {
			return color;
		}
	}

}
