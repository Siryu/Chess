package Model;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Helper.Location;

public abstract class Piece
{
	boolean isBlack;
	boolean hasMoved;
	private BufferedImage piecePicture;
	// put picture for piece here....
	
	public Piece(boolean isBlack)
	{
		this.isBlack = isBlack;
		this.hasMoved = false;
		this.setPiecePicture();
	}
	
	abstract protected void setPiecePicture();
	
	public boolean getColor()
	{
		return isBlack;
	}
	
	public boolean getHasMoved()
	{
		return this.hasMoved;
	}
	
	public void setMoved()
	{
		this.hasMoved = true;
	}
	
	public Image getPiecePicture()
	{
		return piecePicture;
	}

	public void setPiecePicture(BufferedImage piecePicture)
	{
		//piecePicture.s
		this.piecePicture = piecePicture;
	}

	public abstract boolean checkIfMoveIsValid(Location difference);
	
	public boolean validCapture(Location difference)
	{
		return checkIfMoveIsValid(difference);
	}
	
	/*
	public ArrayList<Location> getMoves(Location pieceSpot,	PieceCollection<Location, Piece> pieceCollection)
	{
		ArrayList<Location> goodMoves = new ArrayList<Location>();
		
		for(int y = ChessManager.HEIGHT; y > 0; y--)
		{
			for(int x = 1; x <= ChessManager.WIDTH; x++)
			{
				Location goodSpot = new Location(x, y);
				Location difference = pieceSpot.difference(goodSpot);
				Piece piece1 = pieceCollection.get(pieceSpot);
				Piece piece2 = pieceCollection.get(goodSpot);
				
				if((checkIfMoveIsValid(difference) && piece2 == null) || (piece2 != null && piece1.getColor() != piece2.getColor() && piece1.validCapture(difference)))
				{
					CheckIfWayIsClearBehavior ciwicb = new CheckIfWayIsClearBehavior();
					if(ciwicb.check(pieceSpot, goodSpot, pieceCollection))
					{
						goodMoves.add(goodSpot);
					}
				}
			}
		}
		return goodMoves;
	}
	*/
	
	public String toString()
	{
		StringBuilder rs = new StringBuilder();
		
		if(this.isBlack)
		{
			rs.append(this.getClass().getSimpleName().toLowerCase().substring(0, 1));
		}
		else
		{
			rs.append(this.getClass().getSimpleName().toUpperCase().substring(0, 1));
		}
		return rs.toString();
	}
}
