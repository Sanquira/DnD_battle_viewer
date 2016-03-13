package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class DeselectButtonGroup extends ButtonGroup {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8396709338748081855L;

	@Override  
    public void setSelected(ButtonModel m, boolean b) {  
      if (b && m != null && m != getSelection()) {  
         super.setSelected(m, b);  
      } else if (!b && m == getSelection()) {  
         clearSelection();  
      }  
    }
	
	@Override
	public void clearSelection(){
		super.clearSelection();
		for (Enumeration<AbstractButton> e = this.getElements(); e.hasMoreElements();)
		   for(ActionListener a:e.nextElement().getActionListeners()){
			   a.actionPerformed(new ActionEvent(null,55,"test"));
		   }
			   
	}
	
}
