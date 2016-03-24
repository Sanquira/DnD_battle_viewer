package hexapaper.graphicCore;

import hexapaper.entity.HPEntity;
import hexapaper.entity.Wall;
import hexapaper.graphicCore.listeners.GraphicCoreMouseListener;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.prvekkNN;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import mathLibrary.vector.Vector2D;

public class Canvas extends JPanel {

	private static final long serialVersionUID = 1L;
	HPSklad sk = HPSklad.getInstance();

	private static PolygonD emptyHexagon;

	// int fontSize = (int) Math.round(sk.c.RADIUS * 0.75);

	private Vector2D viewportCoor;
	private double zoom;

	private JPopupMenu popupMenu;

	public Canvas() {
		this(0, 0, 20);
	}

	public Canvas(int coorX, int coorY, int zoom) {
		viewportCoor = new Vector2D(coorX, coorY);
		this.zoom = zoom;
		init();
	}

	private void init() {
		GraphicCoreMouseListener lis = new GraphicCoreMouseListener(this);
		setBackground(Color.YELLOW);
		addMouseWheelListener(lis);
		addMouseMotionListener(lis);
		addMouseListener(lis);

		popupMenu = new JPopupMenu();
		setupPopupMenu();

		Vector2D tmp = new Vector2D(0, 0);
		long lng = GCMath.getLongFromCoords(tmp);
		sk.entities.put(lng, new Wall());

		sk.entities.put(GCMath.getLongFromCoords(new Vector2D(3, 2)), new Wall());

		changeZoom();

	}

	private void setupPopupMenu() {
		// Set position
		popupMenu.add(new JMenuItem(new AbstractAction(sk.str.GCsetPosition) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Vector2D poloha = GCMath.getHexaCoordinates(viewportCoor, zoom);
				String ret = JOptionPane.showInputDialog(sk.str.GCsetPositionMessage, (int) poloha.u1 + ", " + (int) poloha.u2);
				if (ret != null) {
					String[] tmpf = ret.split(",");
					try {
						int x = Integer.parseInt(tmpf[0].trim());
						int y = Integer.parseInt(tmpf[1].trim());
						viewportCoor = GCMath.getPixelCoordinates(new Vector2D(x, y), zoom);
						repaint();
					} catch (NumberFormatException er) {
						System.err.println(HPSklad.shortenedStackTrace(er, 5));

					}
				}
			}
		}));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Vector2D panelSize = new Vector2D(this.getWidth(), this.getHeight());
		Vector2D maxPC = viewportCoor.add(panelSize.mul(0.5));
		Vector2D minPC = viewportCoor.add(panelSize.mul(-0.5));

		Vector2D minHC = GCMath.getHexaCoordinates(minPC, zoom);
		Vector2D maxHC = GCMath.getHexaCoordinates(maxPC, zoom);

		Graphics2D g2 = (Graphics2D) g;
		// g2.setStroke(new BasicStroke(2));
		for (int x = minHC.getU1I() - 1; x <= maxHC.getU1I() + 1; x++) {
			for (int y = minHC.getU2I() - 1; y <= maxHC.getU2I() + 1; y++) {
				Vector2D hashHC = new Vector2D(x, y);
				long hashLong = GCMath.getLongFromCoords(hashHC);
				HPEntity value = sk.entities.get(hashLong);
				Vector2D hashPC = GCMath.getPixelCoordinates(hashHC, zoom).sub(minPC);
				if (value != null) {
					g2.fillRect(hashPC.getU1I(), hashPC.getU2I(), 10, 10);
				}
				g2.fillRect(hashPC.getU1I(), hashPC.getU2I(), 5, 5);
				g2.drawPolyline(
						emptyHexagon.getXPointsIWithTranslation(hashPC.getU1I()),
						emptyHexagon.getYPointsIWithTranslation(hashPC.getU2I()),
						emptyHexagon.getNPoints());
			}
		}
	}

	private void changeZoom() {
		emptyHexagon = GraphicElements.emptyHexagon();
		emptyHexagon.setScale(zoom);
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		changeZoom();
	}

	public double getZoom() {
		return zoom;
	}

	public void addZoom(double addZoom) {
		zoom += addZoom;
		if (zoom < 1) {
			zoom = 1;
		}
		changeZoom();
	}

	/**
	 * Set position of viewport to <code>position</code>. <code>Position</code> is in <b>pixels</b>. Viewport position is in the middle of component.
	 * 
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		viewportCoor = position;
	}

	/**
	 * Get position of viewport in pixels. Viewport position is in the middle of component.
	 * 
	 * @return
	 */
	public Vector2D getPosition() {
		return viewportCoor;
	}

	/**
	 * Add pixel value to viewport position. Viewport position is in the middle of component.
	 * 
	 * @param position
	 */
	public void addPosition(Vector2D position) {
		viewportCoor = viewportCoor.add(position);
	}

	/**
	 * Set position of viewport to <code>position</code>. <code>Position</code> is in <b>hexa coordinates</b>. Viewport position is in the middle of component.
	 * 
	 * @param position
	 */
	public void setPositionHC(Vector2D position) {
		viewportCoor = GCMath.getPixelCoordinates(position, zoom);
	}

	/**
	 * Get position of viewport in hexa coordinates. Viewport position is in the middle of component.
	 * 
	 * @return
	 */
	public Vector2D getPositionHC() {
		return GCMath.getHexaCoordinates(viewportCoor, zoom);
	}

	/**
	 * Add hexa coordinates value to viewport position. Viewport position is in the middle of component.
	 * 
	 * @param position
	 */
	public void addPositionHC(Vector2D position) {
		viewportCoor = viewportCoor.add(GCMath.getPixelCoordinates(position, zoom));
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	// TODO
	public void rotateEntity(ArrayList<prvekkNN> idx) {
	}

	public boolean insertEntity(Integer idx, HPEntity type, Boolean hard) {
		return false;
	}

	public void drawCursor(double x, double y) {
	}

	public void moveEntity(int oldIdx, int newIdx) {
	}

	public void saveEntity(int idx) {
	}// ??

	public void releaseEntity(int idx) {
	}// ??

}
