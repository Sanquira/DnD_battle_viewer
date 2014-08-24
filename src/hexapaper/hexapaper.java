package hexapaper;

import hexapaper.Listeners.Listenery;
import hexapaper.source.Sklad;
import hexapaper.source.Strings;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

public class hexapaper extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frm;
	Listenery lis = new Listenery();
	static Sklad sk = Sklad.getInstance();
	private JMenuBar HlavniMenu = new JMenuBar();

	private JScrollPane hraciplsc = new JScrollPane();

	public hexapaper() {
		setTitle("Hexapapír " + sk.VERSION);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		initializace();
		setVisible(true);
	}

	private void initializace() {

		menu();
		hraciPlocha();

		add(HlavniMenu, BorderLayout.NORTH);
		add(hraciplsc);
		add(sk.RMenu, BorderLayout.EAST);
	}

	private void menu() {
		JButton novaHra = new JButton(Strings.newPaper);
		JButton ulozHru = new JButton(Strings.savePaper);
		JButton nactiHru = new JButton(Strings.loadPaper);
		JButton pridejArt = new JButton(Strings.addArt);
		JButton pridejPost = new JButton(Strings.addPost);
		JButton konecHry = new JButton(Strings.konec);

		novaHra.addActionListener(lis.new NovaListener());
		ulozHru.addActionListener(lis.new UlozListener());
		nactiHru.addActionListener(lis.new NactiListener());
		pridejArt.addActionListener(lis.new PridejArtefakt());
		pridejPost.addActionListener(lis.new PridejPostavu());
		konecHry.addActionListener(lis.new KonecListener());

		ulozHru.setEnabled(false);
		nactiHru.setEnabled(false);

		HlavniMenu.add(novaHra);
		HlavniMenu.add(ulozHru);
		HlavniMenu.add(nactiHru);
		HlavniMenu.add(pridejArt);
		HlavniMenu.add(pridejPost);
		HlavniMenu.add(konecHry);
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
