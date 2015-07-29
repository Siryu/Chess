package View;

import java.awt.Dimension;

import javax.swing.JFrame;

import Control.ChessManager;
import Control.MouseControlClick;

public class ChessDisplayGUI
{
	ChessManager cm;
	ChessBoard board;
	
	
	public ChessDisplayGUI(ChessManager cm)
	{
		this.cm = cm;
		initComponents();
	}
	
	public void initComponents()
	{
		JFrame frame = new JFrame("Chess Master Of Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension((ChessBoard.squareDimension * ChessManager.WIDTH) + (ChessBoard.squareDimension * 2), 
				(ChessBoard.squareDimension * ChessManager.HEIGHT) + (int)(ChessBoard.squareDimension * 0.5f) + 35)); 
		//frame.setPreferredSize(new Dimension(815, 835));
		frame.setResizable(false);
		MouseControlClick mouse = new MouseControlClick();
		
		board = new ChessBoard(cm, mouse);
		//board.addMouseListener(new MouseControl());
		frame.add(board);
		
		frame.setVisible(true);
		frame.pack();
	}
	
	public void repaint()
	{
		board.repaint();
	}
}
