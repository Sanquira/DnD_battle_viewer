package hexapaper.entity;

import java.awt.Color;
import java.io.Serializable;

import core.Location;
import hexapaper.gui.Gprvky;

public class Wall extends HPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7675708124367625378L;

	public Wall(Location loc) {
		super("", loc, false, false, new Gprvky().wall(loc));
		setBcg(Color.BLACK);
	}

	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek.add(new Gprvky().wall(loc));
	}

}
