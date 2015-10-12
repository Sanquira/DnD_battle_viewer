package dungeonmapper.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import dungeonmapper.source.DMGridElement;
import dungeonmapper.source.DMSklad;

public class SaveFile {

	DMSklad sk = DMSklad.getInstance();

	public SaveFile(ArrayList<DMGridElement> e, int rows, int cols, int size) {
		//TODO - in progress
		//save(DMm_ext, DMm_text);
	}

	public void save(String ext, String desc) {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(desc + " (*." + ext + ")", ext));
		FileWriter fileWriter;
		int returnVal = fc.showSaveDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				String name = "";
				if (!file.getAbsolutePath().contains("." + ext)) {
					name = file.getAbsolutePath() + "." + ext;
				}
				else {
					name = file.getAbsolutePath();
				}
				fileWriter = new FileWriter(new File(name));
				// fileWriter.write(jsonOutput);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
