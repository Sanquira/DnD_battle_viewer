package core;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class NoneSelectedButtonGroup extends ButtonGroup {

	  @Override
	  public void setSelected(ButtonModel model, boolean selected) {

	    if (selected) {

	      super.setSelected(model, selected);

	    } else {
	      //super.setSelected(model, false);
	      clearSelection();
	      //model.setArmed(false);
	      //super.setSelected(model, selected);
	    }
	  }
	  @Override
	  public void clearSelection(){
		  super.clearSelection();
//		  Enumeration<AbstractButton> num = this.getElements();
//		  while(num.hasMoreElements()){
//			  AbstractButton param = num.nextElement();
//			  for(ActionListener l:param.getActionListeners()){
//				  l.actionPerformed(new ActionEvent(param,0,param.getActionCommand()));
//			  }
//		  }
	  }
}

