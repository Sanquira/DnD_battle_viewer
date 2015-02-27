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
import hexapaper.network.server.HexaClient;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import addons.dice.DiceLog;
import core.LangFile;
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
	public ArrayList<Component> serverbanned = new ArrayList<>();

	public boolean hidePlayerColor = false;
	public boolean hideNPCColor = false;
	public boolean repeatableInsert = false;
	public boolean canEvent = false;
	public boolean isConnected = false;
	public boolean isPJ = false;
	public boolean insertingEntity = false;
	public boolean banned = false;
	public boolean colorAdd = false;

	public HexaClient client;
	public LangFile str;

	public final String VERSION = "v0.3l";
	public final String FILEVERSION = "0.2";

	public void send(Object o, String header, boolean PJ) {
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkVersion(String Version){
		HPSklad sk=HPSklad.getInstance();
		if(Version!=null){
			if(Version.equals(sk.FILEVERSION)){
				return true;
			}
		}
		Object[] options = {sk.str.get("OldFileVersionYes"),sk.str.get("OldFileVersionNo")};
		int n = JOptionPane.showOptionDialog(null, sk.str.get("OldFileVersionText"),
					sk.str.get("OldFileVersionHeader"),
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
		Color color = Color.DARK_GRAY;
		if (banned) {
			color = Color.RED;
		}
		for (Component item : serverbanned) {
			item.setForeground(color);
			item.repaint();
		}
	}

	public void init() {
		initLang();
		hraciPlocha = new HraciPlocha();

		prvky = new Gprvky();
		RMenu = new HPRightMenu();
	}
	
	public void initLang(){
		str = new LangFile(HPStrings.class);
		str.loadLang();
	}
	
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
		colorAdd = isActive;
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

	public void updatePosition(double x1, double y1) {
		double r = Math.cos(Math.toRadians(30)) * c.RADIUS;
		position.setText(str.get("Position") + Math.round(((x1 / c.RADIUS) - 1) * (2 / 3.) + 1) + "," + Math.round(((y1 / r) - ((y1 / r) + 1) % 2 - 1) / 2));
		position.repaint();
	}

	public void updateConnect() {
		if (isConnected && isPJ) {
			connected.setForeground(Color.BLUE);
			banned = false;
		}
		if (isConnected && !isPJ) {
			connected.setForeground(Color.GREEN);
			banned = true;
		}
		if (!isConnected) {
			connected.setForeground(Color.RED);
			banned = false;
		}
		connected.setText(str.get("ConnectLabel") + "{" + isConnected + "," + isPJ + "}");
		colorJMenu();
	}
	public void connect(){
		if(client!=null){
			try {
				client.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client=null;
		}
		client=new HexaClient();
		try {
			client.connect(c.IP, c.port, c.lastName);
			isConnected=true;
			updateConnect();			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    str.get("ConnectError")+e.getMessage(),
				    str.get("IOError"),
				    JOptionPane.ERROR_MESSAGE);		
		}
	}
	public DiceLog getDiceLog() {
		return PJInfo.getLog();
	}

	public DiceLog getPJLog() {
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