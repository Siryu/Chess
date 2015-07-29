package Control;

import java.io.File;
import java.io.IOException;

public class ChessMasterOfChess
{
	public static void main(String[] args)
	{
		ChessManager cm = new ChessManager();
		File moveFile;
		
		if((args.length > 0))
		{
			try
			{	moveFile = new File(args[0]);
				cm.initSetup();
			} 
			catch (IOException e)
			{
				System.err.println("Error loading the supplied moves file! Loading default instead...");
				moveFile = new File("");
				try
				{
					cm.initSetup(moveFile);
				} 
				catch (IOException ex)
				{
					System.err.println("Error loading any files... extreme error!, closing program.....");
					ex.printStackTrace();
				}
			}			
		}
		else
		{
			moveFile = new File("");
			try
			{
				cm.initSetup();
			} 
			catch (IOException ex)
			{
				System.err.println("Error loading default setup... extreme error!, closing program.....");
				ex.printStackTrace();
			}
		}
	}
}
