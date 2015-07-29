package Exception;

import Helper.Location;

@SuppressWarnings("serial")
public class InvalidTurnException extends Exception
{
	private Location p1;
	private Location p2;
	private String isWhiteTurn;
	
	public InvalidTurnException(Location p1, Location p2, boolean isWhiteTurn)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.isWhiteTurn = isWhiteTurn ? "White" : "Black";
	}
	
	@Override
	public String getMessage()
	{
		return "Invalid move \"" + p1 + " " + p2 + "\" -- It is " +
				isWhiteTurn + "'s turn.  Skipping move...";
	}
}
