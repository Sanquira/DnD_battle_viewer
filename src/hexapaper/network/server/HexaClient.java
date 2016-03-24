package hexapaper.network.server;

import java.awt.Color;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map.Entry;

import hexapaper.entity.FreeSpace;
import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;
import network.command.users.CommandClient;

public class HexaClient extends CommandClient{
	private HPSklad storage=HPSklad.getInstance();
	public HexaClient(){
		new ClientListeners(this,storage);
	}
	public void updateDatabase(){
		send(storage.databazeArtefaktu,"DBartefact");
		send(storage.databazePostav,"DBcharacter");
	}
	public void updateHexapaper() {
		for(Entry<Long, HPEntity> set: storage.entities.entrySet()){
			Object[] o = {set.getKey(),set.getValue()};
			send(o,"insertEnt");
		}
//		for(Integer i=0;i<storage.souradky.size();i++){
//			HPEntity e = storage.souradky.get(i);
//			if(!(e instanceof FreeSpace) || e.getBcg()!= Color.WHITE){
//				Object[] o = {i,e};
//				send(o,"insertEnt");
//			}
//		}
//			for(HPEntity e:storage.souradky){
//				if(!(e instanceof FreeSpace)){
//					Object[] o = {(Integer) storage.souradky.indexOf(e),e};
//					send(o,"insertEnt");
//				}
//			}
	}
	public void radiusHexapaper(){
		Object[] hexapaper={storage.c.gridRa,storage.c.gridSl,storage.c.RADIUS};
		send(hexapaper, "RadiusHexapaper");
	}
//	public void updateNameTag(HPEntity e){
//		Object[] o={e.loc.getX(),e.loc.getY(),e.tag};
//		send(o,"EntChangeTag");
//	}
	public void sendCoord(Integer x,Integer y){
		Integer[] table={x,y};
		send(table,"PJcursor");
	}
	public void connect(String ip,int port,String name) throws UnknownHostException, IOException{
		super.connect(ip, port, name);
		storage.updateConnectInfo();
	}
}
