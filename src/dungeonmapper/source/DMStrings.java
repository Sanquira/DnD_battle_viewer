package dungeonmapper.source;

import hexapaper.language.HPStrings;

import java.io.File;
import java.net.URISyntaxException;

import com.google.gson.annotations.Expose;

import core.file.Config;
import core.file.FileHandler;

public class DMStrings {
	@Expose private static DMStrings instance;

	public String ano = "Ano";
	public String ne = "Ne";
	public String ztrataDat = "Ztráta dat";
	public String zpravaZtrataDat = "Pozor program automaticky NEUKLÁDÁ změny v databázích. \nVěechny neuložené změny budou ztraceny. \nChcete opravdu skončit?";
	public String gameMenu = "Hra";
	public String saveGame = "Uložit hru";
	public String loadGame = "Načíst hru";
	public String endGame = "Konec";
	public String newGame = "Nová hra";

	public String CreatePaperFrame = "Tvorba plochy";
	public String CreatePaperButton = "Vytvor plochu";
	public String Size = "Velikost políčka";
	public String Rows = "Počet řádků";
	public String Columns = "Počet sloupců";
	
	public String DMm_ext = "map";
	public String DMm_text = "Mapa dungeonu";
	public String drawOrder = "Způsob kreslení";
	public String drawShape = "Nástroj kreslení";

	public String drawTooltip = "Chodby";
	public String eraseTooltip = "Zdi";
	public String negateTooltip = "Neguj";
	public String penTooltip = "Čára";
	public String rectTooltip = "Obdélník";
	public String circTooltip = "Elipsa";
	public String SUTooltip = "Schody nahoru";
	public String SDTooltip = "Schody dolů";
	public String holeTooltip = "Díra";
	
	public static DMStrings getInstance() {
		if (instance == null) {			
			try {
				instance = loadFile();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	private static DMStrings loadFile() throws URISyntaxException{
		File j = new File(Config.getConfigDir() + File.separatorChar + HPStrings.class.getSimpleName() + ".lang.json");
		if(j.exists()&&!j.isDirectory()){
			FileHandler fh=new FileHandler(j.getAbsolutePath());
			return fh.load(DMStrings.class);
		}
		return new DMStrings();
	}
}
