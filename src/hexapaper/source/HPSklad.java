package hexapaper.source;

import hexapaper.hexapaper;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.HPEntity;
import hexapaper.gui.Gprvky;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.HPRightMenu;
import hexapaper.network.server.HexaClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JScrollPane;

import core.LangFile;
import core.Location;

public class HPSklad {

	private static HPSklad instance = null;

	public Gprvky prvky;
	public HraciPlocha hraciPlocha;
	public HPRightMenu RMenu;

	public int RADIUS = 25;
	public int gridSl = 0;
	public int gridRa = 0;
	
	public JScrollPane scroll;
	public Location LocDontCare = new Location(RADIUS, RADIUS, 0);
	public HPEntity insertedEntity;
	public JMenu GameMenu;
	public JMenu ExportMenu;
	
	public ArrayList<HPEntity> souradky;
	public ArrayList<HPEntity> databazePostav = new ArrayList<>();
	public ArrayList<HPEntity> databazeArtefaktu = new ArrayList<>();
	
	public boolean hidePlayerColor = false;
	public boolean hideNPCColor = false;
	public boolean repeatableInsert = false;
	public boolean canEvent = false;
	public boolean isConnected = false;
	public boolean PJ=false;
	public boolean insertingEntity = false;
	
	public HexaClient client;
	public LangFile str;

	public final String VERSION = "v0.1";
	
	public void send(Object o,String header) throws IOException{
		if(isConnected){
			client.send(o, header);
		}
	}
	
	public void reload(){
		initLoad(souradky);
	}
	
	protected HPSklad() {
	}
	
	public void init() {
		str = new LangFile(HPStrings.class);
		str.loadLang();
	
		hraciPlocha = new HraciPlocha();
				
		prvky = new Gprvky();
		RMenu = new HPRightMenu();
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

	public void initLoad(ArrayList<HPEntity> souradky) {
		hraciPlocha = new HraciPlocha();
		for (int i = 0; i < souradky.size(); i++) {
			if (souradky.get(i) instanceof FreeSpace) {
			} else {
				// System.out.println(i);
				hraciPlocha.insertEntity(i, souradky.get(i), true);
			}
		}		
		hexapaper.HPfrm.repaint();
		//scroll.setViewportView(hraciPlocha);
		odblokujListenery();
	}

	public void odblokujListenery() {
		canEvent = true;
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

	public static class PropPair implements Cloneable,Serializable {
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