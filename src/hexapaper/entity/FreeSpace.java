package hexapaper.entity;

import hexapaper.gui.Gprvky;
import hexapaper.source.Location;

public class FreeSpace extends Entity {

	public FreeSpace(Location loc) {
		super("", loc, false, true, new Gprvky().emptyHexagon(loc));
	}

	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek.add(new Gprvky().emptyHexagon(loc));
	}

}
