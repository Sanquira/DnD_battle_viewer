package core.streams;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.SwingUtilities;
import addons.dice.LogWindow;

public class LogOutputStream extends OutputStream {
    private LogWindow textControl;
    
    public LogOutputStream(LogWindow control){
        textControl = control;
    }
    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        final String text = new String (buffer, offset, length);     	
		if(textControl!=null){
	        Runnable  runnable = new Runnable() {
	            public void run(){
	            	textControl.insertMessage(text);
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
