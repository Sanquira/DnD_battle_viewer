package hexapaper.entity;

import java.awt.Color;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import hexapaper.source.HPSklad.PropPair;

@XmlRootElement
public class Artefact extends EditableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6124161923341675505L;
	
	public Artefact(){
		
	}
	public Artefact(String name, ArrayList<PropPair> param) {
		super(name, param);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name, Color background, ArrayList<PropPair> param) {
		super(name, background, param);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name, Color background) {
		super(name, background);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name, String tag, ArrayList<PropPair> param) {
		super(name, tag, param);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name, String tag, Color background, ArrayList<PropPair> param) {
		super(name, tag, background, param);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name, String tag, Color background) {
		super(name, tag, background);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name, String tag) {
		super(name, tag);
		// TODO Auto-generated constructor stub
	}

	public Artefact(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

}
