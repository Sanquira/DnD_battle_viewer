package dungeonmapper.source;

import core.LangFile;
import dungeonmapper.file.LoadFile;

public class DMSklad {

	public String VERSION = "0.1";
	public LangFile str;

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
