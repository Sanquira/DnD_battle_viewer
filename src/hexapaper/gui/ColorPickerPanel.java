package hexapaper.gui;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;


public class ColorPickerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3634531589546651936L;
	/**
	 * Create the panel.
	 */
	private Color clr=Color.BLACK;
	private JPanel history;
	private JPanel selColor;
	private ArrayList<Color> colors=new ArrayList<Color>();
	private JColorChooser chooser=new JColorChooser();
	private MouseAdapter chooseColor=new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
			Color c=chooser.showDialog(null, "Vyber barvu", clr);
			if(c!=null){
				clr=c;
				colors.add(1,clr);
				if(colors.size()>64){
					colors.remove(64);
				}
				redraw();
			}
			//System.out.println(clr);
		}
	};
	private MouseAdapter setColor=new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
			clr=((JPanel) e.getComponent()).getBackground();
			selColor.setBackground(clr);
		}
	};
	public ColorPickerPanel() {
		colors.add(Color.BLACK);
		//colors.add(Color.BLUE);
		
		setLayout(new GridLayout(2, 0, 0, 0));
		
		selColor = new JPanel();
		//selColor.setBorder(new TitledBorder("Vybraná barva"));
		selColor.addMouseListener(chooseColor);
		//selColor.setForeground(Color.BLACK);
		//selColor.repaint();
		add(selColor);
		
		history = new JPanel();
		history.setBorder(new TitledBorder("Historie barev"));
		add(history);
		history.setLayout(new GridLayout(4, 16, 0, 0));
		
		redraw();
				
	}
	
	public void redraw(){
		selColor.setBackground(clr);
		for(int i=0;i<64;i++){
			JPanel panel=new JPanel();
			panel.addMouseListener(setColor);
			Color c=Color.WHITE;
			if(i<colors.size()){
				c=colors.get(i);
				System.out.println("Super");
			}
			panel.setBackground(c);
			history.add(panel);
		}
		history.revalidate();
		history.repaint();
		this.repaint();
		System.out.println(colors);
	}
	
	public void constructFrame(){
		JFrame frame=new JFrame();		
	}

}
