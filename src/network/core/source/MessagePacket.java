package network.core.source;

import java.io.Serializable;

public class MessagePacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4376394310978683485L;
	/**
	 * 
	 */
	private String nick;
	private Serializable object;
	private String header;
		
//	public MessagePacket(String nick,Serializable object){
//		this.nick=nick;
//		this.object=object;
//		this.setHeader("none");
//	}
	public MessagePacket(String nick,String header,Serializable object){
		this.nick=nick;
		this.object=object;
		this.setHeader(header);
	}
	public Serializable getObject() {
		return object;
	}
	public void setObject(Serializable object) {
		this.object = object;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
//    private void writeObject(
//    	      ObjectOutputStream aOutputStream
//    	    ) throws IOException {
//    	      //perform the default serialization for all non-transient, non-static fields
//    	      aOutputStream.defaultWriteObject();
//      }
//    private void readObject(
//    	     ObjectInputStream aInputStream
//    	   ) throws ClassNotFoundException, IOException {
//    	     //always perform the default de-serialization first
//    	     aInputStream.defaultReadObject();
//    	  }
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	} 
}
