package core.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import addons.dice.LogWindow;

public class LoggingStream extends OutputStream {
    private LogWindow textControl;
    private FileWriter writer;
    
    public LoggingStream(String name, LogWindow control ) {
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
    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        final String text = new String (buffer, offset, length);
		writer.write(text);
		writer.flush();      	
		if(textControl!=null){
	        Runnable  runnable = new Runnable() {
	            public void run(){
	            	try {
						textControl.insertMessage(text);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        };
	        SwingUtilities.invokeLater(runnable);		
		}
    }

    @Override
    public void write(int b) throws IOException
    {
        write (new byte [] {(byte)b}, 0, 1);
    }

}
