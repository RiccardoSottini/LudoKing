import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameField extends JFrame {
	private final Color[] colors = {
		Color.BLUE,
		Color.RED,
		Color.GREEN,
		Color.YELLOW,
		Color.WHITE
	};
	
	private final int[][] openPositions = {
		{6, 13},	{6, 12},	{6, 11},	{6, 10}, 	{6, 9},
		{5, 8},		{4, 8},  	{3, 8},		{2, 8},		{1, 8},
		{0, 8},		{0, 7},		{0, 6},		
		{1, 6},		{2, 6},		{3, 6},		{4, 6},		{5, 6},
		{6, 5},		{6, 4},		{6, 3},		{6, 2},		{6, 1},
		{6, 0},		{7, 0},		{8, 0},
		{8, 1},		{8, 2},		{8, 3},		{8, 4},		{8, 5},
		{9, 6},		{10, 6},	{11, 6},	{12, 6},	{13, 6},
		{14, 6},	{14, 7},	{14, 8},
		{13, 8},	{12, 8},	{11, 8},	{10, 8},	{9, 8},
		{8, 9},		{8, 10},	{8, 11},	{8, 12},	{8, 13},
		{8, 14},	{7, 14},	{6, 14}
	};
	
	private final int[][] closePositions = {
		{7, 13},	{7, 12},	{7, 11},	{7, 10},	{7, 9},		{7, 8},
		{1, 7},		{2, 7},		{3, 7},		{4, 7},		{5, 7},		{6, 7},
		{7, 1},		{7, 2},		{7, 3},		{7, 4},		{7, 5},		{7, 6},
		{13, 7},	{12, 7},	{11, 7},	{10, 7},	{9, 7},		{8, 7}
	};
	
	private final int[][] basePositions = {
		{0, 9},		{0, 0},		{9, 0},		{9, 9}
	};
	
	private final int nCells = 16;
	private final int cellWidth = 40;
	private final int cellHeight = 40;
	
	private final int baseWidth = cellWidth * 6;
	private final int baseHeight = cellHeight * 6;
	
	private final int frameWidth = cellWidth * nCells - 20;
	private final int frameHeight = cellHeight * nCells;
	
	private Cell[] openCells;
	private Cell[][] closeCells;
	
	public GameField(Cell[] openCells, Cell[][] closeCells) {
		this.openCells = openCells;
		this.closeCells = closeCells;
		
		this.setupFrame();
	}
	
	public void setupFrame() {
		this.setTitle("Ludo King");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setResizable(false);                              
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		//this.add(this.setupBoard());
		this.setContentPane(this.createBoard());
		
		this.setPreferredSize(new Dimension(this.frameWidth, this.frameHeight));
		this.pack();
		this.setVisible(true);
	}
	
	public JPanel createBoard() {
		JPanel boardPanel = new JPanel();
		
		boardPanel.setLayout(null);
		boardPanel.setPreferredSize(new Dimension(this.frameWidth, this.frameHeight));
	
		for(int c = 0; c < this.openCells.length; c++) {
			Dimension cellDimension = new Dimension(this.cellWidth, this.cellHeight);
			
			int positionX = openPositions[c][0] * cellWidth;
			int positionY = openPositions[c][1] * cellHeight;
			Point cellPosition = new Point(positionX, positionY);
			
			this.openCells[c].drawCell(cellDimension, cellPosition, boardPanel);
		}
		
		for(int p = 0; p < closeCells.length; p++) {
			for(int c = 0; c < closeCells[p].length; c++) {				
				Dimension cellDimension = new Dimension(this.cellWidth, this.cellHeight);
				
				int positionIndex = (p * closeCells[p].length) + c;
				int positionX = closePositions[positionIndex][0] * cellWidth;
				int positionY = closePositions[positionIndex][1] * cellHeight;
				Point cellPosition = new Point(positionX, positionY);
				
				this.closeCells[p][c].drawCell(cellDimension, cellPosition, boardPanel);
			}
		}
		
		for(int baseIndex = 0; baseIndex < 4; baseIndex++) {
			boardPanel.add(this.setupBaseCenter(baseIndex));
			boardPanel.add(this.setupBase(baseIndex));
		}
		
		JPanel player1 = new JPanel();
		//player1.setSize(d);
		
		
		return boardPanel;
	}
	
	public JPanel setupBase(int baseIndex) {
		JPanel basePanel = new JPanel();
		
		Dimension baseDimension = new Dimension(baseWidth, baseHeight);
		
		int positionX = basePositions[baseIndex][0] * cellWidth;
		int positionY = basePositions[baseIndex][1] * cellHeight;
		Point basePosition = new Point(positionX, positionY);
		
		Border baseBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		basePanel.setBorder(baseBorder);
		basePanel.setBackground(colors[baseIndex]);
		
		basePanel.setSize(baseDimension);
		basePanel.setLocation(basePosition);
		basePanel.setOpaque(true);
		basePanel.setVisible(true);
		
		return basePanel;
	}
	
	public JPanel setupBaseCenter(int baseIndex) {
		JPanel baseCenter = new JPanel();
		
		Dimension centerDimension = new Dimension(baseWidth - cellWidth * 2, baseHeight - cellHeight * 2);
		
		int positionX = (basePositions[baseIndex][0] + 1) * cellWidth;
		int positionY = (basePositions[baseIndex][1] + 1) * cellHeight;
		Point centerPosition = new Point(positionX, positionY);
		
		System.out.println(centerPosition.x + ", " + centerPosition.y);
		
		Border centerBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
		baseCenter.setBorder(centerBorder);
		baseCenter.setBackground(Color.LIGHT_GRAY);
		
		baseCenter.setSize(centerDimension);
		baseCenter.setLocation(centerPosition);
		baseCenter.setOpaque(true);
		baseCenter.setVisible(true);
		
		return baseCenter;
	}
	
	/*public JPanel setupBoard() {
		JPanel boardPanel = new JPanel();
		
		boardPanel.setLayout(new GridLayout(this.nCells, this.nCells));
		boardPanel.setOpaque(false);
		
		for(int row = 0; row < nCells; row++) {
			for(int column = 0; column < nCells; column++) {
				boardPanel.add(this.setupCell());
			}
		}
		
		return boardPanel;
	}
	
	public JLabel setupCell() {
		JLabel cellPanel = new JLabel();
		Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		cellPanel.setPreferredSize(new Dimension(cellWidth, cellHeight));
		cellPanel.setOpaque(true);
		cellPanel.setBackground(Color.decode("#593E1A"));
		cellPanel.setBorder(cellBorder);
        
        return cellPanel;
	}*/
	
	public void setupCells() {
		
	}
}
