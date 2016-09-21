package hexapaper.graphicCore;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import hexapaper.entity.HPEntity;
import hexapaper.entity.Wall;
import hexapaper.graphicCore.controls.GraphicCoreMouseListener;
import hexapaper.graphicCore.primitives.DrawShape;
import hexapaper.graphicCore.primitives.MyPopupMenu;
import hexapaper.graphicCore.primitives.Zoom;
import hexapaper.language.HPStrings;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.prvekkNN;
import mathLibrary.vector.Vector2D;

public class Canvas extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final float MIN_RADIUS = 10;
	HPSklad sk = HPSklad.getInstance();
	HPStrings strings = HPSklad.getInstance().str;
	private static final boolean DEBUG_DRAW_ENABLED = true;

	private static PolygonD emptyHexagon;

	// int fontSize = (int) Math.round(sk.c.RADIUS * 0.75);

	private Vector2D viewportCoor;
	private Zoom zoom;
	private Vector2D[] dragPoints = new Vector2D[2];

	private DrawShape drawShape = null;

	private JPopupMenu popupMenu;

	public Canvas() {
		this(0, 0, 20);
	}

	public Canvas(int coorX, int coorY, int zoom) {
		viewportCoor = new Vector2D(coorX, coorY);
		this.zoom = new Zoom(zoom, zoomChandedCallback());
		init();
	}

	private void init() {
		GraphicCoreMouseListener listener = new GraphicCoreMouseListener(this);
		setBackground(Color.YELLOW);
		addMouseWheelListener(listener);
		addMouseMotionListener(listener);
		addMouseListener(listener);

		popupMenu = new JPopupMenu();
		initPopupMenu();

		Vector2D tmp = new Vector2D(0, 0);
		long lng = GCMath.getLongFromCoords(tmp);
		sk.entities.put(lng, new Wall());

		sk.entities.put(GCMath.getLongFromCoords(new Vector2D(3, 2)),
				new Wall());

		try {
			zoomChandedCallback().call();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initPopupMenu() {
		// Set position
		popupMenu.add(new JMenuItem(new AbstractAction(strings.GCsetPosition) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Vector2D poloha = GCMath.getHexaCoordinates(viewportCoor, zoom.getZoomValue());
				String ret = JOptionPane.showInputDialog(
						sk.str.GCsetPositionMessage, (int) poloha.u1 + ", "
								+ (int) poloha.u2);
				if (ret != null) {
					String[] tmpf = ret.split(",");
					try {
						int x = Integer.parseInt(tmpf[0].trim());
						int y = Integer.parseInt(tmpf[1].trim());
						viewportCoor = GCMath.getPixelCoordinates(new Vector2D(x, y), zoom.getZoomValue());
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

		Vector2D minHC = GCMath.getHexaCoordinates(minPC, zoom.getZoomValue());
		Vector2D maxHC = GCMath.getHexaCoordinates(maxPC, zoom.getZoomValue());

		Graphics2D g2 = (Graphics2D) g;
		// g2.setStroke(new BasicStroke(2));
		for (int x = minHC.getU1I() - 1; x <= maxHC.getU1I() + 1; x++) {
			for (int y = minHC.getU2I() - 1; y <= maxHC.getU2I() + 1; y++) {
				Vector2D hashHC = new Vector2D(x, y);
				long hashLong = GCMath.getLongFromCoords(hashHC);
				HPEntity value = sk.entities.get(hashLong);
				Vector2D hashPC = GCMath.getPixelCoordinates(hashHC, zoom.getZoomValue()).sub(
						minPC);
				if (value != null) {
					g2.fillRect(hashPC.getU1I(), hashPC.getU2I(), 10, 10);
				} else {
					g2.fillRect(hashPC.getU1I(), hashPC.getU2I(), 5, 5);
				}
				g2.drawPolyline(emptyHexagon.getXPointsIWithTranslationAndScale(hashPC
						.getU1I()), emptyHexagon
						.getYPointsIWithTranslation(hashPC.getU2I()),
						emptyHexagon.getNPoints());
			}
		}

		if (DEBUG_DRAW_ENABLED)
			debugDraw(g2);

		if (drawShape != null)
			drawMouseShape(g2);
	}

	private void drawMouseShape(Graphics2D g2) {
		if (drawShape == DrawShape.RECTANLGE) {
			g2.drawRect(dragPoints[0].getU1I(), dragPoints[0].getU2I(), dragPoints[1].getU1I(), dragPoints[1].getU2I());
		}
	}

	private void debugDraw(Graphics2D g2) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, this.getHeight() - 20, 300, 20);
		g2.setColor(Color.WHITE);
		g2.drawString(GCMath.getHexaCoordinates(viewportCoor, zoom.getZoomValue()).toString(), 10,
				this.getHeight() - 5);
	}

	private Callable<?> zoomChandedCallback() {
		return new Callable<Integer>() {
			@Override
			public Integer call() {
				emptyHexagon = GraphicElements.emptyHexagon();
				emptyHexagon.setScale(zoom.getZoomValue());
				return zoom.getZoomValue();
			}
		};
	}

	public Zoom getZoom(){
		return zoom;
	}
	
//	public void addZoom(double addZoom) {
//		zoom += addZoom;
//		if (zoom < MIN_RADIUS) {
//			zoom = MIN_RADIUS;
//		}
//		changeZoom();
//	}

	/**
	 * Set position of viewport to <code>position</code>. <code>Position</code>
	 * is in <b>pixels</b>. Viewport position is in the middle of component.
	 * 
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		viewportCoor = position;
	}

	/**
	 * Get position of viewport in pixels. Viewport position is in the middle of
	 * component.
	 * 
	 * @return
	 */
	public Vector2D getPosition() {
		return viewportCoor;
	}

	/**
	 * Add pixel value to viewport position. Viewport position is in the middle
	 * of component.
	 * 
	 * @param position
	 */
	public void addPosition(Vector2D position) {
		viewportCoor = viewportCoor.add(position);
	}

	/**
	 * Set position of viewport to <code>position</code>. <code>Position</code>
	 * is in <b>hexa coordinates</b>. Viewport position is in the middle of
	 * component.
	 * 
	 * @param position
	 */
	public void setPositionHC(Vector2D position) {
		viewportCoor = GCMath.getPixelCoordinates(position, zoom.getZoomValue());
	}

	/**
	 * Get position of viewport in hexa coordinates. Viewport position is in the
	 * middle of component.
	 * 
	 * @return
	 */
	public Vector2D getPositionHC() {
		return GCMath.getHexaCoordinates(viewportCoor, zoom.getZoomValue());
	}

	/**
	 * Add hexa coordinates value to viewport position. Viewport position is in
	 * the middle of component.
	 * 
	 * @param position
	 */
	public void addPositionHC(Vector2D position) {
		viewportCoor = viewportCoor.add(GCMath.getPixelCoordinates(position, zoom.getZoomValue()));
	}

	/**
	 * Return popup menu object.
	 * 
	 * @return
	 */
	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	public void setDraggedPoints(Vector2D firstPoint, Vector2D secondPoint) {
		dragPoints[0] = firstPoint;
		dragPoints[1] = secondPoint;
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
