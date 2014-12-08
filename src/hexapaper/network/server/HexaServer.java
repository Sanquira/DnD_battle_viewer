package hexapaper.network.server;

import hexapaper.gui.ServerCreateFrame;
import hexapaper.gui.ServerGUI;
import hexapaper.source.HPSklad;

import java.io.IOException;

import network.command.users.CommandServer;

public class HexaServer {
	private static boolean isConsole=false;
	private static CommandServer s=null;
	public static void main(String[] args) throws IOException {
		HPSklad.getInstance().init();
		if(isConsole){
			consoleStart(args);
			return;
		}
		guiStart();
	}
	private static void guiStart() {		
		new ServerCreateFrame();	
	}
	public static Exception consoleStart(String[] args) {
		s=new CommandServer();		
		new ServerListeners(s);
		try {
			s.create(args[0],Integer.parseInt(args[1]));
			if(!isConsole){
				new ServerGUI(s).setVisible(true);
			}
			System.out.println("Verze serveru "+HPSklad.getInstance().VERSION);
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e;
		}
		return null;
		
	}
}
