package hexapaper.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import addons.dice.LogWindow;

import javax.swing.BoxLayout;
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
	private LogWindow Dicelog;
	private LogWindow PJLog;
	private JTextField CmdField;
	private ActionListener sendCmd=new ActionListener(){
		public void actionPerformed(ActionEvent e){
			//storage.client.send(CmdField.getText(), "PJcmd");
		}
	};
	private JTextPane textPane;

	public LogWindow getLog(){
		return Dicelog;
	}
	public LogWindow getInfo(){
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
		//System.out.println(Message);
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
		//setResizable(false);
		setBounds(100, 100, 580, 321);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx =0.5;
		gbc.weighty = 0.5;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		JPanel control = new JPanel();
		//control.setBounds(236, 5, 91, 227);
		control.setBorder(new TitledBorder("ControlPanel"));
		contentPane.add(control,gbc);
		control.setLayout(new GridLayout(0, 1, 0, 0));
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		control.add(textPane);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.gridheight = 7;
		gbc.gridwidth = 3;
		Dicelog = new LogWindow();
		//Dicelog.setBounds(0, 5, 237, 227);
		Dicelog.setBorder(new TitledBorder("DiceLog"));
		//Dicelog.addMessage("Kostka Načtena");
		contentPane.add(Dicelog,gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 7;
		gbc.gridwidth = 3;
		JPanel PJControl = new JPanel();
		PJControl.setLayout(new BoxLayout(PJControl, BoxLayout.Y_AXIS));
		PJControl.setBorder(new TitledBorder("PJLog"));
		PJLog = new LogWindow();	

		CmdField = new JTextField();
		CmdField.setHorizontalAlignment(SwingConstants.LEFT);
		CmdField.addActionListener(sendCmd);
		CmdField.setColumns(10);
		CmdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, CmdField.getPreferredSize().height));
		PJControl.setLayout(new BoxLayout(PJControl, BoxLayout.Y_AXIS));
		
		PJControl.add(PJLog);
		PJControl.add(CmdField);
		contentPane.add(PJControl);
		
		contentPane.add(PJControl,gbc);
		

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.weighty = 0.1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		JPanel buttonPane=new JPanel();
		contentPane.add(buttonPane,gbc);

		
	}
}
