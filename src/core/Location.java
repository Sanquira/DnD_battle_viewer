package core;

import java.awt.Point;
import java.io.Serializable;

public class Location implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6007945538361059723L;
	private Integer x = 0;
	private Integer y = 0;
	private Integer direction = 0;

	public Location(Integer x, Integer y, Integer dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;

	}

	public Location clone() throws CloneNotSupportedException {
		return (Location) super.clone();
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getDir() {
		return direction;
	}

	public void setDir(Integer dir) {
		this.direction = dir;
	}

	public void setX(Integer dir) {
		this.x = dir;
	}

	public void setY(Integer dir) {
		this.y = dir;
	}

	public Point getPoint() {
		return new Point(this.x, this.y);
	}

	@Override
	public String toString() {
		return "x: " + x + ", y: " + y + ", dir: " + direction;
	}

}
