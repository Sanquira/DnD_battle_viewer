package hexapaper.file;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import hexapaper.source.HPSklad;

@XmlRootElement
@XmlSeeAlso({XmlDatabaseWrapper.class,XmlMapWrapper.class})
public abstract class XmlAbstractWrapper {
	
	private String version = HPSklad.FILEVERSION;
	
	@XmlAttribute
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
