package hexapaper.entity;

import java.awt.Color;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlSeeAlso;
import hexapaper.source.HPSklad.PropPair;

@XmlSeeAlso({Postava.class,Artefact.class})
public abstract class EditableEntity extends RotatableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4866601385389286073L;
	protected ArrayList<PropPair> param = new ArrayList<PropPair>();
	protected String tag = null;
	protected String name;
	public boolean visible;
	
	public EditableEntity(){
		
	}
	public EditableEntity(String name){
		this(name,name);
	}
	public EditableEntity(String name, ArrayList<PropPair> param){
		this(name, name, param);
	}
	public EditableEntity(String name, Color background){
		this(name,name,background);
	}
	public EditableEntity(String name, Color background, ArrayList<PropPair> param){
		this(name,name,background,param);
	}
	public EditableEntity(String name, String tag, ArrayList<PropPair> param){
		this(name, tag);
		setParam(param);
	}
	public EditableEntity(String name, String tag, Color background){
		this(name,tag);
		setBcg(background);
	}
	public EditableEntity(String name, String tag, Color background, ArrayList<PropPair> param){
		this(name,tag,param);
		setBcg(background);
	}
	public EditableEntity(String name, String tag, Color background, ArrayList<PropPair> param, boolean visible){
		this(name,tag,background,param);
		setVisible(visible);
	}
	public EditableEntity(String name, String tag) {
		setTag(tag);
		setName(name);
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

	@Override
	public String toString() {
		return name;
	}
	public void setTag(String tag) {
		try {
			this.tag = tag.substring(0, 2);
		} catch (StringIndexOutOfBoundsException e) {
			this.tag = tag.substring(0, name.length());
		}
	}
	public String getTag() {
		return tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String nick) {
		this.name = nick;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
