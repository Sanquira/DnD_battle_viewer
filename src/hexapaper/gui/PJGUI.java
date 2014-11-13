package hexapaper.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import addons.dice.DiceLog;

public class PJGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970551322101463562L;
	private JPanel contentPane;
	private DiceLog Dicelog;
	private DiceLog PJLog;

	public DiceLog getLog(){
		return Dicelog;
	}
	public DiceLog getInfo(){
		return PJLog;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PJGUI frame = new PJGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PJGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 580, 321);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel control = new JPanel();
		control.setBounds(236, 5, 91, 227);
		control.setBorder(new TitledBorder("ControlPanel"));
		contentPane.add(control);
		
		Dicelog = new DiceLog();
		Dicelog.setBounds(0, 5, 237, 227);
		Dicelog.setBorder(new TitledBorder("DiceLog"));
		Dicelog.addMessage("Kostka Načtena");
		contentPane.add(Dicelog);
		
		PJLog = new DiceLog();
		PJLog.setBounds(327, 5, 237, 227);
		PJLog.setBorder(new TitledBorder("PJLog"));
		PJLog.addMessage("PJLog initializován");
		contentPane.add(PJLog);
		
	}
}
