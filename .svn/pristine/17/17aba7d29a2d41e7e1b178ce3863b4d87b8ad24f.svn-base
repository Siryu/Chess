package Model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helper.Location;

public class Pawn extends Piece
{
	private boolean isEnPassant = false;
	
	public Pawn(boolean isBlack)
	{
		super(isBlack);
	}

	@Override
	public boolean checkIfMoveIsValid(Location difference)
	{
		boolean isValid = false;
		int color = isBlack ? 1 : -1;
			
		if((difference.getX() == 0 && difference.getY() == 1 * color) || (difference.getX() == 0 && difference.getY() == 2 * color && !this.getHasMoved()))
		{
			isValid = true;
		}
	
		return isValid;
	}
	
	@Override
	public boolean validCapture(Location difference)
	{
		boolean isValid = false;
		int color = isBlack ? 1 : -1;
			
		if((Math.abs(difference.getX()) == 1 && difference.getY() == 1 * color))
		{
			isValid = true;
		}
	
		return isValid;
	}

	@Override
	protected void setPiecePicture()
	{
		try
		{
			if(this.getColor())
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\blackPawn.png")));
			}
			else
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\whitePawn.png")));
			}
		}
		catch(IOException ex)
		{
			
		}
	}

	public boolean isEnPassant()
	{
		return isEnPassant;
	}

	public void setEnPassant(boolean isEnPassant)
	{
		this.isEnPassant = isEnPassant;
	}
}




// don't pass in abs values of numbers, pass in the actual difference, then you can figure it out... change to abs in each pieces class