package hexapaper.source;

public class HPStrings {

	public static String varovani = "Varování­";
	public static String warningNameIsEmpty = "Hodnota jméno nesmí být prázdná!!!";
	public static String vytvorPostavu = "Vytvoř postavu";
	public static String vytvorenePostavy = "Vytvořené postavy";
	public static String vytvorArtefakt = "Vytvoř artefakt";
	public static String vytvoreneArtefakty = "Vytvořené artefakty";
	public static String vytvorPaper = "Vytvoř hexu";
	public static String ano = "Ano";
	public static String ne = "Ne";
	public static String NPC = "NPC";
	public static String artefakt = "Artefakt";
	public static String polomerHexu = "Polomšr Hexu";
	public static String pocetRadku = "Počet řádků";
	public static String pocetSloupcu = "Počet sloupců";

	public static String Hex_ext = "hex";
	public static String Hex_text = "Hexovy papir";
	public static String Db_ext = "entd";
	public static String Db_text = "Database entit";
	public static String File_ext = "ent";
	public static String File_text = "Soubory entit";
	public static String desc = "HexaPaper soubory";

	public static String newPaper = "Nový papír";
	public static String savePaper = "Ulož papír";
	public static String loadPaper = "Načti papír";
	public static String addArt = "Přidej artefakt";
	public static String addPost = "Přidej postavu";

	public static String ovladaniBitvy = "Ovládání bitvy";
	public static String vlastnostiObj = "Vlastnosti objektu";
	public static String showPlayerColor = "Skrýt hráče";
	public static String showNPCColor = "Skrýt NPC";
	public static String addWall = "Přidej zeď";
	public static String addFreeSpace = "Odeber";

	public static String player = "Hráč";
	public static String LangFileLoaded = "Jazykový soubor načten";
	public static String name = "Jméno";
	public static String Tag = "Tag";
//	public static String race = "Rasa";
//	public static String health = "Zdraví";
//	public static String mags = "Magy";
//	public static String weapon = "Zbraň";
//	public static String armor = "Armor";
//	public static String type = "Typ";
	public static String addPropBut = "Přidej";
	public static String delPropBut = "Odeber";

	public static String hra = "Hra";
	public static String upravy = "Úpravy";
	public static String export = "Export";
	public static String exportAP = "Export ...";
	public static String importAP = "Import";
	public static String exportArtDat = "... databáze artefaktů";
	public static String exportPostDat = "... databáze postav";
	public static String exportArtOne = "... jednoho artefaktu";
	public static String exportPostOne = "... jedné postavy";
	public static String utility = "Utility";
	public static String kostka = "Kostka";
	public static String PJInfo = "PJGUI";
	public static String ExportLang = "ExportLang";
		
	public static String ipField = "IP serveru";
	public static String portField = "Port serveru";
	public static String nameField = "Nick";
	public static String Connect = "Připojit";
	public static String ConnectFrame = "Připojení k serveru";
	public static String IOError = "Chyba při připojování";
	public static String ConnectError = "Nepodařilo se připojit na server z důvodu: ";
	public static String Position = "Pozice: ";
	public static String ConnectLabel = "Připojeno: ";
	public static String DisconnectMessage = "Odpojeno od serveru";
	public static String DisconnectWindow = "Odpojeno";
	public static String KickMessage = "Vyhozen ze serveru z duvodu: ";
	public static String KickWindow = "Vyhozen ze serveru";
	public static String Range = "Rozsah";
	public static String Modifier = "Bonus";	
	//public static String Player = "Play";
	
	//Dialogové překlady
	public static String Close = "Zavřít";
	public static String Confirm = "Ok";
	public static String Reset = "Reset";
	public static String konec = "Konec";
	
	//FileVersionCheck
	public static String OldFileVersionText = "Vybrany soubor je uložen ve starší verzi formátu, chcete se ho přesto pokusit načíst?";
	public static String OldFileVersionYes = "Ano";
	public static String OldFileVersionNo = "Ne";
	public static String OldFileVersionHeader = "Detekována stará verze";
	
	//CreateServerFrame
	public static String ServerCreate = "Vytvořit";
	public static String ServerCreateFrame = "Vytváření serveru";
	public static String ServerIOError = "Chyba při vytváření";
	public static String ServerError = "Nepodařilo se vytvořit server z důvodu: ";
		
	//Server
	public static String ServerVersion = "Verze serveru ";
	public static String ClientConnected = "Hráč připojen %name";
	public static String ClientDisconnected = "Odpojen %pj %name z důvodu: %error";
	public static String PJDisconnected = "Odpojen %pj %name z důvodu: %error";
	public static String RadiusReceived = "Radius hexapaperu přijat";
	public static String EntityReceived = "Entity Hexapaperu přijaty";
	public static String ArtefactsReceived = "Artefacty přijaty";
	public static String CharactersReceived = "Postavy přijaty";
	public static String DiceRolled = "%name si hodil %roll na %range kostce.";
	public static String DiceRolledModifier = "%name si hodil %modifier na %range kostce se základním hodem %roll";
	public static String ClientVersion = "%name má verzi %version";
	public static String ClientsetPJ = "Hráči %name byl nastaven PJ";
	public static String ClientNoPlayer = "Hráč %name není připojen";	
	public static String ServerNoPJ = "PJ není zvolen";
	public static String ServerPJ = "PJ je %name";
	public static String ServerDice = "(Příkaz)%name si hodil %roll na %range kostce.";
	
	public static String ztrataDat = "Ztráta dat";
	public static String zpravaZtrataDat = "Pozor program automaticky NEUKLÁDÁ změny v databázích. \nVšechny neuložené změny budou ztraceny. \nChcete opravdu skončit?";
	
	public static String diceTitle = "Kostka Log";
	
	//Commands strings
	public static String pjHelp = "Zkontroluje, zda je hráč PJ";
	public static String pjUsage = "pj <Name>";
	public static String setpjUsage = "setpj <Name>";
	public static String setpjHelp = "Nastaví PJ";
	public static String kickUsage = "Kick <Name> <Reason>";
	public static String kickHelp = "Vykopne hráče ze srveru";
	public static String diceUsage = "Dice <Roll> <Side> <Player>";
	public static String diceHelp = "Hodí za hráče";
	public static String versionUsage = "Version <Name>";
	public static String versionHelp = "Požádá hráče o verzi clienta";
}
