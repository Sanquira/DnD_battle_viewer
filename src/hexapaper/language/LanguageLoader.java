package hexapaper.language;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;

import core.file.Config;
import core.file.FileHandler;

public class LanguageLoader {
	
	public static File dir = Paths.get(Config.getConfigDir(), "lang").toFile();
	private static String ext = ".lang";
	private LanguageLoader(){}
	
	public static ArrayList<String> getLanguages(){
		ArrayList<String> list = new ArrayList<String>();
		File[] files = dir.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(ext);
			}
		});
		for(File f : files){
			list.add(f.getName().replace(ext, ""));
		}
		return list;
	}
	public static HPStrings load(String name) throws IOException, JAXBException{
		File langFile = Paths.get(dir.getAbsolutePath(), name + ext).toFile();
		if(!langFile.exists()){
			throw new IOException("LangFile for "+name+" doesn't exist");
		}
		FileHandler langHandler = new FileHandler(langFile);
		return langHandler.loadLang();
	}
	public static void createPreset(String name) throws JAXBException, IOException{
		File langFile = Paths.get(dir.getAbsolutePath(), name + ext).toFile();
		FileHandler langHandler = new FileHandler(langFile);
		langHandler.saveDefaultLang();
	}
	public static void setupDir(){
		if(!dir.exists()){
			dir.mkdirs();
			try {
				copyLangFiles();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void copyLangFiles() throws IOException{
		URL url = LanguageLoader.class.getProtectionDomain().getCodeSource().getLocation();
		String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
		Path from;
		if(jarPath.endsWith(".jar")){
			Map<String, String> env = new HashMap<>();		
			final URI jarFileUril = URI.create("jar:file:" + jarPath);
			final FileSystem fs = FileSystems.newFileSystem(jarFileUril, env);
			from = fs.getPath("/hexapaper/languages");
		}
		else{
			from = Paths.get(jarPath, "hexapaper","languages");			
		}
		Path to = Paths.get(dir.getAbsolutePath());
		try (final Stream<Path> sources = Files.walk(from)) {
		     sources.forEach(src -> {
		    	 System.out.println(src);
		    	 if(Files.isDirectory(src)){
		    		 System.out.println("ano");
		    	 }
		    	 else{
			         Path dest = to.resolve(from.relativize(src).toString());
			         System.out.println(dest.toString());
					 try {
						Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	 }
		     });
		}
	}
}
