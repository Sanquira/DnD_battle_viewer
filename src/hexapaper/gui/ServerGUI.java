package hexapaper.gui;

import hexapaper.network.server.HexaServer;
import hexapaper.source.HPSklad;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.JTextField;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.border.TitledBorder;

import core.file.LoggingStream;
import network.command.users.CommandServer;

public class ServerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6963390687125685145L;
	private JPanel contentPane;
	private CommandServer server;
	private JTextField textField;
	private ActionListener sendCmd=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(textField.getText());
			if(!server.getCommandStorage().checkCommand(textField.getText())){
				System.out.println("Neznámý příkaz");
			}
			textField.setText("");
		}		
	};
	private JPanel panel;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ServerGUI(){
		initialize();
	}
	public ServerGUI(CommandServer server){
		this.server=server;
		initialize();
	}
	
	/**
	 * Create the frame.
	 */
	public void initialize(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("HexaServer "+HPSklad.getInstance().VERSION);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();		
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane TextPanel = new JScrollPane();
		TextPanel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_TextPanel = new GridBagConstraints();
		gbc_TextPanel.weighty = 2.0;
		gbc_TextPanel.weightx = 1.0;
		gbc_TextPanel.insets = new Insets(0, 0, 5, 0);
		gbc_TextPanel.gridheight = 7;
		gbc_TextPanel.fill = GridBagConstraints.BOTH;
		gbc_TextPanel.gridx = 0;
		gbc_TextPanel.gridy = 0;
		contentPane.add(TextPanel, gbc_TextPanel);
		//TextPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel = new JPanel();
		TextPanel.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		panel.add(textArea);
		
		textField = new JTextField();
		
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.weightx = 1.0;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 7;
		contentPane.add(textField, gbc_textField);
		textField.addActionListener(sendCmd);
		textField.setColumns(10);
		System.setOut(new PrintStream(new LoggingStream("HexaServer",textArea)));
	}

}
