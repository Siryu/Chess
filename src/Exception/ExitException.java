package Exception;

@SuppressWarnings("serial")
public class ExitException extends Exception
{
	public ExitException(boolean isWhitesTurn)
	{
		super((isWhitesTurn ? "White" : "Black") + " quit the game");
	}
}
