package hexapaper.entity;

import java.util.ArrayList;

import core.Location;
import hexapaper.source.BPolygon;
import hexapaper.source.HPSklad.PropPair;

public abstract class EditableEntity extends HPEntity {

	protected ArrayList<PropPair> param = new ArrayList<PropPair>();
	
	public EditableEntity(String name, Location loc, boolean Rotatable, boolean Colidable, ArrayList<BPolygon> prvek) {
		super(name, loc, Rotatable, Colidable, prvek);
		// TODO Auto-generated constructor stub
	}

	public EditableEntity(String name, Location loc, boolean Rotatable, boolean Colidable, BPolygon prvek) {
		super(name, loc, Rotatable, Colidable, prvek);
		// TODO Auto-generated constructor stub
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
	public void setParam(ArrayList<PropPair> param){
		this.param = param;
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
	public EditableEntity clone() {
		EditableEntity cloned = (EditableEntity) super.clone();
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
	@Override
	public String toString() {
		return name;
	}

}
