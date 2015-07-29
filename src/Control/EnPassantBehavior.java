package Control;

import Helper.Location;

public class EnPassantBehavior extends BoardBehavior
{

	public EnPassantBehavior(Location p1, Location p2)
	{
		super(p1, p2, null, null, null);
	}
	
	@Override
	public String toString()
	{
		return "En Passant's the piece on " + this.getP1().toString() + " to " + this.getP2().toString();
	}

}
