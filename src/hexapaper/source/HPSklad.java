package hexapaper.source;

import hexapaper.hexapaper;
import hexapaper.Listeners.HPListenery;
import hexapaper.entity.HPEntity;
import hexapaper.file.Wrappers;
import hexapaper.file.Wrappers.DatabaseWrapper;
import hexapaper.file.Wrappers.HexWrapper;
import hexapaper.gui.ColorPicker;
import hexapaper.gui.Gprvky;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.HPRightMenu;
import hexapaper.gui.PJGUI;
import hexapaper.language.HPStrings;
import hexapaper.network.server.HexaClient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import addons.dice.LogWindow;
import core.Location;
import core.file.Config;
import core.file.FileHandler;

public class HPSklad {

	private static HPSklad instance = null;

	public Gprvky prvky;
	public HraciPlocha hraciPlocha;
	public HPRightMenu RMenu;
	public Config c = Config.getInstance();
	
	public JScrollPane scroll;
	public Location LocDontCare = new Location(c.RADIUS, c.RADIUS, 0);
	public HPEntity insertedEntity;
	public JMenu GameMenu;
	public JMenu ExportMenu;
	public JLabel connected;
	public JLabel position;
	public PJGUI PJInfo;
	public JLabel statusBar;
	public ColorPicker clr;
	public Color color = Color.WHITE;
	
	public Wrappers wrappers=new Wrappers();

	public ArrayList<HPEntity> souradky;
	public ArrayList<HPEntity> databazePostav = new ArrayList<>();
	public ArrayList<HPEntity> databazeArtefaktu = new ArrayList<>();
	private HashMap<Component,LabelSystem> labels= new HashMap<Component,LabelSystem>();
//	public ArrayList<Component> needPJ = new ArrayList<>();
//	public ArrayList<Component> needConnect = new ArrayList<>();

	public boolean hidePlayerColor = false;
	public boolean hideNPCColor = false;
	public boolean repeatableInsert = false;
	public boolean canEvent = false;
	public boolean isConnected = false;
	public boolean isPJ = false;
	public boolean insertingEntity = false;
	public boolean notServer = false;
	public boolean colorAdd = false;
	public boolean multipj = isPJ && isConnected;
	public boolean singleorPJ = !isConnected || multipj;
	
	public HexaClient client;
	public HPStrings str;
	
	public final static String VERSION = "v0.5d";
	public final static String FILEVERSION = "0.2";
	public final static String[] Languages = {"HPStrings","Czech"};
	
	public enum LabelSystem{
		SingleOnly,MultiOnly,SingleOrPJ,MultiAndPJ
	}
	public void send(Serializable o, String header, boolean PJ) {
		if (isConnected) {
			if (PJ && isPJ) {
				client.send(o, header);
				return;
			}
			if (!PJ && !isPJ) {
				client.send(o, header);
				return;
			}
		}
	}
	
	public boolean checkVersion(String Version){
		if(Version!=null){
			if(Version.equals(HPSklad.FILEVERSION)){
				return true;
			}
		}
		Object[] options = {str.OldFileVersionYes,str.OldFileVersionNo};
		int n = JOptionPane.showOptionDialog(null, str.OldFileVersionText,
					str.OldFileVersionHeader,
					JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,null);
		if(n==JOptionPane.YES_OPTION){
			return true;
		}
		return false;
	}
	
	public void reload() {
		initLoad(souradky);
	}

	protected HPSklad() {
	}

	public void colorJMenu() {
		boolean singleonly = !isConnected;
		multipj = isConnected && isPJ;
		singleorPJ = singleonly || multipj;
		for(Entry<Component, LabelSystem> entry: labels.entrySet()){
			boolean enabled = false;
			switch(entry.getValue()){
			case SingleOnly:
				enabled = singleonly;
				break;
			case MultiAndPJ:
				enabled = multipj;
				break;
			case MultiOnly:
				enabled = !singleonly;
				break;
			case SingleOrPJ:
				enabled = singleorPJ;
				break;				
			}
			entry.getKey().setEnabled(enabled);
		}
//		Boolean PJactive = isPJ;
//		Boolean Connectactive = true;
//		if (notServer) {
//			PJactive = true;
//		}
//		if (!isConnected){
//			Connectactive = false;
//		}
//		for (Component item : needPJ) {
//			item.setEnabled(PJactive);
//			item.repaint();
//		}
//		for (Component item : needConnect) {
//			item.setEnabled(Connectactive);
//			item.repaint();
//		}
	}
	public void addButton(Component c,LabelSystem l){
		labels.put(c, l);
	}
	public void SetupLang(String lang){
//		str = new English();
		try {
			str = HPStrings.loadFile(lang);
		} catch (InstantiationException | ClassNotFoundException
				| IllegalAccessException e) {
			e.printStackTrace();
			str = new HPStrings();
			System.out.println("Unable to load "+lang+" localization");
		}
	}
	public void init() {
		System.out.println(c.Language);
		SetupLang(c.Language);
		hraciPlocha = new HraciPlocha();

		prvky = new Gprvky();
		RMenu = new HPRightMenu();
	}
	
//	public void initLang(){
//		HPStrings.load();
//	}
	
	public static HPSklad getInstance() {
		if (instance == null) {
			instance = new HPSklad();
		}
		return instance;
	}

	public void setupInserting(HPEntity insert, boolean repeat) {
		if (insert == null) {
			insertedEntity = null;
			insertingEntity = false;
			repeatableInsert = false;
		} else {
			insertedEntity = insert;
			insertingEntity = true;
			repeatableInsert = repeat;
		}
	}

	public void setupColor(boolean isActive) {
		this.colorAdd = isActive;
	}

	public void initLoad(ArrayList<HPEntity> souradky) {
		hraciPlocha = new HraciPlocha(c.gridSl,c.gridRa,c.RADIUS);
		for (int i = 0; i < souradky.size(); i++) {
			// if (souradky.get(i) instanceof FreeSpace) {
			hraciPlocha.insertEntity(i, souradky.get(i), true);
			// }
		}	
		hexapaper.HPfrm.repaint();
		HPListenery lis = new HPListenery();
		hraciPlocha.addMouseListener(lis.new HraciPlochaListener());
		hraciPlocha.addMouseMotionListener(lis.new HraciPlochaListener());
		scroll.setViewportView(hraciPlocha);
		scroll.getViewport().addChangeListener(lis.new ScrollListener());
		odblokujListenery();
	}

	public void odblokujListenery() {
		canEvent = true;
	}

	public void updatePosition(int x1, int y1) {
		Point point = getPosition(x1,y1);
		position.setText(str.Position + point.x + "," + point.y);
		//position.repaint();
	}
	public Point getPosition(int x1, int y1){
		double r = Math.cos(Math.toRadians(30)) * c.RADIUS;
		return new Point((int) Math.round(((x1 / c.RADIUS) - 1) * (2 / 3.) + 1),(int) Math.round(((y1 / r) - ((y1 / r) + 1) % 2 - 1) / 2));
	}
	public void updateConnect() {
		if (isConnected && isPJ) {
			connected.setForeground(Color.BLUE);
			notServer = false;
		}
		if (isConnected && !isPJ) {
			connected.setForeground(Color.GREEN);
			notServer = true;
		}
		if (!isConnected) {
			connected.setForeground(Color.RED);
			notServer = false;
		}
		connected.setText(str.ConnectLabel + "{" + isConnected + "," + isPJ + "}");
		colorJMenu();
	}
	public void setIcon(JFrame frame){
		try {
			InputStream stream = hexapaper.class.getResourceAsStream( File.separatorChar+"icon.png" );
			BufferedImage image = ImageIO.read( stream );
			frame.setIconImage(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void connect(){
		if(client!=null){
			try {
				System.out.println("trying to disc");
				client.disconnect();
				System.out.println("Dsc");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client=null;
		}
		client = new HexaClient();
		try {
			client.connect(c.IP, c.port, c.lastName);
			isConnected=true;
			updateConnect();			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    str.ConnectError+e.getMessage(),
				    str.IOError,
				    JOptionPane.ERROR_MESSAGE);		
		}
		PJInfo = new PJGUI(client);	
	}
	public LogWindow getDiceLog() {
		return PJInfo.getDice();
	}

	public LogWindow getPJLog() {
		return PJInfo.getInfo();
	}

	public void setStatus(String Message) {
		statusBar.setText(Message);
	}
	
	public void saveArtefacts(FileHandler fh) throws IOException{
		if(databazeArtefaktu.size()>0 && fh!=null){
			DatabaseWrapper db = wrappers.new DatabaseWrapper();
			db.Version=FILEVERSION;
			db.addEntities(databazeArtefaktu);
			if(fh!=null){
				fh.write(db);
			}
		}
	}
	public void saveCharacters(FileHandler fh) throws IOException{
		if(databazePostav.size()>0 && fh!=null){
			DatabaseWrapper db = wrappers.new DatabaseWrapper();
			db.Version=FILEVERSION;
			db.addEntities(databazePostav);
			fh.write(db);
		}
	}
	public void saveMap(FileHandler fh) throws IOException{
		if(souradky.size()>0 && fh!=null){
			HexWrapper HWrapper=wrappers.new HexWrapper(c.gridSl,c.RADIUS,c.gridRa,FILEVERSION);
			HWrapper.addEntities(souradky);
			fh.write(HWrapper);
		}
	}
	public static class prvekkNN implements Cloneable {
		private double x1, y1, vzd;
		private int idx;

		public prvekkNN(int index, double xp, double yp, double dist) {
			x1 = xp;
			y1 = yp;
			vzd = dist;
			idx = index;
		}

		public double getX1() {
			return x1;
		}

		public void setX1(double x1) {
			this.x1 = x1;
		}

		public double getY1() {
			return y1;
		}

		public void setY1(double y1) {
			this.y1 = y1;
		}

		public double getVzd() {
			return vzd;
		}

		public void setVzd(double vzd) {
			this.vzd = vzd;
		}

		public int getIdx() {
			return idx;
		}

		public void setIdx(int idx) {
			this.idx = idx;
		}

		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		public String toString() {
			return idx + ", " + x1 + ", " + y1 + ", " + vzd + "; ";
		}
	}

	public static class PropPair implements Cloneable, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2280106317511129808L;
		public String name;
		public String value;

		public PropPair(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return name + ": " + value;
		}

		@Override
		public PropPair clone() throws CloneNotSupportedException {
			return (PropPair) super.clone();
		}

	}

}