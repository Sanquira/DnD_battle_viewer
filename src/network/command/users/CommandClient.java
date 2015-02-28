package network.command.users;

import java.io.IOException;
import java.net.UnknownHostException;

import network.command.annotations.CommandAnnotationChecker;
import network.command.interfaces.CommandListener;
import network.command.source.CommandHandler;
import network.command.source.CommandInfo;
import network.command.source.CommandStorage;
import network.core.users.NetworkClient;

public class CommandClient extends NetworkClient {
	CommandStorage cmd=CommandStorage.getInstance();
	private CommandHandler cmdThread;
	public CommandClient(){
		cmd.reset();
	}
	public void registerCommand(String name,int arguments,String usage,String help, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, arguments,usage,help,listener));
	}
	public void registerCommand(String name,int min,int max,String usage,String help, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, min,max,usage,help,listener));
	}
	public void setDefaultCommand(CommandListener commandListener){
		cmd.setDefaultCommand(new CommandInfo("default",CommandStorage.UNLIMITED,"","",commandListener));
	}
	@Override
	public void connect(int timeout, String hostName, int port, String name) throws UnknownHostException, IOException{
		super.connect(timeout, hostName, port, name);
		registerInitialcommands();
		cmdThread=new CommandHandler();
	}
	@Override
	public void connect(String hostName, int port, String name) throws UnknownHostException, IOException{
		connect(getNetworkStorage().defaultclientTimeout,hostName,port,name);
	}
	public CommandStorage getCommandStorage(){
		return cmd;
	}
	public void registerInitialcommands(){
		registerCommand("help", 0, "Help", "Zobrazí všechny dostupné příkazy", cmd.help);
	}
	@Override
	public void registerClass(Object obj){
		super.registerClass(obj);
		try {
			new CommandAnnotationChecker(obj,this).processClass();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void disconnect() throws IOException{
		super.disconnect();
		cmdThread.interrupt();
	}
}
