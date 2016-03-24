package hexapaper.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import hexapaper.entity.HPEntity;
import hexapaper.graphicCore.GCMath;

@XmlRootElement(name="Map")
public class XmlMapWrapper extends XmlAbstractWrapper{
	
	//private String version = HPSklad.FILEVERSION;
	private ArrayList<EntityEntry> entities;
	
	public XmlMapWrapper(){}
	public XmlMapWrapper(HashMap<Long, HPEntity> entities) {
		setMap(entities);
	}
	public ArrayList<EntityEntry> getEntry() {
		return entities;
	}
	public void setEntry(ArrayList<EntityEntry> entities) {
		this.entities = entities;
	}
	public void setMap(HashMap<Long, HPEntity> entities){
		this.entities = new ArrayList<EntityEntry>();
		for(Entry<Long, HPEntity> e:entities.entrySet()){
			this.entities.add(new EntityEntry(GCMath.getCoordsFromLong(e.getKey()),e.getValue()));
		}
	}
	@XmlTransient
	public HashMap<Long, HPEntity> getMap(){
		HashMap<Long, HPEntity> map = new HashMap<Long, HPEntity>();
		for(EntityEntry entry:entities){
			map.put(entry.getLong(), entry.ent);
		}
		return map;
	}
}
