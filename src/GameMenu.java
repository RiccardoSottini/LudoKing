import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameMenu extends JPanel {
	private final Color[] playerColors = {
		Color.decode("#65CDD1"),
		Color.decode("#EE6E6E"),
		Color.decode("#89C66C"),
		Color.decode("#E8E557")
	};
	
	private final int MIN_PLAYERS = 2;
	private final int MAX_PLAYERS = 4;
	private int nPlayers;
	
	private final Dimension menuDimension;
	
	private JPanel menuCenter;
	
	private JPanel numberPanel;
	private JLabel numberLabel;
	private JSpinner numberField;
	
	private JPanel playerList;
	private JPanel[] playerPanels;
	private JTextField[] playerFields;
	
	private JButton playButton;
	
	private boolean hasInput;
	
	public GameMenu(Dimension menuDimension) {
		this.menuDimension = menuDimension;
		this.nPlayers = MIN_PLAYERS;
		this.hasInput = false;
		
		this.setupMenu();
	}
	
	public void waitMenu() {
		while(!this.hasInput) {
			try {
			    Thread.sleep(100);
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
	public String[] getPlayers() {
		String[] playerNames = new String[this.nPlayers];
		
		for(int playerIndex = 0; playerIndex < this.nPlayers; playerIndex++) {
			playerNames[playerIndex] = playerFields[playerIndex].getText();
		}
		
		return playerNames;
	}
	
	public void setupMenu() {
		this.setLayout(null);
		this.setPreferredSize(this.menuDimension);
		this.setLocation(0, 0);
		
		this.setupMenuCenter();
		
		this.setOpaque(true);
		this.setVisible(true);
	}
	
	public void setupMenuCenter() {
		this.menuCenter = new JPanel();
		this.menuCenter.setLayout(null);
		
		int centerWidth = (int) (this.menuDimension.width * 0.5);
		int centerHeight = (int) (this.menuDimension.height * 0.8);
		this.menuCenter.setSize(centerWidth, centerHeight);
		
		int centerPositionX = (this.menuDimension.width - centerWidth) / 2;
		int centerPositionY = (this.menuDimension.height - centerHeight) / 2;
		this.menuCenter.setLocation(centerPositionX, centerPositionY);
		
		Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.menuCenter.setBorder(cellBorder);
		this.menuCenter.setBackground(Color.WHITE);
		
		this.setupNumberPanel();
		this.setupPlayerList();
		this.setupPlayButton();
		
		this.menuCenter.setOpaque(true);
		this.menuCenter.setVisible(true);
		this.add(menuCenter);
	}
	
	public void setupNumberPanel() {
		this.numberPanel = new JPanel();
		this.numberPanel.setLayout(null);
		
		int panelWidth = (int) this.menuCenter.getWidth() - 40;
		int panelHeight = 50;
		this.numberPanel.setSize(panelWidth, panelHeight);
		this.numberPanel.setLocation(20, 20);
		
		Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.numberPanel.setBorder(cellBorder);
		this.numberPanel.setBackground(Color.WHITE);

		this.setupNumberField();
		this.setupNumberLabel();
		
		this.numberPanel.setOpaque(true);
		this.numberPanel.setVisible(true);
		this.menuCenter.add(this.numberPanel);
	}
	
	public void setupNumberLabel() {
		this.numberLabel = new JLabel("Number of Players:");
		numberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		
		int labelWidth = (this.numberPanel.getWidth() - this.numberField.getWidth()) - 50;
		int labelHeight = 30;
		this.numberLabel.setSize(labelWidth, labelHeight);
		this.numberLabel.setLocation(20, 10);
		
		this.numberLabel.setVisible(true);
		this.numberPanel.add(this.numberLabel);
	}
	
	public void setupNumberField() {
		SpinnerModel fieldModel = new SpinnerNumberModel(MIN_PLAYERS, MIN_PLAYERS, MAX_PLAYERS, 1);
		this.numberField = new JSpinner(fieldModel);
		
		this.numberField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner numberField = (JSpinner) e.getSource();
                
                nPlayers = (int) numberField.getValue();
                showPlayers();
            }
        });
		
		Dimension fieldDimension = new Dimension(60, 26);
		this.numberField.setSize(fieldDimension);
		
		int fieldPositionX = (this.numberPanel.getWidth() - fieldDimension.width) - 20;
		int fieldPositionY = 12;
		this.numberField.setLocation(fieldPositionX, fieldPositionY);
		
		JFormattedTextField numberText = ((JSpinner.DefaultEditor) this.numberField.getEditor()).getTextField();
		numberText.setFont(new Font("Arial", Font.PLAIN, 14));
		numberText.setHorizontalAlignment(JFormattedTextField.CENTER);
		numberText.setEditable(false);
		numberText.setBackground(Color.WHITE);
		
		this.numberPanel.add(this.numberField);
	}
	
	public void setupPlayerList() {
		this.playerList = new JPanel();
		this.playerList.setLayout(null);
		
		int listWidth = this.menuCenter.getWidth() - 40;
		int listHeight = (this.menuCenter.getHeight() - this.numberPanel.getHeight()) - 120;
		this.playerList.setSize(listWidth, listHeight);
		
		int listPositionX = 20;
		int listPositionY = this.numberPanel.getHeight() + 40;
		this.playerList.setLocation(listPositionX, listPositionY);
		
		Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.playerList.setBorder(cellBorder);
		this.playerList.setBackground(Color.WHITE);
		
		this.playerPanels = new JPanel[MAX_PLAYERS];
		this.playerFields = new JTextField[MAX_PLAYERS];
		for(int playerIndex = 0; playerIndex < this.playerPanels.length; playerIndex++) {
			this.setupPlayerPanel(playerIndex);
		}
		
		this.showPlayers();
		
		this.playerList.setOpaque(true);
		this.playerList.setVisible(true);
		this.menuCenter.add(this.playerList);
	}
	
	public void setupPlayerPanel(int playerIndex) {
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(null);
		
		int panelWidth = this.playerList.getWidth() - 20;
		int panelHeight = 40;
		playerPanel.setSize(panelWidth, panelHeight);
		
		int panelPositionX = 10;
		int panelPositionY = ((panelHeight + 5) * playerIndex) + 10;
		playerPanel.setLocation(panelPositionX, panelPositionY);
		
		Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		playerPanel.setBorder(cellBorder);
		playerPanel.setBackground(Color.WHITE);
		playerPanel.setVisible(false);
		
		this.playerPanels[playerIndex] = playerPanel;
		this.playerList.add(playerPanel);

		this.setupPlayerColor(playerIndex);
		this.setupPlayerField(playerIndex);
	}
	
	public void setupPlayerColor(int playerIndex) {
		JPanel panelColor = new JPanel();
		
		int panelWidth = this.playerPanels[playerIndex].getHeight() - 14;
		int panelHeight = this.playerPanels[playerIndex].getHeight() - 14;
		panelColor.setSize(panelWidth, panelHeight);
		
		int panelPositionX = (this.playerPanels[playerIndex].getHeight() - panelWidth) / 2;
		int panelPositionY = (this.playerPanels[playerIndex].getHeight() - panelHeight) / 2;
		panelColor.setLocation(panelPositionX, panelPositionY);
		
		panelColor.setBackground(this.playerColors[playerIndex]);
		panelColor.setVisible(true);
		
		this.playerPanels[playerIndex].add(panelColor);
	}
	
	public void setupPlayerField(int playerIndex) {
		JTextField playerField = new JTextField();

		int fieldSize = this.playerPanels[playerIndex].getHeight() - 14;
		int fieldOffset = (this.playerPanels[playerIndex].getHeight() - fieldSize) / 2;
		
		int fieldWidth = this.playerPanels[playerIndex].getWidth() - fieldSize - (fieldOffset * 3);
		int fieldHeight = fieldSize;
		playerField.setSize(fieldWidth, fieldHeight);
		
		int fieldPositionX = fieldSize + (fieldOffset * 2);
		int fieldPositionY = fieldOffset;
		playerField.setLocation(fieldPositionX, fieldPositionY);
		
		Border fieldPadding = BorderFactory.createEmptyBorder(2, 6, 2, 6);
		playerField.setBorder(BorderFactory.createCompoundBorder(playerField.getBorder(), fieldPadding));
		
		this.playerFields[playerIndex] = playerField;
		this.playerPanels[playerIndex].add(playerField);
	}
	
	public void showPlayers() {
		for(int playerIndex = 0; playerIndex < this.MAX_PLAYERS; playerIndex++) {
			if(playerIndex < this.nPlayers) {
				if(!this.playerPanels[playerIndex].isVisible()) {
					this.playerPanels[playerIndex].setVisible(true);
				}
			} else {
				this.playerPanels[playerIndex].setVisible(false);
			}
		}
	}
	
	public void setupPlayButton() {
		this.playButton = new JButton("PLAY");
		
		int buttonWidth = this.menuCenter.getWidth() - 40;
		int buttonHeight = 40;
		this.playButton.setSize(new Dimension(buttonWidth, buttonHeight));
		
		int fieldPositionX = 20;
		int fieldPositionY = this.numberLabel.getHeight() + this.playerList.getHeight() + 80;
		this.playButton.setLocation(fieldPositionX, fieldPositionY);
		
		this.playButton.setFont(new Font("Arial", Font.PLAIN, 14));
		this.playButton.setHorizontalAlignment(JFormattedTextField.CENTER);
		this.playButton.setBackground(Color.WHITE);
		
		this.playButton.setVisible(true);
		this.menuCenter.add(this.playButton);
		
		this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	hasInput = true;
            }
        });
	}
}
