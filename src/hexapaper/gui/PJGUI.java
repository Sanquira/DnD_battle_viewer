package hexapaper.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import addons.dice.DiceLog;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

import java.awt.GridLayout;
import java.util.Map;
import java.util.Map.Entry;

public class PJGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970551322101463562L;
	private JPanel contentPane;
	private Map<String,String> versions;
	private DiceLog Dicelog;
	private DiceLog PJLog;
	private JTextField CmdField;
	private ActionListener sendCmd=new ActionListener(){
		public void actionPerformed(ActionEvent e){
			//storage.client.send(CmdField.getText(), "PJcmd");
		}
	};
	private JTextPane textPane;

	public DiceLog getLog(){
		return Dicelog;
	}
	public DiceLog getInfo(){
		return PJLog;
	}
	public void updateClients(Map<String,String> ver){
		versions=ver;
		updateList();
	}
	public void updateList(){
		String Message = new String();
		for(Entry<String, String> entry:versions.entrySet()){
			Message+=entry.getKey()+"("+entry.getValue()+")\n";
		}
		textPane.setText(Message);
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
		control.setLayout(new GridLayout(0, 1, 0, 0));
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		control.add(textPane);
		
		Dicelog = new DiceLog();
		Dicelog.setBounds(0, 5, 237, 227);
		Dicelog.setBorder(new TitledBorder("DiceLog"));
		//Dicelog.addMessage("Kostka Načtena");
		contentPane.add(Dicelog);
		
		PJLog = new DiceLog();
		PJLog.setBounds(327, 5, 237, 227);
		PJLog.setBorder(new TitledBorder("PJLog"));
		//PJLog.addMessage("PJLog initializován");
		contentPane.add(PJLog);
		
		CmdField = new JTextField();
		CmdField.setHorizontalAlignment(SwingConstants.LEFT);
		CmdField.setBounds(327, 229, 237, 20);
		CmdField.addActionListener(sendCmd);
		contentPane.add(CmdField);
		CmdField.setColumns(10);
		
	}
}
