package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.Entity;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.source.Sklad;
import hexapaper.source.Strings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveFile {
	private JSONObject j=new JSONObject();
	private Gson gson;
	public SaveFile(Entity e){
		j.put("1", saveChar(e));
		save(Strings.File_ext,Strings.File_text);
	}

	public SaveFile(ArrayList<Entity> man){
		for(int i=0;i<man.size();i++){
			j.put(i, saveChar(man.get(i)));
		}
		save(Strings.Db_ext,Strings.Db_text);
	}
	public SaveFile(ArrayList<Entity>  man,int Radius, int gridSl,int gridRA){
		JSONObject entity=new JSONObject();
		for(int i=0;i<man.size();i++){
			if(!(man.get(i) instanceof FreeSpace)){
				entity.put(i, saveChar(man.get(i)));
			}	
		}
		j.put("GridSl", gridSl);
		j.put("Radius", Radius);
		j.put("GridRA", gridRA);
		j.put("Entity", entity);
		save(Strings.Hex_ext,Strings.Hex_text);
	}	
	public SaveFile(List<Postava> man,List<Artefact> art){
		for(Artefact e:art){
			j.put(e.getNick(), saveChar(e));
		}
		for(Postava e:man){
			j.put(e.getNick(), saveChar(e));
		}
		save(Strings.Db_ext,Strings.Db_text);
	}
	private JSONObject saveChar(Entity c) {
		JSONObject p=new JSONObject();
		p.put("Location",c.loc);
		if(c instanceof Postava){
			Postava m=(Postava) c;
			p.put("Type", "Postava");
			p.put("Name", m.getNick());
			p.put("PJ", m.isPJ());
			p.put("List", m.getParam());
		}
		if(c instanceof Artefact){
			Artefact m=(Artefact) c;
			p.put("Type", "Artefact");
			p.put("Name", m.getNick());
			p.put("List", m.getParam());
		}
		if(c instanceof Wall){
			p.put("Type", "Wall");
		}	
		return p;
	}
	public void save(String ext,String desc){
		JFileChooser fc = new JFileChooser();	
		fc.setFileFilter(new FileNameExtensionFilter(desc+" (*."+ext+")", ext));
		FileWriter fileWriter;
		gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(j);
		int returnVal = fc.showSaveDialog(new JFrame());		 
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
            	String name="";
				if(!file.getAbsolutePath().contains("."+ext)){
					name=file.getAbsolutePath()+"."+ext;
				}
				else{
					name=file.getAbsolutePath();
				}
            	fileWriter=new FileWriter(new File(name));
				fileWriter.write(jsonOutput);
            	fileWriter.flush();
            	fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

}
