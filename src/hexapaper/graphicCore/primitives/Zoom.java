package hexapaper.graphicCore.primitives;

import java.util.concurrent.Callable;

public class Zoom {

	private static final int MINIMAL_RADIUS = 10;

	private int zoom;
	private Callable<?> callbackZoomChanged;

	public Zoom(int initialZoom, Callable<?> callable) {
		zoom = initialZoom;
		callbackZoomChanged = callable;
	}

	public int getZoomValue() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
		try {
			callbackZoomChanged.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addZoom(int add) {
		zoom += add;
		if (zoom < MINIMAL_RADIUS) {
			zoom = MINIMAL_RADIUS;
		}
		try {
			callbackZoomChanged.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
