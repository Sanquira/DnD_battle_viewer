package network.command.users;

import java.io.IOException;

import network.command.interfaces.CommandListener;
import network.command.source.CommandHandler;
import network.command.source.CommandInfo;
import network.command.source.CommandStorage;
import network.core.users.NetworkServer;

public class CommandServer extends NetworkServer {
	private CommandStorage cmd=CommandStorage.getInstance();
	public void registerCommand(String name,int arguments,String usage, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, arguments,usage,listener));
	}
	public void registerCommand(String name,int min,int max,String usage, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, min,max,usage,listener));
	}
	public void create(String hostname, int port) throws IOException{
		super.create(hostname,port);
		new Thread(new CommandHandler()).start();
	}
	public void create(int port) throws IOException{
		super.create(port);
		new Thread(new CommandHandler()).start();
	}
}
