package Demos;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import core.file.Config;
import core.file.FileHandler;
import hexapaper.entity.Artefact;
import hexapaper.entity.EditableEntity;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.HPEntity;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.file.XmlAbstractWrapper;
import hexapaper.file.XmlDatabaseWrapper;

public class XMLTest {

	public static void main(String[] args) throws JAXBException, IOException {
		//m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
		JAXBContext context = JAXBContext.newInstance(HPEntity[].class, XmlAbstractWrapper.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Unmarshaller u = context.createUnmarshaller();
		HashMap<Long, HPEntity> entities = new HashMap<Long, HPEntity>();
		entities.put((long) 20,new Artefact("Bedna"));
		entities.put((long) 21,new Artefact("Karel"));
		entities.put((long) 22,new Wall());
		entities.put((long) 23,new FreeSpace());
		entities.put((long) 24,new Postava("Deserd"));
//		XmlMapWrapper test = new XmlMapWrapper( entities);
//		Config c = new Config();
		
		ArrayList<EditableEntity> ent = new ArrayList<EditableEntity>();
		ent.add(new Artefact("Bedna"));
		ent.add(new Artefact("Karel"));
		ent.add(new Postava("Deserd"));
		FileHandler fh = new FileHandler("text.xml");
		//fh.saveDB(ent);
		//m.marshal(db, new File("test.xml"));
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("test", "testík");
		fh.saveMap(entities);
		//m.marshal(w, new File("test.xml"));
		HashMap<Long, HPEntity> loaded = fh.loadMap();
		System.out.println(loaded.get((long) 21));
		//System.out.println(loaded.get(2).getClass());

	}

}
