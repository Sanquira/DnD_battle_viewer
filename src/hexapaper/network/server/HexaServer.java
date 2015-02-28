package hexapaper.network.server;

import hexapaper.gui.ServerCreateFrame;
import hexapaper.gui.ServerGUI;
import hexapaper.source.HPSklad;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import network.command.users.CommandServer;
import network.core.source.NetworkStorage;

public class HexaServer {
	private static boolean isConsole=false;
	private static HPSklad storage=HPSklad.getInstance();
	private static CommandServer s=null;
	
	public HexaServer(boolean GUI,boolean show){
		storage.initLang();
		if(!show){
			new ServerCreateFrame(this);
		}
		else{
			start();
		}
		isConsole=show;
	}
	public void start() {
		s=new CommandServer();
		new ServerListeners(s);
		try {
			s.create(storage.c.serverIP,storage.c.port);
			if(!isConsole){
				new ServerGUI(s).setVisible(true);
			}
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("Version", HPSklad.VERSION);
			map.put("coreVersion", NetworkStorage.version);
			System.out.println(storage.str.sub("ServerVersion", map));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
			  storage.str.get("ServerError")+e.getMessage(),
			  storage.str.get("ServerIOError"),
			  JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
	}
}
