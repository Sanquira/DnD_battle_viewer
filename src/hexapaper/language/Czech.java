package hexapaper.language;


public class Czech extends HPStrings {
	
	public static String lang_name = "Čeština";
	
	public Czech(){
		super.Warning = "Varování­";
		super.WarningNameIsEmpty = "Hodnota jméno nesmí být prázdná!!!";
		super.CreateCharacter = "Vytvoř postavu";
		super.CreatedCharacters = "Vytvořené postavy";
		super.CreateArtefact = "Vytvoř artefakt";
		super.CreatedArtefacts = "Vytvořené artefakty";
		super.CreateHex = "Vytvoř hexu";
		super.Yes = "Ano";
		super.No = "Ne";
		super.NPC = "NPC";
		super.Artefact = "Artefakt";
		super.Radius = "Poloměr Hexu";
		super.LineCount = "Počet řádků";
		super.CollumnCount = "Počet sloupců";

		super.Hex_ext = "hex";
		super.Hex_text = "Hexovy papir";
		super.Db_ext = "entd";
		super.Db_text = "Database entit";
		super.File_ext = "ent";
		super.File_text = "Soubory entit";
		super.desc = "HexaPaper soubory";

		super.newPaper = "Nový papír";
		super.savePaper = "Ulož papír";
		super.loadPaper = "Načti papír";
		super.addArt = "Přidej artefakt";
		super.addPost = "Přidej postavu";

		super.Battlecontrol = "Ovládání bitvy";
		super.Objproperties = "Vlastnosti objektu";
		super.showPlayerColor = "Skrýt hráče";
		super.showNPCColor = "Skrýt NPC";
		super.addWall = "Přidej zeď";
		super.addFreeSpace = "Odeber";

		super.player = "Hráč";
		super.LangFileLoaded = "Jazykový soubor načten";
		super.name = "Jméno";
		super.Tag = "Tag";
		super.addPropBut = "Přidej";
		super.delPropBut = "Odeber";

		super.GameMenu = "Hra";
		super.EditMenu = "Úpravy";
		super.export = "Export";
		super.exportAP = "Export ...";
		super.importAP = "Import";
		super.exportArtDat = "... databáze artefaktů";
		super.exportPostDat = "... databáze postav";
		super.exportArtOne = "... jednoho artefaktu";
		super.exportPostOne = "... jedné postavy";
		super.utility = "Utility";
		super.Dice = "Kostka";
		super.PJInfo = "PJGUI";
		super.ExportLang = "ExportLang";

		super.ipField = "IP serveru";
		super.portField = "Port serveru";
		super.nameField = "Nick";
		super.Connect = "Připojit";
		super.ConnectFrame = "Připojení k serveru";
		super.IOError = "Chyba při připojování";
		super.ConnectError = "Nepodařilo se připojit na server z důvodu: ";
		super.Position = "Pozice: ";
		super.ConnectLabel = "Připojeno: ";
		super.DisconnectMessage = "Odpojeno od serveru z duvodu: ";
		super.DisconnectWindow = "Odpojeno";
		super.KickMessage = "Vyhozen ze serveru z duvodu: ";
		super.KickWindow = "Vyhozen ze serveru";
		super.Range = "Rozsah";
		super.Modifier = "Bonus";
		// public static String Player = "Play";

		// Dialogové překlady
		super.Close = "Zavřít";
		super.Confirm = "Ok";
		super.Reset = "Reset";
		super.End = "Konec";

		// FileVersionCheck
		super.OldFileVersionText = "Vybrany soubor je uložen ve starší verzi formátu, chcete se ho přesto pokusit načíst?";
		super.OldFileVersionYes = "Ano";
		super.OldFileVersionNo = "Ne";
		super.OldFileVersionHeader = "Detekována stará verze";

		// CreateServerFrame
		super.ServerCreate = "Vytvořit";
		super.ServerCreateFrame = "Vytváření serveru";
		super.ServerIOError = "Chyba při vytváření";
		super.ServerError = "Nepodařilo se vytvořit server z důvodu: ";

		// Server
		super.ServerVersion = "Verze serveru %Version, verze jádra %coreVersion";
		super.ClientConnected = "Hráč připojen %name";
		super.ClientDisconnected = "Odpojen %pj %name z důvodu: %error";
		super.ClientKicked = "Vyhozen %pj %name z důvodu: %error";
		super.PJDisconnected = "Odpojen %pj %name z důvodu: %error";
		super.RadiusReceived = "Radius hexapaperu přijat";
		super.EntityReceived = "Entity Hexapaperu přijaty";
		super.ArtefactsReceived = "Artefacty přijaty";
		super.CharactersReceived = "Postavy přijaty";
		super.DiceRolled = "%name si hodil %roll na %range kostce.";
		super.DiceRolledModifier = "%name si hodil %modifier na %range kostce se základním hodem %roll";
		super.ClientVersion = "%name má verzi %version";
		super.ClientsetPJ = "Hráči %name byl nastaven PJ";
		super.ClientNoPlayer = "Hráč %name není připojen";
		super.ServerNoPJ = "PJ není zvolen";
		super.ServerPJ = "PJ je %name";
		super.ServerDice = "(Příkaz)%name si hodil %roll na %range kostce.";

		super.DataLoss = "Ztráta dat";
		super.DataLossMessage = "Pozor program automaticky NEUKLÁDÁ změny v databázích. \nVšechny neuložené změny budou ztraceny. \nChcete opravdu skončit?";

		super.diceTitle = "Kostka Log";

		// Commands strings
		super.pjHelp = "Zkontroluje, zda je hráč PJ";
		super.pjUsage = "pj <Name>";
		super.setpjUsage = "setpj <Name>";
		super.setpjHelp = "Nastaví PJ";
		super.kickUsage = "Kick <Name> <Reason>";
		super.kickHelp = "Vykopne hráče ze srveru";
		super.diceUsage = "Dice <Roll> <Side> <Player>";
		super.diceHelp = "Hodí za hráče";
		super.versionUsage = "Version <Name>";
		super.versionHelp = "Požádá hráče o verzi clienta";
	}

}
