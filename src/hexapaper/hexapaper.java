package hexapaper;

import hexapaper.Listeners.HPListenery;
import hexapaper.gui.ColorPicker;
import hexapaper.gui.PJGUI;
import hexapaper.source.HPSklad;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;

import core.file.Config;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

		getContentPane().setLayout(new BorderLayout());
		initializace();
		setVisible(true);
		sk.PJInfo=new PJGUI();	
		Config.loadTmp();
		try {
			InputStream stream = hexapaper.class.getResourceAsStream( File.separatorChar+"icon.png" );
			BufferedImage image = ImageIO.read( stream );
			setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
//		System.err.println("hexapaper 49-54");
//		sk.gridRa = 10;
//		sk.gridSl = 10;
//		sk.init();
//		sk.odblokujListenery();
//		revalidate();
//		repaint();
		
		//logging
//		File f=new File("HexaLog.log");
//		try {
//			FileStreamWriter fs = new FileStreamWriter(f);
//			System.setOut(fs);
//			System.setErr(fs);	
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}	
	}

	private void initializace() {

		hraciPlocha();

		getContentPane().add(menu(), BorderLayout.NORTH);
		getContentPane().add(hraciplsc);
		getContentPane().add(sk.RMenu, BorderLayout.EAST);
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

		JMenu addons = new JMenu(sk.str.get("utility"));

		JMenuItem kostka = new JMenuItem(sk.str.get("kostka"));
		JMenuItem PJInfo = new JMenuItem(sk.str.get("PJInfo"));
		JMenuItem ExportLang = new JMenuItem(sk.str.get("ExportLang"));
		//JMenuItem ColorPicker = new JMenuItem("ColorPicker");

		kostka.addActionListener(lis.new DiceListener());
		PJInfo.addActionListener(lis.new PJInfoListener());
		ExportLang.addActionListener(lis.new ExportLangListener());

		addons.add(PJInfo);
		addons.add(kostka);
		addons.add(ExportLang);

		HlavniMenu.add(hraMenu);
		HlavniMenu.add(upravy);
		HlavniMenu.add(addons);
		
		sk.serverbanned.add(PJInfo);
		sk.serverbanned.add(novyPaper);
		sk.serverbanned.add(nactiPaper);
		sk.serverbanned.add(pridejArt);
		sk.serverbanned.add(pridejPost);
		// TODO Ostranit tohle tlacitko
//		System.err.println("Nezapomen odstranit tlacitko v hexapaper u TODO");
//		JButton ulozLangy = new JButton("Uloz langy DOCASNE");
//		ulozLangy.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				sk.str.saveLang();
//				DMSklad sk1 = DMSklad.getInstance();
//				sk1.init();
//				sk1.str.saveLang();
//			}
//		});
//		HlavniMenu.add(ulozLangy);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		HlavniMenu.add(panel);
		
		JLabel StatusBar = new JLabel();
		StatusBar.setHorizontalAlignment(SwingConstants.RIGHT);
		StatusBar.setPreferredSize(new Dimension(300, 14));
		panel.add(StatusBar);
		
		JLabel connected = new JLabel(sk.str.get("ConnectLabel")+"{"+sk.isConnected+","+sk.isPJ+"}");
		panel.add(connected);
		connected.setForeground (Color.red);
		
		sk.connected=connected;
		sk.statusBar=StatusBar;
		
		JLabel position = new JLabel();
		panel.add(position);
		position.setHorizontalAlignment(SwingConstants.RIGHT);
		sk.position=position;
		
		sk.clr=new ColorPicker();

		return HlavniMenu;
	}

	private void hraciPlocha() {
		sk.hraciPlocha.addMouseListener(lis.new HraciPlochaListener());
		sk.hraciPlocha.addMouseMotionListener(lis.new HraciPlochaListener());
		hraciplsc.getVerticalScrollBar().setUnitIncrement(16);
		hraciplsc.getHorizontalScrollBar().setUnitIncrement(16);
		hraciplsc.setViewportView(sk.hraciPlocha);
		hraciplsc.getViewport().addChangeListener(lis.new ScrollListener());
		sk.scroll=hraciplsc;
	}
}
