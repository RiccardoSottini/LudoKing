import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Cell extends JPanel implements MouseListener  {
	private final Color[] colors = {
		Color.BLUE,
		Color.RED,
		Color.GREEN,
		Color.YELLOW,
		Color.WHITE
	};
	
	private final double[][][] endPositions = {
		{ {0.0, 1.0}, {1.0, 1.0}, {0.5, 0.0} },
		{ {0.0, 0.0}, {0.0, 1.0}, {1.0, 0.5} },
		{ {0.0, 0.0}, {1.0, 0.0}, {0.5, 1.0} },
		{ {1.0, 0.0}, {1.0, 1.0}, {0.0, 0.5} }
	};
	
	private final ArrayList<Pawn> pawns;
	private final CellColor cellColor;
	private final CellType cellType;
	
	//private JPanel cellPanel;
	private Dimension cellDimension;
	private Point cellPosition;
	
	public Cell(CellColor cellColor, CellType cellType) {
		this.pawns = new ArrayList<Pawn>();
		this.cellColor = cellColor;
		this.cellType = cellType;
		
		this.addMouseListener(this);
	}
	
	private Shape createTriangle() {
        Polygon polygon = new Polygon();
        int index = cellColor.ordinal();
        
        for(int p = 0; p < 3; p++) {
        	int positionX = (int) (cellDimension.width * endPositions[index][p][0]);
        	int positionY = (int) (cellDimension.height * endPositions[index][p][1]);
        	
        	polygon.addPoint(positionX, positionY);
        }
        
        return polygon;
    }
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(this.cellType == CellType.End) {
        	Graphics2D graphics2D = (Graphics2D) g;
        	Shape triangleShape = this.createTriangle();

        	graphics2D.setColor(colors[cellColor.ordinal()]);
        	graphics2D.fill(triangleShape);
        	
            int index = cellColor.ordinal();
            
        	for(int line = 0; line < 3; line++) {
        		int nextLine = (line + 1) % 3;
        		
        		int x1 = (int) (cellDimension.width * endPositions[index][line][0]);
        		int y1 = (int) (cellDimension.height * endPositions[index][line][1]);
        		int x2 = (int) (cellDimension.width * endPositions[index][nextLine][0]);
        		int y2 = (int) (cellDimension.height * endPositions[index][nextLine][1]);
        		
                graphics2D.setColor(Color.BLACK);
                graphics2D.setStroke(new BasicStroke(2));
        		graphics2D.drawLine(x1, y1, x2, y2);
        	}
        }
    }
	
	public void drawCell(Dimension cellDimension, Point cellPosition, JPanel boardPanel) {
		this.cellDimension = cellDimension;
		this.cellPosition = cellPosition;
		
		if(this.cellType == CellType.End) {
			int cellPositionX, cellPositionY;
			int cellWidth, cellHeight;
			
			if(this.cellColor == CellColor.Blue || this.cellColor == CellColor.Green) {
				cellPositionX = cellPosition.x - cellDimension.width;
				cellPositionY = (this.cellColor == CellColor.Blue) ? cellPosition.y - cellDimension.height / 2 : cellPosition.y;
				
				cellWidth = cellDimension.width * 3;
				cellHeight = (int) (cellDimension.height * 1.5);
			} else {
				cellPositionX = (this.cellColor == CellColor.Yellow) ? cellPosition.x - cellDimension.width / 2 : cellPosition.x;
				cellPositionY = cellPosition.y - cellDimension.height;
				
				cellWidth = (int) (cellDimension.width * 1.5);
				cellHeight = (int) (cellDimension.height * 3);
			}
			
			this.cellDimension = new Dimension(cellWidth, cellHeight);
			this.cellPosition = new Point(cellPositionX, cellPositionY);
			
			this.setOpaque(false);
		} else {
			Border cellBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
			this.setBorder(cellBorder);
			
			this.setBackground(colors[cellColor.ordinal()]);
			this.setOpaque(true);
		}
		
		this.setSize(this.cellDimension);
		this.setLocation(this.cellPosition);
		this.setVisible(true);
		
		boardPanel.add(this);
	}
	
	public boolean canKill() {
		return cellType == CellType.Open && cellColor == CellColor.White;
	}
	
	public void addPawn(Pawn pawn) {
		this.pawns.add(pawn);
	}
	
	public void removePawn(Pawn pawn) {
		for(int p = 0; p < this.pawns.size(); p++) {
			if(pawn == this.pawns.get(p)) {
				this.pawns.remove(p);
			}
		}
	}
	
	public void killPawns(Pawn selectedPawn) {
		if(this.canKill()) {
			for(int p = 0; p < this.pawns.size(); p++) {
				Pawn comparedPawn = this.pawns.get(p);
				
				if(comparedPawn.getPlayerCode() != selectedPawn.getPlayerCode()) {
					System.out.println("Pawn Killed: " + comparedPawn.getCode());
					comparedPawn.setDead();
					this.removePawn(comparedPawn);
				}
			}
		}
	}
	
	public ArrayList<Pawn> getPawns() {
		return this.pawns;
	}

	public ArrayList<String> getPawnCodes() {
		ArrayList<String> pawnCodes = new ArrayList<String>();
		
		for(Pawn pawn : this.pawns) {
			pawnCodes.add(pawn.getCode());
		}
		
		return pawnCodes;
	}
	
	public CellColor getColor() {
		return this.cellColor;
	}
	
	public CellType getType() {
		return this.cellType;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("CLICK ON CELL ");
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
