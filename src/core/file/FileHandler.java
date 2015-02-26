package core.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileHandler {
	private String name;
	private GsonBuilder build=new GsonBuilder().setPrettyPrinting();
	private Gson gson;
	
	public FileHandler(String name){
		this.name=name;		
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
	
	public void write(Object obj) throws IOException{
		FileWriter fw=new FileWriter(name);
		gson=build.create();
		gson.toJson(obj,fw);
		fw.flush();
		fw.close();
	}
//	public <T extends JsonDeserializer> T load(Class<T> type, JsonDeserializer json) {
//	    build.registerTypeAdapter(type, type.cast(json));
//	    gson=build.create();
//		return gson.fromJson(fr, type);
//	}
	public <T> T load(Class<T> type) {
	    //build.registerTypeAdapter(type, type.cast(json));
	    FileReader fr;
	    T t=null;
		try {
			fr = new FileReader(name);
			gson=build.create();
		    t=gson.fromJson(fr,type);
		    fr.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return t;
	}


}
