package network.command.users;

import java.io.IOException;
import java.net.UnknownHostException;

import network.command.interfaces.CommandListener;
import network.command.source.CommandHandler;
import network.command.source.CommandInfo;
import network.command.source.CommandStorage;
import network.core.users.NetworkClient;

public class CommandClient extends NetworkClient {
	CommandStorage cmd=CommandStorage.getInstance();
	public CommandClient(){
		cmd.reset();
	}
	public void registerCommand(String name,int arguments,String usage,String help, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, arguments,usage,help,listener));
	}
	public void setDefaultCommand(CommandListener commandListener){
		cmd.setDefaultCommand(new CommandInfo("default",CommandStorage.UNLIMITED,"","",commandListener));
	}
	public void connect(String hostName,int port, String name) throws UnknownHostException, IOException{
		super.connect(hostName, port, name);
		registerInitialcommands();
		Thread cmd=new Thread(new CommandHandler());
		cmd.setName("CommandThread");
		cmd.start();
	}
	public CommandStorage getCommandStorage(){
		return cmd;
	}
	public void registerInitialcommands(){
		registerCommand("help", 0, "Help", "Zobrazí všechny dostupné příkazy", cmd.help);
	}
}
