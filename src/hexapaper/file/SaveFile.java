package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.Entity;
import hexapaper.entity.Postava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private String File_ext="ent";
	private String Db_ext="entd";
	private String Db_txt="Entity Database files";
	private String File_txt="Entity files";	
	public SaveFile(Postava man){
		j.put(man.getNick(), saveChar(man));
		save(File_ext,File_txt);
	}
	public SaveFile(Artefact man){
		j.put(man.getNick(), saveChar(man));
		save(File_ext,File_txt);
	}	

	public SaveFile(List<Postava> man){
		for(Postava e:man){
			j.put(e.getNick(), saveChar(e));
		}
		save(Db_ext,Db_txt);
	}
	public SaveFile(List<Artefact>  man){
		for(Artefact e:man){
			j.put(e.getNick(), saveChar(e));
		}
		save(Db_ext,Db_txt);
	}
	public SaveFile(List<Postava> man,List<Artefact> art){
		for(Artefact e:art){
			j.put(e.getNick(), saveChar(e));
		}
		for(Postava e:man){
			j.put(e.getNick(), saveChar(e));
		}
		save(Db_ext,Db_txt);
	}
	private JSONObject saveChar(Postava c) {
		JSONObject p=new JSONObject();
		p.put("Artefact", false);
		p.put("List", c.getParam());		
		return p;
	}
	private JSONObject saveChar(Artefact c) {
		JSONObject p=new JSONObject();
		p.put("Artefact", true);
		p.put("List", c.getParam());		
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
				fileWriter=new FileWriter(new File(file.getAbsolutePath()+"."+ext));
				fileWriter.write(jsonOutput);
            	fileWriter.flush();
            	fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

}
