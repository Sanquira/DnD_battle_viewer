package hexapaper.graphicCore;

import java.util.Arrays;

import mathLibrary.vector.VectorND;

public class PolygonD {

	private VectorND xpoints, ypoints;
	public boolean isFilled;

	public PolygonD() {
		xpoints = new VectorND();
		ypoints = new VectorND();
	}

	public void addPoint(double x, double y) {
		xpoints.addDimension(x);
		ypoints.addDimension(y);
	}

	public void setScale(double scale) {
		xpoints = xpoints.mul(scale);
		ypoints = ypoints.mul(scale);
	}

	public int[] getXPointsIWithTranslationAndScale(int trX) {
		int[] ret = new int[xpoints.dimension];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) Math.round(xpoints.elements[i]) + trX;
		}
		return ret;
	}

	public int[] getYPointsIWithTranslation(int trY) {
		int[] ret = new int[ypoints.dimension];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) Math.round(ypoints.elements[i]) + trY;
		}
		return ret;
	}

	public int getNPoints() {
		return xpoints.dimension;
	}
}
