package hexapaper.entity;

import java.awt.Color;

import hexapaper.gui.Gprvky;
import hexapaper.source.Location;

public class Wall extends Entity {

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
