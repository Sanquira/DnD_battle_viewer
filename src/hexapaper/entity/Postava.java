package hexapaper.entity;

import hexapaper.gui.Gprvky;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import core.Location;

public class Postava extends EditableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2929222903743095773L;

	/**
	 * 
	 * @param name
	 * @param loc
	 * @param PJ
	 * @param prop
	 */

	public Postava(String name, ArrayList<PropPair> param) {
		super(name, param);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name, Color background, ArrayList<PropPair> param) {
		super(name, background, param);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name, Color background) {
		super(name, background);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name, String tag, ArrayList<PropPair> param) {
		super(name, tag, param);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name, String tag, Color background, ArrayList<PropPair> param) {
		super(name, tag, background, param);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name, String tag, Color background) {
		super(name, tag, background);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name, String tag) {
		super(name, tag);
		// TODO Auto-generated constructor stub
	}

	public Postava(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void recreateGraphics() {
		
	}
	
}
