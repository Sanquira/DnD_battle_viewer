package Demos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hexapaper.graphicCore.MyCanvas;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Testing class for Graphic core.
 * 
 * @author Sanquira Van Delbar
 *
 */
public class MainGCTest extends JFrame {

	public MainGCTest() {
		setTitle("Testing GC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setUndecorated(true);

		MyCanvas plocha = new MyCanvas();

		add(plocha);

		setVisible(true);
	}

	public static void main(String[] args) {
		MainGCTest mainGCTest = new MainGCTest();
		Timer tim = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(-25);
			}
		});
//		tim.start();

	}

}
