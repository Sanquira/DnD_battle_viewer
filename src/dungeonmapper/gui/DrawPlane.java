package dungeonmapper.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Grids;
import dungeonmapper.source.DMGridElement;
import dungeonmapper.source.DMMapTypesEnum;
import dungeonmapper.source.DMSklad;

public class DrawPlane extends JPanel implements MouseMotionListener, MouseListener {

	private DMSklad sk = DMSklad.getInstance();
	private ArrayList<ArrayList<DMGridElement>> layers = new ArrayList<>();
	private int chsnLay = 0;
	private int[] startCoor = new int[2];
	private int[] endCoorTmp = new int[2];
	private ArrayList<int[]> drawTrajectory = new ArrayList<>();
	private int[] cursCoor = new int[2];
	private boolean draw = false;

	public DrawPlane() {
		setBackground(Color.gray);
		genDefaultGrid(chsnLay);
		setPreferredSize(new Dimension(sk.COLS * sk.CSIZE, sk.ROWS * sk.CSIZE));
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	private void genDefaultGrid(int lay) {
		// sk.COLS = 10;
		// sk.ROWS = 5;
		ArrayList<DMGridElement> grid = new ArrayList<>();
		int[][] coor = Grids.gridSqr(sk.COLS, sk.ROWS, sk.CSIZE);
		for (int[] is : coor) {
			grid.add(new DMGridElement(is[0], is[1], "W"));
		}
		grid.get(1).setType(DMMapTypesEnum.F);
		grid.get(2).setType(DMMapTypesEnum.H);
		grid.get(3).setType(DMMapTypesEnum.s);
		grid.get(4).setType(DMMapTypesEnum.S);

		layers.add(lay, grid);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int[] shapeParam = getShapeParam();
		for (DMGridElement gridEl : layers.get(chsnLay)) {
			if (sk.DPSC.getViewport().getViewRect().contains(gridEl.getPoint())) {
				g.setColor(setColorInsideShape(gridEl, shapeParam));
				g.fillRect(gridEl.getX(), gridEl.getY(), sk.CSIZE - 1, sk.CSIZE - 1);
				try {
					g.drawImage(gridEl.getType().getIcon().getImage(), gridEl.getX(), gridEl.getY(), sk.CSIZE - 1, sk.CSIZE - 1, this);
				} catch (NullPointerException e) {
				}
			}
		}

		g.setColor(Color.white);
		if (sk.drawShape.equals(sk.drawShapes[0]) && draw) {
			g.drawPolyline(getCol(drawTrajectory, 0), getCol(drawTrajectory, 1), drawTrajectory.size());
		}
		if (sk.drawShape.equals(sk.drawShapes[1]) && draw) {
			g.drawRect(shapeParam[0], shapeParam[1], shapeParam[2], shapeParam[3]);
		}
		if (sk.drawShape.equals(sk.drawShapes[2]) && draw) {
			g.drawOval(shapeParam[0], shapeParam[1], shapeParam[2], shapeParam[3]);
		}

	}

	private int[] getCol(ArrayList<int[]> matrix, int col) {
		int[] column = new int[matrix.size()];
		int l = 0;
		for (int[] obj : matrix) {
			column[l] = obj[col];
			l++;
		}
		return column;
	}

	private int[] getShapeParam() {
		int[] param = new int[4];
		if (sk.drawShape.equals(sk.drawShapes[1])) {
			param[0] = Math.min(startCoor[0], endCoorTmp[0]);
			param[1] = Math.min(startCoor[1], endCoorTmp[1]);
			param[2] = Math.abs(endCoorTmp[0] - startCoor[0]);
			param[3] = Math.abs(endCoorTmp[1] - startCoor[1]);
		}
		if (sk.drawShape.equals(sk.drawShapes[2])) {
			param[2] = 2 * Math.abs(endCoorTmp[0] - startCoor[0]);
			param[3] = 2 * Math.abs(endCoorTmp[1] - startCoor[1]);
			param[0] = startCoor[0] - param[2] / 2;
			param[1] = startCoor[1] - param[3] / 2;
		}
		return param;
	}

	// TODO improve potencial detected
	public boolean isInsideOfShape(DMGridElement gridEl, int[] shapeParam) {
		int x = shapeParam[0];
		int y = shapeParam[1];
		int width = shapeParam[2];
		int height = shapeParam[3];
		int[][] rohy = {
				{ gridEl.getX(), gridEl.getY() },
				{ gridEl.getX(), gridEl.getY() + sk.CSIZE },
				{ gridEl.getX() + sk.CSIZE, gridEl.getY() },
				{ gridEl.getX() + sk.CSIZE, gridEl.getY() + sk.CSIZE },

				{ gridEl.getX() + sk.CSIZE / 2, gridEl.getY() },
				{ gridEl.getX(), gridEl.getY() + sk.CSIZE / 2 },
				{ gridEl.getX() + sk.CSIZE, gridEl.getY() + sk.CSIZE / 2 },
				{ gridEl.getX() + sk.CSIZE / 2, gridEl.getY() + sk.CSIZE } };
		if (sk.drawShape.equals(sk.drawShapes[0])) {
			for (int[] is : drawTrajectory) {
				if (is[0] >= rohy[0][0] && is[0] < rohy[2][0] && is[1] >= rohy[0][1] && is[1] < rohy[1][1]) {
					return true;
				}
			}
		}
		for (int[] is : rohy) {
			if (sk.drawShape.equals(sk.drawShapes[1]) &&
					is[0] > x && is[0] < x + width && is[1] > y && is[1] < y + height) {
				return true;
			}
			if (sk.drawShape.equals(sk.drawShapes[2]) &&
					(Math.pow(is[0] - startCoor[0], 2)) / Math.pow(width / 2, 2) +
							(Math.pow(is[1] - startCoor[1], 2)) / Math.pow(height / 2, 2) <= 1) {
				return true;
			}
		}
		return false;
	}

	private void changeElementByShape() {
		ArrayList<DMGridElement> insideShapeList = new ArrayList<>();
		for (DMGridElement dmGridElement : layers.get(chsnLay)) {
			if (isInsideOfShape(dmGridElement, getShapeParam())) {
				insideShapeList.add(dmGridElement);
			}
		}
		for (DMGridElement dmGridElement : insideShapeList) {
			if (sk.drawOrder.contains(sk.drawOrders[3])) {
				dmGridElement.setType(DMMapTypesEnum.S);
				continue;
			}
			if (sk.drawOrder.contains(sk.drawOrders[4])) {
				dmGridElement.setType(DMMapTypesEnum.s);
				continue;
			}
			if (sk.drawOrder.contains(sk.drawOrders[5])) {
				dmGridElement.setType(DMMapTypesEnum.H);
				continue;
			}
			if (sk.drawOrder.equals(sk.drawOrders[0])) {
				dmGridElement.setType(DMMapTypesEnum.F);
				continue;
			}
			if (sk.drawOrder.equals(sk.drawOrders[1])) {
				dmGridElement.setType(DMMapTypesEnum.W);
				continue;
			}
			if (sk.drawOrder.equals(sk.drawOrders[2])) {
				if (dmGridElement.getType().equals(DMMapTypesEnum.F)) {
					dmGridElement.setType(DMMapTypesEnum.W);
					continue;
				}
				if (dmGridElement.getType().equals(DMMapTypesEnum.W)) {
					dmGridElement.setType(DMMapTypesEnum.F);
					continue;
				}
			}

		}

	}

	private Color setColorInsideShape(DMGridElement gridEl, int[] shapeParam) {
		if (isInsideOfShape(gridEl, shapeParam) && draw) {
			return new Color(gridEl.getType().getBcgColor().getRed() / 2,
					gridEl.getType().getBcgColor().getGreen() / 2,
					gridEl.getType().getBcgColor().getBlue() / 2,
					128);
		}
		return gridEl.getType().getBcgColor();
	}

	private void resetDraw() {
		// sk.drawShape = "null";
		// sk.drawOrder = "null";
		draw = false;
		drawTrajectory = new ArrayList<>();
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	@Override
	public void mousePressed(MouseEvent paramMouseEvent) {
		if (!sk.drawOrder.contains("null") && !sk.drawShape.contains("null")) {
			startCoor[0] = paramMouseEvent.getX();
			startCoor[1] = paramMouseEvent.getY();
			endCoorTmp = startCoor.clone();
			drawTrajectory.add(endCoorTmp.clone());
			draw = true;
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent) {
		if (draw) {

			changeElementByShape();
		}
		resetDraw();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent paramMouseEvent) {
		endCoorTmp[0] = paramMouseEvent.getX();
		endCoorTmp[1] = paramMouseEvent.getY();
		drawTrajectory.add(endCoorTmp.clone());
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent paramMouseEvent) {
		cursCoor[0] = paramMouseEvent.getX();
		cursCoor[1] = paramMouseEvent.getY();
		repaint();
	}

}
