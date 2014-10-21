package addons.dice;

import hexapaper.source.HPSklad;

import java.awt.HeadlessException;

import javax.swing.JFrame;

public class DiceLog extends JFrame{

	HPSklad sk = HPSklad.getInstance();
	
	public DiceLog() throws HeadlessException {
		super(sk.str.get(""));
		// TODO Auto-generated constructor stub
	}
	
	

}
