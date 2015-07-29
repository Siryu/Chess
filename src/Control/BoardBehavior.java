package Control;

import Helper.Location;
import Model.Piece;

public class BoardBehavior
{
	protected Location p1;
	protected Location p2;
	protected Location p3;
	protected Location p4;
	protected Piece piece1;
	
	public BoardBehavior(Location p1, Location p2, Location p3, Location p4, Piece piece1)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.piece1 = piece1;
	}
	
	
	public Location getP1()
	{
		return this.p1;
	}
	
	public Location getP2()
	{
		return this.p2;
	}
	
	public Location getP3()
	{
		return this.p3;
	}
	
	public Location getP4()
	{
		return this.p4;
	}
	
	public Piece getPiece1()
	{
		return this.piece1;
	}
	
	//public abstract void executeBehavior(Piece piece1, Piece piece2) throws InvalidMoveException;
	
	protected boolean checkTurn(Piece p1, boolean isWhitesTurn)
	{
		if(p1 != null && isWhitesTurn == !p1.getColor())
			return true;
		return false;
	}
}
