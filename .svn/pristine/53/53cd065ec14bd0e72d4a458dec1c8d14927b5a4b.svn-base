package Model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helper.Location;

public class Bishop extends Piece
{
	public Bishop(boolean isBlack)
	{
		super(isBlack);
	}

	@Override
	public boolean checkIfMoveIsValid(Location difference)
	{
		if(Math.abs(difference.getX()) == Math.abs(difference.getY()))
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
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\blackbishop.png")));
			}
			else
			{
				this.setPiecePicture(ImageIO.read(new File("piecePictures\\whiteBishop.png")));
			}
		}
		catch(IOException ex)
		{
			
		}
		
	}
}
