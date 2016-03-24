package hexapaper.entity;

import java.awt.Color;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Wall extends HPEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7675708124367625378L;
	
	public Wall() {
		setBcg(Color.BLACK);
		
	}
}
