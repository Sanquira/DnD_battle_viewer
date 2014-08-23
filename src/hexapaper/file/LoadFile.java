package hexapaper.file;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadFile {
	private String desc="Entity files";
	private String ext="ent";
	private String Db_ext="entd";		
	public LoadFile(){
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(new JFrame());		 
        	if (returnVal == JFileChooser.APPROVE_OPTION) {
        		fc.setFileFilter(new FileNameExtensionFilter(desc+" (*."+ext+Db_ext+")", ext,Db_ext));
        	}
	}
}
