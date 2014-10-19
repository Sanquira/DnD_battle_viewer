package hexapaper.entity;

import java.io.Serializable;

import core.Location;
import hexapaper.gui.Gprvky;

public class FreeSpace extends HPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3653592535754279158L;

	public FreeSpace(Location loc) {
		super("", loc, false, true, new Gprvky().emptyHexagon(loc));
	}

	@Override
	public void recreateGraphics() {
		prvek.clear();
		prvek.add(new Gprvky().emptyHexagon(loc));
	}

}
