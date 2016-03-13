package hexapaper.gui.frames;

import hexapaper.source.HPSklad;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.border.TitledBorder;

import addons.dice.LogWindow;
import core.streams.LogPrintStream;
import net.miginfocom.swing.MigLayout;
import network.command.users.CommandServer;

import javax.swing.JTabbedPane;


public class ServerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6963390687125685145L;
	private JPanel contentPane;
	private CommandServer server;
	JTextField cmdField;
	private HashMap<String,JLabel> playersMap = new HashMap<String,JLabel>();
	protected ActionListener sendCmd=new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(textField.getText());
			//textField.getText()
			server.getCommandStorage().checkCommand(cmdField.getText());
			cmdField.setText("");
		}		
	};
	private LogWindow infoLog;
	private JScrollPane playersScroll;
	private JPanel players;
	private JPanel LogPanel;
	public LogWindow diceLog;
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
	public ServerGUI(ActionListener cmd){
		this.sendCmd = cmd;
		initialize();
	}
	
	/**
	 * Create the frame.
	 */
	public void initialize(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		HPSklad.getInstance().setIcon(this);
		setTitle("HexaServer "+HPSklad.VERSION);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();		
		gbl_contentPane.rowWeights = new double[]{0.0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		JTabbedPane CardPanel = new JTabbedPane(JTabbedPane.TOP);
		CardPanel.setBorder(null);
		GridBagConstraints gbc_CardPanel = new GridBagConstraints();
		gbc_CardPanel.weighty = 2.0;
		gbc_CardPanel.weightx = 1.0;
		gbc_CardPanel.insets = new Insets(0, 0, 5, 5);
		gbc_CardPanel.fill = GridBagConstraints.BOTH;
		gbc_CardPanel.gridx = 0;
		gbc_CardPanel.gridy = 0;
		contentPane.add(CardPanel, gbc_CardPanel);
		
		LogPanel = new JPanel();
		CardPanel.add("Log",LogPanel);
		GridBagLayout gbl_LogPanel = new GridBagLayout();
		gbl_LogPanel.columnWeights = new double[]{0.0};
		gbl_LogPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		LogPanel.setLayout(gbl_LogPanel);
		
		infoLog = new LogWindow();
		GridBagConstraints gbc_infoLog = new GridBagConstraints();
		gbc_infoLog.weighty = 1.0;
		gbc_infoLog.weightx = 1.0;
		gbc_infoLog.fill = GridBagConstraints.BOTH;
		gbc_infoLog.gridheight = 7;
		gbc_infoLog.anchor = GridBagConstraints.NORTHWEST;
		gbc_infoLog.insets = new Insets(0, 0, 5, 0);
		gbc_infoLog.gridx = 0;
		gbc_infoLog.gridy = 0;
		LogPanel.add(infoLog, gbc_infoLog);
		System.setOut(new LogPrintStream(infoLog));
		//System.setOut(new PrintStream(new LoggingStream("HexaServer",textArea)));
		
		cmdField = new JTextField();
		GridBagConstraints gbc_cmdField = new GridBagConstraints();
		gbc_cmdField.fill = GridBagConstraints.BOTH;
		gbc_cmdField.weightx = 1.0;
		gbc_cmdField.anchor = GridBagConstraints.NORTH;
		gbc_cmdField.gridx = 0;
		gbc_cmdField.gridy = 7;
		LogPanel.add(cmdField, gbc_cmdField);
		cmdField.addActionListener(sendCmd);
		cmdField.setColumns(10);
		
		diceLog = new LogWindow();
		CardPanel.addTab("Dice",diceLog);
		
		playersScroll = new JScrollPane();
		playersScroll.setLayout(new ScrollPaneLayout());
		playersScroll.setBorder(new TitledBorder(null, "Hr\u00E1\u010Di", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_playersScroll = new GridBagConstraints();
		gbc_playersScroll.weighty = 1.0;
		gbc_playersScroll.insets = new Insets(0, 0, 5, 0);
		gbc_playersScroll.ipadx = 110;
		gbc_playersScroll.fill = GridBagConstraints.BOTH;
		gbc_playersScroll.gridx = 1;
		gbc_playersScroll.gridy = 0;
		contentPane.add(playersScroll, gbc_playersScroll);
		
		players = new JPanel();
		playersScroll.setViewportView(players);
		players.setLayout(new MigLayout("", "[grow]", "[]"));
		

		//gbc.ipady = 25;
//		for(int i=0;i<12;i++){
//			addPlayer("test"+i);
//		}
		//playersScroll.add(players);
		//players.setSize(players.getPreferredSize());
		//playersScroll.setColumnHeaderView(players);
	}
	public void addPlayer(String name){
		JLabel lab = new JLabel(name);
		players.add(lab,"growx, wrap");
		playersMap.put(name, lab);
		players.revalidate();
		players.repaint();
	}
	public void removePlayer(String name){
		players.remove(playersMap.get(name));
		players.revalidate();
		players.repaint();
	}
	public LogWindow getInfo(){
		return infoLog;
	}
	public LogWindow getDice(){
		return diceLog;
	}
	public void insert(String text){
		diceLog.insertMessage(text);
	}
}
