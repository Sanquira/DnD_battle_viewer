package dungeonmapper.source;

import javax.swing.JScrollPane;

import core.JNumberTextField;
import core.LangFile;
import dungeonmapper.gui.DrawPlane;

public class DMSklad {

//	public int COLS = 60;
//	public int ROWS = 42;
	public int COLS = 600;
	public int ROWS = 420;
	public int CSIZE = 20;
	public String VERSION = "0.1";
	public LangFile str;

	public String[] drawShapes = { "pen", "rect", "circ" };
	public String[] drawOrders = { "draw", "erase", "negate", "SU", "SD", "hole" };
	public DMGridElement drawElement = null;
	public String drawShape = "null";
	public String drawOrder = "null";
	
	public DrawPlane drawPlane;
	public JNumberTextField layer;
	public JScrollPane DPSC;

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
