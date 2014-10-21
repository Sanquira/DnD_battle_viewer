package hexapaper.entity;

import hexapaper.source.BPolygon;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import core.Location;

public abstract class HPEntity implements Cloneable,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6970412701137178068L;
	public boolean isColidable;
	public boolean isRotateable;
	public Location loc;
	public ArrayList<BPolygon> prvek = new ArrayList<>();
	public Color background = Color.white;
	public String tag = null;
	protected String name;

	public HPEntity(String name, Location loc, boolean Rotatable, boolean Colidable, ArrayList<BPolygon> prvek) {
		this.name = name;
		this.loc = loc;
		this.isRotateable = Rotatable;
		this.isColidable = Colidable;
		this.prvek = prvek;
		setTag(name);
	}

	public HPEntity(String name, Location loc, boolean Rotatable, boolean Colidable, BPolygon prvek) {
		this.name = name;
		this.loc = loc;
		this.isRotateable = Rotatable;
		this.isColidable = Colidable;
		this.prvek.add(prvek);
		setTag(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HPEntity clone() {
		try {
			HPEntity cloned = (HPEntity) super.clone();
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

	public HPEntity setBcg(Color clr) {
		this.background = clr;
		return this;
	}

	public void setTag(String tag) {
		if (this.tag == null) {
			try {
				this.tag = name.substring(0, 2);
			} catch (StringIndexOutOfBoundsException e) {
				this.tag = name;
			}
		} else {
			this.tag = tag;
		}
	}

	public String getNick() {
		return name;
	}

	public void setNick(String nick) {
		this.name = nick;
	}

	abstract public void recreateGraphics();
}
