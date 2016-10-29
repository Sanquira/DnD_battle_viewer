package hexapaper.graphicCore.controls;

import hexapaper.graphicCore.MyCanvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

import mathLibrary.vector.Vector2D;

public class GraphicCoreMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {


	private MyCanvas canvas;
	private Vector2D pressPoint;

	public GraphicCoreMouseListener(MyCanvas canvas) {

		this.canvas = canvas;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		canvas.getZoom().addZoom((int) Math.round(-e.getPreciseWheelRotation()));
		canvas.repaint();
//		OnMouseWheelEvent(new MyMouseWheelEvent(e));
	}
	
//	private void OnMouseWheelEvent(MyMouseWheelEvent myMVEvent){
//		if(m_wheelCallBacks==null)
//			return;
//		foreach(var handler in m_wheelCallBacks){
//			handler.Call(myMVEvent);
//		}
//	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector2D dragPoint = new Vector2D(e.getX(), e.getY());
		if (SwingUtilities.isMiddleMouseButton(e)) {
			canvas.addPosition(pressPoint.add(dragPoint.mul(-1)));
			pressPoint = dragPoint;
			canvas.repaint();
		}else
		if(SwingUtilities.isLeftMouseButton(e)){
			canvas.setDraggedPoints(pressPoint,dragPoint);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isMiddleMouseButton(e)) {
			canvas.getPopupMenu().show(canvas, e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressPoint = new Vector2D(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
