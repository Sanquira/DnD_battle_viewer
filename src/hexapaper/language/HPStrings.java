package hexapaper.language;

import com.google.gson.annotations.Expose;

public class HPStrings {

	@Expose
	public static HPStrings instance;
	public static String lang_name = "English";
	
	public String Warning = "Warning";
	public String WarningNameIsEmpty = "Name field must be filled!";
	public String CreateCharacter = "Create Character";
	public String CreatedCharacters = "Created Characters";
	public String CreateArtefact = "Create Artefact";
	public String CreatedArtefacts = "Created Artefacts";
	public String CreateHex = "Create Hex";
	public String Yes = "Yes";
	public String No = "No";
	public String NPC = "NPC";
	public String Artefact = "Artefact";
	public String Radius = "Poloměr Hexu";
	public String LineCount = "Počet řádků";
	public String CollumnCount = "Počet sloupců";

	public String Hex_ext = "hex";
	public String Hex_text = "Hexovy papir";
	public String Db_ext = "entd";
	public String Db_text = "Database entit";
	public String File_ext = "ent";
	public String File_text = "Soubory entit";
	public String desc = "HexaPaper soubory";

	public String newPaper = "Nový papír";
	public String savePaper = "Ulož papír";
	public String loadPaper = "Načti papír";
	public String addArt = "Přidej artefakt";
	public String addPost = "Přidej postavu";

	public String Battlecontrol;
	public String Objproperties = "Vlastnosti objektu";
	public String showPlayerColor = "Skrýt hráče";
	public String showNPCColor = "Skrýt NPC";
	public String addWall = "Přidej zeď";
	public String addFreeSpace = "Odeber";

	public String player = "Hráč";
	public String LangFileLoaded = "Jazykový soubor načten";
	public String name = "Jméno";
	public String Tag = "Tag";
	public String addPropBut = "Přidej";
	public String delPropBut = "Odeber";

	public String GameMenu = "Hra";
	public String EditMenu = "Úpravy";
	public String export = "Export";
	public String exportAP = "Export ...";
	public String importAP = "Import";
	public String exportArtDat = "... databáze artefaktů";
	public String exportPostDat = "... databáze postav";
	public String exportArtOne = "... jednoho artefaktu";
	public String exportPostOne = "... jedné postavy";
	public String utility = "Utility";
	public String Dice = "Kostka";
	public String PJInfo = "PJGUI";
	public String ExportLang = "ExportLang";

	public String ipField = "IP serveru";
	public String portField = "Port serveru";
	public String nameField = "Nick";
	public String Connect = "Připojit";
	public String ConnectFrame = "Připojení k serveru";
	public String IOError = "Chyba při připojování";
	public String ConnectError = "Nepodařilo se připojit na server z důvodu: ";
	public String Position = "Pozice: ";
	public String ConnectLabel = "Připojeno: ";
	public String DisconnectMessage = "Odpojeno od serveru z duvodu: ";
	public String DisconnectWindow = "Odpojeno";
	public String KickMessage = "Vyhozen ze serveru z duvodu: ";
	public String KickWindow = "Vyhozen ze serveru";
	public String Range = "Rozsah";
	public String Modifier = "Bonus";
	// public static String Player = "Play";

	// Dialogové překlady
	public String Close = "Zavřít";
	public String Confirm = "Ok";
	public String Reset = "Reset";
	public String End = "Konec";

	// FileVersionCheck
	public String OldFileVersionText = "Vybrany soubor je uložen ve starší verzi formátu, chcete se ho přesto pokusit načíst?";
	public String OldFileVersionYes = "Ano";
	public String OldFileVersionNo = "Ne";
	public String OldFileVersionHeader = "Detekována stará verze";

	// CreateServerFrame
	public String ServerCreate = "Vytvořit";
	public String ServerCreateFrame = "Vytváření serveru";
	public String ServerIOError = "Chyba při vytváření";
	public String ServerError = "Nepodařilo se vytvořit server z důvodu: ";

	// Server
	public String ServerVersion = "Verze serveru %Version, verze jádra %coreVersion";
	public String ClientConnected = "Hráč připojen %name";
	public String ClientDisconnected = "Odpojen %pj %name z důvodu: %error";
	public String ClientKicked = "Vyhozen %pj %name z důvodu: %error";
	public String PJDisconnected = "Odpojen %pj %name z důvodu: %error";
	public String RadiusReceived = "Radius hexapaperu přijat";
	public String EntityReceived = "Entity Hexapaperu přijaty";
	public String ArtefactsReceived = "Artefacty přijaty";
	public String CharactersReceived = "Postavy přijaty";
	public String DiceRolled = "%name si hodil %roll na %range kostce.";
	public String DiceRolledModifier = "%name si hodil %modifier na %range kostce se základním hodem %roll";
	public String ClientVersion = "%name má verzi %version";
	public String ClientsetPJ = "Hráči %name byl nastaven PJ";
	public String ClientNoPlayer = "Hráč %name není připojen";
	public String ServerNoPJ = "PJ není zvolen";
	public String ServerPJ = "PJ je %name";
	public String ServerDice = "(Příkaz)%name si hodil %roll na %range kostce.";

	public String DataLoss = "Ztráta dat";
	public String DataLossMessage = "Pozor program automaticky NEUKLÁDÁ změny v databázích. \nVšechny neuložené změny budou ztraceny. \nChcete opravdu skončit?";
	public String LanguageChange = "Změna jazyka";
	public String LanguageChangeMessage ="Změna se projeví až po restartu aplikace.";
	
	public String diceTitle = "Kostka Log";

	// Commands strings
	public String pjHelp = "Zkontroluje, zda je hráč PJ";
	public String pjUsage = "pj <Name>";
	public String setpjUsage = "setpj <Name>";
	public String setpjHelp = "Nastaví PJ";
	public String kickUsage = "Kick <Name> <Reason>";
	public String kickHelp = "Vykopne hráče ze srveru";
	public String diceUsage = "Dice <Roll> <Side> <Player>";
	public String diceHelp = "Hodí za hráče";
	public String versionUsage = "Version <Name>";
	public String versionHelp = "Požádá hráče o verzi clienta";
	
	public String LangBar = "Jazyky";

	public static <T extends HPStrings> T loadFile(String name) throws InstantiationException, ClassNotFoundException, IllegalAccessException{
		Class<T> type = (Class<T>) Class.forName("hexapaper.language."+name);
		return type.newInstance();
	}
}
