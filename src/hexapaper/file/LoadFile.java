package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.Entity;
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
	private ArrayList<Entity> souradky = new ArrayList<>();
	private JFileChooser fc = new JFileChooser();

	public LoadFile(String desce, String... ext) {
		String desc = desce + " (*.";
		for (int i = 0; i < ext.length - 1; i++) {
			desc += ext[i] + ",";
		}
		desc += ext[ext.length - 1];
		desc += ")";
		fc.setFileFilter(new FileNameExtensionFilter(desc, ext));
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				JSONObject a = (JSONObject) new JSONParser().parse(new FileReader(file));
				if (file.getName().contains("." + Strings.Hex_ext)) {
					s.gridRa = ((Long) a.get("GridRA")).intValue();
					s.gridSl = ((Long) a.get("GridSl")).intValue();
					s.RADIUS = ((Long) a.get("Radius")).intValue();
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
		for (Object t : j.keySet()) {
			int i=((Long) t).intValue();
			JSONObject value = (JSONObject) j.get(t);
			if (((String) value.get("Type")).equals("Artefact")) {
				souradky.set(i,new Artefact((String) value.get("Name"), loadLoc((JSONObject) value.get("Location")), loadList(value)));
				continue;
			}
			if (((String) value.get("Type")).equals("Postava")) {
				souradky.set(i,new Postava((String) value.get("Name"), loadLoc((JSONObject) value.get("Location")), (boolean) value.get("PJ"), loadList(value)));
				continue;
			}
			if (((String) value.get("Type")).equals("Wall")) {
				souradky.add(i,new Wall(loadLoc((JSONObject) value.get("Location"))));
				continue;
			}
		}
	}

	public ArrayList<Entity> getSouradky() {
		return souradky;
	}

	private Location loadLoc(JSONObject a) {
		return new Location(((Long) a.get("x")).intValue(), ((Long) a.get("y")).intValue(), ((Long) a.get("direction")).intValue());
	}
}
