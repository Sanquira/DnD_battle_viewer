package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientInfo {
	private int port;
	private String hostName;
	private String nick;
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Map<String,Object> atributes=new HashMap<String,Object>();
	
	public ClientInfo(String hostName, int port, String nick, Socket socket, ObjectInputStream i, ObjectOutputStream o){
		this.port=port;
		this.hostName=hostName;
		this.nick=nick;
		this.setSocket(socket);
		this.inputStream=i;
		this.outputStream=o;
		
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public ObjectInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void send(Object o) throws IOException{
		try{
			outputStream.writeObject(new MessagePacket("Server",o));
			outputStream.flush();
		}
		catch(IOException e){
			e.printStackTrace();
			kick();
		}
	}
	public void send(Object o,String header) throws IOException{
		try{
			outputStream.writeObject(new MessagePacket("Server",header,o));
			outputStream.flush();
		}
		catch(IOException e){
			e.printStackTrace();
			kick();
		}
	}
	public void send(String nick,Object o) throws IOException{
		try{
			outputStream.writeObject(new MessagePacket(nick,"none",o));
			outputStream.flush();
		}
		catch(IOException e){
			e.printStackTrace();
			kick();
		}
	}
	public void send(String nick,Object o,String header) throws IOException{
		try{
			outputStream.writeObject(new MessagePacket(nick,header,o));
			outputStream.flush();
		}
		catch(IOException e){
			e.printStackTrace();
			kick();
		}
	}
	public void kick() throws IOException{
		socket.close();
	}
	public Map<String,Object> getAtributes() {
		return atributes;
	}	
}
