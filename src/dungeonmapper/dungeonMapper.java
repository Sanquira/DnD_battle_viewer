package dungeonmapper;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		// TODO Dodelat menu
		return MB;
	}

	private JPanel RMenu() {
		JPanel RM = new JPanel();
		// TODO Dodelat prave menu
		return RM;
	}

	private JScrollPane drawPanel() {
		JScrollPane DPSC = new JScrollPane();
		return DPSC;
	}
}
