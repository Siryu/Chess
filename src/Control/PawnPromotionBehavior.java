package Control;

import Helper.Location;
import Model.Piece;

public class PawnPromotionBehavior extends BoardBehavior {
	
	private boolean isCapture;

	public PawnPromotionBehavior(Location p1, Location p2, Piece piece, boolean isCapture) {
		super(p1, p2, null, null, piece);
		this.isCapture = isCapture;
	}
	
	@Override
	public String toString() {
		return "Pawn at " + p1.toString() + " moved to " + p2.toString() + " and turned into a " + piece1.getClass().getSimpleName();
	}
	
	public boolean getCapture() {
		return isCapture;
	}

}
