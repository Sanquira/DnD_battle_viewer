package hexapaper.language;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="LanguageStrings")
public class HPStrings {

	public String lang_name = "English";
	
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
	public String Radius = "Hex Radius";
	public String LineCount = "Number of lines";
	public String CollumnCount = "Number of collumns";

	public String Hex_ext = "hex";
	public String Hex_text = "HexaPaper";
	public String Db_ext = "entd";
	public String Db_text = "Entity Databaze";
	public String File_ext = "ent";
	public String File_text = "Entity";
	public String desc = "Hexa files";

	public String newPaper = "New paper";
	public String savePaper = "Save paper";
	public String loadPaper = "Load paper";
	public String addArt = "Create artefact";
	public String addPost = "Create character";

	public String Battlecontrol;
	public String Objproperties = "Properties";
	public String showPlayerColor = "Skrýt hráče";
	public String showNPCColor = "Hide NPC";
	public String addWall = "Add wall";
	public String addFreeSpace = "Remove";

	public String player = "Player";
	public String LangFileLoaded = "Loaded language file";
	public String name = "Name";
	public String Tag = "Tag";
	public String addPropBut = "Add";
	public String delPropBut = "Remove";

	public String GameMenu = "Game";
	public String EditMenu = "Edit";
	public String export = "Export";
	public String exportAP = "Export ...";
	public String importAP = "Import";
	public String exportArtDat = "... artefact database";
	public String exportPostDat = "... character database";
	public String exportArtOne = "... artefact";
	public String exportPostOne = "... character";
	public String utility = "Utilities";
	public String Dice = "Dice";
	public String PJInfo = "PJGUI";

	public String ipField = "Server IP";
	public String portField = "Server port";
	public String nameField = "Nick";
	public String Connect = "Connect";
	public String ConnectFrame = "Connecting";
	public String IOError = "Connecting error";
	public String ConnectError = "Unable to connect to server: ";
	public String Position = "Position: ";
	public String ConnectLabel = "Connected: ";
	public String DisconnectMessage = "Disconnected from server: ";
	public String DisconnectWindow = "Disconnected";
	public String KickMessage = "Kick from server: ";
	public String KickWindow = "Kick from server";
	public String Range = "Range";
	public String Modifier = "Modifier";
	// public static String Player = "Play";

	// Dialogové překlady
	public String Close = "Close";
	public String Confirm = "Ok";
	public String Reset = "Reset";
	public String End = "End";

	// FileVersionCheck
	public String OldFileVersionText = "Selected file is in older version of fileformat, do you still want to load it?";
	public String OldFileVersionYes = "Yes";
	public String OldFileVersionNo = "No";
	public String OldFileVersionHeader = "Old version detected";

	// CreateServerFrame
	public String ServerCreate = "Start";
	public String ServerCreateFrame = "Start a server";
	public String ServerIOError = "Error while starting server";
	public String ServerError = "Unable to start server due to: ";

	// Server
	public String ServerVersion = "Server version %Version, core version %coreVersion";
	public String ClientConnected = "Player connected %name";
	public String ClientDisconnected = "%pj %name was disconnected due to: %error";
	public String ClientKicked = "Kicked %pj %name because of: %error";
	public String RadiusReceived = "Radius accepted";
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
	
	public String diceTitle = "Log kostky";	
	public String colorPickerTitle = "Color Picker";
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
	public String disconnect = "Odpojit se";
	
	// GC Strings
	public String GCsetPosition = "Nastav pozici";
	public String GCsetPositionMessage = "Zadej pozici hexy na kterou chceš přejít.";

	public static <T extends HPStrings> T loadFile(String name) throws InstantiationException, ClassNotFoundException, IllegalAccessException{
		Class<T> type = (Class<T>) Class.forName("hexapaper.language."+name);
		return type.newInstance();
	}
}
