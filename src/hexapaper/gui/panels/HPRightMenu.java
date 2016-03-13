package hexapaper.gui.panels;

import hexapaper.entity.EditableEntity;
import hexapaper.source.HPSklad;
import javax.swing.JTabbedPane;

public class HPRightMenu extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5637331808007212483L;

	HPSklad sk = HPSklad.getInstance();
	public ControlPanel cpane;
	public EditEntityPane editPane;
	private boolean editPaneState = false;

	public HPRightMenu() {
		editPane = new EditEntityPane();
		cpane = new ControlPanel();
		this.add("Ovladani",cpane);
	}
	
	public void updateEntity(EditableEntity e){
		//System.out.println("updating");
		setEditPaneVisible(true);
		editPane.setEntity(e);
	}
	public void setEditPaneVisible(Boolean b){
		if(b & !editPaneState){
			//System.out.println("Vidím");
			this.add("Editace",editPane);
			editPaneState = true;
			repaint();
		}
		else if(!b & editPaneState){
			this.remove(editPane);
			editPaneState = false;
			repaint();
		}
	}


}
