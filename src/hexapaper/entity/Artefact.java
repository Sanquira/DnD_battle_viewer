package hexapaper.entity;

import java.io.Serializable;
import java.util.ArrayList;

import core.Location;
import hexapaper.gui.Gprvky;
import hexapaper.source.HPSklad.PropPair;

public class Artefact extends HPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6124161923341675505L;
	private ArrayList<PropPair> param = new ArrayList<PropPair>();

	public Artefact(String name, Location loc, ArrayList<PropPair> prop) {
		super(name, loc, false, true, new Gprvky().artefact(loc));
		this.param = prop;
	}

	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek = new Gprvky().artefact(loc);
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

	public String getNick() {
		return name;
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

	public Artefact clone() {
		Artefact cloned = (Artefact) super.clone();
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
		return getNick();
	}

}
