package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.HPEntity;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.source.HPSklad;
import hexapaper.source.HPStrings;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveFile {
	private JSONObject j = new JSONObject();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	HPSklad sk = HPSklad.getInstance();

	private String Db_ext = sk.str.get("Db_ext");
	private String Db_text = sk.str.get("Db_text");
	private String File_ext = sk.str.get("File_ext");
	private String File_text = sk.str.get("FIle_text");
	private String Hex_ext = sk.str.get("Hex_ext");
	private String Hex_text = sk.str.get("Hex_text");

	public SaveFile(HPEntity e) {
		j.put("1", saveChar(e));
		save(File_ext, File_text);
	}

	public SaveFile(ArrayList<HPEntity> man) {
		for (int i = 0; i < man.size(); i++) {
			j.put(i, saveChar(man.get(i)));
		}
		save(Db_ext, Db_text);
	}

	public SaveFile(ArrayList<HPEntity> man, int Radius, int gridSl, int gridRA) {
		JSONObject entity = new JSONObject();
		for (int i = 0; i < man.size(); i++) {
			if (!(man.get(i) instanceof FreeSpace)||man.get(i).background!=Color.WHITE) {
				entity.put(i, saveChar(man.get(i)));
			}
		}
		j.put("GridSl", gridSl);
		j.put("Radius", Radius);
		j.put("GridRA", gridRA);
		j.put("Entity", entity);
		save(Hex_ext, Hex_text);
	}

	public SaveFile(List<Postava> man, List<Artefact> art) {
		for (Artefact e : art) {
			j.put(e.getNick(), saveChar(e));
		}
		for (Postava e : man) {
			j.put(e.getNick(), saveChar(e));
		}
		save(Db_ext, Db_text);
	}

	private JSONObject saveChar(HPEntity entity) {
		JSONObject section = new JSONObject();
		section.put("Location", entity.loc);
		if (entity instanceof Postava) {
			Postava m = (Postava) entity;
			section.put("Type", "Postava");
			section.put("Name", m.getNick());
			section.put("PJ", m.isPJ());
			section.put("List", m.getParam());
			section.put("Bcg", m.getBcg().getRGB());
		}
		if (entity instanceof Artefact) {
			Artefact m = (Artefact) entity;
			section.put("Type", "Artefact");
			section.put("Name", m.getNick());
			section.put("List", m.getParam());
			section.put("Bcg", m.getBcg().getRGB());			
		}
		if (entity instanceof Wall) {
			section.put("Type", "Wall");
		}
		if(entity instanceof FreeSpace) {
			section.put("Type", "Free");
			section.put("Bcg", entity.getBcg().getRGB());
		}
		return section;
	}

	public void save(String ext, String desc) {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(desc + " (*." + ext + ")", ext));
		FileWriter fileWriter;
		String jsonOutput = gson.toJson(j);
		int returnVal = fc.showSaveDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				String name = "";
				if (!file.getAbsolutePath().contains("." + ext)) {
					name = file.getAbsolutePath() + "." + ext;
				}
				else {
					name = file.getAbsolutePath();
				}
				fileWriter = new FileWriter(new File(name));
				fileWriter.write(jsonOutput);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
