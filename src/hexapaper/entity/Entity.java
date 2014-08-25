package hexapaper.entity;

import hexapaper.source.Location;
import hexapaper.source.BPolygon;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public abstract class Entity implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isColidable;
	public boolean isRotateable;
	public Location loc;
	public ArrayList<BPolygon> prvek = new ArrayList<>();
	public Color background = Color.white;
	public String tag;
	protected String name;

	public Entity(String name, Location loc, boolean Rotatable, boolean Colidable, ArrayList<BPolygon> prvek) {
		this.name=name;
		this.loc = loc;
		this.isRotateable = Rotatable;
		this.isColidable = Colidable;
		this.prvek = prvek;
		setTag(name);
	}

	public Entity(String name, Location loc, boolean Rotatable, boolean Colidable, BPolygon prvek) {
		this.name=name;
		this.loc = loc;
		this.isRotateable = Rotatable;
		this.isColidable = Colidable;
		this.prvek.add(prvek);
		setTag(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity clone() {
		try {
			Entity cloned = (Entity) super.clone();
			cloned.loc = cloned.loc.clone();
			cloned.prvek = (ArrayList<BPolygon>) cloned.prvek.clone();
			for (int i = 0; i < cloned.prvek.size(); i++) {
				cloned.prvek.set(i, cloned.prvek.get(i).clone());
			}
			return cloned;
		} catch (CloneNotSupportedException e) {
			System.err.println(e);
		}
		return null;
	}

	public Color getBcg() {
		return background;
	}

	public Entity setBcg(Color clr) {
		this.background = clr;
		return this;
	}

	public void setTag(String tag) {
		try {
			this.tag = tag.substring(0, 1);
		} catch (StringIndexOutOfBoundsException e) {
			this.tag = "";
		}
	}

	public String getNick() {
		return name;
	}

	abstract public void recreateGraphics();
}
