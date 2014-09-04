package hexapaper;

import hexapaper.Listeners.HPListenery;
import hexapaper.source.HPSklad;
import hexapaper.source.HPStrings;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class hexapaper extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frm;
	HPListenery lis = new HPListenery();
	static HPSklad sk = HPSklad.getInstance();

	private JScrollPane hraciplsc = new JScrollPane();

	public hexapaper() {
		sk.init();
		frm = this;
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

		JMenu hraMenu = new JMenu(HPStrings.get("hra"));
		JMenuItem novyPaper = new JMenuItem(HPStrings.get("newPaper"));
		JMenuItem nactiPaper = new JMenuItem(HPStrings.get("loadPaper"));
		JMenuItem ulozPaper = new JMenuItem(HPStrings.get("savePaper"));
		JMenuItem konec = new JMenuItem(HPStrings.get("konec"));

		novyPaper.addActionListener(lis.new NovaListener());
		nactiPaper.addActionListener(lis.new NactiListener());
		ulozPaper.addActionListener(lis.new UlozListener());
		konec.addActionListener(lis.new KonecListener());

		hraMenu.add(novyPaper);
		hraMenu.add(nactiPaper);
		hraMenu.add(ulozPaper);
		hraMenu.add(konec);

		JMenu upravy = new JMenu(HPStrings.get("upravy"));
		JMenuItem pridejArt = new JMenuItem(HPStrings.get("addArt"));
		JMenuItem pridejPost = new JMenuItem(HPStrings.get("addPost"));
		JMenu exportAP = new JMenu(HPStrings.get("exportAP"));
		JMenuItem exportArtDat = new JMenuItem(HPStrings.get("exportArtDat"));
		JMenuItem exportPostDat = new JMenuItem(HPStrings.get("exportPostDat"));
		JMenuItem exportArtOne = new JMenuItem(HPStrings.get("exportArtOne"));
		JMenuItem exportPostOne = new JMenuItem(HPStrings.get("exportPostOne"));

		JMenuItem importAP = new JMenuItem(HPStrings.get("importAP"));

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

		JMenu addons = new JMenu(HPStrings.get("addons"));

		JMenuItem kostka = new JMenuItem(HPStrings.get("kostka"));

		kostka.addActionListener(lis.new KostkaListener());

		addons.add(kostka);

		HlavniMenu.add(hraMenu);
		HlavniMenu.add(upravy);
		HlavniMenu.add(addons);
		return HlavniMenu;
	}

	private void hraciPlocha() {
		sk.hraciPlocha.addMouseListener(lis.new HraciPlochaListener());
		sk.hraciPlocha.addMouseMotionListener(lis.new HraciPlochaListener());
		hraciplsc.getVerticalScrollBar().setUnitIncrement(16);
		hraciplsc.getHorizontalScrollBar().setUnitIncrement(16);
		hraciplsc.setViewportView(sk.hraciPlocha);
	}
}
