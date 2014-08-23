package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;
import hexapaper.source.Sklad;
import hexapaper.source.Sklad.PropPair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoadFile {
	private String desc="Entity files";
	private String ext="ent";
	private String Db_ext="entd";		
	public LoadFile(){
		Sklad s = Sklad.getInstance();
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(desc+" (*."+ext+","+Db_ext+")", ext,Db_ext));
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                	JSONObject a =(JSONObject) new JSONParser().parse(new FileReader(file));
                	for(Object t:a.keySet()){
                		String name=(String) t;
                		ArrayList<PropPair> props=new ArrayList<>();
                		JSONObject value=(JSONObject) a.get(t);                		
                		JSONArray array=(JSONArray) value.get("List");
                		for(Object c:array){
                 			JSONObject subList=(JSONObject) c;
                			props.add(new PropPair((String) subList.get("name"),(String) subList.get("value")));
                		}
                		if((boolean) value.get("Artefact")){
                			s.databazeArtefaktu.add(new Artefact(name, s.LocDontCare, props));
                		}
                		else{
                			s.databazePostav.add(new Postava(name, s.LocDontCare, (boolean) value.get("PJ"), props));
                		}
                  	}
                	
				} catch (Exception e) {					
					e.printStackTrace();
				}
        	}
	}
}
