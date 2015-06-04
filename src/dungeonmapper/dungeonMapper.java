package dungeonmapper;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dungeonmapper.gui.DMRightMenu;
import dungeonmapper.gui.DrawPlane;
import dungeonmapper.listeners.DMListenery;
import dungeonmapper.source.DMSklad;

import javax.swing.JButton;
import core.JNumberTextField;

public class dungeonMapper extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame DMfrm;
	private static DMSklad sk = DMSklad.getInstance();
	private DMListenery lis = new DMListenery();
	public JNumberTextField layerField;

	public dungeonMapper() {
		sk.init();
		DMfrm = this;
		setTitle("Dungeon Mapper " + sk.VERSION);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(lis.new KonecHardListener());
		getContentPane().setLayout(new BorderLayout());
		initializace();
		setVisible(true);
		sk.DPSC.getHorizontalScrollBar().setValue(sk.COLS * sk.CSIZE / 2);
		sk.DPSC.getVerticalScrollBar().setValue(sk.ROWS * sk.CSIZE / 2);
	}

	private void initializace() {
		// hraciPlocha();

		getContentPane().add(drawPanel());
		getContentPane().add(menu(), BorderLayout.NORTH);
		getContentPane().add(RMenu(), BorderLayout.EAST);

	}

	private JMenuBar menu() {
		JMenuBar MB = new JMenuBar();
		JMenu hra = new JMenu(sk.str.gameMenu);
		MB.add(hra);

		// JMenuItem konec = new JMenuItem(sk.str.get("endGame"));
		// konec.addActionListener(lis.new KonecListener());
		// MB.add(konec);

		JMenuItem newPaper = new JMenuItem(sk.str.newGame);
		newPaper.addActionListener(lis.new NewPaper());
		hra.add(newPaper);

		JMenuItem save = new JMenuItem(sk.str.saveGame);
		save.addActionListener(lis.new SaveGame());
		hra.add(save);

		JMenuItem load = new JMenuItem(sk.str.loadGame);
		load.addActionListener(lis.new LoadGame());
		hra.add(load);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		MB.add(panel);

		JButton plusButton = new JButton("+");
		plusButton.setPreferredSize(new Dimension(41, 20));
		plusButton.addActionListener(lis.new ChangeLayer(1));
		panel.add(plusButton);

		layerField = new JNumberTextField();
		// layerField.setFont(new Font("Sitka Small", Font.PLAIN, 9));
		layerField.setInt(sk.drawPlane.getChsnLay());
		layerField.addActionListener(lis.new SetLayer());
		panel.add(layerField);
		layerField.setColumns(6);

		JButton minusButton = new JButton("-");
		minusButton.addActionListener(lis.new ChangeLayer(-1));
		minusButton.setPreferredSize(new Dimension(41, 20));
		panel.add(minusButton);
		// TODO Dodelat menu
		sk.layer = layerField;
		return MB;
	}

	private Component RMenu() {
		JScrollPane RMSP = new JScrollPane();
		// TODO Dodelat prave menu
		RMSP.setViewportView(new DMRightMenu());
		RMSP.setPreferredSize(new Dimension(260, 100));
		RMSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		RMSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return RMSP;
	}

	private JScrollPane drawPanel() {
		sk.drawPlane = new DrawPlane();
		sk.DPSC = new JScrollPane();
		sk.DPSC.setViewportView(sk.drawPlane);
		sk.DPSC.getViewport().addChangeListener(lis.new ScrollListener());
		sk.DPSC.getVerticalScrollBar().setUnitIncrement(8);
		sk.DPSC.getHorizontalScrollBar().setUnitIncrement(8);
		return sk.DPSC;
	}
}
