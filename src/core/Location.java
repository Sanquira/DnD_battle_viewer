package core;


public class Location implements Cloneable {
	private int x = 0;
	private int y = 0;
	private int direction = 0;

	public Location(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.direction = dir;

	}

	public Location clone() throws CloneNotSupportedException {
		return (Location) super.clone();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDir() {
		return direction;
	}

	public void setDir(int dir) {
		this.direction = dir;
	}

	public void setX(int dir) {
		this.x = dir;
	}

	public void setY(int dir) {
		this.y = dir;
	}

	@Override
	public String toString() {
		return "x: " + x + ", y: " + y + ", dir: " + direction;
	}

}
