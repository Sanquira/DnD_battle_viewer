package dungeonmapper.file;

import hexapaper.source.HPStrings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.LangFile;
import dungeonmapper.source.DMSklad;

public class LoadFile {

	DMSklad s = DMSklad.getInstance();

	public LoadFile() {
		s.str = new LangFile(HPStrings.class);
		File f;
		FileReader fr;
		try {
			File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			String joinedPath = new File(jar.getParent(), "DMStrings.lang.json").getAbsolutePath();
			f = new File(joinedPath);
			// System.out.println(f.getAbsolutePath());
			if (f.exists() && !f.isDirectory()) {
				// System.out.println("Soubor nalezen");
				fr = new FileReader(f);
				JSONObject a = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8")));
				for (Object o : a.keySet()) {
					String key = (String) o;
					String value = (String) a.get(o);
					s.str.set(key, value);
				}
			}
			// else{
			// ClassLoader classLoader = getClass().getClassLoader();
			// System.out.println(classLoader.getResource("lang.json").toURI().getPath());
			// fr=new FileReader(new
			// File(classLoader.getResource("lang.json").toURI().getPath()));
			// }

		} catch (IOException | ParseException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
