package dungeonmapper;

import hexapaper.Listeners.HPListenery.ScrollListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import core.Grids;
import dungeonmapper.gui.DMRightMenu;
import dungeonmapper.gui.DrawPlane;
import dungeonmapper.listeners.DMListenery;
import dungeonmapper.source.DMSklad;

public class dungeonMapper extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame DMfrm;
	private static DMSklad sk = DMSklad.getInstance();
	private DMListenery lis = new DMListenery();

	public dungeonMapper() {
		sk.init();
		DMfrm = this;
		setTitle("Dungeon Mapper " + sk.VERSION);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(lis.new KonecHardListener());
		setLayout(new BorderLayout());
		initializace();
		setVisible(true);
	}

	private void initializace() {
		// hraciPlocha();

		add(menu(), BorderLayout.NORTH);
		add(drawPanel());
		add(RMenu(), BorderLayout.EAST);

	}

	private JMenuBar menu() {
		JMenuBar MB = new JMenuBar();
		JMenu hra = new JMenu(sk.str.get("gameMenu"));
		MB.add(hra);

		JMenuItem konec = new JMenuItem(sk.str.get("endGame"));
		konec.addActionListener(lis.new KonecListener());
		MB.add(konec);
		// TODO Dodelat menu
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
