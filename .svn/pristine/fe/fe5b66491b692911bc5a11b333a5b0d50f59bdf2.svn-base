package Control;

import Helper.Location;

public class CaptureBehavior extends BoardBehavior
{
	public CaptureBehavior(Location p1, Location p2)
	{
		super(p1, p2, null, null, null);
	}

	@Override
	public String toString()
	{
		return "moves the piece on " + p1.toString() + " to " + p2.toString() +
				" and captures the piece on " + p2.toString();
	}
	
	/*
	@Override
	public void executeBehavior(Piece piece1, Piece piece2) throws InvalidMoveException
	{
		Location difference = this.getP1().difference(this.getP2());
				
		if((piece1 != null && piece2 != null) && piece1.getColor() != piece2.getColor() && piece1.validCapture(difference))
		{
			piece1.setMoved();
			pieces.remove(p2);
			pieces.put(p2, piece1);
			pieces.remove(p1);
		}	
	
		else
		{
			throw new InvalidMoveException("Can't capture your own piece", this.getP1(), this.getP2());
		}
	}
	*/
}
