package addons.dice;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PresetButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8639158753016975248L;
	private Preset preset;
	private Dice dice;
	private ActionListener click = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(preset.getFastValue()){
				dice.multipleRoll(preset.getSides(), preset.getMod(), preset.getCount());
				return;
			}
			dice.setText(preset.getSides(), preset.getMod(), preset.getCount());
			
		}		
	};
	public PresetButton(Preset prs, Dice dice){
		super(prs.getName());
		this.dice = dice;
		this.preset = prs;
		super.addActionListener(click);
		super.setPreferredSize(new Dimension(0,20));
	}

}
