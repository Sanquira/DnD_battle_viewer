package hexapaper.entity;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import core.Location;
import hexapaper.source.BPolygon;

public abstract class HPEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6970412701137178068L;
	public boolean isColidable;
	public boolean isRotateable;
	public Location loc;
	public ArrayList<BPolygon> prvek = new ArrayList<>();
	public Color background = Color.white;
	public String tag = null;
	protected String name;

	public HPEntity(String name, Location loc, boolean Rotatable, boolean Colidable, ArrayList<BPolygon> prvek)
	{
		this.name = name;
		this.loc = new Location(loc);
		this.isRotateable = Rotatable;
		this.isColidable = Colidable;
		this.prvek = ClonePrvek(prvek);
		setTag(name);
	}

	public HPEntity(String name, Location loc, boolean Rotatable, boolean Colidable, BPolygon prvek)
	{
		this.name = name;
		this.loc = new Location(loc);
		this.isRotateable = Rotatable;
		this.isColidable = Colidable;
		this.prvek.add(new BPolygon(prvek));
		setTag(name);
	}

	public HPEntity(HPEntity entity)
	{
		this(entity.name, entity.loc, entity.isRotateable, entity.isColidable, entity.prvek);
		this.background = entity.background;
	}

	private static ArrayList<BPolygon> ClonePrvek(ArrayList<BPolygon> value)
	{
		ArrayList<BPolygon> ret = new ArrayList<BPolygon>();
		for (int i = 0; i < value.size(); i++)
		{
			ret.add(new BPolygon(value.get(i)));
		}
		return ret;
	}

	public Color getBcg()
	{
		return background;
	}

	public HPEntity setBcg(Color clr)
	{
		this.background = clr;
		return this;
	}

	public HPEntity setTag(String tag)
	{
		if (this.tag == null)
		{
			try
			{
				this.tag = name.substring(0, 2);
			} catch (StringIndexOutOfBoundsException e)
			{
				this.tag = name.substring(0, name.length());
			}
		} else
		{
			this.tag = tag;
		}
		return this;
	}

	public String getNick()
	{
		return name;
	}

	public void setNick(String nick)
	{
		this.name = nick;
	}

	abstract public void recreateGraphics();
}
