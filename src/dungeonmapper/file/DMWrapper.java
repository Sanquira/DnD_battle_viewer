package dungeonmapper.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dungeonmapper.gui.DrawPlane;
import dungeonmapper.source.DMGridElement;
import dungeonmapper.source.DMMapTypesEnum;
import dungeonmapper.source.DMSklad;

public class DMWrapper {	
	
	public int COLS,ROW,CSIZE;
	public HashMap<Integer,ArrayList<DMGridElement>> layers = new HashMap<Integer,ArrayList<DMGridElement>>();
	public DMWrapper(int COLS,int ROW,int CSIZE){
		this.COLS=COLS;
		this.ROW=ROW;
		this.CSIZE=CSIZE;
	}
	public void addEntities(HashMap<Integer,ArrayList<DMGridElement>> original){
		ArrayList<DMGridElement> filtered;
		for(Entry<Integer, ArrayList<DMGridElement>> set:original.entrySet()){
			ArrayList<DMGridElement> layer = set.getValue();
			Integer pos = set.getKey();
			filtered=new ArrayList<DMGridElement>();
			for(DMGridElement ele:layer){
				if(ele.getType()!=DMMapTypesEnum.W){
					//ele.setY(null);
					ele.pos=layer.indexOf(ele);
					filtered.add(ele);
				}
			}
			layers.put(pos,filtered);
		}
	}
	public void loadEntities(){
		DMSklad sk=DMSklad.getInstance();
		DrawPlane drawPlane=sk.drawPlane;
		sk.COLS=COLS;
		sk.CSIZE=CSIZE;
		sk.ROWS=ROW;
		drawPlane.layers=new HashMap<Integer,ArrayList<DMGridElement>>();
		for(Entry<Integer, ArrayList<DMGridElement>> set:layers.entrySet()){
			ArrayList<DMGridElement> layer = set.getValue();
			Integer pos = set.getKey();
			Integer size=drawPlane.layers.size();
			drawPlane.genDefaultGrid(pos);
			ArrayList<DMGridElement> list=drawPlane.layers.get(size);
			for(DMGridElement ele:layer){
				list.set(ele.pos,ele);
			}
			drawPlane.layers.put(pos,list);
		}
//		for(ArrayList<DMGridElement> layer:drawPlane.layers){
//			System.out.println("Layer");
//			for(DMGridElement e:layer){
//				if(e.getType()!=DMMapTypesEnum.W){
//					System.out.println("Entity");
//				}
//			}
//		}
//		for(DMGridElement e:drawPlane.layers.get(1)){
//			if(e.getType()!=DMMapTypesEnum.W){
//				System.out.println("Entity");
//			}
//		}
		drawPlane.revalidate();		
		drawPlane.repaint();
		
	}
}
