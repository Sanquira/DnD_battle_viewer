package hexapaper.entity;

import java.awt.Color;
import java.io.Serializable;

public abstract class HPEntity implements Cloneable,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6970412701137178068L;
	public static boolean isColidable = true;
	public static boolean isRotateable = true;
	public Color background = Color.white;

	public HPEntity() {

	}
	public HPEntity(Color background){
		this.setBcg(background);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public HPEntity clone() {
//		try {
//			HPEntity cloned = (HPEntity) super.clone();
//			cloned.loc = cloned.loc.clone();
//			cloned.prvek = (ArrayList<BPolygon>) cloned.prvek.clone();
//			for (int i = 0; i < cloned.prvek.size(); i++) {
//				cloned.prvek.set(i, cloned.prvek.get(i).clone());
//			}
//			return cloned;
//		} catch (CloneNotSupportedException e) {
//			System.err.println(e);
//		}
//		return null;
//	}

	public Color getBcg() {
		return background;
	}

	public void setBcg(Color clr) {
		this.background = clr;
	}

	abstract public void recreateGraphics();
}
