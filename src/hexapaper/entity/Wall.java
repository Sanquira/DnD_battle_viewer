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
	public static boolean isRotateable = false;
	public Wall() {
		setBcg(Color.BLACK);
	}

	@Override
	public void recreateGraphics() {

	}

}
