package core.file;

import hexapaper.file.Wrappers.DatabaseWrapper;
import hexapaper.file.Wrappers.HexWrapper;
import hexapaper.file.XmlAbstractWrapper;
import hexapaper.source.HPSklad;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class Config extends XmlAbstractWrapper{
	@XmlTransient private static Config instance = new Config();
	@XmlTransient private static String configDir = "hex";
	@XmlTransient private static String cnfFile = "config.cnf";
	@XmlTransient private static String mapFile = "map.hex";
	@XmlTransient private static String dbFile = "db.entd";
	public String lastName = "Player";
	public int RADIUS = 25;
	public int gridSl = 0;
	public int gridRa = 0;
	
	public int port = 10555;
	public String IP = "212.96.186.28";
	public String serverIP = "192.168.0.100";
	public int serverport = 5222;
	public String Language = "Czech";
	
	private static String configDirectory(String name)
	{
	    String OS = System.getProperty("os.name").toUpperCase();
	    String path = "";
	    if (OS.contains("WIN"))
	        path = System.getenv("APPDATA");
	    else if (OS.contains("MAC"))
	        path = System.getProperty("user.home") + "/Library/Application "
	                + "Support";
	    else if (OS.contains("NUX"))
	        path = System.getProperty("user.home");
	    else{
	    	path = System.getProperty("user.dir");
	    }   
	    return path+File.separatorChar+"."+name;	    
	}
	public static String getConfigDir(){
		return configDirectory(configDir);
	}
	public static File getConfigFile(String name){
		return new File(getConfigDir() + File.separatorChar + name);
	}
	public static String getConfigFile(){
		File dir = new File(configDirectory(configDir));
		if(!dir.exists()){
			dir.mkdirs();
		}
		return configDirectory(configDir)+File.separatorChar+cnfFile;
	}
	
	public void saveConfig(){
		try {
			new FileHandler(getConfigFile()).write(this);
			System.out.println("Config uložen");
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadConfig() throws JAXBException{
		if(new File(getConfigFile()).exists()){
			instance = new FileHandler(getConfigFile()).load(Config.class);
			System.out.println("Config načten");
		}
		else{
			System.out.println("Config nenalezen");
		}
	}
//	public void saveDb() throws IOException{
//		HPSklad.getInstance().saveCharacters(new FileHandler(getConfigDir()+File.separatorChar+dbcFile));
//		HPSklad.getInstance().saveArtefacts(new FileHandler(getConfigDir()+File.separatorChar+dbaFile));
//		System.out.println("Dočasná databáze uložena");
//	}
//	public void saveMap() throws IOException{
//		if(HPSklad.getInstance().souradky.size()>0){
//			HPSklad.getInstance().saveMap(new FileHandler(getConfigDir()+File.separatorChar+mapFile));
//			System.out.println("Dočasná mapa uložena");
//		}	
//	}
//	public static void loadTmp(){
//		try {
//			if (new File(getConfigDir() + File.separatorChar + dbcFile)
//					.exists()) {
//				new FileHandler(getConfigDir() + File.separatorChar + dbcFile)
//						.load(DatabaseWrapper.class).loadDatabase();
//			}
//			if (new File(getConfigDir() + File.separatorChar + dbaFile)
//					.exists()) {
//				new FileHandler(getConfigDir() + File.separatorChar + dbaFile)
//						.load(DatabaseWrapper.class).loadDatabase();
//			}
//			if (new File(getConfigDir() + File.separatorChar + mapFile)
//					.exists()) {
//				System.out.println("Dočasná mapa načtena");
//				HPSklad sk = HPSklad.getInstance();
//				HexWrapper HWrapper = new FileHandler(getConfigDir()
//						+ File.separatorChar + mapFile).load(HexWrapper.class);
//				if (HWrapper != null) {
//					sk.c.RADIUS = HWrapper.Radius;
//					sk.c.gridRa = HWrapper.GridRA;
//					sk.c.gridSl = HWrapper.GridSl;
//					//sk.initLoad(HWrapper.load());
//					if (sk.isConnected && sk.isPJ) {
//						sk.client.radiusHexapaper();
//						sk.client.updateHexapaper();
//					}
//				}
//			}
//		} catch (NullPointerException | JAXBException e) {
//			new File(getConfigDir() + File.separatorChar + dbcFile).delete();
//			new File(getConfigDir() + File.separatorChar + dbaFile).delete();
//			new File(getConfigDir() + File.separatorChar + mapFile).delete();
//		}
//
//	}
	public static void loadTmp(){
		File db = getConfigFile(dbFile);
		File map = getConfigFile(mapFile);
		if(db.exists()){
			HPSklad.loadDB(new FileHandler(db).loadDB());
		}
		
	}
	public static Config getInstance() {
//		if (instance == null) {
//			instance = new Config();
//		}
		return instance;
	}
}
