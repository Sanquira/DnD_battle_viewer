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
import core.Location;

public class Wrappers {

	public class HexWrapper{
		public Integer GridSl, Radius, GridRA;
		public String Version;
		public DatabaseWrapper Database=new DatabaseWrapper(); 
		public ArrayList<EntityWrapper> Entity=new ArrayList<EntityWrapper>();
		public HexWrapper(Integer GridSl,Integer Radius, Integer GridRA,String Version){
			this.GridSl=GridSl;
			this.Radius=Radius;
			this.GridRA=GridRA;
			this.Version=Version;
		}		
		public ArrayList<HPEntity> load(){
			return Database.load(GridRA, GridSl);
		}
		public void addEntities(ArrayList<HPEntity> c){
			Database.addEntities(c);
		}
		
	}
	
	public class EntityWrapper{
		public String Type, Name, Tag;
		public Integer Bcg, pos;
		public Location loc;
		public ArrayList<PropPair> List;
		public EntityWrapper(String Type, int pos,HPEntity ent, ArrayList<PropPair> List){
			this.Type = Type;
			this.Bcg = ent.background.getRGB();
			this.pos = pos;
			this.Name = ent.getName();
			this.Tag = ent.tag;
			this.loc = ent.loc;
			this.List = List;
		}
	}
	public class DatabaseWrapper{
		public String Version;
		public ArrayList<EntityWrapper> Entity=new ArrayList<EntityWrapper>();
		public void addEntity(HPEntity e,Integer position){
			ArrayList<PropPair> List=new ArrayList<PropPair>();
			String Type="FreeSpace";
			if(e instanceof Artefact){
				Type="Artefact";
				List=((Artefact) e).getParam();
			}
			if(e instanceof Postava){
				Type="Postava";
				List=((Postava) e).getParam();
			}
			if(e instanceof Wall){
				Type="Wall";
			}
			Entity.add(new EntityWrapper(Type,position,e,List));
		}
		public void addEntities(ArrayList<? extends HPEntity> coords){
			for(HPEntity e:coords){
				if(!(e instanceof FreeSpace)||e.getBcg()!=Color.WHITE){
					addEntity(e,coords.indexOf(e));
				}
			}
		}
		public ArrayList<HPEntity> load(Integer GridRA,Integer GridSl){
			ArrayList<HPEntity> souradky=new ArrayList<HPEntity>();
			for (int i = 0; i < GridRA * GridSl; i++) {
				souradky.add(new FreeSpace(HPSklad.getInstance().LocDontCare));
			}
			for (EntityWrapper wrap : Entity) {
				if(wrap.Type.equals("Artefact")){
					souradky.set(wrap.pos,new Artefact(wrap.Name,wrap.loc,wrap.List).setBcg(new Color(wrap.Bcg)).setTag(wrap.Tag));
				}
				if(wrap.Type.equals("Postava")){
					souradky.set(wrap.pos,new Postava(wrap.Name,wrap.loc,false,wrap.List).setBcg(new Color(wrap.Bcg)).setTag(wrap.Tag));
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
		public void loadDatabase(){
			HPSklad sk=HPSklad.getInstance();
			for (EntityWrapper wrap : Entity){
				loadEntity(wrap,sk);
			}
			sk.RMenu.cpane.updateDatabase();
		}
		public void loadEntity(EntityWrapper wrap,HPSklad sk){
			if(wrap.Type.equals("Artefact")){
				sk.databazeArtefaktu.add((Artefact) new Artefact(wrap.Name,wrap.Tag,wrap.loc,wrap.List).setBcg(new Color(wrap.Bcg)));
			}
			if(wrap.Type.equals("Postava")){
				sk.databazePostav.add((Postava) new Postava(wrap.Name,wrap.Tag,wrap.loc,false,wrap.List).setBcg(new Color(wrap.Bcg)));
			}
		}
	}
}
