package hexapaper.source;

import java.awt.Polygon;

public class BPolygon extends Polygon implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isFilled = false;

	public BPolygon() {
	}

	public BPolygon(Polygon poly) {
		this.xpoints = poly.xpoints;
		this.ypoints = poly.ypoints;
		this.npoints = poly.npoints;
	}

	public BPolygon(Polygon poly, boolean fill) {
		this.xpoints = poly.xpoints;
		this.ypoints = poly.ypoints;
		this.npoints = poly.npoints;
		isFilled = fill;
	}

	public BPolygon setFilled(boolean fill) {
		isFilled = fill;
		return this;
	}

	public BPolygon setFilled() {
		isFilled = true;
		return this;
	}

	@Override
	public BPolygon clone() throws CloneNotSupportedException {
		return (BPolygon) super.clone();
	}

	public String toString() {
		return super.xpoints.toString() + ": " + super.ypoints.toString() + ", " + super.npoints + ", " + isFilled;
	}

}
