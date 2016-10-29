package hexapaper.graphicCore;

public class GCSource
{
	/**
	 * Set maximum elements at one position in map
	 */
	public static final int MAX_ELEMENTS_AT_ONE_POSITION = 6;

	/*
	 * Enum initialization
	 */
	private static GCSource instance;

	static
	{
		instance = new GCSource();
	}

	public GCSource getInstance()
	{
		return instance;
	}

	public GCSource()
	{

	}

}
