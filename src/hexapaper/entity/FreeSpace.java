package hexapaper.entity;

import java.io.Serializable;

import core.Location;
import hexapaper.gui.Gprvky;

public class FreeSpace extends HPEntity implements Serializable {

	/**
	 * 
	 */
	public static boolean isColidable = false;
	public static boolean isRotateable = false;
	private static final long serialVersionUID = -3653592535754279158L;

	public FreeSpace() {
		
	}

	@Override
	public void recreateGraphics() {

	}

}
