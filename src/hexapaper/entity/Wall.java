package hexapaper.entity;

import java.awt.Color;

import core.Location;

import hexapaper.gui.Gprvky;

public class Wall extends HPEntity {

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
