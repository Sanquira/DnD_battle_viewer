package hexapaper.file;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import hexapaper.entity.EditableEntity;

@XmlRootElement(name = "Database")
public class XmlDatabaseWrapper extends XmlAbstractWrapper{
	ArrayList<EditableEntity> list;
	public XmlDatabaseWrapper() {}
	public XmlDatabaseWrapper(ArrayList<EditableEntity> list){
		this.list = list;
	}
	public ArrayList<EditableEntity> getList() {
		return list;
	}
	public void setList(ArrayList<EditableEntity> list) {
		this.list = list;
	}
}
