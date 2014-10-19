package hexapaper.entity;

import hexapaper.gui.Gprvky;
import hexapaper.source.HPSklad.PropPair;

import java.io.Serializable;
import java.util.ArrayList;

import core.Location;

public class Postava extends HPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2929222903743095773L;
	private boolean PJ;
	private ArrayList<PropPair> param = new ArrayList<PropPair>();

	/**
	 * 
	 * @param name
	 * @param loc
	 * @param PJ
	 * @param prop
	 */
	public Postava(String name, Location loc, boolean PJ, ArrayList<PropPair> prop) {
		// super(name, loc, Rotatable, Colidable, prvek);
		super(name, loc, true, false, new Gprvky().entity(loc));
		if (prop != null) {
			this.param = prop;
		}
		this.PJ = PJ;

	}

	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek = new Gprvky().entity(loc);
	}

	public void setPJ(boolean isPJ) {
		PJ = isPJ;
	}

	public boolean isPJ() {
		return PJ;
	}

	public void setParamValue(int pos, String newValue) {
		PropPair p = param.get(pos);
		p.value = newValue;
		param.set(pos, p);
	}

	public void setParamName(int pos, String newValue) {
		PropPair p = param.get(pos);
		p.name = newValue;
		param.set(pos, p);
	}

	public void addParam(String name, String value) {
		param.add(new PropPair(name, value));
	}

	public void addParam(PropPair value) {
		param.add(value);
	}

	public void setNick(String name) {
		this.name = name;
	}

	public ArrayList<PropPair> getParam() {
		return param;
	}

	public String getParamName(int pos) {
		return param.get(pos).name;
	}

	public String getParamValue(int pos) {
		return param.get(pos).value;
	}

	public void removeParam(int pos) {
		param.remove(pos);
	}

	public Postava clone() {
		Postava cloned = (Postava) super.clone();
		cloned.param = (ArrayList<PropPair>) cloned.param.clone();
		for (int i = 0; i < cloned.param.size(); i++) {
			try {
				cloned.param.set(i, cloned.param.get(i).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return cloned;
	}

	public String toString() {
		// String ret = "name: " + this.getText() + ", Loc: " + loc.toString() +
		// ", PJ: " + PJ + ", Param: ";
		// if (param != null) {
		// for (int i = 0; i < param.size(); i++) {
		// ret = ret.concat(param.get(i).toString()).concat(", ");
		// }
		// } else {
		// ret = ret.concat("<null>");
		// }
		// return ret;
		String ispj;
		if (isPJ()) {
			ispj = "(NPC)";
		} else {
			ispj = "";
		}
		return ispj + " " + getNick();
	}
}
