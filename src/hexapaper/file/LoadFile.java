package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.source.Location;
import hexapaper.source.Sklad;
import hexapaper.source.Sklad.PropPair;
import hexapaper.source.Strings;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoadFile {
	private Sklad s = Sklad.getInstance();

	public LoadFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(Strings.desc + " (*." + Strings.File_ext + "," + Strings.Db_ext + "," + Strings.Hex_ext + ")", Strings.File_ext, Strings.Db_ext, Strings.Hex_ext));
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				JSONObject a = (JSONObject) new JSONParser().parse(new FileReader(file));
				if (file.getName().contains("." + Strings.Hex_ext)) {
					s.gridRa = Integer.valueOf((String) a.get("GridRA"));
					s.gridSl = Integer.valueOf((String) a.get("GridSl"));
					s.RADIUS = Integer.valueOf((String) a.get("Radius"));
					loadHexEntities((JSONObject) a.get("Entity"));
				}
				else {
					loadEntities(a);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<PropPair> loadList(JSONObject j) {
		ArrayList<PropPair> props = new ArrayList<>();
		JSONArray array = (JSONArray) j.get("List");
		for (Object c : array) {
			JSONObject subList = (JSONObject) c;
			props.add(new PropPair((String) subList.get("name"), (String) subList.get("value")));
		}
		return props;
	}

	public void loadEntities(JSONObject j) {
		for (Object t : j.values()) {
			JSONObject value = (JSONObject) t;
			if (((String) value.get("Type")).equals("Artefact")) {
				s.databazeArtefaktu.add(new Artefact((String) value.get("Name"), s.LocDontCare, loadList(value)));
				continue;
			}
			if (((String) value.get("Type")).equals("Postava")) {
				s.databazePostav.add(new Postava((String) value.get("Name"), s.LocDontCare, (boolean) value.get("PJ"), loadList(value)));
				continue;
			}
		}
	}

	public void loadHexEntities(JSONObject j) {
		s.souradky = new ArrayList<>();
		for (Object t : j.values()) {
			JSONObject value = (JSONObject) t;
			if ((String) value.get("Type") == "Artefact") {
				s.souradky.add(new Artefact((String) value.get("Name"), loadLoc((JSONObject) value.get("Location")), loadList(value)));
				continue;
			}
			if ((String) value.get("Type") == "Postava") {
				s.souradky.add(new Postava((String) value.get("Name"), loadLoc((JSONObject) value.get("Location")), (boolean) value.get("PJ"), loadList(value)));
				continue;
			}
			if ((String) value.get("Type") == "Wall") {
				s.souradky.add(new Wall(loadLoc((JSONObject) value.get("Location"))));
				continue;
			}
		}
	}

	private Location loadLoc(JSONObject j) {
		return new Location((int) j.get("x"), (int) j.get("y"), (int) j.get("direction"));
	}
}
