package Model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helper.Location;


public class Rook extends Piece
{
	public Rook(boolean isBlack)
	{
		super(isBlack);
	}

	@Override
	public boolean checkIfMoveIsValid(Location difference)
	{
		// piece can only move a straight line either on x or y axis, no combination of the two.
		if((Math.abs(difference.getX()) > 0 && Math.abs(difference.getY()) == 0) || (Math.abs(difference.getX()) == 0 && Math.abs(difference.getY()) > 0))
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
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\blackRook.png")));
			}
			else
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\whiteRook.png")));
			}
		}
		catch(IOException ex)
		{
			
		}
	}
}
