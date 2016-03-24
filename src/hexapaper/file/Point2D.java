package hexapaper.file;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import mathLibrary.vector.Vector2D;

@XmlRootElement
public class Point2D {
	private int x;
	private int y;
	
	public Point2D(){
		
	}
	public Point2D(Vector2D vec){
		setX(vec.getU1I());
		setY(vec.getU2I());
	}
	public int getY() {
		return y;
	}
	@XmlAttribute
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	@XmlAttribute
	public void setX(int x) {
		this.x = x;
	}
	public Vector2D toVector(){
		return new Vector2D(x,y);
	}
}
