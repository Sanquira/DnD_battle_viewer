package hexapaper.entity;

import java.io.Serializable;
import java.util.ArrayList;

import core.Location;
import hexapaper.gui.Gprvky;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;

public class Artefact extends EditableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6124161923341675505L;
	ArrayList<PropPair> param = new ArrayList<PropPair>();
	
	public Artefact(){
		this("Jmeno", HPSklad.getInstance().LocDontCare, new ArrayList<PropPair>());
	}
	public Artefact(String name, Location loc, ArrayList<PropPair> prop) {
		super(name, loc, false, true, new Gprvky().artefact(loc));
		this.param = prop;
	}
	
	public Artefact(String name, String tag, Location loc, ArrayList<PropPair> prop) {
		this(name,loc,prop);
		this.setTag(tag);
	}
	
	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek = new Gprvky().artefact(loc);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getName();
	}

}
