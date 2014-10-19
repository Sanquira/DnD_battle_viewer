package hexapaper.network.server;

import java.io.IOException;

import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;
import network.command.users.CommandClient;

public class HexaClient extends CommandClient{
	private HPSklad storage=HPSklad.getInstance();
	public HexaClient(){
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
		Object[] hexapaper={storage.gridRa,storage.gridSl,storage.RADIUS};
		try {
			send(hexapaper, "RadiusHexapaper");
		} catch (IOException e) {
			System.out.println("Failed to send Hexapaper");
			e.printStackTrace();
		}
	}
	public void updateNameTag(HPEntity e){
		Object[] o={e.loc.getX(),e.loc.getY(),e.getNick(),e.tag};
		try {
			send(o,"EntChangeName");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
