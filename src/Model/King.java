package Model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helper.Location;

public class King extends Piece
{
	public King(boolean isBlack)
	{
		super(isBlack);
	}

	@Override
	public boolean checkIfMoveIsValid(Location difference)
	{
		if((Math.abs(difference.getX()) == 1 && Math.abs(difference.getY()) == 0) || (Math.abs(difference.getX()) == 0 && Math.abs(difference.getY()) == 1) || 
				(Math.abs(difference.getX()) == 1 && Math.abs(difference.getY()) == 1) || (Math.abs(difference.getX()) == 2 && difference.getY() == 0 &&
				this.hasMoved == false))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validCapture(Location difference)
	{
		if((Math.abs(difference.getX()) == 1 && Math.abs(difference.getY()) == 0) || (Math.abs(difference.getX()) == 0 && Math.abs(difference.getY()) == 1) || 
				(Math.abs(difference.getX()) == 1 && Math.abs(difference.getY()) == 1))
		{
			return true;
		}
		return false;
	}

	@Override
	protected void setPiecePicture()
	{
		try
		{
			if(this.getColor())
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\blackKing.png")));
			}
			else
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\whiteKing.png")));
			}
		}
		catch(IOException ex)
		{
			
		}
		
	}
}
