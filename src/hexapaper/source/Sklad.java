package hexapaper.source;

import hexapaper.entity.Artefact;
import hexapaper.entity.Entity;
import hexapaper.entity.Postava;
import hexapaper.gui.Gprvky;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.PraveMenu;

import java.util.ArrayList;

public class Sklad {

	private static Sklad instance = null;

	public Gprvky prvky;
	public HraciPlocha hraciPlocha;
	public PraveMenu RMenu;

	public int RADIUS = 25;
	public int gridSl = 0;
	public int gridRa = 0;
	public ArrayList<Entity> souradky;
	public boolean insertingEntity = false;
	public Location LocDontCare = new Location(RADIUS, RADIUS, 0);
	public ArrayList<Entity> databazePostav = new ArrayList<>();
	public ArrayList<Entity> databazeArtefaktu = new ArrayList<>();
	public Entity insertedEntity;
	public boolean hidePlayerColor = false;
	public boolean hideNPCColor = false;
	public boolean repeatableInsert = false;

	public final String VERSION = "v0.1";

	protected Sklad() {
	}

	public void init() {
//		gridSl = 10;
//		gridRa = 10;
		
		hraciPlocha = new HraciPlocha();
		prvky = new Gprvky();

		// // //////////////////////////////////////////////
		// ArrayList<PropPair> prp = new ArrayList<>();
		// prp.add(new PropPair("Obsah", "temné nic"));
		// databazeArtefaktu.add(new Artefact("Truhla", LocDontCare, prp));
		//
		// prp = new ArrayList<>();
		// prp.add(new PropPair("Epickost", "HOOOOODNE"));
		// databazeArtefaktu.add(new Artefact("Meč", LocDontCare, prp));
		//
		// prp = new ArrayList<>();
		// prp.add(new PropPair("Rasa", "Elf"));
		// prp.add(new PropPair("Zbran", "Tank"));
		// databazePostav.add(new Postava("Gold", LocDontCare, false, prp));
		//
		// prp = new ArrayList<>();
		// prp.add(new PropPair("Rasa", "Trpajzlik"));
		// prp.add(new PropPair("Zbran", "Sekera"));
		// databazePostav.add(new Postava("Ragnar", LocDontCare, true, prp));
		// int i = 0, j = 0, k = 90;
		// for (i = k; i < k + databazeArtefaktu.size(); i++) {
		// hraciPlocha.insertEntity(i, databazeArtefaktu.get(j).clone());
		// j++;
		// }
		// k = i;
		// j = 0;
		// for (; i < k + databazePostav.size(); i++) {
		// hraciPlocha.insertEntity(i, databazePostav.get(j).clone());
		// j++;
		// }
		// // ////////////////////////////////////////////////////////

		RMenu = new PraveMenu();
	}

	public static Sklad getInstance() {
		if (instance == null) {
			instance = new Sklad();
		}
		return instance;
	}

	public void setupInserting(Entity insert, boolean repeat) {
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

	public static class PropPair implements Cloneable {
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