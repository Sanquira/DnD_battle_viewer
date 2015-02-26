package dungeonmapper.source;

import java.awt.Point;

public class DMGridElement {

	int X = -1, Y = -1;
	public int pos;
	DMMapTypesEnum type = DMMapTypesEnum.W;
	int meta = -1;

	public int getX() {
		return X;
	}

	public void setX(Integer x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
	
	public Point getPoint(){
		return new Point(X, Y);
	}

	public DMMapTypesEnum getType() {
		return type;
	}

	public void setType(DMMapTypesEnum type) {
		this.type = type;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public void setElement(int x, int y, DMMapTypesEnum typ, int meta) {
		this.X = x;
		this.Y = y;
		this.type = typ;
		this.meta = meta;
	}

	public boolean setElement(String typ_Meta) throws IllegalArgumentException {
		if (typ_Meta.contains(":")) {
			String[] strs = typ_Meta.split(":");
			if (strs.length == 2 && strs[0].length() == 1 && strs[1].matches("[0-9]+")) {
				this.type = DMMapTypesEnum.valueOf(strs[0]);
				this.meta = Integer.valueOf(strs[1]);
				return true;
			}
		}
		return false;
	}

	public DMGridElement(int x, int y, DMMapTypesEnum typ, int meta) {
		this.X = x;
		this.Y = y;
		this.type = typ;
		this.meta = meta;
	}

	public DMGridElement(int x, int y, DMMapTypesEnum typ) {
		this(x, y, typ, 0);
	}

	public DMGridElement(int x, int y, String type) throws IllegalArgumentException{
		this(x, y, DMMapTypesEnum.valueOf(type));
	}

	@Override
	public String toString() {
		return "X: " + X + ", Y: " + Y + ", type: " + type.getTypeDesc() + ", meta: " + meta;
	}

}
