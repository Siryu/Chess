package Control;

import Helper.Location;

public class CastleBehavior extends BoardBehavior
{
	public CastleBehavior(Location p1, Location p2, Location rookStartPoint, Location rookEndPoint)
	{
		super(p1, p2, rookStartPoint, rookEndPoint, null);
	}
	
	@Override
	public String toString()
	{
		return "move the king from " + this.getP1().toString() + " to " + this.getP2().toString() + 
				" and the rook from " + this.getP3().toString() + " to " + this.getP4().toString();
	}
	
	/*
	@Override
	public void executeBehavior(PieceCollection<Location, Piece> pieces) throws InvalidMoveException
	{
		Piece kingPiece = pieces.get(this.getP1());
		Piece rookPiece = pieces.get(this.getRookStartPoint());
		
		// castling exclusion
		if((kingPiece instanceof King && rookPiece instanceof Rook) && kingPiece.getColor() == rookPiece.getColor() && 
				new CheckIfWayIsClearBehavior().check(this.getP1(), this.getRookStartPoint(), pieces) && !kingPiece.getHasMoved() && !rookPiece.getHasMoved())
		{		
			kingPiece.setMoved();
			pieces.put(p2, kingPiece);
			pieces.remove(p1);
			
			rookPiece.setMoved();
			pieces.put(rookEndPoint, rookPiece);
			pieces.remove(rookStartPoint);
		}
		
		else
		{
			throw new InvalidMoveException("Can't complete castling action");
		}
	}
	*/
}
