package hexapaper.network.server;

import hexapaper.gui.frames.ServerCreateFrame;
import hexapaper.gui.frames.ServerGUI;
import hexapaper.source.HPSklad;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import core.LangFile;
import network.command.users.CommandServer;
import network.core.source.NetworkStorage;

public class HexaServer {
	private static boolean isConsole=false;
	private static HPSklad storage=HPSklad.getInstance();
	protected ServerGUI gui;
	protected CommandServer server;
	
	public HexaServer(boolean GUI,boolean show){
		storage.SetupLang("Czech");
		if(!show){
			new ServerCreateFrame(this);
		}
		else{
			start();
		}
		isConsole=show;
	}
	public void start() {
		server=new CommandServer();
		new ServerListeners(this);
		try {
			if(!isConsole){
				gui = new ServerGUI(server);
				gui.setVisible(true);
				//System.out.println("test");
				//gui.getInfo().insertMessage("test");
			}
			server.create(storage.c.serverIP,storage.c.serverport);
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("Version", HPSklad.VERSION);
			map.put("coreVersion", NetworkStorage.version);
			System.out.println(LangFile.sub(storage.str.ServerVersion, map));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
			  storage.str.ServerError+e.getMessage(),
			  storage.str.ServerIOError,
			  JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
	}
}
