package dungeonmapper.source;

import core.LangFile;

public class DMSklad {

	public int COLS = 60;
	public int ROWS = 42;
	public int CSIZE = 20;
	public String VERSION = "0.1";
	public LangFile str;

	public String[] drawShapes = { "rect", "circ", "pen" };
	public String[] drawOrders = { "draw", "erase", "negate" };
	public String drawShape = "null";
	public String drawOrder = "null";
	

	private static DMSklad instance = null;

	protected DMSklad() {
	}

	public void init() {
		str = new LangFile(DMStrings.class);
		str.loadLang();
	}

	public static DMSklad getInstance() {
		if (instance == null) {
			instance = new DMSklad();
		}
		return instance;
	}
}
