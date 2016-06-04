package core.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import hexapaper.entity.EditableEntity;
import hexapaper.entity.HPEntity;
import hexapaper.file.XmlAbstractWrapper;
import hexapaper.file.XmlDatabaseWrapper;
import hexapaper.file.XmlMapWrapper;

public class FileHandler {
	private String path;
	private static JAXBContext context;
	private static Marshaller m;
	private static Unmarshaller u;
	
	static {
		try {
			context = JAXBContext.newInstance(HPEntity[].class, Config.class, XmlAbstractWrapper.class);
			m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			u = context.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public FileHandler(String name){
		this.path=name;		
	}
	public FileHandler(File file){
		this(file.getAbsolutePath());
	}
	public static FileHandler showDialog(String ext,String desc,boolean save){
		JFileChooser fc = new JFileChooser();	
		fc.setFileFilter(new FileNameExtensionFilter(desc + " (*." + ext + ")", ext));
		int returnVal = 0;
		if(save){
			returnVal=fc.showSaveDialog(new JFrame());
		}
		else{
			returnVal=fc.showOpenDialog(new JFrame());
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String name = "";
			if (!file.getAbsolutePath().contains("." + ext)) {
				name = file.getAbsolutePath() + "." + ext;
			}
			else {
				name = file.getAbsolutePath();
			}
			return new FileHandler(name);
		}
		return null;
	}
	
	public void write(Object obj) throws JAXBException, IOException{
	    try(FileOutputStream stream = new FileOutputStream(new File(path))){
	    	m.marshal(obj, stream);
	    }
	}
	public <T> T load(Class<T> type) throws JAXBException {
		return u.unmarshal(new StreamSource(path), type).getValue();
	}
	public ArrayList<EditableEntity> loadDB() throws JAXBException {
		return load(XmlDatabaseWrapper.class).getList();
	}
	public HashMap<Long,HPEntity> loadMap() throws JAXBException {
		return load(XmlMapWrapper.class).getMap();
	}
	public Config loadConfig() throws JAXBException{
		return load(Config.class);
	}
	public void saveDB(ArrayList<EditableEntity> list) throws JAXBException, IOException{
		write(new XmlDatabaseWrapper(list));
	}
	public void saveMap(HashMap<Long,HPEntity> map) throws JAXBException, IOException{
		write(new XmlMapWrapper(map));
	}
	public void saveConfig(Config c) throws JAXBException, IOException{
		write(c);
	}
}
