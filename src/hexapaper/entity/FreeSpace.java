package hexapaper.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FreeSpace extends HPEntity implements Serializable {

	/**
	 * 
	 */
	public static boolean isColidable = false;
	private static final long serialVersionUID = -3653592535754279158L;

	public FreeSpace() {
		
	}

}
