package Control;

import Helper.Location;

public class MoveBehavior extends BoardBehavior
{
	
	public MoveBehavior(Location p1, Location p2)
	{
		super(p1, p2, null, null, null);
	}
	
	@Override
	public String toString()
	{
		return "moves the piece on " + this.getP1().toString() + " to " + this.getP2().toString();
	}

	/*
	@Override
	public void executeBehavior(Piece piece1, Piece piece2) throws InvalidMoveException
	{
		Location difference = this.getP1().difference(this.getP2());
				
		if(piece1 != null && piece2 == null && piece1.checkIfMoveIsValid(difference) && new CheckIfWayIsClearBehavior().check(this.getP1(), this.getP2(), pieces))
		{
			piece1.setMoved();
			pieces.remove(p2);
			pieces.put(p2, piece1);
			pieces.remove(p1);
		}	
		else
		{
			throw new InvalidCommandException("Piece can't move like that or wrong format for move", this.getP1(), this.getP2());
		}
	}
	*/
}
