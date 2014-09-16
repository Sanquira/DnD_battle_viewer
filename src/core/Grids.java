package core;

public class Grids {

	public static int[][] gridHexa(int sloupcu, int radku, int radius) {
		int[][] souradky = new int[sloupcu * radku][2];
		int l = 0;
		for (int i = 0; i < sloupcu; i++) {
			for (int j = 0; j < radku; j++) {
				if (i % 2 == 0) {
					souradky[l][0] = (i / 2) * 3 * radius;
					souradky[l][1] = (int) Math.round(j * 2 * Math.cos(Math.toRadians(30)) * radius);
				} else {
					souradky[l][0] = (int) ((1.5 + (i / 2) * 3) * radius);
					souradky[l][1] = (int) Math.round(radius * Math.cos(Math.toRadians(30)) +
							j * 2 * Math.cos(Math.toRadians(30)) * radius);
				}
				l++;
			}
		}
		return souradky;
	}

	public static int[][] gridSqr(int sloupcu, int radku, int strana) {
		int[][] souradky = new int[sloupcu * radku][2];
		for (int i = 0; i < radku; i++) {
			for (int j = 0; j < sloupcu; j++) {
				souradky[sloupcu * i + j][0] = j * strana;
				souradky[sloupcu * i + j][1] = i * strana;
			}
		}
		return souradky;
	}
}
