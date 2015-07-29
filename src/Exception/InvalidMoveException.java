package Exception;

import Helper.Location;

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception
{
	private Location p1;
	private Location p2;
	
	public InvalidMoveException(String error, Location p1, Location p2)
	{
		super(error);
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public InvalidMoveException(String error)
	{
		super(error);
	}
	
	@Override
	public String toString()
	{
		return super.getMessage() + " " + p1 + " to " + p2;
	}
}
