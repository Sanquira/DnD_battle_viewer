package dungeonmapper.source;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import dungeonmapper.gui.DMRightMenu;

public enum DMMapTypesEnum {
	W(Color.black, "Wall", null),
	H(Color.white, "Hole", null),
	S(Color.gray, "Stairs up", "/images/stairdraw.png"),
	s(Color.gray, "Stairs down", "/images/stairdraw.png"),
	F(Color.lightGray, "Free space", null);

	private Color bcg;
	private String typeDesc;
	private ImageIcon icn;

	private DMMapTypesEnum(Color bcg, String desc, String iconPath) {
		this.bcg = bcg;
		this.typeDesc = desc;
		try {
			this.icn = new ImageIcon(ImageIO.read(DMRightMenu.class.getResourceAsStream(iconPath)));
		} catch (Exception e) {
			this.icn = null;
		}
	}

	public void setBcgColor(Color clr){
		bcg = clr;
	}
	
	public String getTypeDesc() {
		return typeDesc;
	}

	public Color getBcgColor() {
		return bcg;
	}

	public ImageIcon getIcon() {
		return icn;
	}

}
