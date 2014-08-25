package hexapaper.source;

import hexapaper.source.Sklad.prvekkNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class kNN {

	Sklad sk = Sklad.getInstance();

	private double eucDistSqr(double x1, double y1, double x2, double y2) {
		return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
	}

	public ArrayList<prvekkNN> getkNNindexes(double xp, double yp) {

		ArrayList<prvekkNN> dists = new ArrayList<>();
		if (sk.souradky.size() == 0) {
			dists.add(new prvekkNN(-1, 0, 0, 0));
			return dists;
		}
		for (int i = 0; i < sk.souradky.size(); i++) {
			double x1 = sk.souradky.get(i).loc.getX();
			double y1 = sk.souradky.get(i).loc.getY();
			double dist = eucDistSqr(x1, y1, xp, yp);
			dists.add(new prvekkNN(i, x1, y1, dist));
		}

		Collections.sort(dists, new distComparator());

		return dists;
	}

	private class distComparator implements Comparator<prvekkNN> {
		@Override
		public int compare(prvekkNN a, prvekkNN b) {
			return a.getVzd() < b.getVzd() ? -1 : a.getVzd() == b.getVzd() ? 0 : 1;
		}
	}

}
