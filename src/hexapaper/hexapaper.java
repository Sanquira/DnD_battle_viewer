package hexapaper;

import hexapaper.Listeners.HPListenery;
import hexapaper.source.HPSklad;
import hexapaper.source.HPStrings;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import dungeonmapper.source.DMSklad;

public class hexapaper extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame HPfrm;
	HPListenery lis = new HPListenery();
	static HPSklad sk = HPSklad.getInstance();

	private JScrollPane hraciplsc = new JScrollPane();

	public hexapaper() {
		sk.init();
		HPfrm = this;
		setTitle("Hexapaper " + sk.VERSION);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(lis.new KonecHardListener());

		setLayout(new BorderLayout());
		initializace();
		setVisible(true);
	}

	private void initializace() {

		hraciPlocha();

		add(menu(), BorderLayout.NORTH);
		add(hraciplsc);
		add(sk.RMenu, BorderLayout.EAST);
	}

	private JMenuBar menu() {
		JMenuBar HlavniMenu = new JMenuBar();

		JMenu hraMenu = new JMenu(sk.str.get("hra"));
		JMenuItem novyPaper = new JMenuItem(sk.str.get("newPaper"));
		JMenuItem nactiPaper = new JMenuItem(sk.str.get("loadPaper"));
		JMenuItem ulozPaper = new JMenuItem(sk.str.get("savePaper"));
		JMenuItem Server = new JMenuItem("Server");
		JMenuItem Client = new JMenuItem("Client");
		JMenuItem konec = new JMenuItem(sk.str.get("konec"));

		novyPaper.addActionListener(lis.new NovaListener());
		Client.addActionListener(lis.new Client());
		nactiPaper.addActionListener(lis.new NactiListener());
		ulozPaper.addActionListener(lis.new UlozListener());
		konec.addActionListener(lis.new KonecListener());

		hraMenu.add(novyPaper);
		hraMenu.add(nactiPaper);
		hraMenu.add(ulozPaper);
		hraMenu.add(Client);
		hraMenu.add(Server);
		hraMenu.add(konec);

		JMenu upravy = new JMenu(sk.str.get("upravy"));
		JMenuItem pridejArt = new JMenuItem(sk.str.get("addArt"));
		JMenuItem pridejPost = new JMenuItem(sk.str.get("addPost"));
		JMenu exportAP = new JMenu(sk.str.get("exportAP"));
		JMenuItem exportArtDat = new JMenuItem(sk.str.get("exportArtDat"));
		JMenuItem exportPostDat = new JMenuItem(sk.str.get("exportPostDat"));
		JMenuItem exportArtOne = new JMenuItem(sk.str.get("exportArtOne"));
		JMenuItem exportPostOne = new JMenuItem(sk.str.get("exportPostOne"));

		JMenuItem importAP = new JMenuItem(sk.str.get("importAP"));

		pridejArt.addActionListener(lis.new PridejArtefakt());
		pridejPost.addActionListener(lis.new PridejPostavu());

		exportArtDat.addActionListener(lis.new ExportArtDat());
		exportArtOne.addActionListener(lis.new ExportArtOne());
		exportPostDat.addActionListener(lis.new ExportPostDat());
		exportPostOne.addActionListener(lis.new ExportPostOne());

		importAP.addActionListener(lis.new ImportAP());

		upravy.add(pridejArt);
		upravy.add(pridejPost);

		exportAP.add(exportArtOne);
		exportAP.add(exportArtDat);
		exportAP.add(exportPostOne);
		exportAP.add(exportPostDat);
		upravy.add(exportAP);

		upravy.add(importAP);

		JMenu addons = new JMenu(sk.str.get("addons"));

		JMenuItem kostka = new JMenuItem(sk.str.get("kostka"));

		kostka.addActionListener(lis.new KostkaListener());

		addons.add(kostka);

		HlavniMenu.add(hraMenu);
		HlavniMenu.add(upravy);
		HlavniMenu.add(addons);

		// TODO Ostranit tohle tlacitko
		System.err.println("Nezapomen odstranit tlacitko v hexapaper u TODO");
		JButton ulozLangy = new JButton("Uloz langy DOCASNE");
		ulozLangy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sk.str.saveLang();
				DMSklad sk1 = DMSklad.getInstance();
				sk1.init();
				sk1.str.saveLang();
			}
		});
		HlavniMenu.add(ulozLangy);

		return HlavniMenu;
	}

	private void hraciPlocha() {
		sk.hraciPlocha.addMouseListener(lis.new HraciPlochaListener());
		sk.hraciPlocha.addMouseMotionListener(lis.new HraciPlochaListener());
		hraciplsc.getVerticalScrollBar().setUnitIncrement(16);
		hraciplsc.getHorizontalScrollBar().setUnitIncrement(16);
		hraciplsc.setViewportView(sk.hraciPlocha);
		sk.scroll=hraciplsc;
	}
}
