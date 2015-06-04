package core.file;

import hexapaper.file.Wrappers.DatabaseWrapper;
import hexapaper.file.Wrappers.HexWrapper;
import hexapaper.source.HPSklad;

import java.io.File;
import java.io.IOException;

import com.google.gson.annotations.Expose;

public class Config {
	@Expose private static Config instance = new Config();
	@Expose private static String configDir = "hex";
	@Expose private static String cnfFile = "config.cnf";
	@Expose private static String mapFile = "map.hex";
	@Expose private static String dbaFile = "dba.entd";
	@Expose private static String dbcFile = "dbc.entd";
	public String lastName = "Player";
	public int RADIUS = 25;
	public int gridSl = 0;
	public int gridRa = 0;
	
	public int port = 10555;
	public String IP = "212.96.186.28";
	public String serverIP = "192.168.0.102";
	public int serverport = 10555;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadConfig(){
		if(new File(getConfigFile()).exists()){
			instance = new FileHandler(getConfigFile()).load(Config.class);
			System.out.println("Config načten");
		}
		else{
			System.out.println("Config nenalezen");
		}
	}
	public void saveDb() throws IOException{
		HPSklad.getInstance().saveCharacters(new FileHandler(getConfigDir()+File.separatorChar+dbcFile));
		HPSklad.getInstance().saveArtefacts(new FileHandler(getConfigDir()+File.separatorChar+dbaFile));
		System.out.println("Dočasná databáze uložena");
	}
	public void saveMap() throws IOException{
		if(HPSklad.getInstance().souradky.size()>0){
			HPSklad.getInstance().saveMap(new FileHandler(getConfigDir()+File.separatorChar+mapFile));
			System.out.println("Dočasná mapa uložena");
		}	
	}
	public static void loadTmp(){
		try {
			if (new File(getConfigDir() + File.separatorChar + dbcFile)
					.exists()) {
				new FileHandler(getConfigDir() + File.separatorChar + dbcFile)
						.load(DatabaseWrapper.class).loadDatabase();
			}
			if (new File(getConfigDir() + File.separatorChar + dbaFile)
					.exists()) {
				new FileHandler(getConfigDir() + File.separatorChar + dbaFile)
						.load(DatabaseWrapper.class).loadDatabase();
			}
			if (new File(getConfigDir() + File.separatorChar + mapFile)
					.exists()) {
				System.out.println("Dočasná mapa načtena");
				HPSklad sk = HPSklad.getInstance();
				HexWrapper HWrapper = new FileHandler(getConfigDir()
						+ File.separatorChar + mapFile).load(HexWrapper.class);
				if (HWrapper != null) {
					sk.c.RADIUS = HWrapper.Radius;
					sk.c.gridRa = HWrapper.GridRA;
					sk.c.gridSl = HWrapper.GridSl;
					sk.initLoad(HWrapper.load());
					if (sk.isConnected && sk.isPJ) {
						sk.client.radiusHexapaper();
						sk.client.updateHexapaper();
					}
				}
			}
		} catch (NullPointerException e) {
			new File(getConfigDir() + File.separatorChar + dbcFile).delete();
			new File(getConfigDir() + File.separatorChar + dbaFile).delete();
			new File(getConfigDir() + File.separatorChar + mapFile).delete();
		}

	}
	public static Config getInstance() {
//		if (instance == null) {
//			instance = new Config();
//		}
		return instance;
	}
}
