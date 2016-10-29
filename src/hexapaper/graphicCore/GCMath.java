package hexapaper.graphicCore;

import com.sun.corba.se.impl.ior.ByteBuffer;

import mathLibrary.vector.Vector2D;

public class GCMath {

	private static final double sqrt3d2 = Math.sqrt(3) / 2;
	private static final double sqrt3 = Math.sqrt(3);

	public static Vector2D getPixelCoordinates(Vector2D hexaCoord, double hexaRad) {
		return new Vector2D(hexaCoord.u1 * 1.5 * hexaRad,
				hexaCoord.u2 * sqrt3 * hexaRad + Math.abs(hexaCoord.u1 % 2) * sqrt3d2 * hexaRad);
	}

	public static Vector2D getHexaCoordinates(Vector2D pixelCoord, double hexaRad) {

		int tmp = (int) (pixelCoord.u1 / (1.5 * hexaRad));
		return new Vector2D(tmp, (int) (((pixelCoord.u2 - Math.abs(tmp % 2) * sqrt3d2 * hexaRad) / (sqrt3 * hexaRad))));
	}

	public static long getLongFromCoords(Vector2D vec) {

		ByteBuffer bb = new ByteBuffer(8);
		bb.append(vec.getU2I());
		bb.append(vec.getU1I());

		byte[] by = bb.toArray();

		long value = 0;
		for (int i = 0; i < by.length; i++) {
			value += ((long) by[i] & 0xffL) << (8 * i);
		}
		return value;
	}

	public static long getLongFromCoords(int x, int y) {
		return getLongFromCoords(new Vector2D(x, y));
	}

	public static Vector2D getCoordsFromLong(long val) {
		Vector2D ret = new Vector2D((int) (val >> 32), (int) ((val << 32) >> 32));
		return ret;
	}

	/**
	 * Convert polar coordinates to cartesian,
	 * 
	 * @param angle
	 *            polar coordinate angle in radians
	 * @param radius
	 *            polar coordinate radius
	 * @return Return {@code Vector2D} representing cartesian coordinates of
	 *         desired polar coordinates.
	 */
	public static Vector2D pol2kar(double angle, int radius) {
		return new Vector2D(radius * Math.cos(angle), radius * Math.sin(angle));
	}

}
