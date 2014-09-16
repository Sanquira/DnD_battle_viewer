package dungeonmapper.source;

import java.awt.Color;

public enum DMMapTypesEnum {
	W(Color.black, "Wall"),
	H(Color.white, "Hole"),
	S(Color.gray, "Stairs up"),
	s(Color.gray, "Stairs down"),
	F(Color.lightGray, "Free space");

	private Color bcg;
	private String typeDesc;

	private DMMapTypesEnum(Color bcg, String desc) {
		this.bcg = bcg;
		this.typeDesc = desc;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public Color getBcgColor() {
		return bcg;
	}

}
