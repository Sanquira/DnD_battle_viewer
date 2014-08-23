package hexapaper.entity;

import java.util.ArrayList;

import hexapaper.gui.Gprvky;
import hexapaper.source.Location;
import hexapaper.source.Sklad.PropPair;

public class Artefact extends Entity {

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
		return this.getText();
	}

	public void setNick(String name) {
		this.setText(name);
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

	public String toString() {
		return getNick();
	}

}
