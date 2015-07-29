package Exception;

import Helper.Location;

@SuppressWarnings("serial")
public class InvalidCommandException extends InvalidMoveException
{
	public InvalidCommandException(String s, Location p1, Location p2)
	{
		super(s, p1, p2);
	}
}
