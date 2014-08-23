package hexapaper.source;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveFile {
	JSONObject j;
	Gson gson;
	public SaveFile(Postava man){
		j=new JSONObject();
		JSONObject o=saveChar(man);
		j.put(man.getNick(), o);
		save();
	}
	public SaveFile(Artefact man){
		j=new JSONObject();
		JSONObject o=saveChar(man);
		j.put(man.getNick(), o);
		save();
	}	


	private JSONObject saveChar(Postava c) {
		JSONObject p=new JSONObject();
		p.put("List", c.getParam());		
		return p;
	}
	private JSONObject saveChar(Artefact c) {
		JSONObject p=new JSONObject();
		p.put("List", c.getParam());		
		return p;
	}
	public void save(){
		JFileChooser fc = new JFileChooser();
		FileWriter fileWriter;
		gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(j);
//		int returnVal = fc.showOpenDialog(new JFrame());	
		int returnVal = fc.showSaveDialog(new JFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
				fileWriter=new FileWriter(file);
				fileWriter.write(jsonOutput);
            	fileWriter.flush();
            	fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

}
