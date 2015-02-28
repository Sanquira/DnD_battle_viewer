package core;

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
	
}
