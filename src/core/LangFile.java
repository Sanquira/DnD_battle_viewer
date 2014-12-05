package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LangFile {

	private JSONObject j = new JSONObject();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	Class cls;
	private Map<String, String> strings = new HashMap<String, String>();

	public LangFile(Class scannedClass) {
		cls = scannedClass;
	}

	public void set(String key, String value) {
		strings.put(key, value);
	}

	public String get(String key) {
		if (strings.containsKey(key)) {
			return strings.get(key);
		}
		// System.out.println(key);
		return getVariable(key);
	}

	private String getVariable(String key) {
		String value = "";
		for (Field field : cls.getFields()) {
			if (field.getName().equals(key)) {
				try {
					value = (String) field.get(String.class);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public void saveLang() {
		Field[] t = cls.getFields();
		for (Field o : t) {
			String strValue = null;
			try {
				strValue = (String) o.get(cls);
				j.put(o.getName(), strValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		File f = null;
		String jsonOutput = gson.toJson(j);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + cls.getSimpleName() + ".lang.json"));
			fileWriter.write(jsonOutput);
			fileWriter.flush();
			fileWriter.close();
			// System.out.println("export jazyku kompletní");
			// System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
			// + cls.getSimpleName() + ".lang.json");
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void loadLang() {
		File f;
		FileReader fr;
		try {
			File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			String joinedPath = new File(jar.getParent(), cls.getSimpleName() + ".lang.json").getAbsolutePath();
			f = new File(joinedPath);
			//System.out.println(f.getAbsolutePath());
			if (f.exists() && !f.isDirectory()) {
				//System.out.println("Soubor nalezen");
				fr = new FileReader(f);
				JSONObject a = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8")));
				for (Object o : a.keySet()) {
					String key = (String) o;
					String value = (String) a.get(o);
					this.set(key, value);
				}
			}

		} catch (IOException | ParseException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
