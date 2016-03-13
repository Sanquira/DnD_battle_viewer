package hexapaper.gui.frames;

import hexapaper.source.HPSklad;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ColorPicker extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5049953320327190756L;
	private JPanel contentPane;
	private HPSklad sk=HPSklad.getInstance();
	private JColorChooser colorPick;
	private ColorPicker frame=this;
	/**
	 * Create the frame.
	 */
	public ColorPicker() {
		setTitle(sk.str.colorPickerTitle);
		setBounds(100, 100, 628, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		colorPick = new JColorChooser();
		contentPane.add(colorPick);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton confirm = new JButton(sk.str.Confirm);
		confirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sk.color=colorPick.getColor();
				sk.RMenu.cpane.updateColor();
				frame.setVisible(false);
			}			
		});
		panel.add(confirm);
		
		JButton reset = new JButton(sk.str.Reset);
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				colorPick.setColor(Color.BLACK);			
			}			
		});
		panel.add(reset);
		
		JButton close = new JButton(sk.str.Close);
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);			
			}			
		});
		panel.add(close);
		//setVisible(true);
	}
}
