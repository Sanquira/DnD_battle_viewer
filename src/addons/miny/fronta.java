package addons.miny;

import java.util.ArrayList;

public class fronta {
	ArrayList<Point2i> list = new ArrayList<Point2i>();

	private class Point2i {
		int x, y;

		public Point2i(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int[] getPoints() {
			int[] ret = { x, y };
			return ret;
		}
	}

	public void addLast(int i, int j) {
		list.add(new Point2i(i, j));
	}

	public int[] deteteFirst() {
		return list.remove(0).getPoints();
	}
}
