package hexapaper;

import hexapaper.Listeners.Listenery;
import hexapaper.source.Sklad;
import hexapaper.source.Strings;

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
	Listenery lis = new Listenery();
	static Sklad sk = Sklad.getInstance();

	private JScrollPane hraciplsc = new JScrollPane();

	public hexapaper() {
		setTitle("Hexapapír " + sk.VERSION);
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

		JMenu hraMenu = new JMenu(Strings.hra);
		JMenuItem novyPaper = new JMenuItem(Strings.newPaper);
		JMenuItem nactiPaper = new JMenuItem(Strings.loadPaper);
		JMenuItem ulozPaper = new JMenuItem(Strings.savePaper);
		JMenuItem konec = new JMenuItem(Strings.konec);

		novyPaper.addActionListener(lis.new NovaListener());
		nactiPaper.addActionListener(lis.new NactiListener());
		ulozPaper.addActionListener(lis.new UlozListener());
		konec.addActionListener(lis.new KonecListener());

		hraMenu.add(novyPaper);
		hraMenu.add(nactiPaper);
		hraMenu.add(ulozPaper);
		hraMenu.add(konec);

		JMenu upravy = new JMenu(Strings.upravy);
		JMenuItem pridejArt = new JMenuItem(Strings.addArt);
		JMenuItem pridejPost = new JMenuItem(Strings.addPost);
		JMenu exportAP = new JMenu(Strings.exportAP);
		JMenuItem exportArtDat = new JMenuItem(Strings.exportArtDat);
		JMenuItem exportPostDat = new JMenuItem(Strings.exportPostDat);
		JMenuItem exportArtOne = new JMenuItem(Strings.exportArtOne);
		JMenuItem exportPostOne = new JMenuItem(Strings.exportPostOne);

		JMenuItem importAP = new JMenuItem(Strings.importAP);

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

		JMenu addons = new JMenu(Strings.addons);

		JMenuItem kostka = new JMenuItem(Strings.kostka);

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

	public static void main(String[] args) throws CloneNotSupportedException {
		 sk.init();
		 frm = new hexapaper();
	}
}
