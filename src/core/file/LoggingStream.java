package core.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

public class LoggingStream extends OutputStream {
    private JTextArea textControl;
    private FileWriter writer;
    
    public LoggingStream(String name, JTextArea control ) {
    	create(name);
        textControl = control;
    }
    public LoggingStream(String name){
    	create(name);
    }
    public void create(String name){
    	try {
    		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getAuthority() + name + ".log";
    		writer = new FileWriter(new File(path));
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    public void write( int b ) throws IOException {
        // append the data as characters to the JTextArea control
    	writer.write(b);
    	writer.flush();
    	if(textControl!=null){
    		textControl.append( String.valueOf( ( char )b ) );
    	}
    }
}
