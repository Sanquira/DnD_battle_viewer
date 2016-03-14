package hexapaper.gui.frames;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;
import hexapaper.gui.panels.EditDatabasePanel;
import hexapaper.source.HPSklad;

public class EditDatabaseFrame extends JFrame {
	
	private HPSklad sk = HPSklad.getInstance();
	private EditDatabasePanel<Postava> characters;
	private EditDatabasePanel<Artefact> artefacts;
	
	public EditDatabaseFrame(){
		setSize(600,450);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTabbedPane pane = new JTabbedPane();
		
		characters = new EditDatabasePanel<Postava>(sk.databazePostav,new Postava());
		pane.add("Postavy", characters);
		
		artefacts = new EditDatabasePanel<Artefact>(sk.databazeArtefaktu, new Artefact());
		pane.add("Artefakty", artefacts);
		
		setContentPane(pane);
	}
	public void showFrame(){
		characters.update(sk.databazePostav);
		artefacts.update(sk.databazeArtefaktu);
		setVisible(true);
	}
}
