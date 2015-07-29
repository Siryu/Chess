package Model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helper.Location;


public class Queen extends Piece
{
	public Queen(boolean isBlack)
	{
		super(isBlack);
		
	}

	@Override
	public boolean checkIfMoveIsValid(Location difference)
	{
		if((Math.abs(difference.getX()) > 0 && Math.abs(difference.getY()) == 0) || (Math.abs(difference.getX()) == 0 && Math.abs(difference.getY()) > 0) ||
				(Math.abs(difference.getX()) == Math.abs(difference.getY())))
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
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\blackQueen.png")));
			}
			else
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\whiteQueen.png")));
			}
		}
		catch(IOException ex)
		{
			
		}
	}
}
