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

public class Dice {

	// /////////////////////////////////////////////////////////////////////////////
	// Začátek negrafickych metod
	// /////////////////////////////////////////////////////////////////////////////
	
	private JFrame frmKostka;
	private ArrayList<Integer> list=new ArrayList<Integer>();
	final Random rand = new Random();
	private HPSklad sk=HPSklad.getInstance();
	private JNumberTextField SideField;
	private JNumberTextField ModField;
	private JLabel RollLabel;
	private JLabel ModLabel;
	private JLabel SideLabel;
	private JPanel DiceLogPane;
	private JButton RollButton;
	private DiceLog log;
	private JScrollPane PresetsPane;

	private ActionListener fastValueButtonListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			SideField.setText(String.valueOf(button.getText()));
			
		}
	};
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
	private ActionListener rollListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			final int min = 1;	
			final int bonus = ModField.getInt();
			final int max = SideField.getInt();
			Integer number=rand.nextInt((max - min) + 1) + min;
			RollLabel.setText(String.valueOf(number+bonus));
			SideLabel.setText(String.valueOf(max));
			ModLabel.setText(String.valueOf(bonus));
			log.addMessage("["+(number+bonus)+";"+max+";"+bonus+"]");
			if(sk.isConnected&&!sk.isPJ){
				Integer[] i={number,max,bonus};
				try {
					sk.client.send(i, "dice");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		}
	};
	private JPanel Pane;
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
		//frmKostka.setResizable(false);
		frmKostka.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
//		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
//		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		frmKostka.getContentPane().setLayout(gridBagLayout);
		
		DiceLogPane = new JPanel();
		DiceLogPane.setBorder(new TitledBorder(null, "DiceLog", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		log=new DiceLog();
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
		
		JPanel DicePane = new JPanel();
		GridBagConstraints gbc_DicePane = new GridBagConstraints();
		gbc_DicePane.weightx = 0.1;
		gbc_DicePane.weighty = 1.0;
		gbc_DicePane.gridwidth = 2;
		gbc_DicePane.fill = GridBagConstraints.BOTH;
		gbc_DicePane.gridx = 0;
		gbc_DicePane.gridy = 0;
		frmKostka.getContentPane().add(DicePane, gbc_DicePane);
		GridBagLayout gbl_DicePane = new GridBagLayout();
		gbl_DicePane.columnWidths = new int[]{300, 22, 70, 0};
		gbl_DicePane.rowHeights = new int[]{167, 0, 0, 0, 0, 0};
		gbl_DicePane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_DicePane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		DicePane.setLayout(gbl_DicePane);
		
		JPanel SettingPane = new JPanel();
		GridBagConstraints gbc_SettingPane = new GridBagConstraints();
		gbc_SettingPane.fill = GridBagConstraints.BOTH;
		gbc_SettingPane.weighty = 0.5;
		gbc_SettingPane.weightx = 0.5;
		gbc_SettingPane.gridwidth = 4;
		gbc_SettingPane.anchor = GridBagConstraints.WEST;
		gbc_SettingPane.insets = new Insets(0, 0, 0, 5);
		gbc_SettingPane.gridx = 0;
		gbc_SettingPane.gridy = 4;
		DicePane.add(SettingPane, gbc_SettingPane);
		SettingPane.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SettingPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel = new JPanel();
		SettingPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		RollButton = new JButton("Roll");
		RollButton.addActionListener(rollListener);
		RollButton.setHorizontalTextPosition(SwingConstants.CENTER);
		RollButton.setPreferredSize(new Dimension(100, 23));
		panel.add(RollButton);
		
		JPanel SidePane = new JPanel();
		SidePane.setToolTipText("Sides");
		SettingPane.add(SidePane);
		
		SideField = new JNumberTextField();
		SideField.setPreferredSize(new Dimension(75, 19));
		SideField.setAlignmentY(Component.TOP_ALIGNMENT);
		SideField.setAlignmentX(Component.LEFT_ALIGNMENT);
		SideField.setToolTipText("Sides");
		SideField.setMaxLength(4);
		SideField.setHorizontalAlignment(SwingConstants.CENTER);
		SideField.setText("6");
		SidePane.add(SideField);
		SideField.setColumns(10);
		
		JPanel ModPane = new JPanel();
		ModPane.getLayout();
		SettingPane.add(ModPane);
		
		ModField = new JNumberTextField();
		ModField.setToolTipText("Modifier");
		ModField.setHorizontalAlignment(SwingConstants.CENTER);
		ModField.setText("0");
		ModPane.add(ModField);
		ModField.setColumns(10);
		
		PresetsPane = new JScrollPane();
		GridBagConstraints gbc_PresetsPane = new GridBagConstraints();
		gbc_PresetsPane.fill = GridBagConstraints.BOTH;
		gbc_PresetsPane.weighty = 1.0;
		gbc_PresetsPane.weightx = 1.0;
		gbc_PresetsPane.gridheight = 4;
		gbc_PresetsPane.anchor = GridBagConstraints.WEST;
		gbc_PresetsPane.insets = new Insets(0, 0, 0, 5);
		gbc_PresetsPane.gridx = 2;
		gbc_PresetsPane.gridy = 0;
		DicePane.add(PresetsPane, gbc_PresetsPane);
		PresetsPane.setBorder(new TitledBorder(null, "Presets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PresetsPane.setLayout(new ScrollPaneLayout());
		
		Pane = new JPanel();
		PresetsPane.setColumnHeaderView(Pane);
		Pane.setLayout(new GridLayout(6, 0, 0, 0));
		
		JPanel DiceRollPane = new JPanel();
		GridBagConstraints gbc_DiceRollPane = new GridBagConstraints();
		gbc_DiceRollPane.fill = GridBagConstraints.BOTH;
		gbc_DiceRollPane.weighty = 1.0;
		gbc_DiceRollPane.weightx = 1.0;
		gbc_DiceRollPane.gridheight = 4;
		gbc_DiceRollPane.gridwidth = 2;
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
		initialize();
		frmKostka.setVisible(true);
		//if(sk.isPJ){
			//sk.log.setVisible(true);
		//}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Konec negrafickych metod
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * TOHLE budes upravovat
	 */
	private void initialize() {
		// nastaveni titulku okna, velikosti a layoutu
		frmKostka.setTitle("Kostka");
		frmKostka.setSize(567, 300);
		list.add(6);
		list.add(10);
		list.add(100);
		list.add(7);
		generatePresets(Pane,list);
	}
	private void generatePresets(JPanel pane, ArrayList<Integer> presets){
		pane.removeAll();
		//PresetsPane.setLayout(new BoxLayout(PresetsPane,)
			for(Integer i:presets){
				JButton but=new JButton(String.valueOf(i));
				but.setPreferredSize(new Dimension(PresetsPane.getSize().width,but.getPreferredSize().height));
				but.addActionListener(fastValueButtonListener);
				pane.add(but);
			}	
	}
	private JScrollPane generatePresetPane(){
		JScrollPane presetsPane = new JScrollPane();
		presetsPane.setLayout(new ScrollPaneLayout());
		
		JPanel Pane = new JPanel();
		presetsPane.setColumnHeaderView(Pane);
		Pane.setLayout(new GridLayout(6, 0, 0, 0));
		return presetsPane;
	}
}
