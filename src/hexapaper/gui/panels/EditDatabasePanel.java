package hexapaper.gui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import hexapaper.entity.EditableEntity;
import hexapaper.source.HPSklad;

public class EditDatabasePanel<T extends EditableEntity> extends JPanel {

	private EditEntityPane<T> pane;
	private JList list;
	private ArrayList<T> array;
	private DefaultListModel<T> model;
	private HPSklad sk = HPSklad.getInstance();
	public EditDatabasePanel(ArrayList<T> array, T entity){
		list = new JList<>(array.toArray());
		this.model = new DefaultListModel<T>();
		list.setModel(model);
		//this.model = (DefaultListModel<T>) list.getModel(); 
		this.array = array;
		pane = new EditEntityPane<T>("Uložit");
		pane.setEntity(entity);
		createPanel();
	}
	private void createPanel() {
		setLayout(new GridLayout(1,2,10,10));
		pane.setBorder(new TitledBorder("Tvorba"));
		pane.getCustomButton().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				T entity = sk.cloner.deepClone(pane.getEntity());
				array.add(entity);
				model.addElement(entity);
				sk.RMenu.cpane.updateDatabase();
			}
			
		});
		add(pane);
		
		JPanel listPane = new JPanel();
		listPane.setLayout(new GridLayout(1,1));
		listPane.setBorder(new TitledBorder("Databáze"));
		listPane.add(list);
		add(listPane);
		
	}
	public void update(ArrayList<T> array){
		model.removeAllElements();
		for(T entity : array){
			model.addElement(entity);
		}
	}
}
