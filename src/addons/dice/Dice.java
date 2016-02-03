package addons.dice;

import hexapaper.source.HPSklad;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;

import core.JNumberTextField;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.TitledBorder;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Component;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Dice {

	// /////////////////////////////////////////////////////////////////////////////
	// Začátek negrafickych metod
	// /////////////////////////////////////////////////////////////////////////////
	
	private JFrame frmKostka;
	private ArrayList<Preset> list=new ArrayList<Preset>();
	final Random rand = new Random();
	private HPSklad sk=HPSklad.getInstance();
	private JNumberTextField SideField;
	private JNumberTextField ModField;
	private JNumberTextField CountField;
	private JLabel RollLabel;
	private JLabel ModLabel;
	private JLabel SideLabel;
	private JPanel DiceLogPane;
	private JButton RollButton;
	private LogWindow log;
	private JScrollPane PresetsPane;

//	
//	private ActionListener preventNull = new ActionListener(){
//		@Override
//		public void actionPerformed(ActionEvent event) {
//			JNumberTextField field=(JNumberTextField) event.getSource();
//			if(field.getText().equals("")){
//				field.setInt(1);
//			}
//		}		
//	};	
	protected ActionListener rollListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleRoll(SideField.getInt(),ModField.getInt(),CountField.getInt());
		}
	};
	private JMenuBar menuBar;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dice frame = new Dice();
					frame.frmKostka.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Dice() {
		frmKostka = new JFrame();
		initialize();
		//frmKostka.setResizable(false);
		frmKostka.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
//		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
//		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		frmKostka.getContentPane().setLayout(gridBagLayout);
		
		DiceLogPane = new JPanel();
		DiceLogPane.setBorder(new TitledBorder(null, "DiceLog", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		log=new LogWindow();
		GridBagConstraints gbc_DiceLogPane = new GridBagConstraints();
		gbc_DiceLogPane.weightx = 0.2;
		gbc_DiceLogPane.insets = new Insets(0, 0, 5, 0);
		gbc_DiceLogPane.weighty = 1.0;
		gbc_DiceLogPane.fill = GridBagConstraints.BOTH;
		gbc_DiceLogPane.gridx = 3;
		gbc_DiceLogPane.gridy = 0;
		DiceLogPane.setLayout(new GridLayout(0, 1, 0, 0));
		DiceLogPane.add(log);
		frmKostka.getContentPane().add(DiceLogPane, gbc_DiceLogPane);
		
		JPanel SettingPane = new JPanel();
		GridBagConstraints gbc_SettingPane = new GridBagConstraints();
		gbc_SettingPane.fill = GridBagConstraints.BOTH;
		gbc_SettingPane.gridwidth = 4;
		gbc_SettingPane.gridy = 1;
		gbc_SettingPane.gridx = 0;
		frmKostka.getContentPane().add(SettingPane, gbc_SettingPane);
		SettingPane.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SettingPane.setLayout(new GridLayout(0, 4, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Roll", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		SettingPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RollButton = new JButton("Roll");
		RollButton.addActionListener(rollListener);
		RollButton.setHorizontalTextPosition(SwingConstants.CENTER);
		RollButton.setPreferredSize(new Dimension(100, 23));
		panel.add(RollButton);
		
		JPanel SidePane = new JPanel();
		SidePane.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Sides", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		SidePane.setToolTipText("Sides");
		SettingPane.add(SidePane);
		
		SideField = new JNumberTextField();
		SideField.setPreferredSize(new Dimension(75, 19));
		SideField.setAlignmentY(Component.TOP_ALIGNMENT);
		SideField.setAlignmentX(Component.LEFT_ALIGNMENT);
		SideField.setToolTipText("Sides");
		SideField.setMaxLength(4);
		SideField.setHorizontalAlignment(SwingConstants.CENTER);
		SideField.setInt(6);
		SidePane.add(SideField);
		SideField.setColumns(10);
		
		JPanel ModPane = new JPanel();
		ModPane.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Modifier", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		ModPane.getLayout();
		SettingPane.add(ModPane);
		
		ModField = new JNumberTextField();
		ModField.setToolTipText("Modifier");
		ModField.setHorizontalAlignment(SwingConstants.CENTER);
		ModField.setInt(0);
		ModPane.add(ModField);
		ModField.setColumns(10);
		
		JPanel CountPane = new JPanel();
		CountPane.setBorder(new TitledBorder(null, "Count", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		SettingPane.add(CountPane);
		
		CountField = new JNumberTextField();
		CountField.setToolTipText("Count");
		CountField.setText("1");
		CountField.setPreferredSize(new Dimension(75, 19));
		CountField.setMaxLength(2);
		CountField.setHorizontalAlignment(SwingConstants.CENTER);
		CountField.setColumns(10);
		CountPane.add(CountField);
		
		JPanel DicePane = new JPanel();
		GridBagConstraints gbc_DicePane = new GridBagConstraints();
		gbc_DicePane.weightx = 0.1;
		gbc_DicePane.weighty = 1.0;
		gbc_DicePane.gridwidth = 1;
		gbc_DicePane.fill = GridBagConstraints.BOTH;
		gbc_DicePane.gridx = 0;
		gbc_DicePane.gridy = 0;
		frmKostka.getContentPane().add(DicePane, gbc_DicePane);
		GridBagLayout gbl_DicePane = new GridBagLayout();
		gbl_DicePane.columnWidths = new int[]{300, 22, 70, 0};
		gbl_DicePane.rowHeights = new int[]{167, 0, 0, 0, 0, 0};
		DicePane.setLayout(gbl_DicePane);
		
		PresetsPane = generatePresetPane(list);
		GridBagConstraints gbc_PresetsPane = new GridBagConstraints();
		gbc_PresetsPane.fill = GridBagConstraints.BOTH;
		gbc_PresetsPane.weighty = 1.0;
		gbc_PresetsPane.weightx = 1.0;
		gbc_PresetsPane.gridheight = 4;
		gbc_PresetsPane.gridwidth = 2;
		gbc_PresetsPane.anchor = GridBagConstraints.WEST;
		gbc_PresetsPane.insets = new Insets(0, 0, 0, 5);
		gbc_PresetsPane.gridx = 1;
		gbc_PresetsPane.gridy = 0;
		DicePane.add(PresetsPane, gbc_PresetsPane);
		PresetsPane.setBorder(new TitledBorder(null, "Presets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel DiceRollPane = new JPanel();
		GridBagConstraints gbc_DiceRollPane = new GridBagConstraints();
		gbc_DiceRollPane.fill = GridBagConstraints.BOTH;
		gbc_DiceRollPane.weighty = 1.0;
		gbc_DiceRollPane.weightx = 1.0;
		gbc_DiceRollPane.gridheight = 4;
		gbc_DiceRollPane.gridwidth = 1;
		gbc_DiceRollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_DiceRollPane.gridx = 0;
		gbc_DiceRollPane.gridy = 0;
		DicePane.add(DiceRollPane, gbc_DiceRollPane);
		DiceRollPane.setBorder(new TitledBorder(null, "Dice", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_DiceRollPane = new GridBagLayout();
		gbl_DiceRollPane.columnWeights = new double[]{0.0};
		gbl_DiceRollPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		DiceRollPane.setLayout(gbl_DiceRollPane);
		
		JPanel RollPane = new JPanel();
		RollPane.setBorder(new TitledBorder(null, "Roll", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_RollPane = new GridBagConstraints();
		gbc_RollPane.weighty = 1.0;
		gbc_RollPane.weightx = 1.0;
		gbc_RollPane.gridheight = 3;
		gbc_RollPane.fill = GridBagConstraints.BOTH;
		gbc_RollPane.insets = new Insets(0, 0, 5, 0);
		gbc_RollPane.gridx = 0;
		gbc_RollPane.gridy = 0;
		DiceRollPane.add(RollPane, gbc_RollPane);
		RollPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		RollLabel = new JLabel("0");
		RollLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		RollLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RollPane.add(RollLabel);
		
		JPanel ModsPane = new JPanel();
		ModsPane.setBorder(new TitledBorder(null, "Modifier", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_ModsPane = new GridBagConstraints();
		gbc_ModsPane.weighty = 0.5;
		gbc_ModsPane.weightx = 1.0;
		gbc_ModsPane.fill = GridBagConstraints.BOTH;
		gbc_ModsPane.insets = new Insets(0, 0, 5, 0);
		gbc_ModsPane.gridx = 0;
		gbc_ModsPane.gridy = 3;
		DiceRollPane.add(ModsPane, gbc_ModsPane);
		ModsPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		ModLabel = new JLabel("0");
		ModLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ModsPane.add(ModLabel);
		
		JPanel SidesPane = new JPanel();
		SidesPane.setBorder(new TitledBorder(null, "Sides", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_SidesPane = new GridBagConstraints();
		gbc_SidesPane.insets = new Insets(0, 0, 5, 0);
		gbc_SidesPane.weighty = 0.5;
		gbc_SidesPane.weightx = 1.0;
		gbc_SidesPane.fill = GridBagConstraints.BOTH;
		gbc_SidesPane.gridx = 0;
		gbc_SidesPane.gridy = 4;
		DiceRollPane.add(SidesPane, gbc_SidesPane);
		SidesPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		SideLabel = new JLabel("0");
		SideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SidesPane.add(SideLabel);
		
		constructMenu();
		frmKostka.setVisible(true);
		//if(sk.isPJ){
			//sk.log.setVisible(true);
		//}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Konec negrafickych metod
	// /////////////////////////////////////////////////////////////////////////////

	private void constructMenu() {
		menuBar = new JMenuBar();
		frmKostka.setJMenuBar(menuBar);
		
		JMenu Action = new JMenu("Akce");
		JMenuItem multiple = new JMenuItem("Vícenásobný Hod");
		JMenuItem presets = new JMenuItem("Presets");
		
		Action.add(multiple);
		Action.add(presets);
		menuBar.add(Action);
		
	}
	/**
	 * TOHLE budes upravovat
	 */
	private void initialize() {
		// nastaveni titulku okna, velikosti a layoutu
		frmKostka.setTitle("Kostka");
		frmKostka.setSize(567, 300);
		list.add(new Preset(6,0,1));
		list.add(new Preset(10,0,1));
		list.add(new Preset(100,0,1));
		list.add(new Preset(7,0,1));
		//generatePresets(Pane,list);
	}
//	private JPanel generatePresets(ArrayList<Integer> presets){
//		JPanel pane = new JPanel();
//		//PresetsPane.setLayout(new BoxLayout(PresetsPane,)
//			for(Integer i:presets){
//				JButton but=new JButton(String.valueOf(i));
//				but.setPreferredSize(new Dimension(PresetsPane.getSize().width,but.getPreferredSize().height));
//				but.addActionListener(fastValueButtonListener);
//				pane.add(but);
//			}	
//	}
	private JScrollPane generatePresetPane(ArrayList<Preset> presets){
		JScrollPane presetsPane = new JScrollPane();
		presetsPane.setLayout(new ScrollPaneLayout());
		
		JPanel pane = new JPanel();
		presetsPane.add(pane);
		int col = 5;
		presetsPane.getVerticalScrollBar().setEnabled(false);
		if(presets.size()>5){
			col = presets.size();
			presetsPane.getVerticalScrollBar().setEnabled(true);
		}
		pane.setLayout(new GridLayout(col,1,10,0));
		for(Preset pr:presets){
//			JPanel j = new JPanel();
//			j.setPreferredSize(new Dimension(0,30));
//			j.add(new PresetButton(pr,this));
			pane.add(new PresetButton(pr,this));
		}
		presetsPane.setViewportView(pane);
		return presetsPane;
	}
	private Integer getRoll(int sides){
		return rand.nextInt(sides) + 1;
	}
	private void roll(int sides, int modifier){
		final Integer number = getRoll(sides);
		RollLabel.setText(String.valueOf(number+modifier));
		SideLabel.setText(String.valueOf(sides));
		ModLabel.setText(String.valueOf(modifier));
		log.addMessage("["+(number+modifier)+";"+sides+";"+modifier+"]",Color.BLACK,false);
		if(sk.isConnected&&!sk.isPJ){
			Integer[] i={number,sides,modifier};
			sk.client.send(i, "dice");
		}
	}
	protected void setText(int sides, int modifier, int count){
		SideField.setInt(sides);
		ModField.setInt(modifier);
		CountField.setInt(count);
	}
	protected void multipleRoll(int sides, int modifier, int count){
		for(int i=0;i<count;i++){
			roll(sides,modifier);
		}
	}
}
