package hexapaper.source;

import java.awt.Polygon;
import java.io.Serializable;

public class BPolygon extends Polygon implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2159232486203749704L;
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
	
	public BPolygon(BPolygon polygon){
		this.xpoints = new int[polygon.xpoints.length];
		this.ypoints = new int[polygon.ypoints.length];
		for (int i = 0; i < polygon.xpoints.length; i++)
		{
			this.xpoints[i] = polygon.xpoints[i];
			this.ypoints[i] = polygon.ypoints[i];
		}
		this.npoints = polygon.npoints;
		this.isFilled = polygon.isFilled;
	}

	public BPolygon setFilled(boolean fill) {
		isFilled = fill;
		return this;
	}

	public BPolygon setFilled() {
		isFilled = true;
		return this;
	}

	public String toString() {
		return super.xpoints.toString() + ": " + super.ypoints.toString() + ", " + super.npoints + ", " + isFilled;
	}

}
