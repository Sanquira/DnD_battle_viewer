package dungeonmapper.source;

public class DMSklad {

	private static DMSklad instance = null;

	public static DMSklad getInstance() {
		if (instance == null) {
			instance = new DMSklad();
		}
		return instance;
	}
}
