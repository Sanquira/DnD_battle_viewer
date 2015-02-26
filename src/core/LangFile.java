package core;

import hexapaper.file.Wrappers.LangWrapper;
import hexapaper.source.HPSklad;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.file.FileHandler;

public class LangFile {

	private HPSklad sk=HPSklad.getInstance();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	Class cls;
	private Map<String, String> strings = new HashMap<String, String>();

	public LangFile(Class scannedClass) {
		cls = scannedClass;
	}

	public void set(String key, String value) {
		strings.put(key, value);
	}

	public final String get(String key) {
		if (strings.containsKey(key)) {
			return strings.get(key);
		}
		return getVariable(key);
	}
	
	public String sub(String key,Map<String,String> map){
		String str=get(key);
		for(Entry<String, String> entry:map.entrySet()){
			String subKey=entry.getKey();
			String sub=entry.getValue();
			if(sub!=null){
				String replaced=str.replace("%"+subKey, sub);
				if(replaced!=null){
					str=replaced;
				}
			}
		}
		return str;		
	}
	public String sub(String key,String name,String value){
		String str=get(key);
		return str.replace("%"+name, value);
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
		if(value.equals("")){ System.out.println(key+" není známý řetězec");}
		return value;
	}

	public void saveLang() {
		LangWrapper wrapper=sk.wrappers.new LangWrapper();
		Field[] t = cls.getFields();
		try {
			for (Field o : t) {
				wrapper.lang.put(o.getName(), (String) o.get(cls));
			}
			FileHandler fh;		
			fh = new FileHandler(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + cls.getSimpleName() + ".lang.json");
			fh.write(wrapper);
		} catch (URISyntaxException | IOException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

//	public void loadLang() {
//		File f;
//		FileReader fr;
//		try {
//			File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
//			String joinedPath = new File(jar.getParent(), cls.getSimpleName() + ".lang.json").getAbsolutePath();
//			f = new File(joinedPath);
//			//System.out.println(f.getAbsolutePath());
//			if (f.exists() && !f.isDirectory()) {
//				//System.out.println("Soubor nalezen");
//				fr = new FileReader(f);
//				JSONObject a = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8")));
//				for (Object o : a.keySet()) {
//					String key = (String) o;
//					String value = (String) a.get(o);
//					this.set(key, value);
//				}
//			}
//
//		} catch (IOException | ParseException | URISyntaxException e) {
//			e.printStackTrace();
//		}
//	}
	public void loadLang(){
		String path="";
		try {
			path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + cls.getSimpleName() + ".lang.json";
			File t=new File(path);
			if(t.exists()&&!t.isDirectory()){
				FileHandler fh=new FileHandler(path);
				LangWrapper lw=fh.load(LangWrapper.class);
				strings=lw.lang;
				//sk.setStatus("LangFileLoaded");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
