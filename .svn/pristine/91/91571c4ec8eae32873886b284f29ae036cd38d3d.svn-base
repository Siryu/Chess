package Model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helper.Location;

public class Knight extends Piece
{
	public Knight(boolean isBlack)
	{
		super(isBlack);
	}

	@Override
	public boolean checkIfMoveIsValid(Location difference)
	{
		if((Math.abs(difference.getX()) == 1 && Math.abs(difference.getY()) == 2) || (Math.abs(difference.getX()) == 2 && Math.abs(difference.getY()) == 1))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return isBlack ? "n".toLowerCase() : "n".toUpperCase();
	}

	@Override
	protected void setPiecePicture()
	{
		try
		{
			if(this.getColor())
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\blackKnight.png")));
			}
			else
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\whiteKnight.png")));
			}
		}
		catch(IOException ex)
		{
			
		}
		
	}
}
