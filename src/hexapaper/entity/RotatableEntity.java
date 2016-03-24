package hexapaper.entity;

import java.awt.Color;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({EditableEntity.class})
public abstract class RotatableEntity extends HPEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7100284996478693689L;
	private int rotation;

	public RotatableEntity() {
		// TODO Auto-generated constructor stub
	}

	public RotatableEntity(Color background) {
		super(background);
		// TODO Auto-generated constructor stub
	}

	public RotatableEntity(Color background, int rotation) {
		super(background);
		setRotation(rotation);
	}
	
	public RotatableEntity(int rotation) {
		setRotation(rotation);
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

}
