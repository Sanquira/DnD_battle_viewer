package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private Object object;
	
	public MessagePacket(String nick,Object object){
		this.nick=nick;
		this.object=object;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
    private void writeObject(
    	      ObjectOutputStream aOutputStream
    	    ) throws IOException {
    	      //perform the default serialization for all non-transient, non-static fields
    	      aOutputStream.defaultWriteObject();
      }
    private void readObject(
    	     ObjectInputStream aInputStream
    	   ) throws ClassNotFoundException, IOException {
    	     //always perform the default de-serialization first
    	     aInputStream.defaultReadObject();
    	  } 
}
