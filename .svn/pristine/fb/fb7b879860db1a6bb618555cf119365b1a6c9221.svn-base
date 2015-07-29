package Control;

import Helper.Location;
import Model.Piece;

public class CreateBehavior extends BoardBehavior
{	
	public CreateBehavior(Piece piece, char rank, int file)
	{
		super(new Location(rank, file), null, null, null, piece);
	}
		
	@Override
	public String toString()
	{
		StringBuilder sentence = new StringBuilder();
		sentence.append("places a ");	
		sentence.append(this.piece1.getColor() ? "black (dark) " : "white (light) "); 
		sentence.append(this.getPiece1().getClass().getSimpleName() + " on ");
		sentence.append("" + this.getP1());
		return sentence.toString();
	}

	/*
	@Override
	public void executeBehavior(Piece piece1, Piece piece2) throws InvalidCommandException
	{
		pieces.put(new Location(this.getX(), this.getY()), this.getPiece());		
	}
	*/

	@Override
	protected boolean checkTurn(Piece p1, boolean isWhitesTurn)
	{
			return true;
	}
}