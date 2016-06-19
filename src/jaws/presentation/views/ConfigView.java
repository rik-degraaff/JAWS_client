package jaws.presentation.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import jaws.presentation.controllers.ConfigDelegate;
import jaws.presentation.controllers.MenuDelegate;
import jaws.presentation.controllers.PresetDelegate;

/**
 * A class representing the main view of the JAWS config-client
 * 
 * @author Rik
 */
public class ConfigView extends JFrame {

	private static final long serialVersionUID = -9041765805686943532L;

	JComboBox<String> presetCombo;

	JTextField webrootField;

	JList<String> logList;
	JTextField logPathField;
	JButton applyButton, resetButton, onOffButton;

	public ConfigView(PresetDelegate presetDelegate, ConfigDelegate configDelegate, MenuDelegate menuDelegate,
			ListModel<String> logsModel, SpinnerModel httpPortModel, SpinnerModel threadModel) {

		super("JAWS config client");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		Container pane = getContentPane();

		// main menu
		JMenuItem connectItem = new JMenuItem("connect");
		connectItem.addActionListener(ae -> menuDelegate.connectClicked());
		JMenuItem importItem = new JMenuItem("import");
		importItem.addActionListener(ae -> menuDelegate.importClicked());
		JMenuItem exportItem = new JMenuItem("export");
		exportItem.addActionListener(ae -> menuDelegate.exportClicked());

		JMenu connectionMenu = new JMenu("connection");
		connectionMenu.add(connectItem);

		JMenu importExportMenu = new JMenu("import/export");
		importExportMenu.add(importItem);
		importExportMenu.add(exportItem);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(connectionMenu);
		mainMenu.add(importExportMenu);

		setJMenuBar(mainMenu);

		// config
		addWithConstraints(pane, new JLabel("Preset"), 0, 0);
		presetCombo = new JComboBox<>();
		addWithConstraints(pane, presetCombo, 1, 0, 2, 1);

		JButton loadPresetButton = new JButton("load");
		loadPresetButton.addActionListener(ae -> presetDelegate.loadPresetClicked());
		JButton savePresetButton = new JButton("save");
		savePresetButton.addActionListener(ae -> presetDelegate.savePresetClicked());
		JButton newPresetButton = new JButton("new");
		newPresetButton.addActionListener(ae -> presetDelegate.newPresetClicked());
		addWithConstraints(pane, loadPresetButton, 3, 0);
		addWithConstraints(pane, savePresetButton, 4, 0);
		addWithConstraints(pane, newPresetButton, 5, 0);

		addWithConstraints(pane, new JLabel("Webroot"), 0, 1);
		webrootField = new JTextField();
		addWithConstraints(pane, webrootField, 1, 1, 5, 1);

		addWithConstraints(pane, new JLabel("Port HTTP"), 0, 2);
		JSpinner httpPortSpinner = new JSpinner(httpPortModel);
		addWithConstraints(pane, httpPortSpinner, 1, 2, 2, 1);

		addWithConstraints(pane, new JLabel("Threads"), 3, 2);
		JSpinner threadSpinner = new JSpinner(threadModel);
		addWithConstraints(pane, threadSpinner, 4, 2, 2, 1, 0, 0);

		// loglogPanel.add(logPathPanel, BorderLayout.NORTH);

		addWithConstraints(pane, new JLabel("Logs"), 0, 3);
		logPathField = new JTextField();
		addWithConstraints(pane, logPathField, 1, 3, 5, 1);

		logList = new JList<>(logsModel);
		addWithConstraints(pane, new JScrollPane(logList), 0, 4, 6, 1, 6, 6);

		applyButton = new JButton("apply");
		applyButton.addActionListener(ae -> configDelegate.applyClicked());
		addWithConstraints(pane, applyButton, 0, 5);
		resetButton = new JButton("reset");
		resetButton.addActionListener(ae -> configDelegate.resetClicked());
		addWithConstraints(pane, resetButton, 1, 5);

		onOffButton = new JButton("on/off");
		onOffButton.addActionListener(ae -> configDelegate.onOffClicked());
		addWithConstraints(pane, onOffButton, 5, 5);

		setBounds(200, 200, 500, 600);
		setMinimumSize(new Dimension(450, 550));
		setVisible(true);
	}
	
	/**
	 * Populates the preset combobox
	 * 
	 * @param names the names of the presets to populate the combobox with
	 */
	public void setPresetNames(List<String> names) {
		
		presetCombo.removeAll();
		names.stream().forEach(presetCombo::addItem);
	}
	
	/**
	 * @return the name of the currently selected preset
	 */
	public String getPresetName() {
		return presetCombo.getItemAt(presetCombo.getSelectedIndex());
	}
	
	/**
	 * @return the webroot as specified in the GUI
	 */
	public String getWebroot() {
		return webrootField.getText();
	}
	
	/**
	 * Sets the webroot setting in the GUI
	 * 
	 * @param webroot the webroot to set to
	 */
	public void setWebroot(String webroot) {
		webrootField.setText(webroot);
	}
	
	/**
	 * @return the path where the logs are saved on the server as specified in the GUI
	 */
	public String getLogPath() {
		return logPathField.getText();
	}
	
	/**
	 * Sets the logPath setting in the GUI
	 * 
	 * @param logPath the logPath to ste to
	 */
	public void setLogPath(String logPath) {
		logPathField.setText(logPath);
	}

	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy) {
		addWithConstraints(container, component, gridx, gridy, 1, 1);
	}

	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy,
	                               int gridWidth, int gridHeight) {
		addWithConstraints(container, component, gridx, gridy, gridWidth, gridHeight, gridWidth == 1 ? 0 : gridWidth, gridHeight == 1 ? 0 : gridHeight);
	}

	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy,
	                               int gridWidth, int gridHeight,
	                               int weightx, int weighty) {

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = weighty > 0 ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(2, 5, 2, 5);
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		constraints.weightx = weightx;
		constraints.weighty = weighty;

		container.add(component);
		((GridBagLayout) container.getLayout()).setConstraints(component, constraints);
	}
}
