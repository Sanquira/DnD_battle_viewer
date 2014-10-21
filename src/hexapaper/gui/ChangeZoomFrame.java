package hexapaper.gui;

import hexapaper.network.server.HexaClient;
import hexapaper.source.HPSklad;
import hexapaper.source.HPStrings;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.JNumberTextField;

import javax.swing.JSlider;
import javax.swing.SwingConstants;


public class ChangeZoomFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HPSklad sk = HPSklad.getInstance();
	JFrame frame;
	private JTextField Zoom;
	private JSlider slider;

	public ChangeZoomFrame() {
		frame = new JFrame(sk.str.get("ConnectFrame"));
		frame.setSize(520, 205);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		setLayout(new GridLayout(3, 1, 0, 10));
		setBorder(new TitledBorder(sk.str.get("ConnectFrame")));
		init();
		frame.getContentPane().add(this);
		frame.setVisible(true);
	}

	protected void init() {

		JButton hotovo = new JButton(sk.str.get("Connect"));
		hotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				pass();				
			}
		});
		
		JPanel panel = new JPanel();
		add(panel);
		
		Zoom = new JNumberTextField();
		Zoom.setHorizontalAlignment(SwingConstants.CENTER);
		Zoom.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changed();
			}			
		});
		panel.add(Zoom);
		Zoom.setColumns(10);
		
		slider = new JSlider();
		add(slider);
		slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				zoomed();
			}			
		});
		slider.setMinimum(10);
		slider.setMaximum(500);
		add(hotovo);
	}

	private void pass() {
		Rectangle i=sk.scroll.getViewport().getViewRect();
		i.height=i.height*slider.getValue()/100;
		i.width=i.width*slider.getValue()/100;
		//sk.scroll.getViewport().resize(i.getMinX(),i.getMaxY(),i.);
	}
	private void changed() {
		slider.setValue(Integer.valueOf(Zoom.getText())); 
	}
	private void zoomed(){
		//System.out.println("Změnen na "+slider.getValue());
		Zoom.setText(String.valueOf(slider.getValue()));
	}
}
