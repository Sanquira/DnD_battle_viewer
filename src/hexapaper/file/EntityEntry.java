package hexapaper.file;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import hexapaper.entity.HPEntity;
import hexapaper.graphicCore.GCMath;
import mathLibrary.vector.Vector2D;

@XmlRootElement(name="entry")
public class EntityEntry {
	Point2D vec;
	HPEntity ent;
	
	public EntityEntry() {}
	public EntityEntry(Vector2D coords, HPEntity value) {
		vec = new Point2D(coords);
		ent = value;
	}
	
	public Point2D getVec() {
		return vec;
	}
	public void setVec(Point2D vec) {
		this.vec = vec;
	}
	public HPEntity getEnt() {
		return ent;
	}
	public void setEnt(HPEntity ent) {
		this.ent = ent;
	}
	@XmlTransient
	public long getLong(){
		return GCMath.getLongFromCoords(vec.toVector());
	}
}
