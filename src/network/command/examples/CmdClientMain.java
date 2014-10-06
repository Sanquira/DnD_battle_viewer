package network.command.examples;

import java.io.IOException;
import java.net.UnknownHostException;

import network.command.users.CommandClient;


public class CmdClientMain {

	public static void main(String[] args) throws IOException {
		CommandClient c=new CommandClient();
		String help = "Použití: client.jar <IP> <Port> <Name>";
		try {
			new ClientListeners(c);
			c.connect(args[0], Integer.parseInt(args[1]), args[2]);
		} catch (UnknownHostException e) {
			System.out.println("Nepodařilo se připojit k serveru na adrese "+args[0]+":"+args[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IndexOutOfBoundsException e){
			System.out.println(help);
		}
		

	}
}
