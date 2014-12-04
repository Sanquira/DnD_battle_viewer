package hexapaper.file;

import hexapaper.entity.Artefact;
import hexapaper.entity.FreeSpace;
import hexapaper.entity.HPEntity;
import hexapaper.entity.Postava;
import hexapaper.entity.Wall;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.PropPair;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import core.Location;

public class Wrappers {
	public HPSklad sk=HPSklad.getInstance();
	
	public class HexWrapper{
		public Integer GridSl, Radius, GridRA;
		public String Version;
		public ArrayList<EntityWrapper> Entity=new ArrayList<EntityWrapper>();
		public HexWrapper(Integer GridSl,Integer Radius, Integer GridRA,String Version){
			this.GridSl=GridSl;
			this.Radius=Radius;
			this.GridRA=GridRA;
			this.Version=Version;
		}
		public void addEntity(HPEntity e,Integer position){
			ArrayList<PropPair> List=new ArrayList<PropPair>();
			String Type="FreeSpace";
			String Name=null;
			if(e instanceof Artefact){
				Type="Artefact";
				Name=((Artefact) e).getNick();
				List=((Artefact) e).getParam();
			}
			if(e instanceof Postava){
				Type="Postava";
				Name=((Postava) e).getNick();
				List=((Postava) e).getParam();
			}
			if(e instanceof Wall){
				Type="Wall";
			}
			Entity.add(new EntityWrapper(Type,e.getBcg().getRGB(),e.loc,Name,position,List));
			//Entity.add();
		}
		public void addEntities(ArrayList<HPEntity> coords){
			for(HPEntity e:coords){
				if(!(e instanceof FreeSpace)||e.getBcg()!=Color.WHITE){
					addEntity(e,coords.indexOf(e));
				}
			}
		}
		public ArrayList<HPEntity> load(){
			ArrayList<HPEntity> souradky=new ArrayList<HPEntity>();
			for (int i = 0; i < GridRA * GridSl; i++) {
				souradky.add(new FreeSpace(HPSklad.getInstance().LocDontCare));
			}
			for (EntityWrapper wrap : Entity) {
				if(wrap.Type.equals("Artefact")){
					souradky.set(wrap.pos,new Artefact(wrap.Name,wrap.loc,wrap.List).setBcg(new Color(wrap.Bcg)));
				}
				if(wrap.Type.equals("Postava")){
					souradky.set(wrap.pos,new Postava(wrap.Name,wrap.loc,false,wrap.List).setBcg(new Color(wrap.Bcg)));
				}
				if(wrap.Type.equals("Wall")){
					souradky.set(wrap.pos,new Wall(wrap.loc).setBcg(new Color(wrap.Bcg)));
				}
				if(wrap.Type.equals("FreeSpace")){
					souradky.set(wrap.pos,new FreeSpace(wrap.loc).setBcg(new Color(wrap.Bcg)));
				}
			}
			return souradky;
		}
		public boolean checkVersion(){
			HPSklad sk=HPSklad.getInstance();
			if(Version!=null){
				if(Version==sk.FILEVERSION){
					return true;
				}
			}
			Object[] options = {sk.str.get("OldFileVersionYes"),sk.str.get("OldFileVersionNo")};
			int n = JOptionPane.showOptionDialog(null, sk.str.get("OldFileVersionText"),
						sk.str.get("OldFileVersionHeader"),
						JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,null);
			if(n==JOptionPane.YES_OPTION){
				return true;
			}
			return false;
		}
	}
	
	public class EntityWrapper{
		public String Type, Name;
		public Integer Bcg, pos;
		public Location loc;
		public ArrayList<PropPair> List;
		public EntityWrapper(String Type,Integer bcg,Location loc,String name,Integer pos,ArrayList<PropPair> List){
			this.Type=Type;
			this.Bcg=bcg;
			this.pos=pos;
			this.Name=name;
			this.loc=loc;
			this.List=List;
		}
	}
}
