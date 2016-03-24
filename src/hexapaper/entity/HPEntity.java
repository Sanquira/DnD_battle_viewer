package hexapaper.entity;

import java.awt.Color;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import core.file.ColorAdapter;

@XmlRootElement
@XmlSeeAlso({RotatableEntity.class,Wall.class,FreeSpace.class})
public abstract class HPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6970412701137178068L;
	public static boolean isColidable = true;
	@XmlJavaTypeAdapter(ColorAdapter.class)
	public Color background = Color.white;

	public HPEntity() {

	}
	public HPEntity(Color background){
		this.setBcg(background);
	}

	public Color getBcg() {
		return background;
	}
	@XmlTransient
	public void setBcg(Color clr) {
		this.background = clr;
	}
}
