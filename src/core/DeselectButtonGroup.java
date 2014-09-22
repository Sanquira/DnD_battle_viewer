package core;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class DeselectButtonGroup extends ButtonGroup {

    @Override  
    public void setSelected(ButtonModel m, boolean b) {  
      if (b && m != null && m != getSelection()) {  
         super.setSelected(m, b);  
      } else if (!b && m == getSelection()) {  
         clearSelection();  
      }  
    }  
	
}
