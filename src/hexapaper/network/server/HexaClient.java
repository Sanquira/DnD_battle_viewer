package hexapaper.network.server;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;
import network.command.users.CommandClient;

public class HexaClient extends CommandClient{
	private HPSklad storage=HPSklad.getInstance();
	public HexaClient(){
		try {
			getSocket().setKeepAlive(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ClientListeners(this,storage);
	}
	public void updateDatabase(){
		try {
			send(storage.databazeArtefaktu,"DBartefact");
			send(storage.databazePostav,"DBcharacter");
		} catch (IOException e) {
			System.out.println("Failed to send databases");
			e.printStackTrace();
		}
	}
	public void updateHexapaper() {
		//Object[] hexapaper={storage.souradky};
		try {
			send(storage.souradky, "EntityHexapaper");
		} catch (IOException e) {
			System.out.println("Failed to send Hexapaper");
			e.printStackTrace();
		}
	}
	public void radiusHexapaper(){
		Object[] hexapaper={storage.c.gridRa,storage.c.gridSl,storage.c.RADIUS};
		try {
			send(hexapaper, "RadiusHexapaper");
		} catch (IOException e) {
			System.out.println("Failed to send Hexapaper");
			e.printStackTrace();
		}
	}
	public void updateNameTag(HPEntity e){
		Object[] o={e.loc.getX(),e.loc.getY(),e.tag};
		try {
			send(o,"EntChangeTag");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void sendCoord(Integer x,Integer y){
		Integer[] table={x,y};
		try {
			send(table,"PJcursor");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void connect(String ip,int port,String name) throws UnknownHostException, IOException{
		super.connect(ip, port, name);
		storage.updateConnect();
	}
}
