package hexapaper.graphicCore.API;

import java.awt.Color;
import java.awt.Graphics2D;

public interface IMyGraphicElement
{

	/**
	 * Implement graphic representation of element in map. Every element in map
	 * has to have implemented this method, otherwise it will not be drawn.
	 * 
	 * @param g2
	 *            - graphic to draw element
	 * @param zoom
	 *            - scale factor for draw
	 */
	public void Draw(Graphics2D g2, float zoom);

	/**
	 * Element Background color. If null element is transparent and take color
	 * of field background.
	 * Default null.
	 */
	public Color ElementColor = null;

}
