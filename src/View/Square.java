package View;

import java.awt.Color;
import java.awt.Graphics;

public class Square
{
	private int locationX;
	private int locationY;
	private boolean isBlack;
	public static final Color BLACK = new Color(40, 60, 50, 255);
	
	public Square(int x, int y, boolean isBlack)
	{
		this.setLocationX(x);
		this.setLocationY(y);
		this.isBlack = isBlack;
	}
	
	protected int getLocationX()
	{
		return locationX;
	}
	protected void setLocationX(int locationX)
	{
		this.locationX = locationX;
	}
	protected int getLocationY()
	{
		return locationY;
	}
	protected void setLocationY(int locationY)
	{
		this.locationY = locationY;
	}
	protected boolean isBlack()
	{
		return isBlack;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(this.isBlack() ? BLACK : Color.WHITE);
		g.fillRect(this.getLocationX(), this.getLocationY(), ChessBoard.squareDimension, ChessBoard.squareDimension);
	}
	
	// returns what is needed to manipulate the board
	public String toString()
	{
		return  (char)(this.getLocationX() + 96) + "" + Math.abs(this.getLocationY() - 8);
	}
}
