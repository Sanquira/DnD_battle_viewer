package hexapaper.entity;

import core.Location;
import hexapaper.gui.Gprvky;

public class FreeSpace extends HPEntity {

	public FreeSpace(Location loc) {
		super("", loc, false, true, new Gprvky().emptyHexagon(loc));
	}

	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek.add(new Gprvky().emptyHexagon(loc));
	}

}
