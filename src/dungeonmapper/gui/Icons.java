package dungeonmapper.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Icons {

	BufferedImage circleEmpty;

	public BufferedImage makeCircleEmpty(int width, int height) {
		circleEmpty = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = circleEmpty.getGraphics();
		g.drawOval(width/2, height/2, width/3, height/3);

		return circleEmpty;
	}

}
