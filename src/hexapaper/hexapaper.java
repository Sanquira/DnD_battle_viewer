package hexapaper;

import hexapaper.Listeners.HPListenery;
import hexapaper.gui.ColorPicker;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.LabelSystem;

import java.awt.BorderLayout;
import java.awt.Color;

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
		setTitle("Hexapaper " + HPSklad.VERSION);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(lis.new KonecHardListener());

		getContentPane().setLayout(new BorderLayout());
		initializace();
		setVisible(true);
		Config.loadTmp();
		sk.setIcon(this);
		
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

		JMenu hraMenu = new JMenu(sk.str.GameMenu);
		JMenuItem novyPaper = new JMenuItem(sk.str.newPaper);
		JMenuItem nactiPaper = new JMenuItem(sk.str.loadPaper);
		JMenuItem ulozPaper = new JMenuItem(sk.str.savePaper);
		//JMenuItem Server = new JMenuItem("Server");
		JMenuItem Client = new JMenuItem("Client");
		JMenuItem Disconnect = new JMenuItem(sk.str.disconnect);
		JMenuItem konec = new JMenuItem(sk.str.End);

		novyPaper.addActionListener(lis.new NovaListener());
		Client.addActionListener(lis.new Client());
		nactiPaper.addActionListener(lis.new NactiListener());
		ulozPaper.addActionListener(lis.new UlozListener());
		konec.addActionListener(lis.new KonecListener());
		Disconnect.addActionListener(lis.new Dsc());

		hraMenu.add(novyPaper);
		hraMenu.add(nactiPaper);
		hraMenu.add(ulozPaper);
		hraMenu.add(Client);
		hraMenu.add(Disconnect);
		hraMenu.add(konec);

		JMenu upravy = new JMenu(sk.str.EditMenu);
		JMenuItem pridejArt = new JMenuItem(sk.str.addArt);
		JMenuItem pridejPost = new JMenuItem(sk.str.addPost);
		JMenu exportAP = new JMenu(sk.str.exportAP);
		JMenuItem exportArtDat = new JMenuItem(sk.str.exportArtDat);
		JMenuItem exportPostDat = new JMenuItem(sk.str.exportPostDat);
		JMenuItem exportArtOne = new JMenuItem(sk.str.exportArtOne);
		JMenuItem exportPostOne = new JMenuItem(sk.str.exportPostOne);

		JMenuItem importAP = new JMenuItem(sk.str.importAP);

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

		JMenu addons = new JMenu(sk.str.utility);

		JMenuItem kostka = new JMenuItem(sk.str.Dice);
		JMenuItem PJInfo = new JMenuItem(sk.str.PJInfo);
		//JMenuItem ExportLang = new JMenuItem(sk.str.ExportLang);
		//JMenuItem ColorPicker = new JMenuItem("ColorPicker");

		kostka.addActionListener(lis.new DiceListener());
		PJInfo.addActionListener(lis.new PJInfoListener());
		//ExportLang.addActionListener(lis.new ExportLangListener());

		addons.add(PJInfo);
		addons.add(kostka);;
		
		JMenu lang = new JMenu(sk.str.LangBar);
		addLangs(lang);
		HlavniMenu.add(hraMenu);
		HlavniMenu.add(upravy);
		HlavniMenu.add(addons);
		HlavniMenu.add(lang);
		
		sk.addButton(PJInfo, LabelSystem.MultiAndPJ);
		sk.addButton(novyPaper, LabelSystem.SingleOrPJ);
		sk.addButton(nactiPaper, LabelSystem.SingleOrPJ);
		sk.addButton(pridejArt, LabelSystem.SingleOrPJ);
		sk.addButton(pridejPost, LabelSystem.SingleOrPJ);
		sk.addButton(Disconnect, LabelSystem.MultiOnly);
		sk.colorJMenu();
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
		
		JLabel connected = new JLabel(sk.str.ConnectLabel+"{"+sk.isConnected+","+sk.isPJ+"}");
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

	private void addLangs(JMenu menu) {		
		for(String lang:HPSklad.Languages){
			JMenuItem item = new JMenuItem(getLangName(lang));
			if(lang.equals(sk.c.Language)){
				item.setForeground(Color.GREEN);
			}
			else{
				item.addActionListener(lis.new ChangeLang(lang,this));
			}
			menu.add(item);
		}
	}
	
	private String getLangName(String lang){
		try {
			Class<?> type = Class.forName("hexapaper.language."+lang);
			return (String) type.getDeclaredField("lang_name").get(null);
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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
