package hexapaper.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import network.command.users.CommandClient;

public class PJGUI extends ServerGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3574039538906990242L;
	private CommandClient cln;
	private ActionListener sendCmd = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				cln.send(cmdField.getText(), "clientCmd");
				cmdField.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}		
	};
	public PJGUI(CommandClient cln){
		super();
		setTitle("PJGUI");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.cln = cln;
		super.cmdField.removeActionListener(super.sendCmd);
		super.cmdField.addActionListener(sendCmd);
	}
}
